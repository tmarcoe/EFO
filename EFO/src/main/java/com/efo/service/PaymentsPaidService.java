package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PaymentsPaidDao;
import com.efo.entity.PaymentsPaid;
import com.efo.interfaces.IPaymentsPaid;

@Service
public class PaymentsPaidService implements IPaymentsPaid {
	
	@Autowired
	PaymentsPaidDao paymentsPaidDao;

	@Override
	public void create(PaymentsPaid payments) {
		paymentsPaidDao.create(payments);
	}

	@Override
	public PaymentsPaid retreive(int id) {
		return paymentsPaidDao.retreive(id);
	}

	@Override
	public void update(PaymentsPaid payments) {
		paymentsPaidDao.update(payments);
	}

	@Override
	public void delete(int id) {
		paymentsPaidDao.delete(id);
	}
	
	public PagedListHolder<PaymentsPaid> retrieveList(String invoice_num) {
		return new PagedListHolder<PaymentsPaid>(paymentsPaidDao.retreiveList(invoice_num));
	}

}
