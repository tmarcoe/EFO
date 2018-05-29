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

import com.efo.entity.PettyCash;
import com.efo.service.PettyCashService;

@Controller
@RequestMapping("/accounting/")
public class PettyCashController {
	private final String pageLink = "/accounting/pettycashpaging";

	@Autowired
	PettyCashService pettyCashService;

	PagedListHolder<PettyCash> pcList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("listpettycash")
	public String listPettyCash(Model model) {
		
		pcList = pettyCashService.retrieveList();
		
		model.addAttribute("objectList", pcList);
		model.addAttribute("pagelink", pageLink);
		
		return "listpettycash";
	}
	
	@RequestMapping("newpettycash")
	public String newPettyCash(Model model) {
		
		model.addAttribute("pettyCash", new PettyCash(new Date()));
		
		return "newpettycash";
	}
	
	@RequestMapping("addpettycash")
	public String addPettyCash(@Valid @ModelAttribute("pettyCash") PettyCash pettyCash, BindingResult result) {
		
		if (result.hasErrors()) {
			return "newpettycash";
		}
		
		pettyCashService.create(pettyCash);
		
		return "redirect:/accounting/listpettycash";
	}
	
	@RequestMapping("editpettycash")
	public String editPettyCash(@ModelAttribute("id") int id, Model model) {
		
		PettyCash pettyCash = pettyCashService.retrieve(id);
		
		model.addAttribute("pettyCash", pettyCash);
		
		return "editpettycash";
	}
	
	@RequestMapping("updatepettycash")
	public String updatePettyCash(@Valid @ModelAttribute("pettyCash") PettyCash pettyCash, BindingResult result) {
		
		pettyCashService.update(pettyCash);
		
		return "redirect:/accounting/listpettycash";
	}
	
	@RequestMapping(value = "pettycashpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			pcList.nextPage();
		} else if ("prev".equals(page)) {
			pcList.previousPage();
		} else if (pgNum != -1) {
			pcList.setPage(pgNum);
		}
		model.addAttribute("objectList", pcList);
		model.addAttribute("pagelink", pageLink);

		return "listcustomers";
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
