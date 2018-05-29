package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PayablesDao;
import com.efo.entity.Payables;
import com.efo.interfaces.IPayables;

@Service
public class PayablesService implements IPayables {
	
	@Autowired
	PayablesDao payablesDao;

	@Override
	public void create(Payables payables) {
		payablesDao.create(payables);
	}

	@Override
	public Payables retreive(String invoice_num) {
		return payablesDao.retreive(invoice_num);
	}

	@Override
	public void update(Payables payables) {
		payablesDao.update(payables);
	}
	
	public void merge(Payables payables) {
		payablesDao.merge(payables);
	}

	@Override
	public void delete(String invoice_num) {
		payablesDao.delete(invoice_num);
	}
	
	public PagedListHolder<Payables> retrieveList() {
		
		return new PagedListHolder<Payables>(payablesDao.retreiveList());
	}

}
