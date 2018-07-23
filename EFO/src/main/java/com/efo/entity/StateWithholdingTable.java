package com.efo.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class StateWithholdingTable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double lowIncome;
	private double highIncome;
	private Integer allowances;
	private double amountWithHeld;
	private String status;
	private String paySchedule;
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getLowIncome() {
		return lowIncome;
	}
	public void setLowIncome(double lowIncome) {
		this.lowIncome = lowIncome;
	}
	public double getHighIncome() {
		return highIncome;
	}
	public void setHighIncome(double highIncome) {
		this.highIncome = highIncome;
	}
	public Integer getAllowances() {
		return allowances;
	}
	public void setAllowances(Integer allowances) {
		this.allowances = allowances;
	}
	public double getAmountWithHeld() {
		return amountWithHeld;
	}
	public void setAmountWithHeld(double amountWithHeld) {
		this.amountWithHeld = amountWithHeld;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaySchedule() {
		return paySchedule;
	}
	public void setPaySchedule(String paySchedule) {
		this.paySchedule = paySchedule;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


}
