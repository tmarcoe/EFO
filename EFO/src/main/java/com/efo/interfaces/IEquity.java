package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Equity;

public interface IEquity {
	public void create(Equity stocks);
	public Equity retrieve(Long id);
	public List<Equity> retrieveRawList();
	public void update(Equity stocks);
	public void delete(Equity stocks);

}
