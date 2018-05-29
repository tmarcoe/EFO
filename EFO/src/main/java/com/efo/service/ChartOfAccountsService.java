package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.ChartOfAccountsDao;
import com.efo.entity.ChartOfAccounts;
import com.efo.interfaces.IChartOfAccounts;



@Service
public class ChartOfAccountsService implements IChartOfAccounts {

	@Autowired
	ChartOfAccountsDao chartOfAccountsDao;
	
	@Override
	public void create(ChartOfAccounts accounts) {
		chartOfAccountsDao.create(accounts);
	}

	@Override
	public void update(ChartOfAccounts accounts) {
		chartOfAccountsDao.update(accounts);
	}

	@Override
	public ChartOfAccounts retrieve(String account) {
		return chartOfAccountsDao.retrieve(account);
	}


	public PagedListHolder<ChartOfAccounts> retrieveList() {
		return new PagedListHolder<ChartOfAccounts>(chartOfAccountsDao.getRawList());
	}
	
	@Override
	public List<ChartOfAccounts> getRawList() {
		return chartOfAccountsDao.getRawList();
	}

	@Override
	public void delete(ChartOfAccounts account) {
		chartOfAccountsDao.delete(account);
	}

	@Override
	public void delete(String account) {
		chartOfAccountsDao.delete(account);
	}
	
	public boolean exists(String account) {
		return chartOfAccountsDao.exists(account);
	}
	public boolean exists() {
		return chartOfAccountsDao.exist();
	}



}
