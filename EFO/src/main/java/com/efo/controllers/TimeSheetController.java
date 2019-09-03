package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.TimeReportingAccounts;
import com.efo.entity.TimeSheet;
import com.efo.entity.TimeSheetItems;
import com.efo.entity.User;
import com.efo.service.TimeSheetItemsService;
import com.efo.service.TimeSheetService;
import com.efo.service.UserService;

@Controller
@RequestMapping("/timesheet/")
public class TimeSheetController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TimeSheetService timeSheetService;
	
	@Autowired TimeSheetItemsService timeSheetItemsService;

	private final String pageLink = "/timesheet/tsapprovepaging";	
	private PagedListHolder<TimeSheet> tsList;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(HashSet.class, new CustomCollectionEditor(HashSet.class, true));
	}

	@RequestMapping("timesheet")
	public String timeSheet(Principal principal, Model model) {
		LocalDate date = LocalDate.now();
		int dow = date.dayOfWeek().get();
		LocalDate prevSun = dow == DateTimeConstants.SUNDAY ? date : date.withDayOfWeek(DateTimeConstants.SUNDAY).minusWeeks(1);
		User user =  userService.retrieve(principal.getName());
		
		TimeSheet timeSheet = timeSheetService.retrieveByUserId(user.getUser_id());
		
		if (timeSheet == null) {
			timeSheet = new TimeSheet();
			timeSheet.setUser_id(user.getUser_id());
			timeSheet.setBegin_period(prevSun.toDate());
			timeSheet.setName(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
			timeSheetService.create(timeSheet);
		}else if (timeSheet.getTimeSheetItems().size() == 0){
			Set<TimeSheetItems> itemsList = new HashSet<TimeSheetItems>(timeSheetItemsService.retrieveRawList(timeSheet.getReference()));
			if (itemsList.size() > 0) {
				timeSheet.getTimeSheetItems().addAll(itemsList);
				timeSheetService.merge(timeSheet);
			}
		}
		
		LocalDate endPeriod = new LocalDate(timeSheet.getBegin_period()).plusDays(6);
		List<TimeReportingAccounts> accounts = new ArrayList<>(user.getEmployee().getTimeReportingAccounts());
		
		model.addAttribute("endPeriod", endPeriod.toDate());
		model.addAttribute("timeSheet", timeSheet);
		model.addAttribute("accounts", accounts);
		
		return "timesheet";
	}
	
	@RequestMapping("updatetsitem")
	public String updateTimeSheetItem(@ModelAttribute("id") Long id,
									  @ModelAttribute("sun") Double sun,
									  @ModelAttribute("mon") Double mon,
									  @ModelAttribute("tue") Double tue,
									  @ModelAttribute("wed") Double wed,
									  @ModelAttribute("thu") Double thu,
									  @ModelAttribute("fri") Double fri,
									  @ModelAttribute("sat") Double sat) {
		
		TimeSheetItems timeSheetItems = timeSheetItemsService.retrieve(id);
		timeSheetItems.setSun(sun);
		timeSheetItems.setMon(mon);
		timeSheetItems.setTue(tue);
		timeSheetItems.setWed(wed);
		timeSheetItems.setThu(thu);
		timeSheetItems.setFri(fri);
		timeSheetItems.setSat(sat);
		
		timeSheetItemsService.update(timeSheetItems);
		
		return "redirect:/timesheet/timesheet";
	}
	
	@RequestMapping("addtsitem")
	public String addTimeSheetItem(@ModelAttribute("reference") Long reference,
								   @ModelAttribute("account_num") String account_num,
								   @ModelAttribute("sun") Double sun,
								   @ModelAttribute("mon") Double mon,
								   @ModelAttribute("tue") Double tue,
								   @ModelAttribute("wed") Double wed,
								   @ModelAttribute("thu") Double thu,
								   @ModelAttribute("fri") Double fri,
								   @ModelAttribute("sat") Double sat) {
		
		TimeSheet timeSheet = timeSheetService.retrieve(reference);
		TimeSheetItems timeSheetItems = new TimeSheetItems();
		timeSheetItems.setReference(timeSheet.getReference());
		timeSheetItems.setAccount_num(account_num);
		timeSheetItems.setSun(sun);
		timeSheetItems.setMon(mon);
		timeSheetItems.setTue(tue);
		timeSheetItems.setWed(wed);
		timeSheetItems.setThu(thu);
		timeSheetItems.setFri(fri);
		timeSheetItems.setSat(sat);
		timeSheet.getTimeSheetItems().add(timeSheetItems);
		timeSheetItems.setTimeSheet(timeSheet);
		
		timeSheetService.merge(timeSheet);
		
		return "redirect:/timesheet/timesheet";
	}
	
	@RequestMapping("listsubmittedts")
	public String listSubittedTimeSheet(Model model) {
		
		tsList = timeSheetService.listSubmitted();
		
		model.addAttribute("objectList", tsList);
		model.addAttribute("pagelink", pageLink);
		
		return "listsubmittedts";
	}
	
	@RequestMapping("viewtimesheet")
	public String viewTimeSheet(@ModelAttribute("reference") Long reference, Model model) {
		
		TimeSheet timeSheet = timeSheetService.retrieve(reference);
		LocalDate endPeriod = new LocalDate(timeSheet.getBegin_period()).plusDays(6);
		
		model.addAttribute("timeSheet", timeSheet);
		model.addAttribute("endPeriod", endPeriod.toDate());
		
		
		return "viewtimesheet";
	}
	
	@RequestMapping("submitts")
	public String submitTimeSheet(@ModelAttribute("reference") Long reference) {
		
		TimeSheet timeSheet = timeSheetService.retrieve(reference);
		timeSheet.setSubmitted(new Date());
		timeSheet.setRejected(null);
		
		timeSheetService.merge(timeSheet);
		
		return "redirect:/";
	}
	
	@RequestMapping("acceptts")
	public String acceptTimeSheet(@ModelAttribute("reference") Long reference) {
		
		TimeSheet timeSheet = timeSheetService.retrieve(reference);
		
		timeSheet.setApproved(new Date());
		
		timeSheetService.merge(timeSheet);
		
		return "redirect:/timesheet/listsubmittedts";
	}
	
	@RequestMapping("rejectts")
	public String rejectTimeSheet(@ModelAttribute("reference") Long reference) {
		
		TimeSheet timeSheet = timeSheetService.retrieve(reference);
		timeSheet.setRejected(new Date());
		timeSheet.setSubmitted(null);
		
		timeSheetService.merge(timeSheet);
		
		
		return "redirect:/timesheet/listsubmittedts";
	}
		
	
	@RequestMapping(value = "tsapprovepaging", method = RequestMethod.GET)
	public String handleTsApproveRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			tsList.nextPage();
		} else if ("prev".equals(page)) {
			tsList.previousPage();
		} else if (pgNum != -1) {
			tsList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", tsList);
		model.addAttribute("pagelink", pageLink);

		return "listsubmittedts";
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
