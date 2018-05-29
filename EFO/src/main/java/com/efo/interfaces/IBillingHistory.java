package com.efo.interfaces;

import com.efo.entity.BillingHistory;

public interface IBillingHistory {
	
	public void create(BillingHistory billingHistory);
	public BillingHistory retrieve(int id);
	public void update(BillingHistory billingHistory);
	public void delete(int id);

}
