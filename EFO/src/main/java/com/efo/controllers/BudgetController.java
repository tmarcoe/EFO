package com.efo.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.BudgetItems;
import com.efo.entity.User;
import com.efo.service.BudgetItemsService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/accounting/")
public class BudgetController {
	@Autowired
	private BudgetItemsService budgetService;
	
	@Autowired
	private UserService userService;
	
	private final String pageLink = "/accounting/budgetpaging/%s";
	private PagedListHolder<BudgetItems> budgetList;
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("listbudget/{parent}")
	public String listBudget(@PathVariable("parent") String parent, Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		budgetList = budgetService.retrieveList(parent, user.getUser_id());
		
		budgetList.setPageSize(20);
		
		model.addAttribute("parent", parent);
		model.addAttribute("objectList", budgetList);
		model.addAttribute("pagelink", String.format(pageLink, parent));

		return "listbudget";
	}
	
	@RequestMapping("uponelevel/{parent}")
	public String upOneLevel(@PathVariable("parent") String parent, Model model) {
		if ("ROOT".compareTo(parent) != 0) {
			BudgetItems budget = budgetService.retrieveByCategory(parent);
			parent = budget.getParent();
		}
		
		return "redirect:/accounting/listbudget/" + parent;
	}
	
	@RequestMapping("newbudgetitem/{parent}")
	public String newBudgetItem(@PathVariable("parent") String parent, Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		BudgetItems budget = new BudgetItems();
		budget.setParent(parent);
		budget.setUser_id(user.getUser_id());
		budget.setCreation_date(new Date());
		budget.setDepartment(user.getEmployee().getDivision());

		model.addAttribute("budget", budget);
		
		return "newbudgetitem";
	}
	
	@RequestMapping("addbudgetitem")
	public String addBudget(@Valid @ModelAttribute("budget") BudgetItems budget, BindingResult result) {
		if (result.hasErrors()) {
			return "newbudgetitem";
		}
		
		if (budgetService.categoryExists(budget.getCategory())) {
			
			result.reject("category", "Exists.budget.category");
			
			return "newbudgetitem";
		}
		budgetService.create(budget);
		
		return "redirect:/accounting/listbudget/" + budget.getParent();
	}

	@RequestMapping("editbudgetitem")
	public String editBudgetItem(@ModelAttribute("id") Long id, Model model) {
		
		model.addAttribute("budget", budgetService.retrieve(id));
		
		return "editbudgetitem";
	}
	
	@RequestMapping("updatebudgetitem")
	public String updateBudgetItem(@Valid @ModelAttribute("budget") BudgetItems budget, BindingResult result) {
		
		budgetService.update(budget);
		
		return "redirect:/accounting/listbudget/" + budget.getParent();
	}
	
	@RequestMapping("submitbudget") 
	public String submitBudget(Principal principal) {
		User user = userService.retrieve(principal.getName());
		budgetService.submitBudget(user.getEmployee().getDivision());
		
		return "redirect:/accounting/listbudget/ROOT";
	}
	
	@RequestMapping(value = "budgetpaging/{parent}", method = RequestMethod.GET)
	public String handleBudgetRequest(@PathVariable("parent") String parent, @ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			budgetList.nextPage();
		} else if ("prev".equals(page)) {
			budgetList.previousPage();
		} else if (pgNum != -1) {
			budgetList.setPage(pgNum);
		}
		
		model.addAttribute("parent", parent);
		model.addAttribute("objectList", budgetList);
		model.addAttribute("pagelink", String.format(pageLink, parent));

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
