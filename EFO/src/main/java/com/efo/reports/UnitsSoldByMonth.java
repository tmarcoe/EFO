package com.efo.reports;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.service.RetailSalesService;

@Component
public class UnitsSoldByMonth {
	@Autowired
	private ReportUtilities reportUtil;
	
	@Autowired
	private RetailSalesService salesService;
	
	public String calcUnitsSold(Date begin, Date end) throws JSONException {
		
		return getCostPerUnit(begin, end);
	}
	private String getCostPerUnit(Date begin, Date end) throws JSONException {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
		List<Double> quantity = reportUtil.translateToDoubleArray(salesService.countProductsByPeriod(begin, end), begin, end);

			
		return JSONCreateSalesReport(quantity, diff, jBegin, String.format("Units Sold From %tD To %tD", begin, end)).toString();
	}
	
	private JSONObject JSONCreateSalesReport(List<Double> qty, int length, LocalDate start, String reportTitle) throws JSONException {
		final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray datasets = new JSONArray();
		JSONObject quantity = new JSONObject();
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
		datasets.put(quantity);
		quantity.put("label", "Quantity Sold" );
		quantity.put("fill", false);
		quantity.put("backgroundColor", "rgba(158, 3, 3, 1.0)");
		quantity.put("borderColor", "rgba(158, 3, 3, .5)");
		quantity.put("data", qty.toArray());
		quantity.put("borderWidth", 2);
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
