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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.BudgetItems;
import com.efo.service.BudgetItemsService;

@Controller
@RequestMapping("/accounting/")
public class BudgetItemsController {
	@Autowired
	private BudgetItemsService budgetItemsService;
	
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
	public String listBudgetItems(@PathVariable("reference") Long reference, @PathVariable("parent") String parent, Model model) {
		
		budgetItemList = budgetItemsService.retrieveList(reference, parent);
		
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
	public String newBudgetItem(@PathVariable("reference") Long reference, @PathVariable("parent") String parent, Model model) {
		
		BudgetItems budgetItem = new BudgetItems();
		budgetItem.setParent(parent);
		budgetItem.setReference(reference);

		model.addAttribute("budgetItem", budgetItem);
		
		return "newbudgetitem";
	}
	
	@RequestMapping("addbudgetitem")
	public String addBudget(@Valid @ModelAttribute("budgetItem") BudgetItems budgetItem, BindingResult result) {
		if (result.hasErrors()) {
			return "newbudgetitem";
		}
		
		if (budgetItemsService.categoryExists(budgetItem.getReference(), budgetItem.getCategory())) {
			
			result.reject("category", "Exists.budget.category");
			
			return "newbudgetitem";
		}
		budgetItemsService.create(budgetItem);
		
		return "redirect:/accounting/listbudgetitems/" + budgetItem.getReference() + "/"+ budgetItem.getParent();
	}

	@RequestMapping("editbudgetitem")
	public String editBudgetItem(@ModelAttribute("id") Long id, Model model) {
		
		model.addAttribute("budget", budgetItemsService.retrieve(id));
		
		return "editbudgetitem";
	}
	
	@RequestMapping("updatebudgetitem")
	public String updateBudgetItem(@Valid @ModelAttribute("budget") BudgetItems budgetItem, BindingResult result) {
		
		budgetItemsService.update(budgetItem);
		
		return "redirect:/accounting/listbudgetitems/" + budgetItem.getReference() + "/" + budgetItem.getParent();
	}
	
	@RequestMapping("deletebudgetitem/{reference}/{parent}/{id}")
	public String deleteBudgetItem(@PathVariable("reference") Long reference, 
								   @PathVariable("parent") String parent, 
								   @PathVariable("id") Long id) {
		
		BudgetItems budgetItem = budgetItemsService.retrieve(id);
		if (budgetItemsService.hasChildren(reference, parent) == false) {
			budgetItemsService.delete(id);
		}
		
		return "redirect:/accounting/listbudgetitems/" + reference + "/" + budgetItem.getParent();
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
