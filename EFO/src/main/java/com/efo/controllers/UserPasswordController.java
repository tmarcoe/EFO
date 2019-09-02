package com.efo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.entity.User;
import com.efo.service.UserService;


@Controller
@RequestMapping("/user/")
public class UserPasswordController {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@RequestMapping("savepassword")
	public String savePassword(@ModelAttribute("user") User user, Principal principal) throws Exception {

		User mergeUser = userService.retrieve(principal.getName());
		
		mergeUser.setPassword(encoder.encode(user.getPassword()));
		mergeUser.setTemp_pw(false);
		
		userService.merge(mergeUser);
		
		return "redirect:/";
	}
	



}
