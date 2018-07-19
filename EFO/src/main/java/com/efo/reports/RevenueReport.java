package com.efo.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.service.PaymentHistoryService;
import com.efo.service.PaymentsBilledService;
import com.efo.service.PaymentsReceivedService;
import com.efo.service.RetailSalesService;

@Component
public class RevenueReport {
	
	@Autowired
	private ReportUtilities reportUtil;
	
	@Autowired
	private RetailSalesService salesService;
	
	@Autowired
	private PaymentsReceivedService receivedService;
	
	@Autowired
	PaymentsBilledService paymentService;
	
	@Autowired
	PaymentHistoryService overheadService;
		
	public String calculateGrossRevenue(Date begin, Date end) throws JSONException {
		
		return revenueReport(begin, end);
	}
	
	private String revenueReport(Date begin, Date end) throws JSONException {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
		
		List<Double> revenue = reportUtil.translateToDoubleArray(salesService.getTotalSaleByPeriod(begin, end), begin, end);
		List<Double> received = reportUtil.translateToDoubleArray(receivedService.totalPayentsByPeriod(begin, end), begin, end);
		List<Double> overhead = reportUtil.translateToDoubleArray(overheadService.totalPayentsByPeriod(begin, end), begin, end);
		List<Double> accountsPayable = reportUtil.translateToDoubleArray(paymentService.totalPayentsByPeriod(begin, end), begin, end);
		
		List<Double> revenueList = new ArrayList<Double>();
		List<Double> expenseList = new ArrayList<Double>();
		
		Iterator<Double> iRevenue = revenue.iterator();
		Iterator<Double> iReceived = received.iterator();
		Iterator<Double> iOverhead = overhead.iterator();
		Iterator<Double> iAccountsPayable = accountsPayable.iterator();
		
		while (iRevenue.hasNext()) {
			Double r = iRevenue.next();
			Double rcvd = iReceived.next();
			Double o = iOverhead.next();
			Double a = iAccountsPayable.next();
			
			revenueList.add(r + rcvd);
			expenseList.add(o + a);
		}
		
		
		return convertToJSON(revenueList, expenseList, diff, jBegin, String.format("Revenue Vs Expense From %tD To %tD", begin, end)).toString();
	}
	
	private JSONObject convertToJSON(List<Double> list, List<Double> expense, int length, LocalDate start, String reportTitle) throws JSONException {	
		final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray datasets = new JSONArray();
		JSONObject revenue = new JSONObject();
		JSONObject exp = new JSONObject();
		String[] labels = new String[length];
		JSONObject options = new JSONObject();
		JSONObject scales = new JSONObject();
		JSONObject title = new JSONObject();
		JSONArray yAxes = new JSONArray();
		JSONObject yObj = new JSONObject();
		JSONObject ticks = new JSONObject();
		int m = 0;
		for (int i = (start.getMonthOfYear() -1) ; i < ((start.getMonthOfYear() + length) - 1) ; i++) {
			labels[m] = months[(i % 12)];
			m++;
		}
		
		json.put("type", "line");
		json.put("data", data);
		data.put("labels", labels);
		data.put("datasets", datasets);
		datasets.put(revenue);
		revenue.put("label", "Gross Revenue" );
		revenue.put("fill", false);
		revenue.put("backgroundColor", "rgba(158, 3, 3, 1.0)");
		revenue.put("borderColor", "rgba(158, 3, 3, .5)");
		revenue.put("data", list.toArray());
		revenue.put("borderWidth", 2);
		datasets.put(exp);
		exp.put("label", "Expenses" );
		exp.put("fill", false);
		exp.put("backgroundColor", "rgba(51, 7, 193, 1.0)");
		exp.put("borderColor", "rgba(51, 7, 193, .5)");
		exp.put("data", expense.toArray());
		exp.put("borderWidth", 2);
		json.put("options", options);
		options.put("scales", scales);
		options.put("responsive", false);
		options.put("title", title);
		title.put("display", true);
		title.put("text", reportTitle);
		title.put("fontSize", 32);
		scales.put("yAxes", yAxes);
		yObj.put("ticks", ticks);
		ticks.put("beginAtZero", true);
		yAxes.put(yObj);
		
		return json;
	}

}
