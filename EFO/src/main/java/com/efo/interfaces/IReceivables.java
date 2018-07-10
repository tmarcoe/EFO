package com.efo.interfaces;

import com.efo.entity.Receivables;

public interface IReceivables {
	
	public void create(Receivables receivables);
	public Receivables retreive(Long reference);
	public void update(Receivables receivables);
	public void delete(Long reference);
	

}
