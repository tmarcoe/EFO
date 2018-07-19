package com.efo.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.stereotype.Component;

@Component
public class ReportUtilities {
	
	public List<Double> translateToDoubleArray(List<Object[]> objectArray, Date begin, Date end) {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
				
		Integer[] reportMonth = new Integer[diff];
		Double[] reportValue = new Double[diff];
		int i = 0;
		for (Object[] item : objectArray) {
			reportMonth[i] = (Integer) item[0];
			reportValue[i] = (Double) item[1];
			i++;
		}
		Integer month = jBegin.getMonthOfYear();
		Integer counter = 0;
		Double value = 0.0;
		
		List<Double> output = new ArrayList<Double>();
		
		for (int j=0 ; j < diff; j++) {
			if (reportMonth[counter] == month) {
				value = reportValue[counter++];
			}else{
				value = 0.0;
			}
			output.add(value);
			month++;
			if (month > 12) month = 1;
		}
		
		return output;
	}
}
