package com.efo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.efo.component.SalesItemProcessor;
import com.efo.entity.GeneralLedger;
import com.efo.entity.Product;
import com.efo.entity.Receivables;
import com.efo.entity.RetailSales;
import com.efo.entity.SalesItem;
import com.efo.entity.User;
import com.efo.service.FetalTransactionService;
import com.efo.service.RetailSalesService;
import com.efo.service.SalesItemService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/admin/")
public class RetailSalesController {
	private final String pageLink = "/accounting/salespaging";
	
	@Value("${efo.creditTerms.downPayment}")
	private String downPayment;
	
	@Value("${efo.creditTerms.interest}")
	private String interest;
	
	@Value("${efo.creditTerms.numberOfPayments}")
	private String numberOfPayments;
	
	@Value("${efo.creditTerms.schedule}")
	private String schedule;
	
	@Autowired
	private RetailSalesService retailSalesService;
	
	@Autowired
	private SalesItemService salesItemService;
	
	@Autowired
	private SalesItemProcessor itemProcessor;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FetalTransactionService transactionService;
	
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
		sales.setChanged(true);
		
		retailSalesService.merge(sales);
		
		
		return "redirect:/admin/browseproducts";
	}
	
	@RequestMapping("cancelsales")
	public String cancelOrder(Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		retailSalesService.cancelSales(sales.getInvoice_num());
		
		return "redirect:/admin/browseproducts";
	}
	
	@RequestMapping("processorder")
	public String processOrder(Principal principal, Model model) {
		User user = userService.retrieve(principal.getName());
		
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		
		
		if (sales.isChanged()) {
			sales.setTotal_price(totalOrder(sales));
			sales.setChanged(false);
		}
		sales.setReceivables(new Receivables());
		sales.getReceivables().setRetailSales(sales);
		sales.getReceivables().setInvoice_num(sales.getInvoice_num());
		sales.getReceivables().setInvoice_date(sales.getProcessed());
		sales.getReceivables().setTotal_due(sales.getTotal_price());
		sales.getReceivables().setDown_payment(Double.valueOf(downPayment));
		sales.getReceivables().setInterest(Double.valueOf(interest));
		sales.getReceivables().setNum_payments(Long.valueOf(numberOfPayments));
		sales.getReceivables().setSchedule(schedule);
		
		model.addAttribute("sales", sales);
		
		return "processorder";
	}
	@RequestMapping("updorder")
	public String updOrder(@Valid @ModelAttribute("sales") RetailSales sales, BindingResult result) throws IOException {
		sales.setProcessed(new Date());
		
		if (sales.getPayment_type().compareTo("Cash") == 0) {
			sales.setReceivables(null);
		}else{
			sales.getReceivables().setCustomer(sales.getCustomer_name());	
			sales.getReceivables().setInvoice_date(sales.getProcessed());
		}
		
		sales.setSalesItem(new HashSet<SalesItem>(salesItemService.retrieveRawList(sales.getInvoice_num())));
		transactionService.retailSalesOrder(sales, new SalesItemProcessor());
		itemProcessor.commitItems(sales.getSalesItem());
		retailSalesService.merge(sales);
		
		return "redirect:/#Tabs-4";
	}
	
	@RequestMapping("deletesalesitem")
	public String deleteSalesItem(@ModelAttribute("item_id") int item_id, Principal principal) {
		salesItemService.deleteSalesItem(item_id);
		User user = userService.retrieve(principal.getName());
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		if (salesItemService.rowCount(sales.getInvoice_num()) == 0) {
			retailSalesService.cancelSales(sales.getInvoice_num());
		}else{
			sales.setChanged(true);
			retailSalesService.merge(sales);
		}
		
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
	
	private double totalOrder(RetailSales sales) {
		
		double total = 0.0;
		for (SalesItem item : sales.getSalesItem()) {
			total += (item.getSold_for() * item.getQty());
		}
		
		return total;
	}
	
}
