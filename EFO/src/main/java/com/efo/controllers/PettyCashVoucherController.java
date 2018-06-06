package com.efo.controllers;

import java.io.IOException;
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

import com.efo.entity.PettyCash;
import com.efo.entity.PettyCashVoucher;
import com.efo.service.FetalTransactionService;
import com.efo.service.PettyCashService;
import com.efo.service.PettyCashVoucherService;

@Controller
@RequestMapping("/accounting/")
public class PettyCashVoucherController {
	private final String pageLink = "/accounting/pettycashpaging";

	@Autowired
	private PettyCashVoucherService pettyCashVoucherService;
	
	@Autowired
	private PettyCashService pettyCashService;
	
	@Autowired
	FetalTransactionService fetalTransactionService;


	PagedListHolder<PettyCashVoucher> pcList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("listpettycash")
	public String listPettyCash(Model model) {
		
		pcList = pettyCashVoucherService.retrieveList();
		
		model.addAttribute("pettyCash", pettyCashService.retrieve(1));
		model.addAttribute("objectList", pcList);
		model.addAttribute("pagelink", pageLink);
		
		return "listpettycash";
	}
	
	@RequestMapping("changeamounts")
	public String changeAmounts(Model model) {
		PettyCash pettyCash =  null;
		
		if (pettyCashService.exists()) {
			pettyCash = pettyCashService.retrieve(1);
		}else{
			pettyCash = new PettyCash();
			pettyCash.setPc_id(1);
		}
		model.addAttribute("pettyCash", pettyCash);
		
		return "changeamounts";
	}
	
	@RequestMapping("updchange")
	public String updateChange(@ModelAttribute("pettyCash") PettyCash pettyCash, BindingResult result) {
		
		pettyCashService.saveOfUpdate(pettyCash);
		
		return "redirect:/accounting/listpettycash";
	}
	
	@RequestMapping("newpettycash")
	public String newPettyCash(Model model) {
		
		model.addAttribute("pettyCashVoucher", new PettyCashVoucher(new Date()));
		
		return "newpettycash";
	}
	
	@RequestMapping("addpettycash")
	public String addPettyCash(@Valid @ModelAttribute("pettyCashVoucher") PettyCashVoucher pettyCashVoucher, BindingResult result) throws RecognitionException, IOException, RuntimeException {
		
		if (result.hasErrors()) {
			return "newpettycash";
		}
		
		fetalTransactionService.loadRule("pcdisbursement.trans");
		
		return "redirect:/accounting/listpettycash";
	}
	
	@RequestMapping("editpettycash")
	public String editPettyCash(@ModelAttribute("id") int id, Model model) {
		
		PettyCashVoucher pettyCashVoucher = pettyCashVoucherService.retrieve(id);
		
		model.addAttribute("pettyCashVoucher", pettyCashVoucher);
		
		return "editpettycash";
	}
	
	@RequestMapping("updatepettycash")
	public String updatePettyCash(@Valid @ModelAttribute("pettyCashVoucher") PettyCashVoucher pettyCashVoucher, BindingResult result) throws IOException {
		double adjustmentAmount = 0.00;
		
		PettyCashVoucher oldPc = pettyCashVoucherService.retrieve(pettyCashVoucher.getId());
		if (oldPc.getAmount() != pettyCashVoucher.getAmount()) {
			adjustmentAmount = pettyCashVoucher.getAmount() - oldPc.getAmount();
			fetalTransactionService.pettyCashAdjustment(pettyCashVoucher, adjustmentAmount);
		}
		
		pettyCashVoucherService.update(pettyCashVoucher);
		
		return "redirect:/accounting/listpettycash";
	}
	
	@RequestMapping(value = "pettycashpaging", method = RequestMethod.GET)
	public String handleUserRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			pcList.nextPage();
		} else if ("prev".equals(page)) {
			pcList.previousPage();
		} else if (pgNum != -1) {
			pcList.setPage(pgNum);
		}
		model.addAttribute("objectList", pcList);
		model.addAttribute("pagelink", pageLink);

		return "listcustomers";
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
