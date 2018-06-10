package com.efo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String sku;
	
	private String upc;
	private String product_name;
	private long amount_in_stock;
	private long min_amount;
	private double price;
	private String category;
	private String subcategory;
	private String keywords;
	private boolean on_sale;
	private boolean discontinue;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "product")
	private Set<Inventory> inventory;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public long getAmount_in_stock() {
		return amount_in_stock;
	}
	public void setAmount_in_stock(long amount_in_stock) {
		this.amount_in_stock = amount_in_stock;
	}
	public long getMin_amount() {
		return min_amount;
	}
	public void setMin_amount(long min_amount) {
		this.min_amount = min_amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public boolean isOn_sale() {
		return on_sale;
	}
	public void setOn_sale(boolean on_sale) {
		this.on_sale = on_sale;
	}
	public boolean isDiscontinue() {
		return discontinue;
	}
	public void setDiscontinue(boolean discontinue) {
		this.discontinue = discontinue;
	}
	public Set<Inventory> getInventory() {
		return inventory;
	}
	public void setInventory(Set<Inventory> inventory) {
		this.inventory = inventory;
	}

}
