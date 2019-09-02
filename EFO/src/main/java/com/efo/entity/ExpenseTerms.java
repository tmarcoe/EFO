package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class ExpenseTerms implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "expenses"))
	@Id
	@GeneratedValue(generator = "generator")
	private Long reference;
	private Double down;
	private Double interest;
	private Long num_payments;
	private Long remaining;
	private Double each_payment;
	private Double loan_balance;
	private Date start;
	@Column(length = 16)
	private String schedule;
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Expenses expenses;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "expenseTerms", cascade = CascadeType.ALL)
	private Set<ExpensePayments> expensePayments = new HashSet<ExpensePayments>(0);
	
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public Double getDown() {
		return down;
	}
	public void setDown(Double down) {
		this.down = down;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Long getNum_payments() {
		return num_payments;
	}
	public void setNum_payments(Long num_payments) {
		this.num_payments = num_payments;
	}
	public Long getRemaining() {
		return remaining;
	}
	public void setRemaining(Long remaining) {
		this.remaining = remaining;
	}
	public Double getEach_payment() {
		return each_payment;
	}
	public void setEach_payment(Double each_payment) {
		this.each_payment = each_payment;
	}
	public Double getLoan_balance() {
		return loan_balance;
	}
	public void setLoan_balance(Double loan_balance) {
		this.loan_balance = loan_balance;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public Expenses getExpenses() {
		return expenses;
	}
	public void setExpenses(Expenses expenses) {
		this.expenses = expenses;
	}
	public Set<ExpensePayments> getExpensePayments() {
		return expensePayments;
	}
	public void setExpensePayments(Set<ExpensePayments> expensePayments) {
		this.expensePayments = expensePayments;
	}
	public void add(ExpensePayments expensePayment) {
		expensePayments.add(expensePayment);
	}
}
