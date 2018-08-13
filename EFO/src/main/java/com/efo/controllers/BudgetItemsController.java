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
public class BudgetItemsController {
	@Autowired
	private BudgetItemsService budgetItemsService;
	
	@Autowired
	private UserService userService;
	
	private final String pageLink = "/accounting/budgetitempaging/%d/%s";
	private PagedListHolder<BudgetItems> budgetItemList;
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	@RequestMapping("listbudgetitems/{reference}/{parent}")
	public String listBudgetItems(@PathVariable("reference") Long reference, @PathVariable("parent") String parent, Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		budgetItemList = budgetItemsService.retrieveList(reference, parent, user.getUser_id());
		
		budgetItemList.setPageSize(20);
		
		model.addAttribute("parent", parent);
		model.addAttribute("reference", reference);
		model.addAttribute("objectList", budgetItemList);
		model.addAttribute("pagelink", String.format(pageLink, reference, parent));

		return "listbudgetitems";
	}
	
	@RequestMapping("uponelevel/{reference}/{parent}")
	public String upOneLevel(@PathVariable("reference") Long reference, @PathVariable("parent") String parent, Model model) {
		if ("ROOT".compareTo(parent) != 0) {
			BudgetItems budget = budgetItemsService.retrieveByCategory(reference, parent);
			parent = budget.getParent();
		}
		
		return "redirect:/accounting/listbudgetitems/" + reference + "/" + parent;
	}
	
	@RequestMapping("newbudgetitem/{reference}/{parent}")
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
	public String addBudget(@Valid @ModelAttribute("budget") BudgetItems budgetItems, BindingResult result) {
		if (result.hasErrors()) {
			return "newbudgetitem";
		}
		
		if (budgetItemsService.categoryExists(budgetItems.getReference(), budgetItems.getCategory())) {
			
			result.reject("category", "Exists.budget.category");
			
			return "newbudgetitem";
		}
		budgetItemsService.create(budgetItems);
		
		return "redirect:/accounting/listbudgetitems/" + budgetItems.getReference() + "/"+ budgetItems.getParent();
	}

	@RequestMapping("editbudgetitem")
	public String editBudgetItem(@ModelAttribute("id") Long id, Model model) {
		
		model.addAttribute("budget", budgetItemsService.retrieve(id));
		
		return "editbudgetitem";
	}
	
	@RequestMapping("updatebudgetitem")
	public String updateBudgetItem(@Valid @ModelAttribute("budget") BudgetItems budget, BindingResult result) {
		
		budgetItemsService.update(budget);
		
		return "redirect:/accounting/listbudgetitems/" + budget.getReference() + "/" + budget.getParent();
	}
	
	@RequestMapping("submitbudget/{reference}") 
	public String submitBudget(@PathVariable("reference") Long reference, Principal principal) {
		User user = userService.retrieve(principal.getName());
		budgetItemsService.submitBudget(user.getEmployee().getDivision());
		
		return "redirect:/accounting/listbudgetitems/" + reference + "/ROOT";
	}
	
	@RequestMapping(value = "budgetitempaging/{reference}/{parent}", method = RequestMethod.GET)
	public String handleBudgeItemtRequest(@PathVariable("reference") Long reference, @PathVariable("parent") String parent, @ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			budgetItemList.nextPage();
		} else if ("prev".equals(page)) {
			budgetItemList.previousPage();
		} else if (pgNum != -1) {
			budgetItemList.setPage(pgNum);
		}
		
		model.addAttribute("parent", parent);
		model.addAttribute("reference", reference);
		model.addAttribute("objectList", budgetItemList);
		model.addAttribute("pagelink", String.format(pageLink, reference, parent));

		return "listbudgetitems";
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
