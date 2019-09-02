package com.efo.interfaces;

import java.util.List;

import com.efo.entity.RevenueTerms;

public interface IRevenueTerms {
	
	public void create(RevenueTerms revenueTerms);
	public RevenueTerms retrieve(Long reference);
	public List<RevenueTerms> retrieveRawList();
	public void merge(RevenueTerms revenueTerms);
	public void delete(RevenueTerms revenueTerms);
	

}
