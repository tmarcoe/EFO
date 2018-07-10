package com.efo.entity;

import java.io.Serializable;
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
	private Long id;
	private Long reference;
	private String inv_num;
	private String vendor;
	private String reason;
	private String account;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "overheadExpenses", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
	private Set<PaymentHistory> paymentHistory = new HashSet<PaymentHistory>(0);

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

	public String getInv_num() {
		return inv_num;
	}

	public void setInv_num(String inv_num) {
		this.inv_num = inv_num;
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

	public Set<PaymentHistory> getPaymentHistory() {
		return paymentHistory;
	}

	public void setPaymentHistory(Set<PaymentHistory> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
	
	
}
