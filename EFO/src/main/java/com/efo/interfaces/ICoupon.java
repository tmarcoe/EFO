package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Coupon;

public interface ICoupon {
	public void create(Coupon coupon);
	public Coupon retrieve(int id);
	public List<Coupon> retrieveRawList();
	public void update(Coupon coupon);
	public void delete(Coupon coupon);
}
