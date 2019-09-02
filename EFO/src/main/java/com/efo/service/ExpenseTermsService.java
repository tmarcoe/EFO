package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ExpenseTermsDao;
import com.efo.entity.ExpenseTerms;
import com.efo.interfaces.IExpenseTerms;

@Service
public class ExpenseTermsService implements IExpenseTerms {
	
	@Autowired
	private ExpenseTermsDao expenseTermsDao;

	@Override
	public void create(ExpenseTerms expenseTerms) {
		expenseTermsDao.create(expenseTerms);

	}

	@Override
	public ExpenseTerms retrieve(Long reference) {
		return expenseTermsDao.retrieve(reference);
	}

	@Override
	public List<ExpenseTerms> retrieveRawList() {
		return expenseTermsDao.retrieveRawList();
	}

	@Override
	public void merge(ExpenseTerms expenseTerms) {
		expenseTermsDao.merge(expenseTerms);

	}

	@Override
	public void delete(ExpenseTerms expenseTerms) {
		expenseTermsDao.delete(expenseTerms);

	}

}
