package com.efo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.efo.entity.User;
import com.efo.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	BCryptPasswordEncoder encoder;
	
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

}
