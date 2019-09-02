package com.efo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	@RequestMapping("shutdown")
	public String shutdown() {
		
		System.exit(0);
		
		return "";
	}

}
