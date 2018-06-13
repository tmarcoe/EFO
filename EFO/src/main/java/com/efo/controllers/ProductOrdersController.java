package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Product;
import com.efo.entity.ProductOrders;
import com.efo.entity.User;
import com.efo.service.InventoryService;
import com.efo.service.ProductOrdersService;
import com.efo.service.ProductService;
import com.efo.service.VendorService;

@Controller
@RequestMapping("/admin/")
public class ProductOrdersController {
	private final String pageLink = "/admin/prdorderpaging";
	
	private SimpleDateFormat dateFormat;
	private PagedListHolder<ProductOrders> prdOrderList;

	@Autowired
	private ProductOrdersService ordersService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private VendorService vendorService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("listproductorders")
	public String listProductOrders(Model model) {
		prdOrderList = ordersService.retrieveList();
		
		model.addAttribute("objectList", prdOrderList);
		model.addAttribute("pagelink", pageLink);
		
		return "listproductorders";
	}
	
	@RequestMapping("newproductorder")
	public String newOrder(@ModelAttribute("sku") String sku, Model model) {
		
		List<User> suppliers = vendorService.retrieveRawList("R");
		
		model.addAttribute("productOrder", new ProductOrders(sku, new Date()));
		model.addAttribute("suppliers", suppliers);
		
		return "newproductorder";
	}
	
	@RequestMapping("addproductorder")
	public String addProductOrder(@Valid @ModelAttribute("productOrder") ProductOrders order, BindingResult result) {
		
		ordersService.create(order);
		inventoryService.batchCreate(order);
		
		return "redirect:/admin/listproduct";
	}

	@RequestMapping("editproductorder")
	public String editProductOrder(@ModelAttribute("id") int id, Model model) {
		
		model.addAttribute("productOrder", ordersService.retrieve(id));
		
		return "editproductorder";
	}
	
	@RequestMapping("updproductorder")
	public String updProductOrder(@Valid @ModelAttribute("productOrder") ProductOrders order, BindingResult result) {
		
		ordersService.update(order);
		
		return "redirect:/admin/listproductorders";
	}
	
	@RequestMapping(value = "prdorderpaging", method = RequestMethod.GET)
	public String handlePrdOrdeersRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			prdOrderList.nextPage();
		} else if ("prev".equals(page)) {
			prdOrderList.previousPage();
		} else if (pgNum != -1) {
			prdOrderList.setPage(pgNum);
		}
		model.addAttribute("objectList", prdOrderList);
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
