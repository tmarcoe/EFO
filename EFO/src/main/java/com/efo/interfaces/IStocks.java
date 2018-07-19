package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Stocks;

public interface IStocks {
	public void create(Stocks stocks);
	public Stocks retrieve(Long id);
	public List<Stocks> retrieveRawList();
	public void update(Stocks stocks);
	public void delete(Stocks stocks);

}
