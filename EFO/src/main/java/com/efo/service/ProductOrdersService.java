package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.ProductOrdersDao;
import com.efo.entity.ProductOrders;
import com.efo.interfaces.IProductOrders;

@Service
public class ProductOrdersService implements IProductOrders {
	
	@Autowired
	ProductOrdersDao orderDao;

	@Override
	public void create(ProductOrders orders) {
		orderDao.create(orders);
	}

	@Override
	public ProductOrders retrieve(String invoice_num) {
		return orderDao.retrieve(invoice_num);
	}

	@Override
	public List<ProductOrders> retrieveRawList() {
		return orderDao.retrieveRawList();
	}

	public PagedListHolder<ProductOrders> retrieveList() {
		return new PagedListHolder<ProductOrders>(orderDao.retrieveRawList());
	}
	
	public PagedListHolder<ProductOrders> retrieveOpenOrders() {
		return new PagedListHolder<ProductOrders>(orderDao.retrieveOpenOrders());
	}
	
	public void setStatus(String invoice_num, String status) {
		orderDao.setStatus(invoice_num, status);
	}
	
	@Override
	public void update(ProductOrders orders) {
		orderDao.update(orders);
	}
	
	public void merge(ProductOrders orders) {
		orderDao.merge(orders);
	}

	@Override
	public void delete(ProductOrders orders) {
		orderDao.delete(orders);
	}
	
	public void delete(String invoice_num) {
		ProductOrders orders = retrieve(invoice_num);
		orderDao.delete(orders);
	}

}
