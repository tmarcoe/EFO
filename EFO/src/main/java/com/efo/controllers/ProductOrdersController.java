package com.efo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.efo.entity.Product;
import com.efo.entity.ProductOrders;
import com.efo.entity.User;
import com.efo.entity.OrderItems;
import com.efo.service.FetalTransactionService;
import com.efo.service.OrdersItemService;
import com.efo.service.ProductOrdersService;
import com.efo.service.ProductService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/admin/")
public class ProductOrdersController {
	private final String pageLink = "/admin/prdorderpaging";

	private SimpleDateFormat dateFormat;
	private PagedListHolder<OrderItems> prdOrderList;

	@Autowired
	private OrdersItemService ordersItemsService;
	
	@Autowired
	private ProductOrdersService ordersService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private FetalTransactionService fetalService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("listproductorders")
	public String listOrderItems(Model model) {
		prdOrderList = ordersItemsService.retrieveOpenOrders();
		prdOrderList.setPageSize(20);

		model.addAttribute("objectList", prdOrderList);
		model.addAttribute("pagelink", pageLink);

		return "listproductorders";
	}

	@RequestMapping("editproductorder")
	public String editProductOrder(@ModelAttribute("reference") Long reference, Model model) {

		OrderItems orders = ordersItemsService.retrieve(reference);

		model.addAttribute("product", productService.retrieve(orders.getSku()));
		model.addAttribute("productOrder", orders);

		return "editproductorder";
	}
	
	@RequestMapping("newproductorder")
	public String newProductOrder(Model model, Principal principal) {
		User user = userService.retrieve(principal.getName());
		
		ProductOrders productOrder = ordersService.findOpenOrder(user.getUser_id());
		if (productOrder == null) {
			productOrder = new ProductOrders();
			productOrder.setPayment_type("Cash");
			productOrder.setUser_id(user.getUser_id());
			ordersService.create(productOrder);
		}
		model.addAttribute("productOrder", productOrder);

		return "newproductorder";
	}

	@RequestMapping("addorderitem")
	public String addOrderItem(@ModelAttribute("sku") String sku, 
							   @ModelAttribute("reference") Long reference,
							   @ModelAttribute("order_qty") Double order_qty,
							   @ModelAttribute("price") Double price) {
		ProductOrders order = ordersService.retrieve(reference);
		Product product = productService.retrieve(sku);
		
		OrderItems item = new OrderItems();
		item.setSku(sku);
		item.setReference(reference);
		item.setAmt_ordered(order_qty);
		item.setWholesale(price);
		item.setProduct_name(product.getProduct_name());
		
		item.setProductOrders(order);
		order.getOrderItems().add(item);
		
		ordersService.merge(order);
		
		
		return "redirect:/admin/newproductorder";
	}

	@RequestMapping("processproductorder")
	public String processProductOrder(@Valid @ModelAttribute("productOrder") ProductOrders productOrder, 
									  BindingResult result, Model model) {
		productOrder.setProcess_date(new Date());
		productOrder.setPayment_type("");
		productOrder.setOrderItems(ordersItemsService.retrieveChildItems(productOrder.getReference()));
		
		model.addAttribute("productOrder", productOrder);
		
		return "processproductorder";
	}
	
	@RequestMapping("markasordered")
	public String markAsOrdered(@Valid @ModelAttribute("productOrder") ProductOrders productOrder, BindingResult result, Model model) {
		
		
		
		return "redirect:/admin/listproducts";
	}
	
	@RequestMapping("updproductorder")
	public String updProductOrder(@Valid @ModelAttribute("productOrder") OrderItems order, BindingResult result) {

		ordersItemsService.update(order);

		return "redirect:/admin/listproductorders";
	}

	@RequestMapping("receiveorder")
	public String receiveOrder(@ModelAttribute("reference") Long reference, Model model) {
		OrderItems order = ordersItemsService.retrieve(reference);

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
		if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0) {
			// inventoryService.markAsDelivered(order, (new
			// Double(order.getAmt_this_shipment())).intValue());
		}

		return "redirect:/admin/listproductorders";
	}

	@RequestMapping("cancelorder")
	public String cancelOrder(@ModelAttribute("reference") Long reference) throws IOException {

		OrderItems orders = ordersItemsService.retrieve(reference);
		if (orders.getAmt_received() > 0) {
			return "/admin/listproductorders?error=true";
		}
		Product product = productService.retrieve(orders.getSku());
		fetalService.cancelOrder(orders, product.getFluidInventory());

		return "redirect:/admin/listproductorders";
	}

	@RequestMapping("addproductorder")
	public String addProductOrder(@Valid @ModelAttribute("productOrder") ProductOrders productOrder, BindingResult result) {

		return "redirect:/admin/listproduct";
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
