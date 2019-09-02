package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.component.SendEmail;
import com.efo.entity.User;
import com.efo.service.UserService;


@Controller
@RequestMapping("/personnel/")
public class UserController {
	
	@Value("${spring.mail.username}")
	private String userName;
	
	private final String format = "Dear %s,%n Your new, temporary password is <b>%s</b>.%n"
			+ "Please change it as soon as possible to avoid any sercurity breaches.";

	@Autowired
	private UserService userService;

	@Autowired
	BCryptPasswordEncoder encoder;

	private SimpleDateFormat dateFormat;
	
	@Autowired
	private SendEmail sendEmail;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("deleteuser")
	public String deleteUser(@ModelAttribute("user_id") Long user_id) {

		User user = userService.retrieve(user_id);
		userService.delete(user_id);
		if (user.getCustomer() != null) {
			return "redirect:/personnel/customerlist";
		}else if (user.getEmployee() != null) {
			return "redirect:/personnel/employeelist";
		}else if (user.getInvestor() != null) {
			return "redirect:/personnel/investorlist";
		}else if (user.getVendor() != null ) {
			return "redirect:/personnel/vendorlist";
		}

		return "redirect:/";
	}
	
	@RequestMapping("assignpassword")
	public String assignPassword(@ModelAttribute("user_id") Long user_id, Model model) {
		
		User user = userService.retrieve(user_id);
		user.setPassword("");
		
		model.addAttribute("user", user);
		
		return "assignpassword";
	}
	
	@RequestMapping("savepassword")
	public String savePassword(@ModelAttribute("user") User user) throws Exception {
		String name = "Sir/Maam";
		if (user.getEmployee() != null) {
			name = user.getEmployee().getFirstname();
		}else if (user.getCustomer() != null) {
			name = user.getCustomer().getFirstname();
		}else if (user.getVendor() != null) {
			name = user.getVendor().getFirstname();
		}
		String content = String.format(format, name, user.getPassword());
		sendEmail.sendHtmlMail(userName, user.getUsername(), name, "Password Change", content);
		
		user.setPassword(encoder.encode(user.getPassword()));
		userService.updatePassword(user);
		
		return "redirect:/";
	}
	
}
