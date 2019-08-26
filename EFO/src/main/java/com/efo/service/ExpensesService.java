package com.efo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ExpensesDao;
import com.efo.entity.Expenses;
import com.efo.interfaces.IExpenses;

@Service
public class ExpensesService implements IExpenses {
	
	@Autowired
	private ExpensesDao expensesDao;

	@Override
	public void create(Expenses expenses) {
		expensesDao.create(expenses);

	}

	@Override
	public Expenses retrieve(Long reference) {
		return expensesDao.retrieve(reference);
	}

	@Override
	public List<Expenses> retrieveRawList() {
		return expensesDao.retrieveRawList();
	}

	@Override
	public void merge(Expenses expenses) {
		expensesDao.merge(expenses);

	}

	@Override
	public void delete(Expenses expenses) {
		expensesDao.delete(expenses);

	}
	public Map<String, String> sumMonthlyExpenses(Date begin, Date end) {
		return expensesDao.sumMonthlyExpenses(begin, end);
	}
}
