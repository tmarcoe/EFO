package com.efo.interfaces;

import com.efo.entity.PaymentsReceived;

public interface IPaymentsReceived {
	public void create(PaymentsReceived payments);
	public PaymentsReceived retreive(Long id);
	public void update(PaymentsReceived payments);
	public void delete(Long id);

}
