package com.efo.restController;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.service.FetalTransactionService;

@RestController
@RequestMapping("/rest/")
public class FetalController {
	
	@Autowired
	FetalTransactionService fetalService;
	
	@RequestMapping("calculatepayments")
	public String calculatePayments(@RequestParam(value = "total") Double total, 
									@RequestParam(value = "down") Double down,
									@RequestParam(value = "interest") Double interest,
									@RequestParam(value = "num_payments") Long num_payment) throws Exception {
		double each_payment = fetalService.calculatePayments(total, down, interest, num_payment);
		/*
		DecimalFormat df = new DecimalFormat(".##");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
		
		Double each_payment = ((total - down) * (1.0 + (interest / 100.00))) / Double.valueOf(String.valueOf(num_payment));
		
		each_payment = Double.valueOf(df.format(each_payment.doubleValue()));
		*/
		JSONObject json = new JSONObject();
		json.put("each_payment", each_payment);
		
		return json.toString();
	}

}
