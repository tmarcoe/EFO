package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Loans;

public interface ILoans {
	
	public void create(Loans loans);
	public Loans retrieve(Long reference);
	public List<Loans> retrieveRawList();
	public void merge(Loans loans);
	public void delete(Loans loans);

}
