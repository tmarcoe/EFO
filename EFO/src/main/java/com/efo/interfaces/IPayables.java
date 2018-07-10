package com.efo.interfaces;

import com.efo.entity.Payables;

public interface IPayables {
	public void create(Payables payables);
	public Payables retreive(Long reference);
	public void update(Payables payables);
	public void delete(Long reference);

}
