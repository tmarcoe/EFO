package com.efo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.efo.service.FetalTransactionService;
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
	private ProductService productService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	FetalTransactionService fetalService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping("listproductorders")
	public String listProductOrders(Model model) {
		prdOrderList = ordersService.retrieveOpenOrders();

		model.addAttribute("objectList", prdOrderList);
		model.addAttribute("pagelink", pageLink);

		return "listproductorders";
	}

	@RequestMapping("newproductorder")
	public String newOrder(@ModelAttribute("sku") String sku, Model model) {

		Product product = productService.retrieve(sku);
		List<User> suppliers = vendorService.retrieveRawList("R");

		model.addAttribute("productOrder", new ProductOrders(sku, new Date(), product.getProduct_name()));
		model.addAttribute("suppliers", suppliers);

		return "newproductorder";
	}

	@RequestMapping("addproductorder")
	public String addProductOrder(@Valid @ModelAttribute("productOrder") ProductOrders productOrders,
			BindingResult result, Model model) throws IOException {

		if (result.getErrorCount() > 1) {
			List<User> suppliers = vendorService.retrieveRawList("R");
			model.addAttribute("suppliers", suppliers);
			
			return "newproductorder";

		}

		Product product = productService.retrieve(productOrders.getSku());
		productOrders.setProduct_name(product.getProduct_name());

		if (productOrders.getPayment_type().compareTo("Cash") == 0) {
			fetalService.purchaseInventory(productOrders, product.getInventory());
		}else{
			ordersService.create(productOrders);
			return "redirect:/accounting/newretailpayable?id=" + productOrders.getId();
		}

		return "redirect:/admin/listproduct";
	}

	@RequestMapping("editproductorder")
	public String editProductOrder(@ModelAttribute("id") int id, Model model) {
		
		ProductOrders orders =  ordersService.retrieve(id);
		
		model.addAttribute("product", productService.retrieve(orders.getSku()));
		model.addAttribute("productOrder", orders);

		return "editproductorder";
	}

	@RequestMapping("updproductorder")
	public String updProductOrder(@Valid @ModelAttribute("productOrder") ProductOrders order, BindingResult result) {

		ordersService.update(order);

		return "redirect:/admin/listproductorders";
	}
	
	@RequestMapping("receiveorder")
	public String receiveOrder(@ModelAttribute("id") int id, Model model) {
		
		model.addAttribute("order", ordersService.retrieve(id));
		
		return "receiveorder";
	}
	
	@RequestMapping("stockorder")
	public String stockOrder(@ModelAttribute("productOrder") ProductOrders order) throws RecognitionException, IOException, RuntimeException {
		
		if (order.getDelivery_date() == null) {
			order.setDelivery_date(new Date());
		}
		
		fetalService.orderDelivered(order);
		
		return "redirect:/admin/listproductorders";
	}
	
	@RequestMapping("cancelorder")
	public String cancelOrder(@ModelAttribute("id") int id) throws IOException {
		
		ProductOrders orders = ordersService.retrieve(id);
		if (orders.getAmt_received() > 0) {
			return "/admin/listproductorders?error=true";
		}
		Product product = productService.retrieve(orders.getSku());
		fetalService.cancelOrder(orders, product.getInventory());
		
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
