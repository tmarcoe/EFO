package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ProductOrdersDao;
import com.efo.entity.ProductOrders;
import com.efo.interfaces.IProductOrders;

@Service
public class ProductOrdersService implements IProductOrders {
	
	@Autowired
	private ProductOrdersDao productOrdersDao;

	@Override
	public void create(ProductOrders productOrders) {
		productOrdersDao.create(productOrders);

	}

	@Override
	public ProductOrders retrieve(Long reference) {
		return productOrdersDao.retrieve(reference);
	}

	public ProductOrders findOpenOrder(int user_id) {
		return productOrdersDao.findOpenOrder(user_id);
	}
	
	@Override
	public List<ProductOrders> retrieveRawList() {
		return productOrdersDao.retrieveRawList();
	}

	@Override
	public void update(ProductOrders productOrders) {
		productOrdersDao.update(productOrders);

	}

	public void merge(ProductOrders productOrders) {
		productOrdersDao.merge(productOrders);
	}
	
	@Override
	public void delete(ProductOrders productOrders) {
		productOrdersDao.delete(productOrders);

	}

}
