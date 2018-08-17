package com.efo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.component.SendEmail;
import com.efo.entity.User;
import com.efo.service.UserService;

@Controller
public class HomeController {

	@Value("${efo.admim.name}")
	private String adminName;
	
	@Value("${efo.admin.email}")
	private String adminEmail;
	
	@Autowired
	private SendEmail sendEmail;

	private final String format = "Dear %s,%n Please reset the password for email <b>%s</b> %n";
	@Autowired
	private UserService userService;

	@Value("${efo.formsPath}")
	private String formsPath;

	@Value("${efo.formRepository}")
	private String target;

	@RequestMapping("/")
	public String showHome(Model model, Principal principal){

		User user = null;
		if (principal != null) {
			user = userService.retrieve(principal.getName());
			if (user == null) {
				user = new User();
				user.setUser_id(0);
			}
		} else {
			user = new User();
			user.setUser_id(0);
		}
		model.addAttribute("user", user);

		return "home";
	}

	@RequestMapping("/access-denied")
	public String accessDenied() {

		return "access-denied";
	}

	@RequestMapping("/login")
	public String showLogin() {

		return "login";
	}
	
	@RequestMapping("/resetpassword")
	public String resetPassword(@ModelAttribute("username") String username) throws Exception {
		
		String content = String.format(format, adminName, username);
		
		sendEmail.sendMail(username, adminEmail, adminName, "Reset Password", content);
		
		return "redirect:/";		
	}



}
