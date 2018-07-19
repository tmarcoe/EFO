package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loans implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long trans_id;
	private Long user_id; // id for institution;
	private String institution_name;
	private Date approval;
	private double interest;
	private double down_payment;
	private Long num_payments;
	private String schedule;
	public Long getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(Long trans_id) {
		this.trans_id = trans_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getInstitution_name() {
		return institution_name;
	}
	public void setInstitution_name(String institution_name) {
		this.institution_name = institution_name;
	}
	public Date getApproval() {
		return approval;
	}
	public void setApproval(Date approval) {
		this.approval = approval;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getDown_payment() {
		return down_payment;
	}
	public void setDown_payment(double down_payment) {
		this.down_payment = down_payment;
	}
	public Long getNum_payments() {
		return num_payments;
	}
	public void setNum_payments(Long num_payments) {
		this.num_payments = num_payments;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	
	
}
