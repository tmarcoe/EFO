package com.efo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PaymentsBilledDao;
import com.efo.entity.PaymentsBilled;
import com.efo.interfaces.IPaymentsBilled;

@Service
public class PaymentsBilledService implements IPaymentsBilled {
	
	@Autowired
	PaymentsBilledDao paymentsPaidDao;

	@Override
	public void create(PaymentsBilled payments) {
		paymentsPaidDao.create(payments);
	}

	@Override
	public PaymentsBilled retreive(int id) {
		return paymentsPaidDao.retreive(id);
	}

	@Override
	public void update(PaymentsBilled payments) {
		paymentsPaidDao.update(payments);
	}

	@Override
	public void delete(int id) {
		paymentsPaidDao.delete(id);
	}
	
	public PagedListHolder<PaymentsBilled> retrieveList(Long reference) {
		return new PagedListHolder<PaymentsBilled>(paymentsPaidDao.retreiveList(reference));
	}
	
	public Date lastestDate(Long reference) {
		
		return paymentsPaidDao.lastestDate(reference);
	}

}
