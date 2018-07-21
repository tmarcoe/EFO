package com.efo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.efo.component.ScheduleUtilities;
import com.efo.entity.FluidInventory;
import com.efo.entity.PaymentsReceived;
import com.efo.entity.Product;
import com.efo.entity.Receivables;
import com.efo.entity.RetailSales;
import com.efo.entity.SalesItem;
import com.efo.entity.User;
import com.efo.service.EachInventoryService;
import com.efo.service.FetalTransactionService;
import com.efo.service.InvoiceNumService;
import com.efo.service.PaymentsReceivedService;
import com.efo.service.ProductService;
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
	private ProductService productService;
	
	@Autowired
	private RetailSalesService retailSalesService;
	
	@Autowired
	private PaymentsReceivedService paymentsService;
	
	@Autowired
	private EachInventoryService eachInventoryService;
	
	@Autowired
	private SalesItemService salesItemService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FetalTransactionService transactionService;
	
	@Autowired
	ScheduleUtilities sched;
	
	@Autowired
	InvoiceNumService invoiceNumService;
	
	PagedListHolder<RetailSales> salesList;
	
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
		if (sales == null) {
			sales = new RetailSales();
			sales.setOrdered(new Date());
			sales.setUser_id(user.getUser_id());
			sales.setChanged(true);
			retailSalesService.create(sales);
			sales.setSalesItem(new HashSet<SalesItem>(0));
		}
		
		model.addAttribute("sales", sales);
		
		return "browseproducts";
	}
	
	@RequestMapping("additem")
	public String addItem(@ModelAttribute("reference") Long reference, 
						  @ModelAttribute("sku") String sku, 
						  @ModelAttribute("order_qty") Double order_qty, Model model) {
		
		RetailSales sales = retailSalesService.retrieve(reference);
		Product product = productService.retrieve(sku);
		SalesItem item = salesItemService.getItemBySku(sales.getReference(), sku);
		if (item == null) {
			item = new SalesItem();
			item.setReference(sales.getReference());
			item.setProduct(product);
			item.setProduct_name(product.getProduct_name());
			item.setQty(order_qty.doubleValue());
			item.setRegular_price(product.getPrice());
			item.setRetailSales(sales);
			item.setSku(sku);
			item.setSold_for(product.getPrice());
			sales.getSalesItem().add(item);
		}else{
			FluidInventory inventory = null;
			if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0) {
				inventory = new FluidInventory();
				inventory.setAmt_in_stock(eachInventoryService.getAmountReceived(product.getSku())); 
			}else{
				inventory = product.getFluidInventory();
			}
			if ((item.getQty() + order_qty) > inventory.getAmt_in_stock()) {				
				return "redirect:/admin/browseproducts";
			}

			salesItemService.addQuantity(item, order_qty);
		}
		sales.setChanged(true);

		retailSalesService.merge(sales);

		
		model.addAttribute("sales", sales);
			
		return "redirect:/admin/browseproducts";
	}
	
	@RequestMapping("cancelsales")
	public String cancelOrder(Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		retailSalesService.cancelSales(sales.getReference());
		
		return "redirect:/admin/browseproducts";
	}
	
	@RequestMapping("processorder")
	public String processOrder(@Valid @ModelAttribute("sales") RetailSales sales, BindingResult result, Model model) {
		
		Set<SalesItem> itemList = new HashSet<SalesItem>(salesItemService.retrieveRawList(sales.getReference()));
		sales.setSalesItem(itemList);
		
		if (sales.isChanged()) {
			sales.setTotal_price(totalOrder(sales));	
		}
		
		sales.setInvoice_num(invoiceNumService.getNextKey());
		
		sales.setReceivables(new Receivables());
		sales.getReceivables().setRetailSales(sales);
		sales.getReceivables().setReference(sales.getReference());
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
		if (sales.getCustomer_name().length() == 0 ) {
			result.rejectValue("customer_name", "NotBlank.sales.customer_name");
			return "processorder";
		}
		
		if (sales.getPayment_type().length() == 0 ) {
			result.rejectValue("payment_type", "NotBlank.sales.payment_type");
			return "processorder";
		}
		sales.getReceivables().setInvoice_date(sales.getOrdered());
		PaymentsReceived payment = null;
		Date latest_date = null;
		sales.setProcessed(new Date());
		sales.setSalesItem(new HashSet<SalesItem>(salesItemService.retrieveRawList(sales.getReference())));
	
		if (sales.getPayment_type().compareTo("Cash") == 0) {
			sales.setReceivables(null);
		}else{
			Receivables receivables = sales.getReceivables();

			payment = new PaymentsReceived();
			latest_date = paymentsService.latestDate(sales.getReference());
			if (latest_date == null) {
				latest_date = sales.getReceivables().getInvoice_date();
			}
			payment.setReference(sales.getReference());
			payment.setPayment_due(sales.getReceivables().getEach_payment());
			payment.setReceivables(sales.getReceivables());

			receivables.getPayments().add(payment);
			receivables.setRetailSales(sales);
		}

		transactionService.retailSalesOrder(sales, payment, latest_date);
		
		
		
		return "redirect:/admin/browseproducts";
	}
	
	@RequestMapping("listsales")
	public String listSales(Model model) {
		
		salesList = retailSalesService.getProcessedOrders();

		model.addAttribute("objectList", salesList);
		model.addAttribute("pagelink", pageLink);
		
		return "listsales";
	}
	
	@RequestMapping("deletesalesitem")
	public String deleteSalesItem(@ModelAttribute("item_id") int item_id, Principal principal) {
		salesItemService.deleteSalesItem(item_id);
		User user = userService.retrieve(principal.getName());
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		if (salesItemService.rowCount(sales.getReference()) == 0) {
			retailSalesService.cancelSales(sales.getReference());
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
	public String updateSalesItem(@Valid @ModelAttribute("salesItem") SalesItem salesItem, BindingResult result) {
		
		SalesItem oldItem = salesItemService.retrieve(salesItem.getItem_id());
		Product product = productService.retrieve(salesItem.getSku());
		if (salesItem.getQty() > product.getFluidInventory().getAmt_in_stock()) {
			result.rejectValue("qty", "Quantity.salesItem.qty");
			
			return "editsalesitem";
		}
		if(oldItem.getQty() != salesItem.getQty()) {
			salesItemService.update(salesItem);
			RetailSales sales = retailSalesService.retrieve(salesItem.getReference());
			sales.setChanged(true);
			retailSalesService.merge(sales);
			if (salesItem.getQty() == 0) {
				salesItemService.deleteSalesItem(salesItem.getItem_id());
			}
		}else{
			salesItemService.update(salesItem);
		}
		
		return "redirect:/admin/browseproducts";
	}
	@RequestMapping("editsales")
	public String editSales(@ModelAttribute("reference") Long reference, Model model) {
		RetailSales retailSales = retailSalesService.retrieve(reference);
		
		model.addAttribute("sales", retailSales);
		
		return "editsales";
	}
	
	@RequestMapping("updatesales")
	public String updateSales(@Valid @ModelAttribute("sales") RetailSales sales, BindingResult result) {
		
		retailSalesService.merge(sales);
		
		return "redirect:/admin/listsales";
	}
	
	@RequestMapping("shipsales")
	public String shipSales(@ModelAttribute("reference") Long reference) throws IOException {
		RetailSales sales = retailSalesService.retrieve(reference);
		sales.setSalesItem(new HashSet<SalesItem>(salesItemService.retrieveRawList(reference)));
		
		transactionService.shipSales(sales);
		eachInventoryService.depleteStock(sales.getSalesItem());
		return "redirect:/admin/listsales";
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
		sales.setChanged(false);
		return total;
	}
	
}
