package com.efo.interfaces;

import com.efo.entity.Employee;

public interface IEmployee {
	public void create(Employee employee);
	public Employee retrieve(int user_id);
	public void update(Employee employee);
	public void delete(int user_id);
}
