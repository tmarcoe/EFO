package com.efo.interfaces;

import com.efo.entity.PaymentsPaid;

public interface IPaymentsPaid {
	public void create(PaymentsPaid payments);
	public PaymentsPaid retreive(int id);
	public void update(PaymentsPaid payments);
	public void delete(int id);

}
