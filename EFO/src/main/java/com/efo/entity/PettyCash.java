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

@Entity
public class PettyCash implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pc_id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pettyCash")
	private Set<PettyCashVoucher> pettyCashVoucher = new HashSet<PettyCashVoucher>(0);
	
	private double maxAmount;
	private double minAmount;
	private Date lastTransaction;
	
	
	public int getPc_id() {
		return pc_id;
	}
	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}
	public Set<PettyCashVoucher> getPettyCashVoucher() {
		return pettyCashVoucher;
	}
	public void setPettyCashVoucher(Set<PettyCashVoucher> pettyCashVoucher) {
		this.pettyCashVoucher = pettyCashVoucher;
	}
	public double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}
	public double getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}
	public Date getLastTransaction() {
		return lastTransaction;
	}
	public void setLastTransaction(Date lastTransaction) {
		this.lastTransaction = lastTransaction;
	}
}
