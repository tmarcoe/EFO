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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.OverheadExpenses;
import com.efo.service.FetalTransactionService;
import com.efo.service.OverheadExpensesService;

@Controller
@RequestMapping("/accounting/")
public class OverheadExpensesController {
	
	private final String pageLink = "/accounting/overheadpaging";
	private PagedListHolder<OverheadExpenses> overheadList;
	
	@Autowired
	private OverheadExpensesService expensesService;
	
	@Autowired
	private FetalTransactionService transactionService;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping("listoverhead")
	public String listOverhead(Model model) {
		overheadList = expensesService.retrieveList();
		
		model.addAttribute("objectList", overheadList);
		model.addAttribute("pagelink", pageLink);
		
		return "listoverhead";
	}
	
	@RequestMapping("newexpense")
	public String newExpenses(Model model) {
		
		model.addAttribute("expense", new OverheadExpenses(new Date()));
		
		return "newexpense";
	}
	
	@RequestMapping("editexpense")
	public String editExpense(@ModelAttribute("reference") Long reference, Model model) {
		
		OverheadExpenses expense = expensesService.retrieve(reference);
		
		model.addAttribute("expense", expense);
		
		return "editexpense";
	}
	
	@RequestMapping("updateexpense")
	public String updateExpense(@Valid @ModelAttribute("expense") OverheadExpenses expenses, BindingResult result) {
		
		expensesService.merge(expenses);
		
		return "redirect:/accounting/listoverhead";
	}
	
	@RequestMapping("deleteexpense")
	public String deleteExpense() {
		
		
		return "redirect:/accounting/listoverhead";
	}
	@RequestMapping("addexpense")
	public String addExpense(@Valid @ModelAttribute("expense") OverheadExpenses expenses, BindingResult result) throws IOException {
		
		expensesService.create(expenses);
		transactionService.newOverheadExpense(expenses);
		
		return "redirect:/accounting/listoverhead";
	}
	@RequestMapping(value = "overheadpaging", method = RequestMethod.GET)
	public String handleAssetsRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			overheadList.nextPage();
		} else if ("prev".equals(page)) {
			overheadList.previousPage();
		} else if (pgNum != -1) {
			overheadList.setPage(pgNum);
		}
		model.addAttribute("objectList", overheadList);
		model.addAttribute("pagelink", pageLink);

		return "listoverhead";
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
