package com.efo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class FederalWithholdingTable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double low_income;
	private double high_income;
	private Integer allowances;
	private double amount_withheld;
	private String status;
	private String pay_schedule;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getLow_income() {
		return low_income;
	}
	public void setLow_income(double low_income) {
		this.low_income = low_income;
	}
	public double getHigh_income() {
		return high_income;
	}
	public void setHigh_income(double high_income) {
		this.high_income = high_income;
	}
	public Integer getAllowances() {
		return allowances;
	}
	public void setAllowances(Integer allowances) {
		this.allowances = allowances;
	}
	public double getAmount_withheld() {
		return amount_withheld;
	}
	public void setAmount_withheld(double amount_withHeld) {
		this.amount_withheld = amount_withHeld;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPay_schedule() {
		return pay_schedule;
	}
	public void setPay_schedule(String pay_schedule) {
		this.pay_schedule = pay_schedule;
	}
	
}
