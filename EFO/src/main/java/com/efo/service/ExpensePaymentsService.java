package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ExpensePaymentsDao;
import com.efo.entity.ExpensePayments;
import com.efo.interfaces.IExpensePayments;

@Service
public class ExpensePaymentsService implements IExpensePayments {
	
	@Autowired
	ExpensePaymentsDao expensePaymentsDao;

	@Override
	public void create(ExpensePayments expensePayments) {
		expensePaymentsDao.create(expensePayments);

	}

	@Override
	public ExpensePayments retrieve(Long id) {
		return expensePaymentsDao.retrieve(id);
	}

	@Override
	public List<ExpensePayments> retrieveRawList(Long reference) {
		return expensePaymentsDao.retrieveRawList(reference);
	}

	@Override
	public void merge(ExpensePayments expensePayments) {
		expensePaymentsDao.merge(expensePayments);

	}

	@Override
	public void delete(ExpensePayments expensePayments) {
		expensePaymentsDao.delete(expensePayments);

	}
	
	public Double sumMontlyPayments(int month, int year) {
		
		return expensePaymentsDao.sumMontlyPayments(month, year);
	}

}
