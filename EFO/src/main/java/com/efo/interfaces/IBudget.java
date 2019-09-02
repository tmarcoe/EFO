package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Budget;

public interface IBudget {
	public void create(Budget budget);
	public Budget retrieve(Long reference);
	public List<Budget> retrieveRawList(String department);
	public void update(Budget budget);
	public void merge(Budget budget);
	public void delete(Budget budget);
}
