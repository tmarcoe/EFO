package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PaymentHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long reference;
	private String invoice_num;
	private Date date_due;
	private Date date_paid;
	private double amount_due;
	private double amount_paid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REFERENCE", nullable = false, insertable=false, updatable=false )
	private OverheadExpenses overheadExpenses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public Date getDate_due() {
		return date_due;
	}

	public void setDate_due(Date date_due) {
		this.date_due = date_due;
	}

	public Date getDate_paid() {
		return date_paid;
	}

	public void setDate_paid(Date date_paid) {
		this.date_paid = date_paid;
	}

	public double getAmount_due() {
		return amount_due;
	}

	public void setAmount_due(double amount_due) {
		this.amount_due = amount_due;
	}

	public double getAmount_paid() {
		return amount_paid;
	}

	public void setAmount_paid(double amount_paid) {
		this.amount_paid = amount_paid;
	}

	public OverheadExpenses getOverheadExpenses() {
		return overheadExpenses;
	}

	public void setOverheadExpenses(OverheadExpenses overheadExpenses) {
		this.overheadExpenses = overheadExpenses;
	}

}
