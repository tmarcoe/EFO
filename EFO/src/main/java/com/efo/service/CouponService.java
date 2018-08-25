package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.CouponDao;
import com.efo.entity.Coupon;
import com.efo.interfaces.ICoupon;

@Service
public class CouponService implements ICoupon {
	
	@Autowired
	CouponDao couponDao;

	@Override
	public void create(Coupon coupon) {
		couponDao.create(coupon);
	}

	@Override
	public Coupon retrieve(Long id) {
		return couponDao.retrieve(id);
	}

	@Override
	public List<Coupon> retrieveRawList() {
		return couponDao.retrieveRawList();
	}

	public PagedListHolder<Coupon> retrieveList() {
		return new PagedListHolder<Coupon>(couponDao.retrieveRawList());
	}
	
	@Override
	public void update(Coupon coupon) {
		couponDao.update(coupon);
	}

	@Override
	public void delete(Coupon coupon) {
		couponDao.delete(coupon);	
	}

}
