package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.efo.entity.Profiles;
import com.efo.service.ProfilesService;


@Controller
@RequestMapping("/basic/")
public class ProfilesController {
	
	@Autowired
	private ProfilesService profilesService;
	
	private final String pageLink = "/basic/profilespaging";
	private PagedListHolder<Profiles> profilesList;
	private SimpleDateFormat dateFormat;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("profileslist")
	public String profilesList(Model model) {
		
		profilesList = profilesService.RetrieveList();
		
		profilesList.setPage(0);
		profilesList.setPageSize(35);
		model.addAttribute("objectList", profilesList);
		model.addAttribute("pagelink", pageLink);

		return "profileslist";
	}
	
	@RequestMapping("newprofile")
	public String newProfile(Model model) {
		
		Profiles profile = new Profiles();
		profile.setCreated(new Date());
		
		model.addAttribute("profile", profile);
		
		return "newprofile";
	}
	
	@RequestMapping("addprofile")
	public String addProfile(@Valid @ModelAttribute("profile") Profiles profile, BindingResult result) {
		
		profilesService.create(profile);
		
		return "redirect:/basic/profileslist";
	}
	
	@RequestMapping("editprofile")
	public String editProfile(@ModelAttribute("name") String name, Model model) {
		
		Profiles profile = profilesService.retrieve(name);
		
		model.addAttribute("profile", profile);
		
		return "editprofile";
	}
	
	@RequestMapping("updateprofile")
	public String updateProfile(@Valid @ModelAttribute("profile") Profiles profile, BindingResult result) {
		
		profilesService.merge(profile);
		
		return "redirect:/basic/profileslist";
	}

	@RequestMapping(value = "profilespaging", method = RequestMethod.GET)
	public String handleBudgeItemtRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			profilesList.nextPage();
		} else if ("prev".equals(page)) {
			profilesList.previousPage();
		} else if (pgNum != -1) {
			profilesList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", profilesList);
		model.addAttribute("pagelink", pageLink);

		return "profileslist";
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
