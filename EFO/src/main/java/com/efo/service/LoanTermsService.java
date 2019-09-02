package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.LoanTermsDao;
import com.efo.entity.LoanTerms;
import com.efo.interfaces.ILoanTerms;

@Service
public class LoanTermsService implements ILoanTerms {
	
	@Autowired
	LoanTermsDao loanTermsDao;

	@Override
	public void create(LoanTerms loanTerms) {
		loanTermsDao.create(loanTerms);

	}

	@Override
	public LoanTerms retreive(Long reference) {
		return loanTermsDao.retreive(reference);
	}

	@Override
	public List<LoanTerms> retreiveRawList() {
		return loanTermsDao.retreiveRawList();
	}

	@Override
	public void update(LoanTerms loanTerms) {
		loanTermsDao.update(loanTerms);
	}

	@Override
	public void delete(LoanTerms loanTerms) {
		loanTermsDao.delete(loanTerms);
	}

}
