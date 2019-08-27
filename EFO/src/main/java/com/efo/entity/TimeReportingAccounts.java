package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TimeReportingAccounts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String account_number;
	private String cofa_account;
	private String department;
	private String descriptiion;
	private Date begins;
	private Date expires;
	private Double max_amount;
	private Double max_hours;
	private boolean active;
	
	public String getAccount_number() {
		return account_number;
	}
	
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
	public String getCofa_account() {
		return cofa_account;
	}
	
	public void setCofa_account(String cofa_account) {
		this.cofa_account = cofa_account;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescriptiion() {
		return descriptiion;
	}

	public void setDescriptiion(String descriptiion) {
		this.descriptiion = descriptiion;
	}

	public Date getBegins() {
		return begins;
	}
	
	public void setBegins(Date begins) {
		this.begins = begins;
	}
	
	public Date getExpires() {
		return expires;
	}
	
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	public Double getMax_amount() {
		return max_amount;
	}
	
	public void setMax_amount(Double max_amount) {
		this.max_amount = max_amount;
	}
	
	public Double getMax_hours() {
		return max_hours;
	}
	
	public void setMax_hours(Double max_hours) {
		this.max_hours = max_hours;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
