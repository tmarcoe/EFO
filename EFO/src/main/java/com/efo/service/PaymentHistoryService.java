package com.efo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PaymentHistoryDao;
import com.efo.entity.PaymentHistory;
import com.efo.interfaces.IPaymentHistory;

@Service
public class PaymentHistoryService implements IPaymentHistory {

	@Autowired
	private PaymentHistoryDao paymentDao;
	
	@Override
	public void create(PaymentHistory payment) {
		paymentDao.create(payment);
	}

	@Override
	public PaymentHistory retrieve(Long id) {
		return paymentDao.retrieve(id);
	}

	@Override
	public List<PaymentHistory> retrieveRawList(Long reference) {
		return paymentDao.retrieveRawList(reference);
	}
	
	public PagedListHolder<PaymentHistory> retrrieveList(Long reference) {
		return new PagedListHolder<PaymentHistory>(paymentDao.retrieveRawList(reference));
	}

	@Override
	public void update(PaymentHistory payment) {
		paymentDao.update(payment);
	}

	@Override
	public void delete(PaymentHistory payment) {
		paymentDao.delete(payment);
	}
	
	public List<Object[]> totalPayentsByPeriod(Date from, Date to) {
		return paymentDao.totalPayentsByPeriod(from, to);
	}

}
