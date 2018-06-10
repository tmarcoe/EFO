package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.component.RoleUtilities;
import com.efo.entity.CommonFields;
import com.efo.entity.EmpFinancial;
import com.efo.entity.Employee;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.service.EmployeeService;
import com.efo.service.RoleService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/admin/")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	RoleUtilities roleUtils;

	private final String pageLink = "/admin/empolyeepaging";

	private PagedListHolder<User> employeeList;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class));
	}
	
	@RequestMapping("employeelist")
	public String employeeList(Model model) {
		
		employeeList = employeeService.retrieveEditList();
		
		
		model.addAttribute("objectList", employeeList);
		model.addAttribute("pagelink", pageLink);
		
		return "employeelist";
	}
	
	@RequestMapping("newemployee")
	public String newEmployee(Model model) {
		Calendar cal = Calendar.getInstance();
		cal.set(2100, 11, 31);
		User user = new User();

		user.setRoles(new HashSet<Role>());
		user.getRoles().add(roleService.retrieve("USER"));
		
		user.setRoleString(roleUtils.roleToString(user.getRoles()));
		
		user.setCommon(new CommonFields());
		Employee employee = new Employee();
		employee.setEmp_financial(new EmpFinancial());

		employee.setStart_date(new Date());
		employee.setEnd_date(cal.getTime());
		user.setEmployee(employee);
		
		model.addAttribute("roles", roleService.retrieveList());
		model.addAttribute("user", user);
		
		return "newemployee";
	}
	
	@RequestMapping("addemployee")
	public String addEmployee(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
						
			model.addAttribute("roles", roleService.retrieveList());
			
			return "newemployee";
		}
		
		if (result.getFieldErrorCount() > 2) {

			return "newemployee";
		}
	
		user.setTemp_pw(true);
		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		
		user.getEmployee().setUser(user);
		user.getEmployee().getEmp_financial().setEmployee(user.getEmployee());
		user.getCommon().setUser(user);
		
		userService.create(user);
		
		return "redirect:/admin/employeelist";
	}
	
	@RequestMapping("editemployee")
	public String editEmployee(@ModelAttribute("user_id") int user_id, Model model) {
		User user = userService.retrieve(user_id);
		
		user.setRoleString(roleUtils.roleToString(user.getRoles()));
		
		model.addAttribute("roles", roleService.retrieveList());
		model.addAttribute("user", user);
		
		return "editemployee";
	}
	
	@RequestMapping("updemployee")
	public String updateEmployee(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		if (result.getFieldErrorCount() > 2) {

			return "editemployee";
		}

		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		
		user.getEmployee().setUser(user);
		user.getEmployee().getEmp_financial().setEmployee(user.getEmployee());
		user.getCommon().setUser(user);
		
		userService.merge(user);
		
		return "redirect:/admin/employeelist";
	}
	
	@RequestMapping(value = "employeepaging", method = RequestMethod.GET)
	public String handleEmployeePaging(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			employeeList.nextPage();
		} else if ("prev".equals(page)) {
			employeeList.previousPage();
		} else if (pgNum != -1) {
			employeeList.setPage(pgNum);
		}
		model.addAttribute("objectList", employeeList);
		model.addAttribute("pagelink", pageLink);

		return "listemployees";
	}

	/**************************************************************************************************************************************
	 * Used for both detecting a number, and converting to a number. If this
	 * routine returns a -1, the input parameter was not a number.
	 * 
	 **************************************************************************************************************************************/

	private int isInteger(String s) {
		int retInt;
		try {
			retInt = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
		// only got here if we didn't return false
		return retInt;
	}

	
	
}
