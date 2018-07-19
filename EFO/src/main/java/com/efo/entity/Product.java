package com.efo.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String sku;
	
	private String upc;
	private String product_name;
	private double price;
	private String unit;
	private String category;
	private String subcategory;
	private String keywords;
	private boolean on_sale;
	private boolean discontinue;
	private boolean consignment;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private NonPhysicalInventory inventory = new NonPhysicalInventory();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private Set<ProductOrders> orders = new HashSet<ProductOrders>(0);
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private Set<SalesItem> sales = new HashSet<SalesItem>(0);
	
	
	
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public boolean isConsignment() {
		return consignment;
	}
	public void setConsignment(boolean consignment) {
		this.consignment = consignment;
	}
	public NonPhysicalInventory getInventory() {
		return inventory;
	}
	public void setInventory(NonPhysicalInventory inventory) {
		this.inventory = inventory;
	}
	public Set<ProductOrders> getOrders() {
		return orders;
	}
	public void setOrders(Set<ProductOrders> orders) {
		this.orders = orders;
	}
	public Set<SalesItem> getSales() {
		return sales;
	}
	public void setSales(Set<SalesItem> sales) {
		this.sales = sales;
	}
	
}
