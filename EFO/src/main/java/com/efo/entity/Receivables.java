package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="receivables")
public class Receivables implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long invoice_num;
	private Date invoice_date;
	private String customer;
	private double total_due;
	private Date date_due;
	private long total_payments;
	private double total_balance;
	private String status; // O = open, C = closed, D = dispute
	private String username;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "receivables")
	private Set<PaymentsReceived> payments = new HashSet<PaymentsReceived>(0);

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

	public double getTotal_due() {
		return total_due;
	}

	public void setTotal_due(double total_due) {
		this.total_due = total_due;
	}

	public Date getDate_due() {
		return date_due;
	}

	public void setDate_due(Date date_due) {
		this.date_due = date_due;
	}

	public long getTotal_payments() {
		return total_payments;
	}

	public void setTotal_payments(long total_payments) {
		this.total_payments = total_payments;
	}

	public double getTotal_balance() {
		return total_balance;
	}

	public void setTotal_balance(double total_balance) {
		this.total_balance = total_balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<PaymentsReceived> getPayments() {
		return payments;
	}

	public void setPayments(Set<PaymentsReceived> payments) {
		this.payments = payments;
	}
	

}
