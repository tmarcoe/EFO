package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PayStub implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long user_id;
	private Date begin_period;
	private Date processed;
	private Double hours_worked;
	private Double overtime_worked;
	private Double amount_earned;
	private Double fed_wh;
	private Double st_wh;
	private Double medicare;
	private Double fica;
	private Double fed_unemployment;	// Federal unemployement
	private Double st_unemployment;	// State unemployement
	private Double medical;				// Company medical plan
	private Double retirement;			// Company retirement plan
	private Double union_dues;
	private Double garnishment;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public Date getBegin_period() {
		return begin_period;
	}
	
	public void setBegin_period(Date begin_period) {
		this.begin_period = begin_period;
	}
	
	public Date getProcessed() {
		return processed;
	}

	public void setProcessed(Date processed) {
		this.processed = processed;
	}

	public Double getHours_worked() {
		return hours_worked;
	}
	
	public void setHours_worked(Double hours_worked) {
		this.hours_worked = hours_worked;
	}
	
	public Double getOvertime_worked() {
		return overtime_worked;
	}
	
	public void setOvertime_worked(Double overtime_worked) {
		this.overtime_worked = overtime_worked;
	}
	
	public Double getAmount_earned() {
		return amount_earned;
	}
	
	public void setAmount_earned(Double amount_earned) {
		this.amount_earned = amount_earned;
	}
	
	public Double getFed_wh() {
		return fed_wh;
	}

	public void setFed_wh(Double fed_wh) {
		this.fed_wh = fed_wh;
	}

	public Double getSt_wh() {
		return st_wh;
	}

	public void setSt_wh(Double st_wh) {
		this.st_wh = st_wh;
	}

	public Double getMedicare() {
		return medicare;
	}
	
	public void setMedicare(Double medicare) {
		this.medicare = medicare;
	}
	
	public Double getFica() {
		return fica;
	}
	
	public void setFica(Double fica) {
		this.fica = fica;
	}
	
	public Double getFed_unemployment() {
		return fed_unemployment;
	}
	
	public void setFed_unemployment(Double fed_unemployment) {
		this.fed_unemployment = fed_unemployment;
	}
	
	public Double getSt_unemployment() {
		return st_unemployment;
	}
	
	public void setSt_unemployment(Double st_unemployment) {
		this.st_unemployment = st_unemployment;
	}
	
	public Double getMedical() {
		return medical;
	}
	
	public void setMedical(Double medical) {
		this.medical = medical;
	}
	
	public Double getRetirement() {
		return retirement;
	}
	
	public void setRetirement(Double retirement) {
		this.retirement = retirement;
	}
	
	public Double getUnion_dues() {
		return union_dues;
	}
	
	public void setUnion_dues(Double union_dues) {
		this.union_dues = union_dues;
	}
	
	public Double getGarnishment() {
		return garnishment;
	}
	
	public void setGarnishment(Double garnishment) {
		this.garnishment = garnishment;
	}
	
}
