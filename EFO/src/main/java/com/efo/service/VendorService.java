package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.VendorDao;
import com.efo.entity.User;
import com.efo.entity.Vendor;
import com.efo.interfaces.IVendor;

@Service
public class VendorService implements IVendor {
	
	@Autowired
	VendorDao vendorDao;

	@Override
	public void create(Vendor vendor) {
		vendorDao.create(vendor);
	}

	@Override
	public Vendor retrieve(Long user_id) {
		return vendorDao.retrieve(user_id);
	}

	@Override
	public void update(Vendor vendor) {
		vendorDao.update(vendor);
	}

	@Override
	public void delete(Long user_id) {
		vendorDao.delete(user_id);
	}
	
	public PagedListHolder<User> retrieveEditList() {
		return new PagedListHolder<User>(vendorDao.retrieveEditList());
	}
	
	public List<User> retrieveRawList(String type) {
		return vendorDao.retrieveEditList(type);
	}
	
	public List<Vendor> retrieveRawList() {
		return vendorDao.retrieveList();
	}
	
	public List<Vendor> queryVendow(String name, String type) {
		return vendorDao.queryVendor(name, type);
	}
}
