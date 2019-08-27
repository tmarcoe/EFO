package com.efo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public List<BudgetItems> retrieveRawList(Long reference, String parent) {
		return budgetItemsDao.retrieveRawList(reference, parent);
	}
	
	public Set<BudgetItems> retrieveSet(Long reference, String parent) {
		
		return new HashSet<BudgetItems>(budgetItemsDao.retrieveRawList(reference, parent));
	}
	
	public PagedListHolder<BudgetItems> retrieveList(Long reference, String parent) {
		return new PagedListHolder<BudgetItems>(budgetItemsDao.retrieveRawList(reference, parent));
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
		budgetItemsDao.deleteById(id);
	}
	
	public void createChild(BudgetItems budget, String category) {
		BudgetItems child = new BudgetItems();
		
		child.setLevel(budget.getLevel() + 1);
		child.setCategory(category);
		child.setParent(budget.getCategory());
		child.setProtect(false);
		
		budgetItemsDao.create(child);
	}
	public boolean hasChildren(Long reference, String parent) {
		return budgetItemsDao.hasChildren(reference, parent);
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
	
	public List<Object[]> budgetTree(Long reference) {
		return budgetItemsDao.budgetTree(reference);
	}
}
