package com.efo.interfaces;

import java.util.List;

import com.efo.entity.PaymentHistory;

public interface IPaymentHistory {
	public void create(PaymentHistory payment);
	public PaymentHistory retrieve(Long id);
	public List<PaymentHistory> retrieveRawList(Long reference);
	public void update(PaymentHistory payment);
	public void delete(PaymentHistory payment);

}
