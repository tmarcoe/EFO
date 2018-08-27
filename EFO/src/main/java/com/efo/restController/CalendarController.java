package com.efo.restController;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.component.EventCalendar;

@RestController
@RequestMapping("/rest/")
public class CalendarController {
	
	@RequestMapping("getcalendar")
	public String getCalendar(@RequestParam(value = "month") int month,
							  @RequestParam(value = "year") int year) throws JSONException {
		LocalDate calDate = new LocalDate();
		LocalDate today = new LocalDate();
		calDate = calDate.withMonthOfYear(month).withDayOfMonth(1);
		int dow = calDate.getDayOfWeek();
		calDate = calDate.minusDays(dow);
		List<EventCalendar> calArray = new ArrayList<EventCalendar>();
		
		for(int i=0;i < 42; i++) {
			EventCalendar event = new EventCalendar();
			event.setYear(calDate.getYear());
			event.setMonth(calDate.getMonthOfYear());
			event.setDay(calDate.getDayOfMonth());
			if (DateTimeComparator.getDateOnlyInstance().compare(calDate.toDate(), today.toDate()) == 0 ) {
				event.setToday(true);
			}else{
				event.setToday(false);
			}
			calArray.add(event);
			calDate = calDate.plusDays(1);
		}
		
		return calendarToJSON(calArray, month, year);
	}
	
	private String calendarToJSON(List<EventCalendar> calArray, int month, int year) throws JSONException {
		JSONObject json = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		json.put("calMonth", month);
		json.put("calYear", year);
		for (EventCalendar event : calArray) {
			JSONObject element = new JSONObject();
			element.put("year", event.getYear());
			element.put("month", event.getMonth());
			element.put("day", event.getDay());
			element.put("num_events", event.getNum_events());
			element.put("isToday", event.isToday());
			jArray.put(element);
		}
		json.put("calendar", jArray);
		
		return json.toString();
	}
}
