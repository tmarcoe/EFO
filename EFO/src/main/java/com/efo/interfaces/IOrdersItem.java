package com.efo.interfaces;

import java.util.List;

import com.efo.entity.OrderItems;

public interface IOrdersItem {
	
	public void create(OrderItems orders);
	public OrderItems retrieve(Long id);
	public List<OrderItems> retrieveRawList();
	public void update(OrderItems orders);
	public void delete(OrderItems orders);
}
