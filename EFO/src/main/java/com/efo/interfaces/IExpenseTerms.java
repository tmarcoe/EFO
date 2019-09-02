package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ExpenseTerms;

public interface IExpenseTerms {
	
	public void create(ExpenseTerms expenseTerms);
	public ExpenseTerms retrieve(Long reference);
	public List<ExpenseTerms> retrieveRawList();
	public void merge(ExpenseTerms expenseTerms);
	public void delete(ExpenseTerms expenseTerms);
	

}
