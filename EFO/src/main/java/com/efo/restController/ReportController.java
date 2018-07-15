package com.efo.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.reports.CogsReport;

@RestController
@RequestMapping("/rest/")
public class ReportController {
	
	@Autowired
	private CogsReport cogsReport;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	
	@RequestMapping("cogs")
	public String cogsReport(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws ParseException {
		
		return cogsReport.calculateCogs(dateFormat.parse(from), dateFormat.parse(to));
	}



}
