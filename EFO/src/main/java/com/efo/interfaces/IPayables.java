package com.efo.interfaces;

import com.efo.entity.Payables;

public interface IPayables {
	public void create(Payables payables);
	public Payables retreive(String invoice_num);
	public void update(Payables payables);
	public void delete(String invoice_num);

}
