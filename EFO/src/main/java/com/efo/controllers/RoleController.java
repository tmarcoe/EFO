package com.efo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Role;
import com.efo.service.RoleService;

@Controller
@RequestMapping("/admin/")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	private final String pageLink = "/personnel/rolepaging";
	PagedListHolder<Role> listRoles;
	
	@RequestMapping("listroles")
	public String listRoles(Model model) {
		
		listRoles = roleService.retrieveList();
		
		model.addAttribute("objectList", listRoles);
		model.addAttribute("pagelink", pageLink);
		
		return "listroles";
	}
	
	@RequestMapping("addrole") 
	public String addRole(@ModelAttribute("role") String role) {
		Role r = new Role(role);
		
		roleService.create(r);
		
		return "redirect:/admin/listroles";
	}
	
	@RequestMapping(value = "rolepaging", method = RequestMethod.GET)
	public String handleRoleRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			listRoles.nextPage();
		} else if ("prev".equals(page)) {
			listRoles.previousPage();
		} else if (pgNum != -1) {
			listRoles.setPage(pgNum);
		}
		model.addAttribute("objectList", listRoles);
		model.addAttribute("pagelink", pageLink);

		return "listroles";
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
