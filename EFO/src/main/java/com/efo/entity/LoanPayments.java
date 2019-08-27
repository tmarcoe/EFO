package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LoanPayments implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long reference;
	private Date due;
	private Double amount_due;
	private Date payment_date;
	private Double payment_made;
	private Double penalties;
	private Long event;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE", referencedColumnName ="REFERENCE", nullable = false, insertable=false, updatable=false )
	private LoanTerms loanTerms;
	
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
	public Date getDue() {
		return due;
	}
	public void setDue(Date due) {
		this.due = due;
	}
	public Double getAmount_due() {
		return amount_due;
	}
	public void setAmount_due(Double amount_due) {
		this.amount_due = amount_due;
	}
	public Date getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}
	public Double getPayment_made() {
		return payment_made;
	}
	public void setPayment_made(Double payment_made) {
		this.payment_made = payment_made;
	}
	public Double getPenalties() {
		return penalties;
	}
	public void setPenalties(Double penalties) {
		this.penalties = penalties;
	}
	public Long getEvent() {
		return event;
	}
	public void setEvent(Long event) {
		this.event = event;
	}
	public LoanTerms getLoanTerms() {
		return loanTerms;
	}
	public void setLoanTerms(LoanTerms loanTerms) {
		this.loanTerms = loanTerms;
	}
	
}
