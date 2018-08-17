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
public class Budget implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private String title;
	private Date begin;
	private Date end;
	private String department;
	private String author;
	private Date creation;
	private Date submitted;
	private Date rejected;
	private String reason;
	private Date approved;
	private double total;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "budget", cascade = CascadeType.ALL)
	private Set<BudgetItems> budgetItems = new HashSet<BudgetItems>(0);
	
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public Date getRejected() {
		return rejected;
	}
	public void setRejected(Date rejected) {
		this.rejected = rejected;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getApproved() {
		return approved;
	}
	public void setApproved(Date approved) {
		this.approved = approved;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Set<BudgetItems> getBudgetItems() {
		return budgetItems;
	}
	public void setBudgetItems(Set<BudgetItems> budgetItems) {
		this.budgetItems = budgetItems;
	}
}
