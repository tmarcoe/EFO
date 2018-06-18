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
	public ProductOrders retrieve(int id) {
		return orderDao.retrieve(id);
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
	
	public void setStatus(int id, String status) {
		orderDao.setStatus(id, status);
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
	
	public void delete(int id) {
		ProductOrders orders = retrieve(id);
		orderDao.delete(orders);
	}

}
