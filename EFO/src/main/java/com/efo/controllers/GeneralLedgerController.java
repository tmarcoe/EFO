package com.efo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;


import com.efo.entity.GeneralLedger;
import com.efo.service.GeneralLedgerService;


@Controller
@RequestMapping("/accounting/")
public class GeneralLedgerController {
	
	@Autowired
	GeneralLedgerService ledgerService;
	
	private final String pageLink = "/accounting/glpaging";
	
	PagedListHolder<GeneralLedger> glList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping("ledgerlist/from/{from}/to/{to}")
	public String ledgerList(@PathVariable("from") Date from,@PathVariable("to") Date to, Model model) throws SAXException, IOException, ParserConfigurationException{
		
		glList = ledgerService.getPagedList(from, to);

		model.addAttribute("objectList", glList);
		model.addAttribute("pagelink", pageLink);

		return "ledgerlist";
	}
	
	@RequestMapping(value = "glpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			glList.nextPage();
		} else if ("prev".equals(page)) {
			glList.previousPage();
		} else if (pgNum != -1) {
			glList.setPage(pgNum);
		}
		model.addAttribute("objectList", glList);
		model.addAttribute("pagelink", pageLink);

		return "ledgerlist";
	}

	/**************************************************************************************************************************************
	 * Used for both detecting a number, and converting to a number. If this
	 * routine returns a -1, the input parameter was not a number.
	 * 
	 **************************************************************************************************************************************/

	private int isInteger(String s) {
		int retInt;
		try {
			retInt = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
		// only got here if we didn't return false
		return retInt;
	}

}
