package com.efo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efo.service.BillingHistoryService;

@Controller
@RequestMapping("/accounting/")
public class BillingHistoryController {
	
	@Autowired
	BillingHistoryService billingHistoryService;
	
	@RequestMapping("invoicecustomer")
	public String invoiceCustomer(Model model) {
		
		
		return "invoicecustomer";
	}

}
