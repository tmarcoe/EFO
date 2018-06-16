package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.efo.entity.RetailSales;
import com.efo.entity.SalesItem;
import com.efo.entity.User;
import com.efo.service.ProductService;
import com.efo.service.RetailSalesService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/admin/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RetailSalesService retailSalesService;
	
	private final String pageLink = "/admin/productpaging";
	
	private SimpleDateFormat dateFormat;
	private PagedListHolder<Product> prdList;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("listproduct")
	public String listProduct(Model model) {
		
		prdList = productService.retrieveList();
		
		model.addAttribute("objectList", prdList);
		model.addAttribute("pagelink", pageLink);

		return "listproduct";
	}
	
	@RequestMapping("browseproducts")
	public String browseProducts(Model model) {
		
		prdList = productService.retrieveList();
		
		model.addAttribute("objectList", prdList);
		model.addAttribute("pagelink", pageLink);
		
		return "browseproducts";
	}
	
	@RequestMapping("productdetail") 
	public String productDetail(@ModelAttribute("sku") String sku, Model model, Principal principal){
		User user = userService.retrieve(principal.getName());
		RetailSales sales = retailSalesService.getOpenInvoice(user.getUser_id());
		Product product = productService.retrieve(sku);

		if (sales == null) {
			sales = new RetailSales();
			sales.setUser_id(user.getUser_id());
			sales.setChanged(false);
			sales.setOrdered(new Date());
			sales.setTotal_price(0);
			
			retailSalesService.create(sales);
		}
		
		
		SalesItem item = new SalesItem();
		item.setSku(sku);
		item.setInvoice_num(sales.getInvoice_num());
		item.setRetailSales(sales);
		product.getSales().add(item);
		sales.getSalesItem().add(item);

		model.addAttribute("item", item);
		model.addAttribute("product", product);
		
		return "productdetail";
	}
	
	@RequestMapping("newproduct")
	public String newProduct(Model model) {
		
		model.addAttribute("product", new Product());
		
		return "newproduct";
	}

	@RequestMapping("addproduct")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		
		product.getInventory().setSku(product.getSku());
		product.getInventory().setProduct(product);
		
		productService.create(product);
		
		return "redirect:/admin/listproduct";
	}
	
	@RequestMapping("editproduct")
	public String editProduct(@ModelAttribute("sku") String sku, Model model) {
		
		model.addAttribute("product", productService.retrieve(sku));
		
		return "editproduct";
	}

	@RequestMapping("updateproduct")
	public String updateProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		
		productService.merge(product);
		
		return "redirect:/admin/listproduct";
	}
	
	@RequestMapping("deleteproduct")
	public String deleteProduct(@ModelAttribute("sku") String sku) {
				
		productService.delete(sku);
		
		return "redirect:/admin/listproduct";
	}
	
	@RequestMapping(value = "productpaging", method = RequestMethod.GET)
	public String handleProductRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			prdList.nextPage();
		} else if ("prev".equals(page)) {
			prdList.previousPage();
		} else if (pgNum != -1) {
			prdList.setPage(pgNum);
		}
		model.addAttribute("objectList", prdList);
		model.addAttribute("pagelink", pageLink);

		return "listproduct";
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
