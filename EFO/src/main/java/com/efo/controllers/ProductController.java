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

import com.efo.entity.Product;
import com.efo.service.ProductService;

@Controller
@RequestMapping("/accounting/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	private final String pageLink = "/accounting/appaging";
	
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

	@RequestMapping(value = "productpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
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
