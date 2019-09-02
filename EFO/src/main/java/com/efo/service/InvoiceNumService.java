package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.InvoiceNumDao;
import com.efo.entity.InvoiceNum;
import com.efo.interfaces.IInvoiceNum;

@Service
public class InvoiceNumService implements IInvoiceNum {
	
	@Autowired
	private InvoiceNumDao invoiceNumDao;

	@Override
	public void create(InvoiceNum invoice_num) {
		invoiceNumDao.create(invoice_num);	
	}

	@Override
	public InvoiceNum retrieve(String invoice_num) {
		return invoiceNumDao.retrieve(invoice_num);
	}

	@Override
	public void update(InvoiceNum invoice_num) {
		invoiceNumDao.update(invoice_num);
	}

	@Override
	public void delete(InvoiceNum invoice_num) {
		invoiceNumDao.delete(invoice_num);
	}
	
	public String getNextKey() {
		return invoiceNumDao.getNextKey();
	}

}
