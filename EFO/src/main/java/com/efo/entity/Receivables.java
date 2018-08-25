package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="receivables")
public class Receivables implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long reference;
	private Date invoice_date;
	private String customer;
	private int	user_id;
	private double total_due;
	private double total_tax;
	private double down_payment;
	private double interest;
	private double each_payment;
	private Long   num_payments;
	private String schedule;
	private double total_balance;
	private String status; // O = open, C = closed, D = dispute
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "receivables", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
	private Set<PaymentsReceived> payments = new HashSet<PaymentsReceived>(0);
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE")
	private RetailSales retailSales = new RetailSales();

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getTotal_due() {
		return total_due;
	}

	public void setTotal_due(double total_due) {
		this.total_due = total_due;
	}
	
	public double getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(double total_tax) {
		this.total_tax = total_tax;
	}

	public double getDown_payment() {
		return down_payment;
	}

	public void setDown_payment(double down_payment) {
		this.down_payment = down_payment;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getEach_payment() {
		return each_payment;
	}

	public void setEach_payment(double each_payment) {
		this.each_payment = each_payment;
	}

	public Long getNum_payments() {
		return num_payments;
	}

	public void setNum_payments(Long num_payments) {
		this.num_payments = num_payments;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
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

	public Set<PaymentsReceived> getPayments() {
		return payments;
	}

	public void setPayments(Set<PaymentsReceived> payments) {
		this.payments = payments;
	}

	public RetailSales getRetailSales() {
		return retailSales;
	}

	public void setRetailSales(RetailSales retailSales) {
		this.retailSales = retailSales;
	}
	

}
