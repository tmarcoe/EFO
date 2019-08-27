package com.efo.controllers;

import java.text.SimpleDateFormat;
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
import com.efo.entity.Customer;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.service.InvestorService;
import com.efo.service.RoleService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/personnel/")
public class InvestorController {
	
	@Autowired
	private InvestorService investorService;
	
	@Autowired
	private RoleUtilities roleUtils;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired 
	private UserService userService;
	
	private final String pageLink = "/personnel/investorpaging";

	private PagedListHolder<User> invList;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class));
	}
	
	@RequestMapping("investorlist")
	public String investorList(Model model) {
		invList = investorService.retrieveEditList();
		
		model.addAttribute("objectList", invList);
		model.addAttribute("pagelink", pageLink);

		return "investorlist";
	}
	
	@RequestMapping("newinvestor")
	public String newInvestor(Model model) {
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

		
		return "newinvestor";
	}
	
	@RequestMapping("addinvestor") 
	public String addInvestor(@Valid @ModelAttribute("User") User user, BindingResult result, Model model){
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
						
			model.addAttribute("roles", roleService.retrieveRawList());
			
			return "newcustomer";
		}
		
		if (result.hasErrors()) {
			
			return "newcustomer";
		}
		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		user.getInvestor().setUser(user);
		user.getCommon().setUser(user);
		
		userService.create(user);
		
		return "redirect:/personnel/investorlist";
	}
	
	@RequestMapping("editinvestor")
	public String editInvestor(@ModelAttribute("user_id") Long user_id, Model model) {
		User user = userService.retrieve(user_id);
		user.setRoleString(roleUtils.roleToString(user.getRoles()));

		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("user", user);
		
		return "editinvestor";
	}
	
	@RequestMapping("updateinvestor")
	public String updateInvestor(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if (result.getFieldErrorCount() > 2) {
			
			return "editinvestor";
		}

		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		user.getInvestor().setUser(user);
		user.getCommon().setUser(user);
		
		userService.merge(user);

		return "redirect:/personnel/investorlist";
	}

	@RequestMapping(value = "investorpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			invList.nextPage();
		} else if ("prev".equals(page)) {
			invList.previousPage();
		} else if (pgNum != -1) {
			invList.setPage(pgNum);
		}
		model.addAttribute("objectList", invList);
		model.addAttribute("pagelink", pageLink);

		return "investorlist";
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
