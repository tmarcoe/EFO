package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Expenses;

public interface IExpenses {
	
	public void create(Expenses expenses);
	public Expenses retrieve(Long reference);
	public List<Expenses> retrieveRawList();
	public void merge(Expenses expenses);
	public void delete(Expenses expenses);

}
