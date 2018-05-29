package com.efo.interfaces;

import com.efo.entity.Receivables;

public interface IReceivables {
	
	public void create(Receivables receivables);
	public Receivables retreive(String invoice_num);
	public void update(Receivables receivables);
	public void delete(String invoice_num);
	

}
