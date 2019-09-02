package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ExpensePayments;

public interface IExpensePayments {
	
	public void create(ExpensePayments expensePayments);
	public ExpensePayments retrieve(Long id);
	public List<ExpensePayments> retrieveRawList(Long reference);
	public void merge(ExpensePayments expensePayments);
	public void delete(ExpensePayments expensePayments);

}
