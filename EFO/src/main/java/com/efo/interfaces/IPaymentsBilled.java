package com.efo.interfaces;

import com.efo.entity.PaymentsBilled;

public interface IPaymentsBilled {
	public void create(PaymentsBilled payments);
	public PaymentsBilled retreive(Long id);
	public void update(PaymentsBilled payments);
	public void delete(Long id);

}
