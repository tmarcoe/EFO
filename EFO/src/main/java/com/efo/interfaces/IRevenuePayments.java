package com.efo.interfaces;

import java.util.List;

import com.efo.entity.RevenuePayments;

public interface IRevenuePayments {
	
	public void create(RevenuePayments revenuePayments);
	public RevenuePayments retrieve(Long id);
	public List<RevenuePayments> retrieveRawList(Long reference);
	public void merge(RevenuePayments revenuePayments);
	public void delete(RevenuePayments revenuePayments);

}
