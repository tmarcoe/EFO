package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

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

import com.efo.component.RoleUtilities;
import com.efo.component.SendEmail;
import com.efo.entity.CommonFields;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.entity.Vendor;
import com.efo.service.RoleService;
import com.efo.service.UserService;
import com.efo.service.VendorService;

@Controller
@RequestMapping("/personnel/")
public class VendorController {
	
	@Value("${spring.mail.username}")
	private String userName;
	
	private final String format = "Dear %s,%n Your new, temporary password is %s.%n"
			+ "Please change it as soon as possible to avoid any sercurity breaches.";

	@Autowired
	private SendEmail sendEmail;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	RoleUtilities roleUtils;
	
	@Autowired
	RoleService roleService;
	

	private final String pageLink = "/personnel/vendorpaging";

	private PagedListHolder<User> vendorList;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class));
	}

	@RequestMapping("vendorlist")
	public String customerList(Model model) {
		vendorList = vendorService.retrieveEditList();
		vendorList.setPageSize(20);
		
		model.addAttribute("objectList", vendorList);
		model.addAttribute("pagelink", pageLink);

		return "vendorlist";
	}
	
	@RequestMapping("editvendor")
	public String editVendor(@ModelAttribute("user_id") Long user_id, Model model) {
		User user = userService.retrieve(user_id);
		user.setRoleString(roleUtils.roleToString(user.getRoles()));

		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("user", user);

		return "editvendor";
	}
	
	@RequestMapping("updatevendor")
	public String updateVendor(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.getFieldErrorCount() > 2) {
			
			return "editvendor";
		}

		user.setRoles(roleUtils.stringToRole(user.getRoleString()));

		user.getCommon().setUser(user);
		user.getVendor().setUser(user);
		userService.merge(user);
		
		return "redirect:/personnel/vendorlist";
	}
	
	@RequestMapping("newvendor")
	public String newVendor(Model model) {
		User user = new User();
		user.setRoles(new HashSet<Role>());
		user.getRoles().add(roleService.retrieve("USER"));
		
		user.setRoleString(roleUtils.roleToString(user.getRoles()));
		user.setCommon(new CommonFields());
		
		user.setVendor(new Vendor());
		
		model.addAttribute("roles", roleService.retrieveRawList());
		model.addAttribute("user", user);

		return "newvendor";
	}
	
	@RequestMapping("addvendor")
	public String addVendor(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) throws Exception {
		
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
						
			model.addAttribute("roles", roleService.retrieveRawList());
			
			return "newvendor";
		}
		
		if (result.hasErrors()) {
			
			return "newvendor";
		}
		
		user.setRoles(roleUtils.stringToRole(user.getRoleString()));
		user.getCommon().setUser(user);
		user.getVendor().setUser(user);
		
		if (user.isEnabled() == true) {
			user.setTemp_pw(true);
			String content = String.format(format, user.getVendor().getFirstname(), user.getPassword());
			sendEmail.sendMail(userName, user.getUsername(), user.getVendor().getFirstname(), "New Password", content);
		}
		userService.create(user);
		
		return "redirect:/personnel/vendorlist";
	}
	
	@RequestMapping(value = "vendorpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			vendorList.nextPage();
		} else if ("prev".equals(page)) {
			vendorList.previousPage();
		} else if (pgNum != -1) {
			vendorList.setPage(pgNum);
		}
		model.addAttribute("objectList", vendorList);
		model.addAttribute("pagelink", pageLink);

		return "vendorlist";
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
