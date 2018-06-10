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

import com.efo.entity.Receivables;
import com.efo.entity.User;
import com.efo.service.FetalTransactionService;
import com.efo.service.PaymentsReceivedService;
import com.efo.service.ReceivablesService;
import com.efo.service.UserService;


@Controller
@RequestMapping("/accounting/")
public class AccountsReceivableController {
	
	@Autowired
	ReceivablesService receivablesService;
	
	@Autowired
	PaymentsReceivedService paymentsReceivedService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FetalTransactionService fetalService;	

	private final String pageLink = "/accounting/arpaging";
	
	PagedListHolder<Receivables> arList;
	
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("newreceivable")
	public String newReceivable(@ModelAttribute("username") String username, Model model) {
		Receivables receivables = new Receivables();
		User user = userService.retrieve(username);
		
		receivables.setCustomer(user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname());
		receivables.setInvoice_date(new Date());
		receivables.setUsername(username);
		
		model.addAttribute("receivables", receivables);
		
		return "newreceivable";
	}
	
	@RequestMapping("addreceivable")
	public String addReceivable(@Valid @ModelAttribute("receivables") Receivables receivables, BindingResult result) throws IOException {
		
		if (result.hasErrors() ) {
			return "newreceivable";
		}
		
		fetalService.addAr(receivables);
		
		return "redirect:/accounting/ar";
	}
		
	@RequestMapping("ar")
	public String showAccountsReceivable(Model model) throws SAXException, IOException, ParserConfigurationException {
		
		arList = receivablesService.retreiveList();

		model.addAttribute("objectList", arList);
		model.addAttribute("pagelink", pageLink);

		return "ar";
	}
	
	@RequestMapping("editreceivable")
	public String editReceivable(@ModelAttribute("invoice_num") String invoice_num, Model model) {
		
		model.addAttribute("receivables", receivablesService.retreive(invoice_num));
		
		return "editreceivable";
	}
	
	@RequestMapping("updatereceivable")
	public String updateReceivable(@Valid @ModelAttribute("receivables") Receivables receivables, BindingResult result) throws IOException {
		
		if (result.hasErrors()) {
			return "editreceivable";
		}
		
		Receivables oldReceivables = receivablesService.retreive(receivables.getInvoice_num());
		
		if (oldReceivables.getTotal_due() != receivables.getTotal_due() ) {
			double adj = receivables.getTotal_due() - oldReceivables.getTotal_due();
			fetalService.adjustAr(receivables, adj);
		}
		
		receivablesService.update(receivables);
		
		return "redirect:/accounting/ar";
	}
	
	@RequestMapping(value = "arpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			arList.nextPage();
		} else if ("prev".equals(page)) {
			arList.previousPage();
		} else if (pgNum != -1) {
			arList.setPage(pgNum);
		}
		model.addAttribute("objectList", arList);
		model.addAttribute("pagelink", pageLink);

		return "ar";
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
