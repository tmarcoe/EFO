package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.efo.entity.InventoryLedger;
import com.efo.service.InventoryLedgerService;

@Controller
@RequestMapping("/accounting/")
public class InventoryLedgerController {
	private final String pageLink = "/accounting/ilpaging";
	
	private PagedListHolder<InventoryLedger> ilList;
	
	@Autowired
	private InventoryLedgerService inventoryLedgerService;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("inventoryledgerlist/from/{from}/to/{to}")
	public String inventoryLedgerList(@PathVariable("from") Date from,@PathVariable("to") Date to, Model model) {
		
		ilList = inventoryLedgerService.retrieveList(from, to);
		
		ilList.setPageSize(30);
		ilList.setPage(0);
		
		model.addAttribute("objectList", ilList);
		model.addAttribute("pagelink", pageLink);

		return "inventoryledgerlist";
	}

	@RequestMapping(value = "Ilpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			ilList.nextPage();
		} else if ("prev".equals(page)) {
			ilList.previousPage();
		} else if (pgNum != -1) {
			ilList.setPage(pgNum);
		}
		model.addAttribute("objectList", ilList);
		model.addAttribute("pagelink", pageLink);

		return "inventoryledgerlist";
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
