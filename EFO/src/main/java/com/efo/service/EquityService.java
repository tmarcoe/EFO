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
	private EquityDao equityDao;

	@Override
	public void create(Equity stocks) {
		equityDao.create(stocks);

	}

	@Override
	public Equity retrieve(Long id) {
		return equityDao.retrieve(id);
	}

	@Override
	public List<Equity> retrieveRawList() {
		return equityDao.retrieveRawList();
	}
	
	public PagedListHolder<Equity> retrieveList() {
		return new PagedListHolder<Equity>(equityDao.retrieveRawList());
	}
	
	@Override
	public void update(Equity stocks) {
		equityDao.update(stocks);
	}

	@Override
	public void delete(Equity stocks) {
		equityDao.delete(stocks);
	}

}
