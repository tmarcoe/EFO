package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Budget;
import com.efo.service.BudgetService;

@Controller
@RequestMapping("/accounting/")
public class BudgetApprovalController {
	
	@Autowired
	private BudgetService budgetService;
	
	private final String pageLink = "/accounting/approvepaging";
	private PagedListHolder<Budget> approveList;
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("listapprove")
	public String listApprove(Model model) {
		
		approveList = budgetService.listBudgetsForApproval();
		
		model.addAttribute("objectList", approveList);
		model.addAttribute("pagelink", pageLink);
		
		return "listapprove";
	}
	
	@RequestMapping("approvebudget") 
	public String approveBudget(@ModelAttribute("reference") Long reference ){
		
		budgetService.approveBudget(reference);
		
		return "redirect:/accounting/listapprove";
	}
	
	@RequestMapping(value = "approvepaging", method = RequestMethod.GET)
	public String handleApproveRequest(@PathVariable("parent") String parent, @ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			approveList.nextPage();
		} else if ("prev".equals(page)) {
			approveList.previousPage();
		} else if (pgNum != -1) {
			approveList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", approveList);
		model.addAttribute("pagelink", pageLink);

		return "listapprove";
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
