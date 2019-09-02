package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.efo.entity.BudgetItems;
import com.efo.service.BudgetItemsService;

@Controller
@RequestMapping("/budget/")
public class BudgetItemsController {
	@Autowired
	private BudgetItemsService budgetItemsService;
	
	private SimpleDateFormat dateFormat;
	
	@Value("${efo.federal.fica}")
	private String fica;
	
	@Value("${efo.federal.medicare}")
	private String medicare;
	
	@Value("${efo.federal.unemployment}")
	private String fedUnemployment;
	
	@Value("${efo.state.unemployment}")
	private String stUnemployment;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	@RequestMapping("listbudgetitems/{reference}/{level}")
	public String listBudgetItems(@ModelAttribute("parent") String parent, @PathVariable("reference") Long reference, @PathVariable("level") Long level, Model model) {
		
		
		model.addAttribute("level", level);
		model.addAttribute("parent", parent);
		model.addAttribute("reference", reference);
		model.addAttribute("budgetList", budgetItemsService.retrieveRawList(reference, parent));

		return "listbudgetitems";
	}
	
	@RequestMapping("uponelevel/{reference}/{level}")
	public String upOneLevel(@ModelAttribute("parent") String parent, @PathVariable("reference") Long reference, @PathVariable("level") Long level, Model model) {
		if ("".compareTo(parent) != 0) {
			BudgetItems budget = budgetItemsService.retrieveByCategory(reference, parent);
			parent = budget.getParent();
		}
		
		return "redirect:/budget/listbudgetitems/" + reference + "/" + level + "?parent=" + parent;
	}
	
	@RequestMapping("newbudgetitem/{reference}/{level}")
	public String newBudgetItem(@ModelAttribute("parent") String parent, @PathVariable("reference") Long reference, @PathVariable("level") Long level, Model model) {
		Double employerContribution = Double.valueOf(fica) + Double.valueOf(medicare) + Double.valueOf(fedUnemployment) + Double.valueOf(stUnemployment) + 1;
		
		BudgetItems budgetItem = new BudgetItems();
		budgetItem.setParent(parent);
		budgetItem.setLevel(level);

		budgetItem.setReference(reference);

		model.addAttribute("employerContribution", employerContribution);
		model.addAttribute("budgetItem", budgetItem);
		
		return "newbudgetitem";
	}
	
	@RequestMapping("addbudgetitem")
	public String addBudget(@Valid @ModelAttribute("budgetItem") BudgetItems budgetItem, BindingResult result) {
		if (result.hasErrors() || budgetItemsService.categoryExists(budgetItem.getReference(), budgetItem.getCategory())) {
			
			if (budgetItemsService.categoryExists(budgetItem.getReference(), budgetItem.getCategory())) {
				result.reject("category", "Exists.budget.category");
			}

			return "newbudgetitem";
		}
		
		budgetItemsService.create(budgetItem);
		
		Long level = budgetItem.getLevel();
		
		return "redirect:/budget/listbudgetitems/" + budgetItem.getReference() + "/" + level + "?parent=" + budgetItem.getParent();
	}

	@RequestMapping("editbudgetitem")
	public String editBudgetItem(@ModelAttribute("id") Long id, Model model) {
		Double employerContribution = Double.valueOf(fica) + Double.valueOf(medicare) + Double.valueOf(fedUnemployment) + Double.valueOf(stUnemployment) + 1;
		
		model.addAttribute("employerContribution", employerContribution);
		model.addAttribute("budget", budgetItemsService.retrieve(id));
		
		return "editbudgetitem";
	}
	
	@RequestMapping("updatebudgetitem")
	public String updateBudgetItem(@Valid @ModelAttribute("budget") BudgetItems budgetItem, BindingResult result) {
		
		budgetItemsService.update(budgetItem);
		
		return "redirect:/budget/listbudgetitems/" + budgetItem.getReference() + "/" + budgetItem.getLevel() + "?parent=" + budgetItem.getParent();
	}
	
	@RequestMapping("deletebudgetitem/{reference}/{id}")
	public String deleteBudgetItem(@ModelAttribute("parent") String parent,
								   @PathVariable("reference") Long reference, 
								   @PathVariable("id") Long id) {
		
		BudgetItems budgetItem = budgetItemsService.retrieve(id);
		if (budgetItemsService.hasChildren(reference, parent) == false) {
			budgetItemsService.delete(id);
		}
		
		return "redirect:/budget/listbudgetitems/" + reference + "/" + budgetItem.getLevel() + "?parent=" + budgetItem.getParent();
	}
	
}
