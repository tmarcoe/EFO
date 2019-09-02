package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.RevenuePaymentsDao;
import com.efo.entity.RevenuePayments;
import com.efo.interfaces.IRevenuePayments;

@Service
public class RevenuePaymentsService implements IRevenuePayments {
	
	@Autowired
	RevenuePaymentsDao revenuePaymentsDao;

	@Override
	public void create(RevenuePayments revenuePayments) {
		revenuePaymentsDao.create(revenuePayments);

	}

	@Override
	public RevenuePayments retrieve(Long id) {
		return revenuePaymentsDao.retrieve(id);
	}

	@Override
	public List<RevenuePayments> retrieveRawList(Long reference) {
		return revenuePaymentsDao.retrieveRawList(reference);
	}
	
	public Double sumCreditPayments(int month, int year) {
		return revenuePaymentsDao.sumCreditPayments(month, year);
	}

	@Override
	public void merge(RevenuePayments revenuePayments) {
		revenuePaymentsDao.merge(revenuePayments);

	}

	@Override
	public void delete(RevenuePayments revenuePayments) {
		revenuePaymentsDao.delete(revenuePayments);

	}

}
