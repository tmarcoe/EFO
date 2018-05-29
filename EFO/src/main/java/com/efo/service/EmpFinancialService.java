package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.EmpFinancialDao;
import com.efo.entity.EmpFinancial;
import com.efo.entity.User;
import com.efo.interfaces.IEmpFinancial;


@Service
public class EmpFinancialService implements IEmpFinancial {

	@Autowired
	EmpFinancialDao employeeDao;
	
	@Override
	public void Create(EmpFinancial employee) {
		employeeDao.Create(employee);
	}

	@Override
	public EmpFinancial retrieve(int user_id) {
		return employeeDao.retrieve(user_id);
	}

	@Override
	public void update(EmpFinancial employee) {
		employeeDao.update(employee);
	}

	@Override
	public void delete(EmpFinancial employee) {
		employeeDao.delete(employee);
	}

	public List<User> employeeList() {
		return employeeDao.employeeList();
	}

}
