package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.RevenueTermsDao;
import com.efo.entity.RevenueTerms;
import com.efo.interfaces.IRevenueTerms;

@Service
public class RevenueTermsService implements IRevenueTerms {
	
	@Autowired
	private RevenueTermsDao revenueTermsDao;

	@Override
	public void create(RevenueTerms revenueTerms) {
		revenueTermsDao.create(revenueTerms);

	}

	@Override
	public RevenueTerms retrieve(Long reference) {
		return revenueTermsDao.retrieve(reference);
	}

	@Override
	public List<RevenueTerms> retrieveRawList() {
		return revenueTermsDao.retrieveRawList();
	}

	@Override
	public void merge(RevenueTerms revenueTerms) {
		revenueTermsDao.merge(revenueTerms);

	}

	@Override
	public void delete(RevenueTerms revenueTerms) {
		revenueTermsDao.delete(revenueTerms);
	}

}
