package com.efo.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScheduleUtilities {
	
	@Value("${efo.firstQuarter}")
	private String firstQuarter;
	
	@Value("${efo.secondQuarter}")
	private String secondQuarter;
	
	@Value("${efo.thirdQuarter}")
	private String thirdQuarter;
	
	@Value("${efo.fourthQuarter}")
	private String fourthQuarter;
	
	public enum ScheduleType {
		ANNUALLY,
		BIANNUALLY,
		QUARTERLY,
		MONTHLY,
		BIMONTHLY,
		WEEKLY,
		DAILY;	
	}
	
	public static ScheduleType TYPE;
	
	public int dateToQuarter(Date date) {
		int quarter = 0;
		DateFormat df = new SimpleDateFormat("MM-dd");
		String inpDate = df.format(date);
		
		if (inpDate.compareTo(firstQuarter) >= 0 && inpDate.compareTo(secondQuarter) < 0) {
			quarter = 1;
		}else if (inpDate.compareTo(secondQuarter) >= 0 && inpDate.compareTo(thirdQuarter) < 0) {
			quarter = 2;
		}else if (inpDate.compareTo(thirdQuarter) >= 0 && inpDate.compareTo(fourthQuarter) < 0) {
			quarter = 3;
		}else if (inpDate.compareTo(fourthQuarter) >= 0) {
			quarter = 3;
		}
		
		return quarter;
	}
	
	public Date quarterToDate(int quarter) {
		String current = "";
		Calendar cal = Calendar.getInstance();
		switch (quarter) {
		case 1:
			current = firstQuarter;
			break;
		case 2:
			current = secondQuarter;
			break;
		case 3:
			current = thirdQuarter;
			break;
		case 4:
			current = fourthQuarter;
			break;
		}
		
		String[] mmdd = current.split("-");
		Integer month = Integer.valueOf(mmdd[0]);
		Integer day = Integer.valueOf(mmdd[1]);
		
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public Date nextPayment(Date base, Date last, ScheduleType type ) {
		Date result = null;
		
		switch (type) {
		case ANNUALLY:
			result = annually(base, last);
			break;
		case BIANNUALLY:
			result = biAnnually(base, last);
			break;
		case QUARTERLY:
			result = quarterly(base, last);
			break;
		case MONTHLY:
			result = monthly(base, last);
			break;
		case BIMONTHLY:
			result = biMonthly(base, last);
			break;
		case WEEKLY:
			result = weekly(base, last);
			break;
		case DAILY:
			result = daily(last);
			break;
		}
		
		return result;
	}
	
	private Date annually(Date base, Date last) {
		LocalDate result = null;
		LocalDate jBase = new LocalDate(base);
		LocalDate jLast = new LocalDate(last);

		if (jLast.getDayOfYear() < jBase.getDayOfYear()) {
			result = jBase.withYear(jLast.getYear());
		}else{
			result = jBase.withYear(jLast.getYear() + 1);
		}
		
		return result.toDate();
	}
	
	private Date biAnnually(Date base, Date last) {
		LocalDate result = null;
		LocalDate jBase = new LocalDate(base);
		LocalDate jLast = new LocalDate(last);
		LocalDate fPayment = null;
		LocalDate lPayment = null;
		
		LocalDate present = jBase.withYear(jLast.getYear());
		if (present.getMonthOfYear() > 6) {
			lPayment = present;
			fPayment = present.minusMonths(6);
		}else{
			fPayment = present;
			lPayment = present.plusMonths(6);
		}
		if ( jLast.isBefore(fPayment)) {
			result = fPayment;
		}else{
			result = lPayment;
		}
		return result.toDate();
	}
	
	private Date quarterly(Date base, Date last) {
		LocalDate jLast = new LocalDate(last);
		int quarter = (dateToQuarter(last) + 1);
		if (quarter == 5 ) quarter = 1;
		
		LocalDate result = new LocalDate(quarterToDate(quarter)); 
		result.withYear(jLast.getYear());
		
		return result.toDate();
	}
	
	private Date monthly(Date base, Date last) {
		LocalDate jBase = new LocalDate(base);
		LocalDate jLast = new LocalDate(last);

		return jBase.withMonthOfYear(jBase.getMonthOfYear() + 1).withYear(jLast.getYear()).toDate();
	}
	
	private Date biMonthly(Date base, Date last) {
		LocalDate result = null;
		LocalDate jBase = new LocalDate(base);
		LocalDate jLast = new LocalDate(last);
		LocalDate present = jLast.withDayOfMonth(jBase.getDayOfMonth());
		LocalDate fPayment = null;
		LocalDate lPayment = null;
		int month = present.getMonthOfYear();
		
		if (present.plusWeeks(2).getMonthOfYear() > month) {
			lPayment = present;
			fPayment = present.minusWeeks(2);
		}else{
			fPayment = present;
			lPayment = present.plusWeeks(2);
		}
		if (jLast.isBefore(fPayment)) {
			result = fPayment;
		}else{
			result = lPayment;
		}
		
		return result.toDate();
	}
	
	private Date weekly(Date base, Date last) {
		LocalDate jBase = new LocalDate(base);
		LocalDate jLast = new LocalDate(last);
		LocalDate present = jLast.withDayOfWeek(jBase.getDayOfWeek()).plusWeeks(1);
		
		return present.toDate();
	}
	
	private Date daily(Date current) {
		
		return new LocalDate(current).plusDays(1).toDate();
	}
	
	public ScheduleType stringToEnum(String type) {
		ScheduleType result = null;
		
		switch (type) {
		case "Annually":
			result = ScheduleType.ANNUALLY;
			break;
		case "Bi-Annually":
			result = ScheduleType.BIANNUALLY;
			break;
		case "Quarterly":
			result = ScheduleType.QUARTERLY;
			break;
		case "Monthly":
			result = ScheduleType.MONTHLY;
			break;
		case "Bi-Monthly":
			result = ScheduleType.BIMONTHLY;
			break;
		case "Weekly":
			result = ScheduleType.WEEKLY;
			break;
		case "Daily":
			result = ScheduleType.DAILY;
			break;
		}

		return result;
	}

}
