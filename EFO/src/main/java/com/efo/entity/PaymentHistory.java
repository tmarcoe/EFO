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
	private Date lastPayment;
	private double lastAmount;
	private double nextAmount;
	
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

	public Date getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(Date lastPayment) {
		this.lastPayment = lastPayment;
	}

	public double getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(double lastAmount) {
		this.lastAmount = lastAmount;
	}

	public double getNextAmount() {
		return nextAmount;
	}

	public void setNextAmount(double nextAmount) {
		this.nextAmount = nextAmount;
	}

	public OverheadExpenses getOverheadExpenses() {
		return overheadExpenses;
	}

	public void setOverheadExpenses(OverheadExpenses overheadExpenses) {
		this.overheadExpenses = overheadExpenses;
	}

}
