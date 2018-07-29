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
import com.efo.service.OrdersItemService;

@Component
public class CogsReport {
	
	@Autowired
	private ReportUtilities reportUtil;
	
	@Autowired
	private PaymentsBilledService paymentService;
	
	@Autowired
	private PaymentHistoryService overheadPaymentService;
	
	@Autowired
	private OrdersItemService ordersService;
		
	public String calculateCogs(Date begin, Date end) throws JSONException {
		
		return getCostPerUnit(begin, end);
	}
	
	private String getCostPerUnit(Date begin, Date end) throws JSONException {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
		List<Double> overhead = reportUtil.translateToDoubleArray(overheadPaymentService.totalPayentsByPeriod(begin, end), begin, end);
		List<Double> accountsPayable = reportUtil.translateToDoubleArray(paymentService.totalPayentsByPeriod(begin, end), begin, end);
		List<Double> productOrders = reportUtil.translateToDoubleArray(ordersService.getTotalWholesaleByPeriod(begin, end), begin, end);

		List<Double> expense = new ArrayList<Double>();
		
		Iterator<Double> iOverhead = overhead.iterator();
		Iterator<Double> iAccountsPayable = accountsPayable.iterator();
		Iterator<Double> iOrderItems = productOrders.iterator();
		
		while (iOverhead.hasNext()) {
			Double ovr = iOverhead.next();
			Double ap = iAccountsPayable.next();
			Double orders = iOrderItems.next();
			
			expense.add(ovr + ap + orders);
		}
			
		return JSONCreateSalesReport(expense, diff, jBegin, String.format("CoGS Report From %tD To %tD", begin, end)).toString();
	}
	
	private JSONObject JSONCreateSalesReport(List<Double> totals, int length, LocalDate start, String reportTitle) throws JSONException {
		final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray datasets = new JSONArray();
		JSONObject expense = new JSONObject();
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
		datasets.put(expense);
		expense.put("label", "Expense Per Unit Sold" );
		expense.put("fill", false);
		expense.put("backgroundColor", "rgba(158, 3, 3, 1.0)");
		expense.put("borderColor", "rgba(158, 3, 3, .5)");
		expense.put("data", totals.toArray());
		expense.put("borderWidth", 2);
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
