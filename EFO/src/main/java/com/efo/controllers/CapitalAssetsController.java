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

import com.efo.component.ScheduleUtilities;
import com.efo.entity.CapitalAssets;
import com.efo.entity.PaymentsBilled;
import com.efo.service.CapitalAssetsService;
import com.efo.service.FetalTransactionService;
import com.efo.service.PaymentsBilledService;

@Controller
@RequestMapping("/accounting/")
public class CapitalAssetsController {
	private final String pageLink = "/accounting/assetspaging";
	private PagedListHolder<CapitalAssets> assetsList;

	@Autowired
	private CapitalAssetsService capitalAssetsService;
	
	@Autowired
	private PaymentsBilledService billedService;
	
	@Autowired
	private ScheduleUtilities sched;
	
	@Autowired
	FetalTransactionService transactionService;
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("listassets")
	public String listAssets(Model model) {
		assetsList = capitalAssetsService.retrieveList();
		
		model.addAttribute("objectList", assetsList);
		model.addAttribute("pagelink", pageLink);
		
		return "listassets";
	}
	
	@RequestMapping("newasset")
	public String newAsset(Model model) {
		
		model.addAttribute("asset", new CapitalAssets(new Date()));
		
		return "newasset";
	}
	
	@RequestMapping("addasset")
	public String addAsset(@Valid @ModelAttribute("assets") CapitalAssets assets, BindingResult result) throws Exception {
		PaymentsBilled payments = null;
		
		if (assets.getPurchase_type().compareTo("Cash") == 0) {
			assets.setPayables(null);
		}else{
			payments = new PaymentsBilled();
			Date lastPayment = billedService.lastestDate(assets.getInvoice_num());
			if (lastPayment == null) lastPayment = assets.getDate_purchased();
			payments.setDate_due(sched.nextPayment(assets.getDate_purchased(), lastPayment, assets.getPayables().getSchedule()));
		}
		
		transactionService.purchaseCapital(assets, payments);
		
		return "redirect:/accounting/listassets";
	}
	@RequestMapping("editasset")
	public String editAsset(@ModelAttribute("invoice_num") String invoice_num, Model model) {
		CapitalAssets asset = capitalAssetsService.retrieve(invoice_num);
		
		model.addAttribute("asset", asset);
		
		return "editasset";
	}
	
	@RequestMapping("updateasset")
	public String updateAsset(@ModelAttribute("asset") CapitalAssets asset) {
		
		capitalAssetsService.update(asset);
		
		return "redirect:/accounting/listassets";		
	}
	
	@RequestMapping(value = "assetspaging", method = RequestMethod.GET)
	public String handleAssetsRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			assetsList.nextPage();
		} else if ("prev".equals(page)) {
			assetsList.previousPage();
		} else if (pgNum != -1) {
			assetsList.setPage(pgNum);
		}
		model.addAttribute("objectList", assetsList);
		model.addAttribute("pagelink", pageLink);

		return "listassets";
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
