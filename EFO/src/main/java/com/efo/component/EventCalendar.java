package com.efo.component;

import java.io.Serializable;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
public class EventCalendar implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int year;
	private int month;
	private int day;
	
	private int num_events;
	
	@Transient
	private boolean today;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isToday() {
		return today;
	}

	public void setToday(boolean today) {
		this.today = today;
	}

	public int getNum_events() {
		return num_events;
	}

	public void setNum_events(int num_events) {
		this.num_events = num_events;
	}

}
