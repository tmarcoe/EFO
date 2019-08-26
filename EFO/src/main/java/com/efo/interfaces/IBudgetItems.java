package com.efo.interfaces;

import java.util.List;

import com.efo.entity.BudgetItems;

public interface IBudgetItems {
	
	public void create(BudgetItems budgetItems);
	public BudgetItems retrieve(Long id);
	public List<BudgetItems> retrieveRawList(Long reference, String parent);
	public void update(BudgetItems budgetItems);
	public void delete(BudgetItems budgetItems);

}
