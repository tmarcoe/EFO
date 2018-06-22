package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BillingHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Long invoice_num;
	private Date invoice_date;
	private String customer;
	private double amount_due;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(Long invoice_num) {
		this.invoice_num = invoice_num;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public double getAmount_due() {
		return amount_due;
	}
	public void setAmount_due(double amount_due) {
		this.amount_due = amount_due;
	}
	


}
