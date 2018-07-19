package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounting/")
public class ReportsController {
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("cogsreport/from/{from}/to/{to}")
	public String cogsReport(@PathVariable("from") Date from, @PathVariable("to") Date to, Model model) {
		
		model.addAttribute("from", dateFormat.format(from));
		model.addAttribute("to", dateFormat.format(to));
		model.addAttribute("report", "cogs");
		
		return "cogsreport";
	}
	
	@RequestMapping("revenuereport/from/{from}/to/{to}")
	public String revenueReport(@PathVariable("from") Date from, @PathVariable("to") Date to, Model model) {
		
		model.addAttribute("from", dateFormat.format(from));
		model.addAttribute("to", dateFormat.format(to));
		model.addAttribute("report", "revenue");

		return "revenuereport";
	}

	@RequestMapping("plreport/from/{from}/to/{to}")
	public String profitAndLossReport(@PathVariable("from") Date from, @PathVariable("to") Date to, Model model) {
		
		model.addAttribute("from", dateFormat.format(from));
		model.addAttribute("to", dateFormat.format(to));
		model.addAttribute("report", "profitandloss");
		
		return "plreport";
	}
	
	@RequestMapping("unitssold/from/{from}/to/{to}")
	public String unitsSold(@PathVariable("from") Date from, @PathVariable("to") Date to, Model model) {
		
		model.addAttribute("from", dateFormat.format(from));
		model.addAttribute("to", dateFormat.format(to));
		model.addAttribute("report", "unitsld");
		
		return "unitssold";
	}

}
