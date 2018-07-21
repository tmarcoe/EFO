package com.efo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int holders_id;
	private String holders_name;
	private String date_purchased;
	private double price;
	private double valued_at;
	private Long shares;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getHolders_id() {
		return holders_id;
	}
	public void setHolders_id(int holders_id) {
		this.holders_id = holders_id;
	}
	public String getHolders_name() {
		return holders_name;
	}
	public void setHolders_name(String holders_name) {
		this.holders_name = holders_name;
	}
	public String getDate_purchased() {
		return date_purchased;
	}
	public void setDate_purchased(String date_purchased) {
		this.date_purchased = date_purchased;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getValued_at() {
		return valued_at;
	}
	public void setValued_at(double valued_at) {
		this.valued_at = valued_at;
	}
	public Long getShares() {
		return shares;
	}
	public void setShares(Long shares) {
		this.shares = shares;
	}
	
}
