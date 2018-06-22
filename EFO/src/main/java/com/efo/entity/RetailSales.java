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
public class RetailSales implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	private Long invoice_num;
		
	private int user_id;
	private double total_price;
	private Date ordered;
	private Date processed;
	private Date shipped;
	private boolean changed; //Has the SalesItem list changed?
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "retailSales", cascade = CascadeType.ALL)
	private Set<SalesItem> salesItem = new HashSet<SalesItem>(0);
	
	public Long getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(Long invoice_num) {
		this.invoice_num = invoice_num;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
	public Date getOrdered() {
		return ordered;
	}
	public void setOrdered(Date ordered) {
		this.ordered = ordered;
	}
	public Date getProcessed() {
		return processed;
	}
	public void setProcessed(Date processed) {
		this.processed = processed;
	}
	public Date getShipped() {
		return shipped;
	}
	public void setShipped(Date shipped) {
		this.shipped = shipped;
	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	public Set<SalesItem> getSalesItem() {
		return salesItem;
	}
	public void setSalesItem(Set<SalesItem> salesItem) {
		this.salesItem = salesItem;
	}
}
