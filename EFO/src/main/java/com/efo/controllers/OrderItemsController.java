package com.efo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.antlr.v4.runtime.RecognitionException;
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

import com.efo.component.ScheduleUtilities;
import com.efo.component.ScheduleUtilities.ScheduleType;
import com.efo.entity.Payables;
import com.efo.entity.PaymentsBilled;
import com.efo.entity.EachInventory;
import com.efo.entity.Product;
import com.efo.entity.OrderItems;
import com.efo.service.FetalTransactionService;
import com.efo.service.EachInventoryService;
import com.efo.service.OrdersItemService;
import com.efo.service.ProductService;

@Controller
@RequestMapping("/admin/")
public class OrderItemsController {
	private final String pageLink = "/admin/prdorderpaging";

	private SimpleDateFormat dateFormat;
	private PagedListHolder<OrderItems> prdOrderList;

	@Autowired
	private OrdersItemService ordersService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	FetalTransactionService fetalService;
	
	@Autowired
	private EachInventoryService inventoryService;
	
	@Autowired
	ScheduleUtilities sched;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("listproductorders")
	public String listOrderItems(Model model) {
		prdOrderList = ordersService.retrieveOpenOrders();
		prdOrderList.setPageSize(20);
		
		model.addAttribute("objectList", prdOrderList);
		model.addAttribute("pagelink", pageLink);

		return "listproductorders";
	}

	@RequestMapping("newproductorder")
	public String newOrder(@ModelAttribute("sku") String sku, Model model) {

		Product product = productService.retrieve(sku);
		model.addAttribute("productOrder", new OrderItems(sku, new Date(), product.getProduct_name()));

		return "newproductorder";
	}

	@RequestMapping("addproductorder")
	public String addProductOrder(@Valid @ModelAttribute("productOrder") OrderItems productOrders,
			BindingResult result, Model model) throws IOException {

		if (result.hasErrors()) {
			
 			return "newproductorder";

		}
		Payables payables = productOrders.getPayables();
		productOrders.setPayables(null);
		ordersService.create(productOrders);
		PaymentsBilled payments = null;

		
		Product product = productService.retrieve(productOrders.getSku());
		productOrders.setProduct_name(product.getProduct_name());
		productOrders.setProduct(product);
		if (productOrders.getPayment_type().compareTo("Credit") == 0) {
			productOrders.setPayables(payables);
			payables.setOrderItems(productOrders);
			payables.setReference(productOrders.getReference());
			payments = new PaymentsBilled();
			payments.setReference(productOrders.getReference());
			payments.setDate_due(sched.nextPayment(productOrders.getPayables().getDate_begin(), 
								productOrders.getPayables().getDate_begin(), ScheduleType.MONTHLY));
			payments.setPayment_due(productOrders.getPayables().getEach_payment());
			

			Set<PaymentsBilled> paymentsList = new HashSet<PaymentsBilled>();
			paymentsList.add(payments);
			productOrders.getPayables().setPayments(paymentsList);
		}else{
			productOrders.setPayables(null);
		}
		
		fetalService.purchaseInventory(product, productOrders, payables, new PaymentsBilled());
		if ( product.getUnit().compareTo("Each") == 0 || product.getUnit().compareTo("Pack") == 0 ) {
			EachInventory inventory = new EachInventory();
			inventory.setSku(product.getSku());
			inventory.setOrdered(new Date());
			inventory.setInvoice_num(productOrders.getInvoice_num());
			inventory.setWholesale(productOrders.getWholesale()/ productOrders.getAmt_ordered());
			int qty = (int) productOrders.getAmt_ordered();
			inventoryService.stockShelf(inventory, qty);
		}
		
		
		return "redirect:/admin/listproduct";
	}

	@RequestMapping("editproductorder")
	public String editProductOrder(@ModelAttribute("reference") Long reference, Model model) {
		
		OrderItems orders =  ordersService.retrieve(reference);
		
		model.addAttribute("product", productService.retrieve(orders.getSku()));
		model.addAttribute("productOrder", orders);

		return "editproductorder";
	}

	@RequestMapping("updproductorder")
	public String updProductOrder(@Valid @ModelAttribute("productOrder") OrderItems order, BindingResult result) {

		ordersService.update(order);

		return "redirect:/admin/listproductorders";
	}
	
	@RequestMapping("receiveorder")
	public String receiveOrder(@ModelAttribute("reference") Long reference, Model model) {
		OrderItems order = ordersService.retrieve(reference);
		
		order.setDelivery_date(new Date());
		
		model.addAttribute("order", order);
		
		return "receiveorder";
	}
	
	@RequestMapping("stockorder")
	public String stockOrder(@ModelAttribute("productOrder") OrderItems order) throws RecognitionException, IOException, RuntimeException {
		
		Product product = productService.retrieve(order.getSku());
		
		if (order.getDelivery_date() == null) {
			order.setDelivery_date(new Date());
		}
		
		fetalService.orderDelivered(order, product);
		if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0 ) {
			inventoryService.markAsDelivered(order, (new Double(order.getAmt_this_shipment())).intValue());
		}
		
		return "redirect:/admin/listproductorders";
	}
	
	@RequestMapping("cancelorder")
	public String cancelOrder(@ModelAttribute("reference") Long reference) throws IOException {
		
		OrderItems orders = ordersService.retrieve(reference);
		if (orders.getAmt_received() > 0) {
			return "/admin/listproductorders?error=true";
		}
		Product product = productService.retrieve(orders.getSku());
		fetalService.cancelOrder(orders, product.getFluidInventory());
		
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
