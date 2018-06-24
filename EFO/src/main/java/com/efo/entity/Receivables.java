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
	private double down_payment;
	private double interest;
	private double each_payment;
	private Long   num_payments;
	private String schedule;
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


	public double getDown_payment() {
		return down_payment;
	}

	public void setDown_payment(double down_payment) {
		this.down_payment = down_payment;
	}

	public double getinterest() {
		return interest;
	}

	public void setinterest(double interest) {
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
