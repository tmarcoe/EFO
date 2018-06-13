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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Inventory;
import com.efo.service.InventoryService;

@Controller
@RequestMapping("/accounting/")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	private final String pageLink = "/accounting/appaging";
	
	private SimpleDateFormat dateFormat;
	private PagedListHolder<Inventory> invList;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("listinventory")
	public String listInventory(Model model) {
		invList = inventoryService.retrieveList();
		
		model.addAttribute("objectList", invList);
		model.addAttribute("pageLink", pageLink);
		
		return "listinventory";
	}	
	
	@RequestMapping(value = "inventorypaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			invList.nextPage();
		} else if ("prev".equals(page)) {
			invList.previousPage();
		} else if (pgNum != -1) {
			invList.setPage(pgNum);
		}
		model.addAttribute("objectList", invList);
		model.addAttribute("pagelink", pageLink);

		return "listinventory";
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
