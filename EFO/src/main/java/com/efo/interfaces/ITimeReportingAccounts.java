package com.efo.interfaces;

import java.util.List;

import com.efo.entity.TimeReportingAccounts;

public interface ITimeReportingAccounts {
	public void create(TimeReportingAccounts timeReportingAccounts);
	public TimeReportingAccounts retrieve(String account_number);
	public List<TimeReportingAccounts> retrieveRawList(String department);
	public void merge(TimeReportingAccounts timeReportingAccounts);
	public void delete(String account_number);

}
