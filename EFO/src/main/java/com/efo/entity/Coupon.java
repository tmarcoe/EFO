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
public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	private int coupon_id;
	private String name;
	private String description;
	private String code_word;
	private String fetal_trans;
	private Date expires;
	private boolean valid;
	private String signature;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "coupon", cascade = CascadeType.ALL)
	private Set<UsedCoupons> usedCoupons = new HashSet<UsedCoupons>(0);

	
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int id) {
		coupon_id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode_word() {
		return code_word;
	}
	public void setCode_word(String code_word) {
		this.code_word = code_word;
	}
	public String getFetal_trans() {
		return fetal_trans;
	}
	public void setFetal_trans(String fetal_trans) {
		this.fetal_trans = fetal_trans;
	}
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Set<UsedCoupons> getUsedCoupons() {
		return usedCoupons;
	}
	public void setUsedCoupons(Set<UsedCoupons> usedCoupons) {
		this.usedCoupons = usedCoupons;
	}	
}
