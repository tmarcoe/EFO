package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

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

import com.efo.entity.GeneralLedger;
import com.efo.entity.Product;
import com.efo.entity.RetailSales;
import com.efo.entity.SalesItem;
import com.efo.entity.User;
import com.efo.service.RetailSalesService;
import com.efo.service.SalesItemService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/admin/")
public class RetailSalesController {
	private final String pageLink = "/accounting/salespaging";
	
	@Autowired
	private RetailSalesService retailSalesService;
	
	@Autowired
	private SalesItemService salesItemService;
	
	@Autowired
	UserService userService;
	
	PagedListHolder<GeneralLedger> salesList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("browseproducts")
	public String browseProducts(Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		if (sales != null) {
			model.addAttribute("items", sales.getSalesItem());
		}else{
			model.addAttribute("items", new HashSet<SalesItem>(0));
		}
		
		model.addAttribute("product", new Product());
		
		return "browseproducts";
	}

	@RequestMapping("retailorder")
	public String retailOrder(@Valid @ModelAttribute("product") Product product, BindingResult result, Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		
		
		
		if (sales == null) {
			sales = new RetailSales();
			sales.setUser_id(user.getUser_id());
			sales.setChanged(false);
			sales.setOrdered(new Date());
			sales.setTotal_price(0);
			
			retailSalesService.create(sales);
		}
		SalesItem item = salesItemService.getItemBySku(sales.getInvoice_num(), product.getSku());
		if (item == null) {
			item = new SalesItem();
			item.setSku(product.getSku());
			item.setInvoice_num(sales.getInvoice_num());
			item.setProduct_name(product.getProduct_name());
			item.setRegular_price(product.getPrice());
			item.setSold_for(product.getPrice());
			item.setRetailSales(sales);
			item.setQty(product.getOrder_qty());
			product.getSales().add(item);
			sales.getSalesItem().add(item);
		}else{
			salesItemService.addQuantity(item, product.getOrder_qty());
		}
		
		retailSalesService.merge(sales);
		
		
		return "redirect:/admin/browseproducts";
	}
	@RequestMapping("processorder")
	public String processOrder(Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		
		sales.setProcessed(new Date());
		
		retailSalesService.merge(sales);
		
		return "redirect:/admin/browseproducts";
	}
	@RequestMapping("deletesalesitem")
	public String deleteSalesItem(@ModelAttribute("item_id") int item_id) {
		salesItemService.deleteSalesItem(item_id);
		
		return "redirect:/admin/browseproducts";
	}
	@RequestMapping("editsalesitem")
	public String editSalesItem(@ModelAttribute("item_id") int item_id, Model model) {
		
		model.addAttribute("salesItem", salesItemService.retrieve(item_id));
		
		return "editsalesitem";
	}
	
	@RequestMapping("updsalesitem")
	public String updateSalesItem(@ModelAttribute("salesItem") SalesItem salesItem) {
		
		SalesItem oldItem = salesItemService.retrieve(salesItem.getItem_id());
		
		if(oldItem.getQty() != salesItem.getQty()) {
			salesItemService.update(salesItem);
			RetailSales sales = retailSalesService.retrieve(salesItem.getInvoice_num());
			sales.setChanged(true);
			retailSalesService.merge(sales);
		}else{
			salesItemService.update(salesItem);
		}
		
		return "redirect:/admin/browseproducts";
	}
	
	@RequestMapping(value = "salespaging", method = RequestMethod.GET)
	public String handleSalesRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			salesList.nextPage();
		} else if ("prev".equals(page)) {
			salesList.previousPage();
		} else if (pgNum != -1) {
			salesList.setPage(pgNum);
		}
		model.addAttribute("objectList", salesList);
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
