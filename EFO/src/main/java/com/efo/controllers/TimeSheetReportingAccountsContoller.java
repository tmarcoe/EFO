package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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

import com.efo.entity.Employee;
import com.efo.entity.TimeReportingAccounts;
import com.efo.entity.User;
import com.efo.service.TimeReportingAccountsService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/timesheet/")
public class TimeSheetReportingAccountsContoller {
	
	@Autowired
	private TimeReportingAccountsService timeReportingAccountsService;
	
	@Autowired
	private UserService userService;
	
	private PagedListHolder<TimeReportingAccounts> traList;
	
	private final String pageLink = "/budget/traccountpaging";
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
	}

	@RequestMapping("listtsaccounts")
	public String listTimeSheetAccounts(Principal principal, Model model) {
		User user = userService.retrieve(principal.getName());
		Employee employee = user.getEmployee();
		String department = "";
		
		if (employee != null) {
			department = employee.getDivision();
		}
		
		traList = timeReportingAccountsService.retrieveList(department);
		
		model.addAttribute("objectList", traList);
		model.addAttribute("pagelink", pageLink);
		
		return "listtsaccounts";
	}
	
	@RequestMapping("newtsaccount")
	public String newTimeSheetAccount(Model model, Principal principal) {
		String department = "";
		
		User user = userService.retrieve(principal.getName());
		
		Employee employee = user.getEmployee();
		
		if (employee != null) {
			department = employee.getDivision();
		}
		
		TimeReportingAccounts timeReportingAccounts = new TimeReportingAccounts();
		
		timeReportingAccounts.setBegins(new Date());
		timeReportingAccounts.setDepartment(department);
		timeReportingAccounts.setMax_amount(0.0);
		timeReportingAccounts.setMax_hours(0.0);
		
		model.addAttribute("timeReportingAccounts", timeReportingAccounts);
		
		return "newtsaccount";
	}
	
	@RequestMapping("addtsaccount")
	public String addTimeSheetAccount(@Valid @ModelAttribute("timeReportingAccounts") TimeReportingAccounts tsAccounts, BindingResult result) {
		if (result.hasErrors()) {
			return "newtsaccount";
		}
		
		timeReportingAccountsService.create(tsAccounts);
		
		return "redirect:/timesheet/listtsaccounts";
	}
	
	@RequestMapping("updatetsaccount")
	public String updateTimeSheetAccount(@Valid @ModelAttribute("timeReportingAccounts") TimeReportingAccounts tsAccounts, BindingResult result) {
		if (result.hasErrors()) {
			return "newtsaccount";
		}

		timeReportingAccountsService.merge(tsAccounts);
		
		return "redirect:/timesheet/listtsaccounts";
	}
	
	@RequestMapping("edittsaccount")
	public String editTimeSheetAccount(@ModelAttribute("account_num") String account_num, Model model) {
		
		TimeReportingAccounts tsAccounts = timeReportingAccountsService.retrieve(account_num);
		
		model.addAttribute("timeReportingAccounts", tsAccounts);
		
		return "edittsaccount";
	}
	
	@RequestMapping(value = "traccountpaging", method = RequestMethod.GET)
	public String handleTimeReportingAccountRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			traList.nextPage();
		} else if ("prev".equals(page)) {
			traList.previousPage();
		} else if (pgNum != -1) {
			traList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", traList);
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
