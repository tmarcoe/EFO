package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Coupon;
import com.efo.interfaces.ICoupon;

@Transactional
@Repository
public class CouponDao implements ICoupon {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Coupon coupon) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.disconnect();
	}

	@Override
	public Coupon retrieve(Long id) {
		Session session = session();
		Coupon coupon = (Coupon) session.createCriteria(Coupon.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return coupon;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coupon> retrieveRawList() {
		Session session = session();
		List<Coupon> couponList = session.createCriteria(Coupon.class).list();
		session.disconnect();	
		
		return couponList;
	}

	@Override
	public void update(Coupon coupon) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(coupon);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Coupon coupon) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(coupon);
		tx.commit();
		session.disconnect();
	}

}
