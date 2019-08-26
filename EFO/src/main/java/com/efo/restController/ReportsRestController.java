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

import com.efo.reports.MaleVsFemaleReport;
import com.efo.reports.RevenueReport;

@RestController
@RequestMapping("/rest/")
public class ReportsRestController {

	@Autowired 
	private RevenueReport revenueReport;
	
	@Autowired
	private MaleVsFemaleReport maleVsFemale;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping("revenue")
	public String revenueByMonth(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws JSONException, ParseException {
		String result = revenueReport.revenuesByMonth(dateFormat.parse(from), dateFormat.parse(to));
		
		return result;

	}
	
	@RequestMapping("gender")
	public String gender() throws JSONException {
		return maleVsFemale.maleVsFemale();
	}

}
