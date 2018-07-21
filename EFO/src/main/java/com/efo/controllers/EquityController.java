package com.efo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Equity;
import com.efo.service.EquityService;
import com.efo.service.FetalTransactionService;

@Controller
@RequestMapping("/accounting/")
public class EquityController {
	private final String pageLink = "/accounting/equitypaging";
	private PagedListHolder<Equity> equityList;
	
	@Autowired
	private EquityService equityService;
	
	@Autowired
	private FetalTransactionService transactionService;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("equitylist")
	public String equityList(Model model) {
		
		equityList = equityService.retrieveList();
		
		model.addAttribute("objectList", equityList);
		model.addAttribute("pagelink", pageLink);
		
		return "equitylist";
	}
	
	@RequestMapping("newequity")
	public String newEquity(Model model) {
		
		model.addAttribute("equity", new Equity(new Date()));
		
		return "newequity";
	}
	
	@RequestMapping("addequity")
	public String addEquity(@Valid @ModelAttribute("equity") Equity equity) throws IOException {
		transactionService.addEquity(equity);
		
		return "redirect:/accounting/equitylist";
	}
	
	@RequestMapping("editequity")
	public String editEquity(@ModelAttribute("id") Long id, Model model) {
		
		model.addAttribute("equity", equityService.retrieve(id));
		
		return "editequity";
	}
	
	@RequestMapping("updateequity") 
	public String updateEquity(@ModelAttribute("equity") Equity equity) {
		
		equityService.update(equity);
		
		return "redirect:/accounting/equitylist";
	}
	
	
	@RequestMapping(value = "equitypaging", method = RequestMethod.GET)
	public String handleEquityRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			equityList.nextPage();
		} else if ("prev".equals(page)) {
			equityList.previousPage();
		} else if (pgNum != -1) {
			equityList.setPage(pgNum);
		}
		model.addAttribute("objectList", equityList);
		model.addAttribute("pagelink", pageLink);

		return "stockholderlist";
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
