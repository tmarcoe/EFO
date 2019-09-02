package com.efo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InvoiceNum implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	public InvoiceNum() {
	}

	public InvoiceNum(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	@Id
	private String invoice_num;

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	
}
