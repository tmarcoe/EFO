package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.RetailSalesDao;
import com.efo.entity.RetailSales;
import com.efo.interfaces.IRetailSales;

@Service
public class RetailSalesService implements IRetailSales {
	
	@Autowired
	RetailSalesDao retailSalesDao;

	@Override
	public void create(RetailSales sales) {
		retailSalesDao.create(sales);
	}

	@Override
	public RetailSales retrieve(int id) {
		return retailSalesDao.retrieve(id);
	}

	@Override
	public List<RetailSales> retrieveRawList() {

		return retailSalesDao.retrieveRawList();
	}
	
	public PagedListHolder<RetailSales> retrieveList() {
		return new PagedListHolder<RetailSales>(retailSalesDao.retrieveRawList());
	}

	@Override
	public void update(RetailSales sales) {
		retailSalesDao.update(sales);
	}
	
	public void merge(RetailSales sales) {
		retailSalesDao.merge(sales);
	}

	@Override
	public void delete(RetailSales sales) {
		retailSalesDao.delete(sales);
	}
	
	public void delete(int id) {
		RetailSales sales = retailSalesDao.retrieve(id);
		retailSalesDao.delete(sales);
	}
	
	public RetailSales getOpenInvoice(int user_id) {
		return retailSalesDao.getOpenInvoice(user_id);
	}

}
