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
public class PettyCashVoucher implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int pc_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PC_ID", nullable = false, insertable = false, updatable = false)
	private PettyCash pettyCash;
	private Date timeStamp;
	private String recipient;
	private String fromAccount;
	private String reason;
	private double amount;
	
	public PettyCashVoucher() {
	}
	
	public PettyCashVoucher(Date timeStamp) {
		super();
		this.timeStamp = timeStamp;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPc_id() {
		return pc_id;
	}

	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}

	public PettyCash getPettyCash() {
		return pettyCash;
	}

	public void setPettyCash(PettyCash pettyCash) {
		this.pettyCash = pettyCash;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
