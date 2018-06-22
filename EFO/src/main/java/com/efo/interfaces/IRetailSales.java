package com.efo.interfaces;

import java.util.List;

import com.efo.entity.RetailSales;

public interface IRetailSales {
	
	public void create(RetailSales sales);
	public RetailSales retrieve(Long invoice);
	public List<RetailSales> retrieveRawList();
	public void update(RetailSales sales);
	public void delete(RetailSales sales);

}
