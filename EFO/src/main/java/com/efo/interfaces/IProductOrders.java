package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ProductOrders;

public interface IProductOrders {
	
	public void create(ProductOrders orders);
	public ProductOrders retrieve(String invoice_num);
	public List<ProductOrders> retrieveRawList();
	public void update(ProductOrders orders);
	public void delete(ProductOrders orders);
}
