package com.efo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TimeSheetItems implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long reference;
	private String account_num;
	private Double sun;
	private Double mon;
	private Double tue;
	private Double wed;
	private Double thu;
	private Double fri;
	private Double sat;
	private Double total;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE", referencedColumnName ="REFERENCE", nullable = false, insertable=false, updatable=false )
	private TimeSheet timeSheet;

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

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public Double getSun() {
		return sun;
	}

	public void setSun(Double sun) {
		this.sun = sun;
	}

	public Double getMon() {
		return mon;
	}

	public void setMon(Double mon) {
		this.mon = mon;
	}

	public Double getTue() {
		return tue;
	}

	public void setTue(Double tue) {
		this.tue = tue;
	}

	public Double getWed() {
		return wed;
	}

	public void setWed(Double wed) {
		this.wed = wed;
	}

	public Double getThu() {
		return thu;
	}

	public void setThu(Double thu) {
		this.thu = thu;
	}

	public Double getFri() {
		return fri;
	}

	public void setFri(Double fri) {
		this.fri = fri;
	}

	public Double getSat() {
		return sat;
	}

	public void setSat(Double sat) {
		this.sat = sat;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public TimeSheet getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(TimeSheet timeSheet) {
		this.timeSheet = timeSheet;
	}
	
}
