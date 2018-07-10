package com.efo.controllers;

import java.io.IOException;
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

import com.efo.component.ScheduleUtilities;
import com.efo.component.ScheduleUtilities.ScheduleType;
import com.efo.entity.Payables;
import com.efo.entity.PaymentsBilled;
import com.efo.service.FetalTransactionService;
import com.efo.service.PayablesService;


@Controller
@RequestMapping("/accounting/")
public class AccountsPayableController {
	
	@Autowired
	private PayablesService payablesService;
	
	@Autowired
	ScheduleUtilities sched;
	
	private final String pageLink = "/accounting/appaging";
	
	@Autowired
	FetalTransactionService fetalService;	
	
	PagedListHolder<Payables> apList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}


	@RequestMapping("ap")
	public String showAccountsPayable(Model model) throws SAXException, IOException, ParserConfigurationException{
		
		apList = payablesService.retrieveList();

		model.addAttribute("objectList", apList);
		model.addAttribute("pagelink", pageLink);

		return "ap";
	}
	
	
	
	@RequestMapping("addpayable")
	public String addPayable(@Valid @ModelAttribute("payables") Payables payables, BindingResult result) throws IOException {
		
		if (result.hasErrors() == true) {
			return "newpayable";
		}

		PaymentsBilled bill = new PaymentsBilled();
		bill.setReference(payables.getReference());
		ScheduleType type = sched.stringToEnum(payables.getSchedule());
		Date nextPayment = sched.nextPayment(payables.getDate_begin(), payables.getDate_begin(), type);
		bill.setDate_due(nextPayment);
		
		fetalService.addAp(payables, bill);		
		
		return "redirect:/accounting/ap";
	}
	
	@RequestMapping("editpayable")
	public String editPayable(@ModelAttribute("reference") Long reference, Model model) {
		Payables p = payablesService.retreive(reference);
		
		model.addAttribute("payables", p);
		
		return "editpayable";
	}
	
	@RequestMapping("updatepayable")
	public String updatePayable(@Valid @ModelAttribute("payables") Payables payables, BindingResult result) throws IOException {
		
		if (result.hasErrors() == true) {
			return "editpayable";
		}
		
		Payables oldPayables = payablesService.retreive(payables.getReference());
		
		if (payables.getTotal_due() != oldPayables.getTotal_due()) {
			double adj = payables.getTotal_due() - oldPayables.getTotal_due();
			fetalService.adjustAp(payables, adj);
		}
		
		payablesService.update(payables);
		
		return "redirect:/accounting/ap";
	}
	
	@RequestMapping(value = "appaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			apList.nextPage();
		} else if ("prev".equals(page)) {
			apList.previousPage();
		} else if (pgNum != -1) {
			apList.setPage(pgNum);
		}
		model.addAttribute("objectList", apList);
		model.addAttribute("pagelink", pageLink);

		return "ap";
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
