package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.EmployeeDao;
import com.efo.dao.UserDao;
import com.efo.entity.Employee;
import com.efo.entity.User;
import com.efo.interfaces.IEmployee;

@Service
public class EmployeeService implements IEmployee {
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public void create(Employee employee) {
		employeeDao.create(employee);
	}

	@Override
	public Employee retrieve(int user_id) {
		return employeeDao.retrieve(user_id);
	}

	@Override
	public void update(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public void delete(int user_id) {
		employeeDao.delete(user_id);
	}
	
	public PagedListHolder<User> retrieveEditList() {
				
		return new PagedListHolder<User>(employeeDao.retrieveEditList());
	}
	
	public List<Employee> queryEmployee(String name) {
		return employeeDao.queryEmployee(name);
	}
}
