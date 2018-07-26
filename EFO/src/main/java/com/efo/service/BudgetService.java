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
	public Budget retrieve(Long id) {
		return budgetDao.retrieve(id);
	}

	@Override
	public List<Budget> retrieveRawList(String parent, int user_id) {
		return budgetDao.retrieveRawList(parent, user_id);
	}
	
	public PagedListHolder<Budget> retrieveList(String parent, int user_id) {
		return new PagedListHolder<Budget>(budgetDao.retrieveRawList(parent, user_id));
	}

	@Override
	public void update(Budget budget) {
		budgetDao.update(budget);

	}

	@Override
	public void delete(Budget budget) {
		budgetDao.delete(budget);

	}
	
	public void delete(Long id) {
		Budget budget = budgetDao.retrieve(id);
		budgetDao.delete(budget);
	}
	
	public void createChild(Budget budget, String category) {
		Budget child = new Budget();
		
		child.setUser_id(budget.getUser_id());
		child.setLevel(budget.getLevel() + 1);
		child.setCategory(category);
		child.setParent(budget.getCategory());
		child.setDepartment(budget.getDepartment());
		child.setCreation_date(budget.getCreation_date());
		child.setProtect(false);
		
		budgetDao.create(child);
	}
	
	public boolean categoryExists(String category) {
		return budgetDao.categoryExists(category);
	}
	
	public Budget retrieveByCategory(String category) {
		return budgetDao.retrieveByCategory(category);
	}
	
	public Double sumChildren(int user_id, String parent) {
		return budgetDao.sumChildren(user_id, parent);
	}
}
