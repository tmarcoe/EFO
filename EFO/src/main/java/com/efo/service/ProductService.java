package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.ProductDao;
import com.efo.entity.Product;
import com.efo.interfaces.IProduct;

@Service
public class ProductService implements IProduct {
	@Autowired
	ProductDao productDao;

	@Override
	public void create(Product product) {
		productDao.create(product);
	}

	@Override
	public Product retrieve(String sku) {
		return productDao.retrieve(sku);
	}

	public PagedListHolder<Product> retrieveList() {
		return new PagedListHolder<Product>(productDao.retrieveList());
	}
	
	@Override
	public void update(Product product) {
		productDao.update(product);
	}

	public void merge(Product product) {
		productDao.merge(product);
	}
	
	@Override
	public void delete(Product product) {
		productDao.delete(product);
	}
	
	public void delete(String sku) {
		productDao.delete(sku);
	}

}
