package com.efo.controllers;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.component.ProfileUtils;
import com.efo.component.SendEmail;
import com.efo.emailForms.ApproveEmailForm;
import com.efo.emailForms.RejectEmailForm;
import com.efo.entity.Budget;
import com.efo.entity.Profiles;
import com.efo.entity.Transactions;
import com.efo.entity.User;
import com.efo.service.BudgetItemsService;
import com.efo.service.BudgetService;
import com.efo.service.FetalTransactionService;
import com.efo.service.ProfilesService;
import com.efo.service.TransactionsService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/budget/")
public class ApprovalController {

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private BudgetItemsService budgetItemsService;

	@Autowired
	private ProfilesService profilesService;

	@Autowired
	private FetalTransactionService fetalTransactionService;
	
	@Autowired
	private TransactionsService transactionsService;

	@Autowired
	private ApproveEmailForm approveForm;

	@Autowired
	private RejectEmailForm rejectForm;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private UserService userService;

	@Value("${efo.admin.email}")
	private String adminEmail;

	@Value("${efo.admin.name}")
	private String adminName;

	private final String pageLink = "/budget/approvepaging";
	private PagedListHolder<Budget> approveList;
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("listapprove")
	public String listApprove(Model model) {

		approveList = budgetService.listBudgetsForApproval();

		model.addAttribute("objectList", approveList);
		model.addAttribute("pagelink", pageLink);

		return "listapprove";
	}

	@RequestMapping("approve")
	public String approveBudget(@ModelAttribute("reference") Long reference) throws Exception {
		final String profileName = "New Budget";
		Object[] variables = null;

		budgetService.approveBudget(reference);

		Budget budget = budgetService.retrieve(reference);
		User user = userService.retrieve(budget.getUser_id());

		sendEmail.sendHtmlMail(adminEmail, user.getUsername(), adminName, "Regarding the budget you recently submitted",
				approveForm.createApproveEmail(budget.getAuthor(), budget.getTitle()));

		Profiles profile = profilesService.retrieve(profileName);

		if ("".compareTo(profile.getVariables()) != 0) {
			variables = ProfileUtils.getObject(profile.getVariables());
		}

		Transactions transaction = new Transactions();
		transaction.setName(profileName);
		transaction.setTimestamp(new Date());
		transaction.setStart(budget.getApproved());
		transaction.setPayment_name(budget.getTitle());
		transaction.setDescr("Approved budget, submitted by " + budget.getAuthor());
		transaction.setAmount(budget.getTotal());
		transaction.setUser_id(budget.getUser_id());

		fetalTransactionService.execTransaction(profile, transaction, variables);
		
		transactionsService.create(transaction);

		return "redirect:/budget/listapprove";
	}

	@RequestMapping("reject")
	public String rejectBudget(@ModelAttribute("reference") Long reference, @ModelAttribute("reason") String reason)
			throws UnsupportedEncodingException, MessagingException {

		budgetService.rejectBudget(reference, reason);

		Budget budget = budgetService.retrieve(reference);
		User user = userService.retrieve(budget.getUser_id());
		sendEmail.sendHtmlMail(adminEmail, user.getUsername(), adminName, "Regarding the budget you recently submitted",
				rejectForm.createRejectEmail(budget.getAuthor(), budget.getTitle(), budget.getReason()));

		return "redirect:/budget/listapprove";
	}

	@RequestMapping("viewbudget")
	public String viewBudget(@ModelAttribute("reference") Long reference, Model model) {

		List<Object[]> listItems = budgetItemsService.budgetTree(reference);
		Budget budget = budgetService.retrieve(reference);
		
		
		model.addAttribute("budget", budget);
		model.addAttribute("reference", reference);
		model.addAttribute("listItems", listItems);

		return "viewbudget";
	}

	@RequestMapping(value = "approvepaging", method = RequestMethod.GET)
	public String handleApproveRequest(@PathVariable("parent") String parent, @ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			approveList.nextPage();
		} else if ("prev".equals(page)) {
			approveList.previousPage();
		} else if (pgNum != -1) {
			approveList.setPage(pgNum);
		}

		model.addAttribute("objectList", approveList);
		model.addAttribute("pagelink", pageLink);

		return "listapprove";
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
