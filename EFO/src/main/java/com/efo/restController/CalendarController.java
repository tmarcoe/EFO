package com.efo.restController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.component.EventCalendar;
import com.efo.entity.Events;
import com.efo.service.EventsService;

@RestController
@RequestMapping("/rest/")
public class CalendarController {

	@Autowired
	private EventsService eventsService;

	@RequestMapping("getcalendar")
	public String getCalendar(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year) throws JSONException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate calDate = new LocalDate();
		LocalDate today = new LocalDate();
		calDate = calDate.withYear(year).withMonthOfYear(month).withDayOfMonth(1);
		int dow = calDate.getDayOfWeek();
		if (dow != 7) {
			calDate = calDate.minusDays(dow);
		}
		List<EventCalendar> calArray = new ArrayList<EventCalendar>();

		Map<String, Long> countMap = eventsService.getEventCount(calDate.toDate(), calDate.plusDays(42).toDate());
		int count = 0;
		for (int i = 0; i < 42; i++) {
			EventCalendar event = new EventCalendar();
			event.setYear(calDate.getYear());
			event.setMonth(calDate.getMonthOfYear());
			event.setDay(calDate.getDayOfMonth());
			if (DateTimeComparator.getDateOnlyInstance().compare(calDate.toDate(), today.toDate()) == 0) {
				event.setToday(true);
			} else {
				event.setToday(false);
			}
			Long longValue = countMap.get(df.format(calDate.toDate()));

			if (longValue != null) {
				count = new Long(longValue).intValue();
			} else {
				count = 0;
			}
			event.setNum_events(count);

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

	@RequestMapping("getevents")
	public String getEvents(@RequestParam(value = "year") int year, @RequestParam(value = "month") int month, @RequestParam(value = "day") int day)
			throws JSONException {
		LocalDate dt = new LocalDate();
		dt = dt.withYear(year).withMonthOfYear(month).withDayOfMonth(day);

		return eventListToJSON(eventsService.getEvents(dt.toDate()));
	}

	private String eventListToJSON(List<Events> events) throws JSONException {
		JSONArray jArray = new JSONArray();

		for (Events event : events) {
			JSONObject json = new JSONObject();
			json.put("id", event.getId());
			json.put("date", event.getDate());
			json.put("name", event.getName());
			json.put("link", event.getLink());
			json.put("completed", event.isCompleted());
			jArray.put(json);
		}

		return jArray.toString();
	}
}
