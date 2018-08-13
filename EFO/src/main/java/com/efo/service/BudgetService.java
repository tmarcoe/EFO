package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.BudgetDao;
import com.efo.entity.Budget;
import com.efo.interfaces.IBudget;

@Service
public class BudgetService implements IBudget {
	
	@Autowired
	private BudgetDao budgetDao;

	@Override
	public void create(Budget budget) {
		budgetDao.create(budget);
	}

	@Override
	public Budget retrieve(Long reference) {
		return budgetDao.retrieve(reference);
	}

	@Override
	public List<Budget> retrieveRawList(String department) {
		return budgetDao.retrieveRawList(department);
	}
	
	public PagedListHolder<Budget> retrieveList(String department) {
		return new PagedListHolder<Budget>(budgetDao.retrieveRawList(department));
	}

	@Override
	public void update(Budget budget) {
		budgetDao.update(budget);
	}

	@Override
	public void merge(Budget budget) {
		budgetDao.merge(budget);
	}

	@Override
	public void delete(Budget budget) {
		budgetDao.delete(budget);
	}
	
	public void delete(Long reference) {
		Budget budget = budgetDao.retrieve(reference);
		budgetDao.delete(budget);
	}

}
