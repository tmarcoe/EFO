package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.TimeReportingAccountsDao;
import com.efo.entity.TimeReportingAccounts;
import com.efo.interfaces.ITimeReportingAccounts;

@Service
public class TimeReportingAccountsService implements ITimeReportingAccounts {
	
	@Autowired
	private TimeReportingAccountsDao timeReportingAccountsDao;

	@Override
	public void create(TimeReportingAccounts timeReportingAccounts) {
		timeReportingAccountsDao.create(timeReportingAccounts);
	}

	@Override
	public TimeReportingAccounts retrieve(String account_number) {
		return timeReportingAccountsDao.retrieve(account_number);
	}

	@Override
	public List<TimeReportingAccounts> retrieveRawList(String department) {
		return timeReportingAccountsDao.retrieveRawList(department);
	}
	
	public PagedListHolder<TimeReportingAccounts> retrieveList(String department) {
		return new PagedListHolder<TimeReportingAccounts>(timeReportingAccountsDao.retrieveRawList(department));
	}

	@Override
	public void merge(TimeReportingAccounts timeReportingAccounts) {
		timeReportingAccountsDao.merge(timeReportingAccounts);
	}

	@Override
	public void delete(String account_number) {
		timeReportingAccountsDao.delete(account_number);
	}

}
