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

import com.efo.entity.Loans;
import com.efo.service.FetalTransactionService;
import com.efo.service.LoansService;

@Controller
@RequestMapping("/accounting/")
public class LoansController {
	private final String pageLink = "/accounting/loanspaging";
	
	@Autowired
	private LoansService loansService;
	
	@Autowired
	private FetalTransactionService fetalService;	
	
	private PagedListHolder<Loans> loansList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("listloans")
	public String listLoans(Model model) {
		
		loansList = loansService.retrieveList();
		
		model.addAttribute("objectList", loansList);
		model.addAttribute("pagelink", pageLink);

		return "listloans";
	}
	
	@RequestMapping("newloan")
	public String newLoan( Model model) {
		Loans loan = new Loans();
		loan.setApproval(new Date());
		model.addAttribute("loan", loan);
		
		return "newloan";
	}

	@RequestMapping("addloan")
	public String addLoan(@ModelAttribute("loan") Loans loan, Model model) throws IOException {

		loansService.create(loan);
		fetalService.addLoans(loan);
		
		return "redirect:/accounting/listloans";
	}
	
	@RequestMapping("editloan") 
	public String editLoan(@ModelAttribute("trans_id") Long trans_id, Model model) {
		
		model.addAttribute("loan", loansService.retrieve(trans_id));
		
		return "editloan";
	}
	
	@RequestMapping("updateloan") 
	public String updateLoan(@Valid @ModelAttribute("loan") Loans loan, BindingResult result) {
		
		loansService.update(loan);
		
		return "redirect:/accounting/listloans";
	}

	@RequestMapping(value = "loanspaging", method = RequestMethod.GET)
	public String handleLoansRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			loansList.nextPage();
		} else if ("prev".equals(page)) {
			loansList.previousPage();
		} else if (pgNum != -1) {
			loansList.setPage(pgNum);
		}
		model.addAttribute("objectList", loansList);
		model.addAttribute("pagelink", pageLink);

		return "listloans";
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
