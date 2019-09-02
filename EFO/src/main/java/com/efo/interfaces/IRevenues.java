package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Revenues;

public interface IRevenues {
	
	public void create(Revenues revenues);
	public Revenues retrieve(Long reference);
	public List<Revenues> retrieveRawList();
	public void merge(Revenues revenues);
	public void delete(Revenues revenues);

}
