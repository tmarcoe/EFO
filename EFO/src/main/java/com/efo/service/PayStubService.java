package com.efo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.PayStubDao;
import com.efo.entity.PayStub;
import com.efo.interfaces.IPayStub;

@Service
public class PayStubService implements IPayStub {
	
	@Autowired
	private PayStubDao payStubDao;

	@Override
	public void create(PayStub payStub) {
		payStubDao.create(payStub);
	}

	@Override
	public PayStub retrieve(Long id) {
		return payStubDao.retrieve(id);
	}

	@Override
	public List<PayStub> retrieveRawList() {
		return payStubDao.retrieveRawList();
	}

	@Override
	public List<PayStub> retrievePeriod(Date begin) {
		return payStubDao.retrievePeriod(begin);
	}

	public Object[] totalExpenses(Date begin) {
		return payStubDao.totalExpenses(begin);
	}
	
	@Override
	public void update(PayStub payStub) {
		payStubDao.update(payStub);
	}

	@Override
	public void delete(PayStub payStub) {
		payStubDao.delete(payStub);
	}

}
