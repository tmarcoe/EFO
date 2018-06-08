package com.efo.interfaces;

import com.efo.entity.Product;

public interface IProduct {
	
	public void create(Product product);
	public Product retrieve(String sku);
	public void update(Product product);
	public void delete(Product product);

}
