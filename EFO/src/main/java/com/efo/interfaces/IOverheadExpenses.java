package com.efo.interfaces;

import java.util.List;

import com.efo.entity.OverheadExpenses;

public interface IOverheadExpenses {
	
	public void create(OverheadExpenses expenses);
	public OverheadExpenses retrieve(Long id);
	public List<OverheadExpenses> retrieveRawList();
	public void update(OverheadExpenses expenses);
	public void delete(OverheadExpenses expenses);

}
