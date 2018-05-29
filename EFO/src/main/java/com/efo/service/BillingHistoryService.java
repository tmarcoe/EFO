package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.BillingHistoryDao;
import com.efo.entity.BillingHistory;
import com.efo.interfaces.IBillingHistory;

@Service
public class BillingHistoryService implements IBillingHistory {
	
	@Autowired
	BillingHistoryDao billingHistoryDao;

	@Override
	public void create(BillingHistory billingHistory) {
		billingHistoryDao.create(billingHistory);
	}

	@Override
	public BillingHistory retrieve(int id) {
		return billingHistoryDao.retrieve(id);
	}

	@Override
	public void update(BillingHistory billingHistory) {
		billingHistoryDao.update(billingHistory);
	}

	@Override
	public void delete(int id) {
		billingHistoryDao.delete(id);
	}

}
