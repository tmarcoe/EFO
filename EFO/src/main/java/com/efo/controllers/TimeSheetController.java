package com.efo.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		User user =  userService.retrieve(principal.getName());
		
		TimeSheet timeSheet = timeSheetService.retrieveByUserId(user.getUser_id());
		if (timeSheet == null) {
			DateTime currentDate = new DateTime();
			timeSheet = new TimeSheet();
			timeSheet.setUser_id(user.getUser_id());
			timeSheet.setBegin_period(currentDate.withDayOfWeek(DateTimeConstants.SUNDAY).toDate());
			timeSheetService.create(timeSheet);
		}
		
		model.addAttribute("timeSheet", timeSheet);
		
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
	
	@RequestMapping("assignnumbers")
	public String assignNumbers() {
		
		return "assignnumbers";
	}

}
