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

@Entity
public class Payables implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long reference;
	private Date date_begin;
	private String supplier;
	private String type;
	private double total_due;
	private double down_payment;
	private double interest;
	private double each_payment;
	private Long   num_payments;
	private String schedule;
	private double total_balance;
	private String status; // O = open, C = closed, D = dispute
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "payables", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
	private Set<PaymentsBilled> payments = new HashSet<PaymentsBilled>(0);
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE")
	private ProductOrders productOrders;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE")
	private CapitalAssets capitalAssets;


	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Date getDate_begin() {
		return date_begin;
	}

	public void setDate_begin(Date date_begin) {
		this.date_begin = date_begin;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Set<PaymentsBilled> getPayments() {
		return payments;
	}
	public void setPayments(Set<PaymentsBilled> payments) {
		this.payments = payments;
	}

	public ProductOrders getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(ProductOrders productOrders) {
		this.productOrders = productOrders;
	}

	public CapitalAssets getCapitalAssets() {
		return capitalAssets;
	}

	public void setCapitalAssets(CapitalAssets capitalAssets) {
		this.capitalAssets = capitalAssets;
	}
	
	public void addPayment(PaymentsBilled payment) {
		payments.add(payment);
	}
	
}
