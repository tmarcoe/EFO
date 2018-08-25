package com.efo.controllers;

import java.io.IOException;
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

import com.efo.entity.PaymentsReceived;
import com.efo.entity.Receivables;
import com.efo.service.FetalTransactionService;
import com.efo.service.PaymentsReceivedService;
import com.efo.service.ReceivablesService;

@Controller 
@RequestMapping("/accounting/")
public class ARPaymentsController {
	
	@Autowired
	PaymentsReceivedService paymentsService;
	
	@Autowired
	ReceivablesService receivablesService;
	
	@Autowired
	FetalTransactionService fetalService;	
	
	PagedListHolder<PaymentsReceived> rList;
	
	private final String pageLink = "/accounting/rpaging";
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("requestpayment")
	public String requestPayment(@ModelAttribute("reference") Long reference, Model model) {
		
		return "requestpayment";
	}
	
	@RequestMapping("arpaymentlist")
	public String arPaymentList(@ModelAttribute("reference") Long reference, Model model) {
		
		rList = paymentsService.retreiveList(reference);
		
		model.addAttribute("objectList", rList);
		model.addAttribute("pagelink", pageLink);
		
		return "arpaymentlist";
	}
	
	@RequestMapping("newrpayment")
	public String newPaymentReceived(@ModelAttribute("reference") Long reference, Model model) {
		PaymentsReceived payment = new PaymentsReceived();
		payment.setReference(reference);
		
		model.addAttribute("payment", payment);
		
		return "newrpayment";
	}
	
	@RequestMapping("editrpayment")
	public String editPayment(@ModelAttribute("id") int id, Model model) {
		
		PaymentsReceived rec = paymentsService.retreive(id);
		
		model.addAttribute("payment", rec);
		
		return "editrpayment";
	}
	
	@RequestMapping("receivepayment")
	public String receivePayment(@ModelAttribute("id") int id, Model model) {
		double payment = 0.0;
		PaymentsReceived rec = paymentsService.retreive(id);
		Receivables receivables = receivablesService.retreive(rec.getReference());
		
		rec.setPayment_date(new Date());
		if (receivables.getEach_payment() <= receivables.getTotal_balance()) {
			payment = receivables.getEach_payment();
		}else{
			payment = receivables.getTotal_balance();
		}
		rec.setPayment(payment);
		
		model.addAttribute("payment", rec);
		
		return "receivepayment";
	}
	
	@RequestMapping("updatereceive")
	public String updateReceivePayment(@ModelAttribute("payment") PaymentsReceived payment) throws IOException {
		
		Receivables receivables = receivablesService.retreive(payment.getReference());
		fetalService.receivePaymentFromReceivable(payment, receivables);
		
		return "redirect:/accounting/arpaymentlist?reference=" + payment.getReference();
	}
	
	@RequestMapping("addrpayment")
	public String addReceivablePayment(@ModelAttribute("payment") PaymentsReceived payment) throws IOException {
		
		Receivables receivables = receivablesService.retreive(payment.getReference());
		
		fetalService.receivePayment(payment, receivables);
		
		return "redirect:/accounting/arpaymentlist?reference=" + payment.getReference();
	}
	
	@RequestMapping("updaterpayment")
	public String updateReceivablePayment(@ModelAttribute("payment") PaymentsReceived payment) {
		
		paymentsService.update(payment);
		
		return "redirect:/accounting/arpaymentlist?reference=" + payment.getReference();
	}
	
	@RequestMapping(value = "rpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			rList.nextPage();
		} else if ("prev".equals(page)) {
			rList.previousPage();
		} else if (pgNum != -1) {
			rList.setPage(pgNum);
		}
		model.addAttribute("objectList", rList);
		model.addAttribute("pagelink", pageLink);

		return "arpaymentlist";
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
