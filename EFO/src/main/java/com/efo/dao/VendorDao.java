package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.User;
import com.efo.entity.Vendor;
import com.efo.interfaces.IVendor;

@Transactional
@Repository
public class VendorDao implements IVendor {
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(Vendor vendor) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(vendor);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Vendor retrieve(int user_id) {
		Session session = session();
		Vendor vendor = (Vendor) session.createCriteria(Vendor.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.disconnect();
		
		return vendor;
	}

	@Override
	public void update(Vendor vendor) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(vendor);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(int user_id) {
		Vendor vendor = retrieve(user_id);
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(vendor);
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> retrieveEditList() {
		String sql = "FROM User AS u WHERE EXISTS (FROM Vendor AS v WHERE v.user_id = u.user_id)";
	
		Session session = session();
		List<User> chooseList = session.createQuery(sql).list();
		session.disconnect();
	
	return chooseList;

	}
	
	@SuppressWarnings("unchecked")
	public List<User> retrieveEditList(String type) {
		String sql = "FROM User AS u WHERE EXISTS (FROM Vendor AS v WHERE v.user_id = u.user_id AND v.type =:type)";
	
		Session session = session();
		List<User> chooseList = session.createQuery(sql).setString("type", type).list();
		session.disconnect();
	
	return chooseList;

	}
	
	@SuppressWarnings("unchecked")
	public List<Vendor> retrieveList() {
		Session session = session();
		
		List<Vendor> vendors = session.createCriteria(Vendor.class).list();
		session.disconnect();
		
		return vendors;
	}

}
