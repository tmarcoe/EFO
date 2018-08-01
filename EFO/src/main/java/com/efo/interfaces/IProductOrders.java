package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ProductOrders;

public interface IProductOrders {
	public void create(ProductOrders productOrders);
	public ProductOrders retrieve(Long reference);
	public List<ProductOrders> retrieveRawList();
	public void update(ProductOrders productOrders);
	public void delete(ProductOrders productOrders);

}
