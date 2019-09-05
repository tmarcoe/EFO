package com.efo.interfaces;

import java.util.Date;
import java.util.List;

import com.efo.entity.PayStub;

public interface IPayStub {
	
	public void create(PayStub payStub);
	public PayStub retrieve(Long id);
	public List<PayStub> retrieveRawList();
	public List<PayStub> retrievePeriod(Date begin);
	public void update(PayStub payStub);
	public void delete(PayStub payStub);

}
