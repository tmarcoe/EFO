package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.efo.entity.Customer;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.service.CustomerService;
import com.efo.service.RoleService;
import com.efo.service.UserService;


@Controller
@RequestMapping("/admin/")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	RoleUtilities roleUtils;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	private final String pageLink = "/admin/customerpaging";

	private PagedListHolder<User> customerList;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("customerlist")
	public String customerList(Model model) {
		customerList = customerService.retrieveEditList();
		
		customerList.setPageSize(20);
		model.addAttribute("objectList", customerList);
		model.addAttribute("pagelink", pageLink);

		return "customerlist";
	}
	
	@RequestMapping("editcustomer")
	public String editCustomer(@ModelAttribute("user_id") int user_id, Model model) {
		User user = userService.retrieve(user_id);
		user.setRoleString(roleUtils.roleToString(user.getRoles()));

		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("user", user);
		
		return "editcustomer";
	}
	
	@RequestMapping("updatecustomer")
	public String updateCustomer(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		if (result.getFieldErrorCount() > 2) {
			
			return "editcustomer";
		}

		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		user.getCustomer().setUser(user);
		user.getCommon().setUser(user);
		
		userService.merge(user);
		
		return "redirect:/admin/customerlist";
	}
	
	@RequestMapping("newcustomer")
	public String createCustomer( Model model) {
		User user = new User();
		Customer customer = new Customer();
		customer.setSince(new Date());

		user.setRoles(new HashSet<Role>());
		user.getRoles().add(roleService.retrieve("USER"));
		
		user.setRoleString(roleUtils.roleToString(user.getRoles()));
		user.setCommon(new CommonFields());
		user.setCustomer(customer);
		
		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("user", user);
		
		return "newcustomer";
	}
	
	@RequestMapping("addcustomer")
	public String addCustomer(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
						
			model.addAttribute("roles", roleService.retrieveRawList());
			
			return "newcustomer";
		}
		
		if (result.getFieldErrorCount() > 2) {
			
			return "newcustomer";
		}
		user.setRoles(roleUtils.stringToRole(user.getRoleString()));

		user.setTemp_pw(true);
		user.getCustomer().setUser(user);
		user.getCommon().setUser(user);
		userService.create(user);
		
		return "redirect:/admin/customerlist";
	}
	
	@RequestMapping("choosecustomer")
	public String chooseCustomer(Model model) {
		customerList = customerService.retrieveEditList();
		
		model.addAttribute("objectList", customerList);
		model.addAttribute("pagelink", pageLink);

		return "choosecustomer";
	}
	

	@RequestMapping(value = "customerpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			customerList.nextPage();
		} else if ("prev".equals(page)) {
			customerList.previousPage();
		} else if (pgNum != -1) {
			customerList.setPage(pgNum);
		}
		model.addAttribute("objectList", customerList);
		model.addAttribute("pagelink", pageLink);

		return "customerlist";
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
