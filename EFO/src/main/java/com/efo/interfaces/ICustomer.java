package com.efo.interfaces;

import com.efo.entity.Customer;

public interface ICustomer {
	public void create(Customer customer);
	public Customer retrieve(Long user_id);
	public void update(Customer customer);
	public void delete(Long user_id);
}
