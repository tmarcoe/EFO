package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.efo.component.AccountUtilities;
import com.efo.component.RoleUtilities;
import com.efo.component.SendEmail;
import com.efo.entity.CommonFields;
import com.efo.entity.EmpFinancial;
import com.efo.entity.Employee;
import com.efo.entity.Role;
import com.efo.entity.TimeReportingAccounts;
import com.efo.entity.User;
import com.efo.service.EmployeeService;
import com.efo.service.RoleService;
import com.efo.service.TimeReportingAccountsService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/personnel/")
public class EmployeeController {
	
	@Value("${spring.mail.username}")
	private String userName;
	
	@Value("${efo.departments}")
	private String departments;

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	private SendEmail sendEmail;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleUtilities roleUtils;
	
	@Autowired
	private TimeReportingAccountsService timeReportingAccountsService;
	
	@Autowired
	private AccountUtilities accountUtils;

	private final String pageLink = "/personnel/employeepaging";
	
	private final String format = "Dear %s,%n Your new, temporary password is %s.%n"
								+ "Please change it as soon as possible to avoid any sercurity breaches.";

	private PagedListHolder<User> employeeList;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class));
	}
	
	@RequestMapping("employeelist")
	public String employeeList(Model model) {
		
		employeeList = employeeService.retrieveEditList();
		
		employeeList.setPageSize(20);
		employeeList.setPage(0);
		
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
		EmpFinancial financial = new EmpFinancial();
		financial.setAccount_num("");
		financial.setFed_trans("");
		financial.setSt_trans("");
		financial.setCity_trans("");
		financial.setExemptions(1L);
		financial.setMedical(0.0);
		financial.setUnion_dues(0.0);
		financial.setGarnishment(0.0);
		financial.setRetirement(0.0);
		employee.setEmp_financial(financial);

		employee.setStart_date(new Date());
		employee.setEnd_date(cal.getTime());
		user.setEmployee(employee);
		
		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("departments", new ArrayList<String>(Arrays.asList(departments.split(","))));
		model.addAttribute("user", user);
		
		return "newemployee";
	}
	
	@RequestMapping("addemployee")
	public String addEmployee(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) throws Exception {
		
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
						
			model.addAttribute("roles", roleService.retrieveRawList());
			
			return "newemployee";
		}
		
		if (result.hasErrors()) {

			return "newemployee";
		}
	

		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		user.getEmployee().setUser(user);
		user.getEmployee().getEmp_financial().setEmployee(user.getEmployee());
		user.getCommon().setUser(user);
		
		if (user.isEnabled() == true) {
			user.setTemp_pw(true);
			String content = String.format(format, user.getEmployee().getFirstname(), user.getPassword());
			sendEmail.sendMail(userName, user.getUsername(), user.getEmployee().getFirstname(), "New Password", content);
		}
		userService.create(user);
		
		return "redirect:/personnel/employeelist";
	}
	
	@RequestMapping("editemployee")
	public String editEmployee(@ModelAttribute("user_id") Long user_id, Model model) {
		User user = userService.retrieve(user_id);
		
		user.setRoleString(roleUtils.roleToString(user.getRoles()));
		user.getEmployee().setAccountString(accountUtils.accountsToString(user.getEmployee().getTimeReportingAccounts()));
		
		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("departments", new ArrayList<String>(Arrays.asList(departments.split(","))));
		model.addAttribute("user", user);
		
		return "editemployee";
	}
	
	@RequestMapping("updemployee")
	public String updateEmployee(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		if (result.getFieldErrorCount() > 2) {

			return "editemployee";
		}

		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		user.getEmployee().setTimeReportingAccounts(accountUtils.stringToAccounts(user.getEmployee().getAccountString()));
		user.getEmployee().setUser(user);
		user.getEmployee().getEmp_financial().setEmployee(user.getEmployee());
		user.getCommon().setUser(user);
		
		userService.merge(user);
		
		return "redirect:/personnel/employeelist";
	}
	
	@RequestMapping("assigntsacccounts")
	public String assignTimeSheetAccounts(@ModelAttribute("user_id") Long user_id, Model model) {
		
		Employee employee = employeeService.retrieve(user_id);
		employee.setAccountString(accountUtils.accountsToString(employee.getTimeReportingAccounts()));
		
		List<TimeReportingAccounts> accounts = timeReportingAccountsService.retrieveRawList(employee.getDivision());
		
		model.addAttribute("accounts", accounts);
		model.addAttribute("employee", employee);
		
		
		return "assigntsacccounts";
	}
		
	
	@RequestMapping("updateassignedaccounts")
	public String updateAssignedAccounts(@ModelAttribute("employee") Employee employee ) {
		
		User user = userService.retrieve(employee.getUser_id());
				
			employee.setTimeReportingAccounts(accountUtils.stringToAccounts(employee.getAccountString()));
			employee.setUser(user);
			user.setEmployee(employee);
		
			userService.merge(user);
		
		return "redirect:/personnel/employeelist";
	}
	
	@RequestMapping(value = "employeepaging", method = RequestMethod.GET)
	public String handleEmployeePaging(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);
		if (employeeList == null) {
			return "redirect:/personnel/employeelist";
		}

		if ("next".equals(page)) {
			employeeList.nextPage();
		} else if ("prev".equals(page)) {
			employeeList.previousPage();
		} else if (pgNum != -1) {
			employeeList.setPage(pgNum);
		}
		model.addAttribute("objectList", employeeList);
		model.addAttribute("pagelink", pageLink);

		return "employeelist";
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
