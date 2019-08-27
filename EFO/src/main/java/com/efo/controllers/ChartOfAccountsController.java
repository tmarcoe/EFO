package com.efo.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;


import com.efo.entity.ChartOfAccounts;
import com.efo.forms.PrintAccountsForm;
import com.efo.service.ChartOfAccountsService;


@Controller
@RequestMapping("/accounting/")
public class ChartOfAccountsController {
	private final String pageLink = "/accounting/cofapaging";
	
	@Autowired
	private ChartOfAccountsService chartOfAccountsService;
	
	@Autowired
	private PrintAccountsForm printAccountsForm;
	
	private PagedListHolder<ChartOfAccounts> cofaList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("accountslist")
	public String accountsList(Model model) throws SAXException, IOException, ParserConfigurationException {
		
		cofaList = chartOfAccountsService.retrieveList();
		if (cofaList.getSource().size() == 0) {
			return "redirect:/accounting/newaccount";
		}
		cofaList.setPageSize(30);
		cofaList.setPage(0);
		model.addAttribute("objectList", cofaList);
		model.addAttribute("pagelink", pageLink);

		return "accountslist";
	}
	
	@RequestMapping("newaccount")
	public String newAccount(Model model) {
		ChartOfAccounts account = new ChartOfAccounts();
		
		model.addAttribute("account", account);
		
		return "newaccount";
	}
	
	@RequestMapping("addaccount")
	public String addAccount(@Valid @ModelAttribute("account") ChartOfAccounts account, BindingResult result) {
		
		chartOfAccountsService.create(account);
		
		return "redirect:/accounting/accountslist";
	}
	
	@RequestMapping("editaccount")
	public String editAccount(@ModelAttribute("account") String account, Model model) {
		ChartOfAccounts cofa = chartOfAccountsService.retrieve(account);
		
		model.addAttribute("account", cofa);
		
		return "editaccount";
	}
	
	@RequestMapping("updateaccount")
	public String updateAccount(@Valid @ModelAttribute("account") ChartOfAccounts account, BindingResult result) {
		
		chartOfAccountsService.update(account);
		
		return "redirect:/accounting/accountslist";
	}
	@RequestMapping("deleteaccount")
	public String deleteAccount(@ModelAttribute("account") String account) {
		
		chartOfAccountsService.delete(account);
		
		return "redirect:/accounting/accountslist";
	}
	
	@RequestMapping("printaccounts")
	public String printAccounts() throws FileNotFoundException, MalformedURLException {
		
		printAccountsForm.print();
		
		return "redirect:/#tabs-6";
	}

	@RequestMapping(value = "cofapaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			cofaList.nextPage();
		} else if ("prev".equals(page)) {
			cofaList.previousPage();
		} else if (pgNum != -1) {
			cofaList.setPage(pgNum);
		}
		model.addAttribute("objectList", cofaList);
		model.addAttribute("pagelink", pageLink);

		return "accountslist";
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
