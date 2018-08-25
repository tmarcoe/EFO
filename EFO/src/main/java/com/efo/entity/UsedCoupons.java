package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class UsedCoupons implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "salesItem"))
	@Id
	@GeneratedValue(generator = "generator")
	private Long item_id;
	private String sku;
	private int user_id;
	private int coupon_id;
	private Date used;
	private double couponValue;
	private boolean couponValid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private SalesItem salesItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COUPON_ID", nullable = false, insertable=false, updatable=false )
	private Coupon coupon;
	
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Date getUsed() {
		return used;
	}
	public void setUsed(Date used) {
		this.used = used;
	}
	public double getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(double couponValue) {
		this.couponValue = couponValue;
	}
	public boolean isCouponValid() {
		return couponValid;
	}
	public void setCouponValid(boolean couponValid) {
		this.couponValid = couponValid;
	}
	public SalesItem getSalesItem() {
		return salesItem;
	}
	public void setSalesItem(SalesItem salesItem) {
		this.salesItem = salesItem;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
}
