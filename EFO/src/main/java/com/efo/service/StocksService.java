package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.StocksDao;
import com.efo.entity.Stocks;
import com.efo.interfaces.IStocks;

@Service
public class StocksService implements IStocks {
	
	@Autowired
	private StocksDao stocksDao;

	@Override
	public void create(Stocks stocks) {
		stocksDao.create(stocks);

	}

	@Override
	public Stocks retrieve(Long id) {
		return stocksDao.retrieve(id);
	}

	@Override
	public List<Stocks> retrieveRawList() {
		return stocksDao.retrieveRawList();
	}
	
	public PagedListHolder<Stocks> retrieveList() {
		return new PagedListHolder<Stocks>(stocksDao.retrieveRawList());
	}
	
	@Override
	public void update(Stocks stocks) {
		stocksDao.update(stocks);
	}

	@Override
	public void delete(Stocks stocks) {
		stocksDao.delete(stocks);
	}

}
