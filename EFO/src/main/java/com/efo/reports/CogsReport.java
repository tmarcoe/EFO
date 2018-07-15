package com.efo.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.service.PaymentHistoryService;
import com.efo.service.PaymentsBilledService;
import com.efo.service.RetailSalesService;

@Component
public class CogsReport {
	@Autowired
	private RetailSalesService salesService;
	
	@Autowired
	PaymentsBilledService paymentService;
	
	@Autowired
	PaymentHistoryService overheadPaymentService;
		
	@SuppressWarnings("unused")
	public String calculateCogs(Date from, Date to) {
		Double expenses = 0.0;
		List<Double> expense = getExpensesForPeriod(from, to);
		
		return "";
	}
	
	private List<Double> getExpensesForPeriod(Date from, Date to) {
		LocalDate jFrom = new LocalDate(from);
		
		List<Object[]> listOvr = overheadPaymentService.totalPayentsByPeriod(from, to);
		List<Object[]> listAp = paymentService.totalPayentsByPeriod(from, to);
		List<Double> qty = salesService.countProductsByPeriod(from, to);

		Integer[] monthListOvr = new Integer[12];
		Double[] expenseListOvr = new Double[12];
		Integer[] monthListAp = new Integer[12];
		Double[] expenseListAp = new Double[12];
		int i = 0;
		for (Object[] item : listOvr) {
			monthListOvr[i] = (Integer) item[0]; 
			expenseListOvr[i] = (Double) item[1];
			i++;
		}
		for (Object[] item : listAp) {
			monthListAp[i] = (Integer) item[0]; 
			expenseListAp[i] = (Double) item[1];
			i++;
		}
		//TODO Add in the denominator to figure expense per product sold
		Integer month = jFrom.getMonthOfYear();
		Integer ovrCounter = 0;
		Integer apCounter = 0;
		Double ovrExpense = 0.0;
		Double apExpense = 0.0;
		
		List<Double> expense = new ArrayList<Double>();
		
		for (Double item : qty) {
			Double den = item;
			if (den < 1.0) den = 1.0;
			if (monthListOvr[ovrCounter] == month) {
				ovrExpense = expenseListOvr[ovrCounter];
				ovrCounter++;
			}else{
				ovrExpense = 0.0;
			}
			if (monthListAp[apCounter] == month) {
				apExpense = expenseListAp[apCounter];
				apCounter++;
			}else{
				apExpense = 0.0;
			}
			expense.add((ovrExpense + apExpense)/den);
			month++;
			if (month > 12) month=1;
		}
		
		
		return expense;
	}
}
