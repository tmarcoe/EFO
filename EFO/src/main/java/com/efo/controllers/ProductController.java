package com.efo.controllers;

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
import com.efo.service.ProductService;

@Controller
@RequestMapping("/admin/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
		
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
		prdList.setPageSize(20);
		prdList.setPage(0);
		
		model.addAttribute("objectList", prdList);
		model.addAttribute("pagelink", pageLink);

		return "listproduct";
	}
		
	@RequestMapping("newproduct")
	public String newProduct(Model model) {
		
		model.addAttribute("product", new Product());
		
		return "newproduct";
	}
	
	@RequestMapping("addproduct")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		
		if ("Each".compareTo(product.getUnit()) != 0 && "Pack".compareTo(product.getUnit()) != 0) {
			product.getFluidInventory().setSku(product.getSku());
			product.getFluidInventory().setProduct(product);
			productService.merge(product);
		}else{
			product.setFluidInventory(null);
			productService.create(product);
		}
		
		return "redirect:/admin/listproduct";
	}

	@RequestMapping("editproduct")
	public String editProduct(@ModelAttribute("sku") String sku, Model model) {
		Product product = productService.retrieve(sku);
		model.addAttribute("product", product);
		
		if ("Each".compareTo(product.getUnit()) != 0 && "Pack".compareTo(product.getUnit()) != 0) {
			return "editfluidproduct";
		}else{
			return "editeachproduct";
		}
	}

	@RequestMapping("updatefluidproduct")
	public String updateFluidProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		
		productService.merge(product);
		
		return "redirect:/admin/listproduct";
	}
	
	@RequestMapping("updateeachproduct")
	public String updateEachProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		
		product.setFluidInventory(null);
		
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
