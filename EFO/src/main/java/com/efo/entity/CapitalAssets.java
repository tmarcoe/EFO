package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CapitalAssets implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String invoice_num;
	private String item_name;
	private String item_description;
	private Date date_purchased;
	private String purchase_type; //Cash, Credit
	private String vendor;
	private String depreciation_method;  // Straight Method, Double Declining
	private double item_cost;
	private double salvage_value;
	private double lifetime;
		
	public CapitalAssets(Date date_purchased) {
		this.date_purchased = date_purchased;
	}
	public CapitalAssets() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public Date getDate_purchased() {
		return date_purchased;
	}
	public void setDate_purchased(Date date_purchased) {
		this.date_purchased = date_purchased;
	}
	public String getPurchase_type() {
		return purchase_type;
	}
	public void setPurchase_type(String purchase_type) {
		this.purchase_type = purchase_type;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getDepreciation_method() {
		return depreciation_method;
	}
	public void setDepreciation_method(String depreciation_method) {
		this.depreciation_method = depreciation_method;
	}
	public double getItem_cost() {
		return item_cost;
	}
	public void setItem_cost(double item_cost) {
		this.item_cost = item_cost;
	}
	public double getSalvage_value() {
		return salvage_value;
	}
	public void setSalvage_value(double salvage_value) {
		this.salvage_value = salvage_value;
	}
	public double getLifetime() {
		return lifetime;
	}
	public void setLifetime(double lifetime) {
		this.lifetime = lifetime;
	}
	
}
