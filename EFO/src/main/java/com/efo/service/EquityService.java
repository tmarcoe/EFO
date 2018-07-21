package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.EquityDao;
import com.efo.entity.Equity;
import com.efo.interfaces.IEquity;

@Service
public class EquityService implements IEquity {
	
	@Autowired
	private EquityDao stocksDao;

	@Override
	public void create(Equity stocks) {
		stocksDao.create(stocks);

	}

	@Override
	public Equity retrieve(Long id) {
		return stocksDao.retrieve(id);
	}

	@Override
	public List<Equity> retrieveRawList() {
		return stocksDao.retrieveRawList();
	}
	
	public PagedListHolder<Equity> retrieveList() {
		return new PagedListHolder<Equity>(stocksDao.retrieveRawList());
	}
	
	@Override
	public void update(Equity stocks) {
		stocksDao.update(stocks);
	}

	@Override
	public void delete(Equity stocks) {
		stocksDao.delete(stocks);
	}

}
