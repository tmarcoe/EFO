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

import com.efo.component.ScheduleUtilities;
import com.efo.entity.Payables;
import com.efo.entity.PaymentsBilled;
import com.efo.service.FetalTransactionService;
import com.efo.service.PayablesService;
import com.efo.service.PaymentsBilledService;

@Controller
@RequestMapping("/accounting/")
public class APPaymentsController {
	
	@Autowired
	PaymentsBilledService paymentsService;
	
	@Autowired
	PayablesService payablesService;
	
	@Autowired
	FetalTransactionService fetalService;	
	
	@Autowired
	ScheduleUtilities sched;
	
	PagedListHolder<PaymentsBilled> pList;
	
	private final String pageLink = "/accounting/ppaging";
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("appaymentlist")
	public String apPaymentList(@ModelAttribute("reference") Long reference, Model model) {
		
		pList = paymentsService.retrieveList(reference);
		
		if (pList.getSource().size() == 0) {
			pList = null;
			System.gc();
			
			return "redirect:/accounting/newppayment?reference=" + reference;
		}
		
		model.addAttribute("objectList", pList);
		model.addAttribute("pagelink", pageLink);		
		
		return "appaymentlist";
	}
	@RequestMapping("payamount")
	public String payAmount(@ModelAttribute("id") Long id, Model model) {
		
		PaymentsBilled billed = paymentsService.retreive(id);
		billed.setPayment_date(new Date());
		billed.setPayment(billed.getPayment_due());
		model.addAttribute("billed", billed);
		
		return "payamount";
	}
	@RequestMapping("addpayment") 
	public String addPayment(@ModelAttribute("billed") PaymentsBilled billed) throws IOException {
		
		Payables payables = payablesService.retreive(billed.getReference());
		paymentsService.update(billed);
		fetalService.makePayment(payables, billed);
		
		return "redirect:/accounting/appaymentlist?reference=" + billed.getReference();
	}
	
	@RequestMapping("newppayment")
	public String newPPayment(@ModelAttribute("reference") Long reference, Model model) {
		PaymentsBilled payment = new PaymentsBilled();
		payment.setReference(reference);
		
		model.addAttribute("payment", payment);
		
		return "newppayment";
	}
	
	@RequestMapping("editppayment")
	public String editPPayment(@ModelAttribute("id") Long id, Model model) {
		
		PaymentsBilled paid = paymentsService.retreive(id);
		
		model.addAttribute("payment", paid);
		
		return "editppayment";
	}
	
	@RequestMapping("addppayment")
	public String addPayablePayment(@Valid @ModelAttribute("payment") PaymentsBilled payment, BindingResult result) {
		
		paymentsService.create(payment);
		
		return "redirect:/accounting/appaymentlist?reference=" + payment.getReference();
	}
	
	@RequestMapping("updateppayment")
	public String updatePPayment(@ModelAttribute("payment") PaymentsBilled payment) {
		
		paymentsService.update(payment);
		
		return "redirect:/accounting/appaymentlist?reference=" + payment.getReference();
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
