package com.efo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.OrderItemsDao;
import com.efo.entity.OrderItems;
import com.efo.interfaces.IOrdersItem;

@Service
public class OrdersItemService implements IOrdersItem {
	
	@Autowired
	OrderItemsDao orderDao;

	@Override
	public void create(OrderItems orders) {
		orderDao.create(orders);
	}

	@Override
	public OrderItems retrieve(Long reference) {
		return orderDao.retrieve(reference);
	}

	@Override
	public List<OrderItems> retrieveRawList() {
		return orderDao.retrieveRawList();
	}

	public PagedListHolder<OrderItems> retrieveList() {
		return new PagedListHolder<OrderItems>(orderDao.retrieveRawList());
	}
	
	public PagedListHolder<OrderItems> retrieveOpenOrders() {
		return new PagedListHolder<OrderItems>(orderDao.retrieveOpenOrders());
	}
	public Set<OrderItems> retrieveChildItems(Long reference) {
		return new HashSet<OrderItems>(orderDao.retrieveChildItems(reference));
	}
	public void setStatus(Long reference, String status) {
		orderDao.setStatus(reference, status);
	}
	
	@Override
	public void update(OrderItems orders) {
		orderDao.update(orders);
	}
	
	public void merge(OrderItems orders) {
		orderDao.merge(orders);
	}

	@Override
	public void delete(OrderItems orders) {
		orderDao.delete(orders);
	}
	
	public void delete(Long reference) {
		OrderItems orders = retrieve(reference);
		orderDao.delete(orders);
	}
	
	public List<OrderItems> getPeriodOrders(Date begin, Date end) {
		return orderDao.getPeriodOrders(begin, end);
	}
	
	public List<Object[]> getTotalWholesaleByPeriod(Date begin, Date end) {
		return orderDao.getTotalWholesaleByPeriod(begin, end);
	}
}
