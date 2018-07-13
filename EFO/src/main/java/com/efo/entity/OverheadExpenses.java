package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OverheadExpenses implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private String vendor;
	private String reason;
	private String account;
	private String schedule;
	private Date begin_date;

	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "overheadExpenses", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
	private Set<PaymentHistory> paymentHistory = new HashSet<PaymentHistory>(0);

	public OverheadExpenses() {
	}
	
	public OverheadExpenses(Date begin_date) {
		this.begin_date = begin_date;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Date getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}

	public Set<PaymentHistory> getPaymentHistory() {
		return paymentHistory;
	}

	public void setPaymentHistory(Set<PaymentHistory> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
	
	public void addPaymentHistory(PaymentHistory payment) {
		paymentHistory.add(payment);
	}
}
