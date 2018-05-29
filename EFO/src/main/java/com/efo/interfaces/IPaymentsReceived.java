package com.efo.interfaces;

import com.efo.entity.PaymentsReceived;

public interface IPaymentsReceived {
	public void create(PaymentsReceived payments);
	public PaymentsReceived retreive(int id);
	public void update(PaymentsReceived payments);
	public void delete(int id);

}
