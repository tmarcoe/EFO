package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.PaymentsPaid;
import com.efo.service.FetalTransactionService;
import com.efo.service.PayablesService;
import com.efo.service.PaymentsPaidService;

@Controller
@RequestMapping("/accounting/")
public class APPaymentsController {
	
	@Autowired
	PaymentsPaidService paymentsService;
	
	@Autowired
	PayablesService payablesService;
	
	@Autowired
	FetalTransactionService fetalService;	
	
	PagedListHolder<PaymentsPaid> pList;
	
	private final String pageLink = "/accounting/ppaging";
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("appaymentlist")
	public String apPaymentList(@ModelAttribute("invoice_num") String invoice_num, Model model) {
		
		pList = paymentsService.retrieveList(invoice_num);
		
		if (pList.getSource().size() == 0) {
			pList = null;
			System.gc();
			
			return "redirect:/accounting/newppayment?invoice_num=" + invoice_num;
		}
		
		model.addAttribute("objectList", pList);
		model.addAttribute("pagelink", pageLink);		
		
		return "appaymentlist";
	}
	
	@RequestMapping("newppayment")
	public String newPPayment(@ModelAttribute("invoice_num") String invoice_num, Model model) {
		PaymentsPaid payment = new PaymentsPaid();
		payment.setInvoice_num(invoice_num);
		
		model.addAttribute("payment", payment);
		
		return "newppayment";
	}
	
	@RequestMapping("editppayment")
	public String editPPayment(@ModelAttribute("id") int id, Model model) {
		
		PaymentsPaid paid = paymentsService.retreive(id);
		
		model.addAttribute("payment", paid);
		
		return "editppayment";
	}
	
	@RequestMapping("addppayment")
	public String addPayablePayment(@ModelAttribute("payment") PaymentsPaid payment) {
		
		paymentsService.create(payment);
		
		return "redirect:/accounting/appaymentlist?invoice_num=" + payment.getInvoice_num();
	}
	
	@RequestMapping("updateppayment")
	public String updatePPayment(@ModelAttribute("payment") PaymentsPaid payment) {
		
		paymentsService.update(payment);
		
		return "redirect:/accounting/appaymentlist?invoice_num=" + payment.getInvoice_num();
	}
	
	/************************************************************************************
	 * Processes pagination
	 **********************************************************************************/
	@RequestMapping(value = "ppaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			pList.nextPage();
		} else if ("prev".equals(page)) {
			pList.previousPage();
		} else if (pgNum != -1) {
			pList.setPage(pgNum);
		}
		model.addAttribute("objectList", pList);
		model.addAttribute("pagelink", pageLink);

		return "appaymentlist";
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
