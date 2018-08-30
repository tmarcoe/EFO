package com.efo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

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

import com.efo.entity.OverheadExpenses;
import com.efo.entity.PaymentHistory;
import com.efo.service.FetalTransactionService;
import com.efo.service.OverheadExpensesService;
import com.efo.service.PaymentHistoryService;

@Controller
@RequestMapping("/accounting/")
public class PaymentHistoryController {
	private final String pageLink = "/accounting/paymentpaging";
	
	PagedListHolder<PaymentHistory> paymentList;

	@Autowired
	private PaymentHistoryService paymentService;
	
	@Autowired
	private OverheadExpensesService expensesService;
	
	@Autowired
	private FetalTransactionService transactionService; 
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("listpayments")
	public String listPayments(@ModelAttribute("reference") Long reference, Model model) {
		
		paymentList = paymentService.retrrieveList(reference);
		
		model.addAttribute("objectList", paymentList);
		model.addAttribute("pagelink", pageLink);
		
		return "listpayments";
	}
	
	@RequestMapping("makeopayment")
	public String makePayment(@ModelAttribute("id") Long id, Model model) {
		PaymentHistory payment = paymentService.retrieve(id);
		payment.setDate_paid(new Date());
		OverheadExpenses expenses = expensesService.retrieve(payment.getReference());
		
		model.addAttribute("payment", payment);
		model.addAttribute("expenses", expenses);
		
		return "makepayment";
	}
	
	@RequestMapping("updmakepayment")
	public String updateMakePayment(@Valid @ModelAttribute("payment") PaymentHistory payment, BindingResult result) throws IOException {
		
		OverheadExpenses expense = expensesService.retrieve(payment.getReference());
		expense.setPaymentHistory(new HashSet<PaymentHistory>(paymentService.retrieveRawList(expense.getReference())));
		
		transactionService.payOverheadExpense(expense, payment);
		
		return "redirect:/accounting/listpayments?reference=" + payment.getReference();
	}
	
	@RequestMapping("editopayment")
	public String editOverheadPayment(@ModelAttribute("id") Long id, Model model) {
		PaymentHistory payment = paymentService.retrieve(id);
		
		model.addAttribute("payment", payment);
		return "editopayment";
	}
	
	@RequestMapping(value = "paymentpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			paymentList.nextPage();
		} else if ("prev".equals(page)) {
			paymentList.previousPage();
		} else if (pgNum != -1) {
			paymentList.setPage(pgNum);
		}
		model.addAttribute("objectList", paymentList);
		model.addAttribute("pagelink", pageLink);

		return "listpayments";
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
