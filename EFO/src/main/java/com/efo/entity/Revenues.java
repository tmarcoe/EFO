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
public class Revenues implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private Date received;
	private Date posted;
	@Column(length = 64)
	private String name;
	@Column(length = 16)
	private String source;
	private Double amount;
	private Double tax;
	private Double amt_received;
	@Column(length = 1024)
	private String descr;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "revenues", cascade = CascadeType.ALL)
	private RevenueTerms revenueTerms;
	
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public Date getReceived() {
		return received;
	}
	public void setReceived(Date received) {
		this.received = received;
	}
	public Date getPosted() {
		return posted;
	}
	public void setPosted(Date posted) {
		this.posted = posted;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getAmt_received() {
		return amt_received;
	}
	public void setAmt_received(Double amt_received) {
		this.amt_received = amt_received;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public RevenueTerms getRevenueTerms() {
		return revenueTerms;
	}
	public void setRevenueTerms(RevenueTerms revenueTerms) {
		this.revenueTerms = revenueTerms;
	}
}
