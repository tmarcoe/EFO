package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.BudgetItemsDao;
import com.efo.entity.BudgetItems;
import com.efo.interfaces.IBudgetItems;

@Service
public class BudgetItemsService implements IBudgetItems {
	
	@Autowired
	private BudgetItemsDao budgetItemsDao;

	@Override
	public void create(BudgetItems budget) {
		budgetItemsDao.create(budget);

	}

	@Override
	public BudgetItems retrieve(Long id) {
		return budgetItemsDao.retrieve(id);
	}

	@Override
	public List<BudgetItems> retrieveRawList(Long reference, String parent, int user_id) {
		return budgetItemsDao.retrieveRawList(reference, parent, user_id);
	}
	
	public PagedListHolder<BudgetItems> retrieveList(Long reference, String parent, int user_id) {
		return new PagedListHolder<BudgetItems>(budgetItemsDao.retrieveRawList(reference, parent, user_id));
	}

	@Override
	public void update(BudgetItems budget) {
		budgetItemsDao.update(budget);

	}

	@Override
	public void delete(BudgetItems budget) {
		budgetItemsDao.delete(budget);

	}
	
	public void delete(Long id) {
		BudgetItems budget = budgetItemsDao.retrieve(id);
		budgetItemsDao.delete(budget);
	}
	
	public void createChild(BudgetItems budget, String category) {
		BudgetItems child = new BudgetItems();
		
		child.setUser_id(budget.getUser_id());
		child.setLevel(budget.getLevel() + 1);
		child.setCategory(category);
		child.setParent(budget.getCategory());
		child.setDepartment(budget.getDepartment());
		child.setCreation_date(budget.getCreation_date());
		child.setProtect(false);
		
		budgetItemsDao.create(child);
	}
	
	public boolean categoryExists(Long reference, String category) {
		return budgetItemsDao.categoryExists(reference, category);
	}
	
	public BudgetItems retrieveByCategory(Long reference, String category) {
		return budgetItemsDao.retrieveByCategory(reference, category);
	}
	
	public Double sumChildren(Long reference, String parent) {
		return budgetItemsDao.sumChildren(reference, parent);
	}
	public PagedListHolder<BudgetItems> listBudgetsForApproval() {
		return new PagedListHolder<BudgetItems>(budgetItemsDao.listBudgetsForApproval());
	}
	
	public void approveBudget(String department){
		budgetItemsDao.approveBudget(department);
	}
	
	public void submitBudget(String department) {
		budgetItemsDao.submitBudget(department);
	}
}
