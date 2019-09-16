package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.efo.entity.User;
import com.efo.service.BudgetService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/budget/")
public class BudgetController {
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private UserService userService;
	
	@Value("${efo.director.name}")
	private String directorName;
	
	@Value("${efo.director.email}")
	private String directorEmail;
	
	private final String pageLink = "/budget/budgetpaging";
	private PagedListHolder<Budget> budgetList;
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("listbudget")
	public String listBudget(Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		budgetList = budgetService.retrieveList(user.getEmployee().getDivision());
		
		model.addAttribute("objectList", budgetList);
		model.addAttribute("pagelink", pageLink);

		return "listbudget";
	}
	
	@RequestMapping("newbudget")
	public String newBudget(Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		Budget budget = new Budget();
		budget.setCreation(new Date());
		budget.setDepartment(user.getEmployee().getDivision());
		budget.setUser_id(user.getUser_id());
		budget.setAuthor(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());		
		model.addAttribute("budget", budget);
		
		return "newbudget";
	}
	
	@RequestMapping("addbudget")
	public String addBudget(@ModelAttribute("budget") Budget budget) {
		
		budgetService.create(budget);
		
		return "redirect:/budget/listbudget";
	}
	
	@RequestMapping("editbudget") 
	public String editBudget(@ModelAttribute("reference") Long reference, Model model) {
		
		model.addAttribute("budget", budgetService.retrieve(reference));
		
		return "editbudget";
	}
	
	@RequestMapping("updatebudget")
	public String updateBudget(@ModelAttribute("budget") Budget budget) {
		
		budgetService.update(budget);
	
		return "redirect:/budget/listbudget";
	}	

	@RequestMapping("submitbudget/{reference}")
	public String submitBudget(@PathVariable("reference") Long reference ) {
		
		budgetService.submitBudget(reference);
		
		return "redirect:/budget/listbudget";
	}

	@RequestMapping(value = "budgetpaging", method = RequestMethod.GET)
	public String handleBudgetRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);
		
		if ( budgetList == null) {
			return "redirect:/budget/listbudget";
		}

		if ("next".equals(page)) {
			budgetList.nextPage();
		} else if ("prev".equals(page)) {
			budgetList.previousPage();
		} else if (pgNum != -1) {
			budgetList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", budgetList);
		model.addAttribute("pagelink", pageLink);

		return "listbudget";
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
