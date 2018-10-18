package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PaymentsReceived implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Long reference;
	private String invoice_num;
	private Date payment_date;
	private Date date_due;
	private double payment_due;
	private double payment;
	private double penalties;
	private String comments;
	private Long event;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REFERENCE", referencedColumnName="REFERENCE", nullable = false, insertable=false, updatable=false )
	private Receivables receivables;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="REFERENCE")
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

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public Date getDate_due() {
		return date_due;
	}

	public void setDate_due(Date date_due) {
		this.date_due = date_due;
	}

	public double getPayment_due() {
		return payment_due;
	}

	public void setPayment_due(double payment_due) {
		this.payment_due = payment_due;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getPenalties() {
		return penalties;
	}

	public void setPenalties(double penalties) {
		this.penalties = penalties;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getEvent() {
		return event;
	}

	public void setEvent(Long event) {
		this.event = event;
	}

	public Receivables getReceivables() {
		return receivables;
	}

	public void setReceivables(Receivables receivables) {
		this.receivables = receivables;
	}

	
}
