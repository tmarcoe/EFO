package com.efo.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id 
	private String sku;
	private double amt_in_stock;
	private double min_amount;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="SKU")
	private Product product;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getAmt_in_stock() {
		return amt_in_stock;
	}

	public void setAmt_in_stock(double amt_in_stock) {
		this.amt_in_stock = amt_in_stock;
	}

	public double getMin_amount() {
		return min_amount;
	}

	public void setMin_amount(double min_amount) {
		this.min_amount = min_amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
