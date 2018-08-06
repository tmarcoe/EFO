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

import com.efo.entity.OrderItems;
import com.efo.service.OrdersItemService;
import com.efo.service.ProductOrdersService;

@Controller
@RequestMapping("/admin/")
public class OrderItemsController {
	private final String pageLink = "/admin/orderitemspaging";

	private SimpleDateFormat dateFormat;
	private PagedListHolder<OrderItems> orderItemsList;
	
	@Autowired
	private OrdersItemService ordersItemsService;
	
	@Autowired
	private ProductOrdersService productOrdersService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("receiveorder")
	public String receiveOrder(@ModelAttribute("reference") Long reference, Model model) {
		orderItemsList = ordersItemsService.retrieveOpenItems(reference);

		model.addAttribute("objectList", orderItemsList);
		model.addAttribute("pagelink", pageLink);

		return "receiveorder";
	}
	
	@RequestMapping("updreceiveorder")
	public String updReceiveOrder(@ModelAttribute("id") Long id, 
								  @ModelAttribute("qty") double qty, 
								  @ModelAttribute("reference") Long reference) {
		
		ordersItemsService.receiveOrder(id, qty);
		if (ordersItemsService.hasOutstandingDeliveries(reference) == false) {
			productOrdersService.setStatus("D", reference);
		}
		
		return "redirect:/admin/receiveorder?reference=" + reference;
	}
	
	@RequestMapping(value = "orderitemspaging", method = RequestMethod.GET)
	public String handleOrderItemsRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			orderItemsList.nextPage();
		} else if ("prev".equals(page)) {
			orderItemsList.previousPage();
		} else if (pgNum != -1) {
			orderItemsList.setPage(pgNum);
		}
		model.addAttribute("objectList", orderItemsList);
		model.addAttribute("pagelink", pageLink);

		return "listproductorders";
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
