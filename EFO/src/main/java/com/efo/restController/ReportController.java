package com.efo.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.reports.CogsReport;
import com.efo.reports.ProfitAndLossReport;
import com.efo.reports.RevenueReport;
import com.efo.reports.UnitsSoldByMonth;

@RestController
@RequestMapping("/rest/")
public class ReportController {
	
	@Autowired
	private CogsReport cogsReport;
	
	@Autowired
	private RevenueReport revenueReport;
	
	@Autowired 
	private ProfitAndLossReport plReport;
	
	@Autowired
	private UnitsSoldByMonth soldByMonth;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	
	@RequestMapping("cogs")
	public String cogsReport(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws ParseException, JSONException {
		
		return cogsReport.calculateCogs(dateFormat.parse(from), dateFormat.parse(to));
	}

	@RequestMapping("revenue")
	public String revenueReport(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws ParseException, JSONException {
		
		return revenueReport.calculateGrossRevenue(dateFormat.parse(from), dateFormat.parse(to));
	}
	
	@RequestMapping("profitandloss")
	public String profitAndLoss(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws JSONException, ParseException {
		
		return plReport.ProfitAndLoss(dateFormat.parse(from), dateFormat.parse(to));
	}
	
	@RequestMapping("unitsld")
	public String numberOfUnitsSold(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws JSONException, ParseException {
		
		return soldByMonth.calcUnitsSold(dateFormat.parse(from), dateFormat.parse(to));
	}

}
