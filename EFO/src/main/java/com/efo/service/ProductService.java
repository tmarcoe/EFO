package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.FluidInventoryDao;
import com.efo.dao.ProductDao;
import com.efo.entity.Product;
import com.efo.interfaces.IProduct;

@Service
public class ProductService implements IProduct {
	@Autowired
	ProductDao productDao;
	
	@Autowired
	FluidInventoryDao inventoryDao;

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
	
	public List<Product> retrieveRawList() {
		return productDao.retrieveList();
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
	
	public List<Product> nameSearch(String name) {
		return productDao.nameSearch(name);
	}

}
