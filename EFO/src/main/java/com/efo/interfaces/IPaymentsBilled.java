package com.efo.interfaces;

import com.efo.entity.PaymentsBilled;

public interface IPaymentsBilled {
	public void create(PaymentsBilled payments);
	public PaymentsBilled retreive(int id);
	public void update(PaymentsBilled payments);
	public void delete(int id);

}
