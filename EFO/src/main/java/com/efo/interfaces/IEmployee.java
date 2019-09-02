package com.efo.interfaces;

import com.efo.entity.Employee;

public interface IEmployee {
	public void create(Employee employee);
	public Employee retrieve(Long user_id);
	public void update(Employee employee);
	public void delete(Long user_id);
}
