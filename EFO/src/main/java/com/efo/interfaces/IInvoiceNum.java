package com.efo.interfaces;

import com.efo.entity.InvoiceNum;

public interface IInvoiceNum {
	public void create(InvoiceNum invoice_num);
	public InvoiceNum retrieve(String invoice_num);
	public void update(InvoiceNum invoice_num);
	public void delete(InvoiceNum invoice_num);
}
