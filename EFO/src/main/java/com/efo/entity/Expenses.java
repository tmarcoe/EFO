package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Expenses implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private Date paid;
	@Column(length = 64)
	private String payee;
	private Double amount;
	@Column(length = 1024)
	private String descr;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "expenses", cascade = CascadeType.ALL)
	private ExpenseTerms expenseTerms;
	
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public Date getPaid() {
		return paid;
	}
	public void setPaid(Date paid) {
		this.paid = paid;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public ExpenseTerms getExpenseTerms() {
		return expenseTerms;
	}
	public void setExpenseTerms(ExpenseTerms expenseTerms) {
		this.expenseTerms = expenseTerms;
	}
	
}
