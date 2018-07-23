package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.entity.User;
import com.efo.service.UserService;


@Controller
@RequestMapping("/admin/")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	BCryptPasswordEncoder encoder;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("deleteuser")
	public String deleteUser(@ModelAttribute("user_id") int user_id) {
		userService.delete(user_id);

		return "redirect:/";
	}
	
	@RequestMapping("assignpassword")
	public String assignPassword(@ModelAttribute("user_id") int user_id, Model model) {
		
		User user = userService.retrieve(user_id);
		user.setPassword("");
		
		model.addAttribute("user", user);
		
		return "assignpassword";
	}
	
	@RequestMapping("savepassword")
	public String savePassword(@ModelAttribute("user") User user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		userService.updatePassword(user);
		
		return "redirect:/";
	}
	
}
