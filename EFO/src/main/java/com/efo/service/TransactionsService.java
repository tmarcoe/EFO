package com.efo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.TransactionsDao;
import com.efo.entity.Transactions;
import com.efo.interfaces.ITransactions;

@Service
public class TransactionsService implements ITransactions {
	
	@Autowired
	TransactionsDao transactionsDao;

	@Override
	public void create(Transactions transactions) {
		transactionsDao.create(transactions);
	}

	@Override
	public Transactions retrieve(Long reference) {
		return transactionsDao.retrieve(reference);
	}

	@Override
	public List<Transactions> retrieveRawList(Date begin, Date end) {
		return transactionsDao.retrieveRawList(begin, end);
	}
	
	public PagedListHolder<Transactions> retrieveList(Date begin, Date end) {
		return new PagedListHolder<Transactions>(transactionsDao.retrieveRawList(begin, end));
	}

	@Override
	public void update(Transactions transactions) {
		transactionsDao.update(transactions);
	}

	@Override
	public void delete(Transactions transactions) {
		transactionsDao.delete(transactions);
	}
	
	public boolean overheadExists(String name, String profileName) {
		return transactionsDao.overheadExists(name, profileName);
	}

}
