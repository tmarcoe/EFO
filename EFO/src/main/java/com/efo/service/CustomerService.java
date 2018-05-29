package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.CustomerDao;
import com.efo.dao.UserDao;
import com.efo.entity.Customer;
import com.efo.entity.User;
import com.efo.interfaces.ICustomer;

@Service
public class CustomerService implements ICustomer {

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public void create(Customer customer) {
		customerDao.create(customer);
	}

	@Override
	public Customer retrieve(int user_id) {
		return customerDao.retrieve(user_id);
	}

	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public void delete(int user_id) {
		customerDao.delete(user_id);		
	}
		
	public PagedListHolder<User> retrieveEditList() {
		return new PagedListHolder<User>(customerDao.retrieveEditList());
	}
	
	public List<User> retrieveRawList() {
		return customerDao.retrieveEditList();
	}
	
}
