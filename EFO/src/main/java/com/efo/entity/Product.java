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
	private Long shelf_life; // In Days
	private double min_amount;
	private double order_amount;
	private boolean on_sale;
	private boolean discontinue;
	private boolean consignment;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy = "product", cascade = CascadeType.MERGE)
	private FluidInventory fluidInventory = new FluidInventory();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private Set<EachInventory> eachInventory = new HashSet<EachInventory>(0);
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private Set<OrderItems> orders = new HashSet<OrderItems>(0);
	
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
	public Long getShelf_life() {
		return shelf_life;
	}
	public void setShelf_life(Long shelf_life) {
		this.shelf_life = shelf_life;
	}
	public double getMin_amount() {
		return min_amount;
	}
	public void setMin_amount(double min_amount) {
		this.min_amount = min_amount;
	}
	public double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
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
	public FluidInventory getFluidInventory() {
		return fluidInventory;
	}
	public void setFluidInventory(FluidInventory inventory) {
		this.fluidInventory = inventory;
	}
	public Set<EachInventory> getEachInventory() {
		return eachInventory;
	}
	public void setEachInventory(Set<EachInventory> eachInventory) {
		this.eachInventory = eachInventory;
	}
	public Set<OrderItems> getOrders() {
		return orders;
	}
	public void setOrders(Set<OrderItems> orders) {
		this.orders = orders;
	}
	public Set<SalesItem> getSales() {
		return sales;
	}
	public void setSales(Set<SalesItem> sales) {
		this.sales = sales;
	}
	
}
