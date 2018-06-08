package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	private String sku;
	private double wholesale;
	private double sold_for;
	private Date ordered;
	private Date sold;
	private Date processed;
	private Date shipped;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sku", nullable = false, insertable=false, updatable=false )
	private Product product;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getWholesale() {
		return wholesale;
	}
	public void setWholesale(double wholesale) {
		this.wholesale = wholesale;
	}
	public double getSold_for() {
		return sold_for;
	}
	public void setSold_for(double sold_for) {
		this.sold_for = sold_for;
	}
	public Date getOrdered() {
		return ordered;
	}
	public void setOrdered(Date ordered) {
		this.ordered = ordered;
	}
	public Date getSold() {
		return sold;
	}
	public void setSold(Date sold) {
		this.sold = sold;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
