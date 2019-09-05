package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.PayStub;
import com.efo.interfaces.IPayStub;

@Transactional
@Repository
public class PayStubDao implements IPayStub {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(PayStub payStub) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		session.save(payStub);
		
		tx.commit();
		session.close();
	}

	@Override
	public PayStub retrieve(Long id) {
		Session session = session();
		PayStub payStub = (PayStub) session.createCriteria(PayStub.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return payStub;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayStub> retrieveRawList() {
		Session session = session();
		
		List<PayStub> stubList = session.createCriteria(PayStub.class).list();
		
		session.close();
		
		return stubList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayStub> retrievePeriod(Date begin) {
		Session session = session();
		
		List<PayStub> stubList = session.createCriteria(PayStub.class).add(Restrictions.eq("begin_period", begin)).list();
		
		session.close();
		
		return stubList;
	}

	@Override
	public void update(PayStub payStub) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		session.update(payStub);
		
		tx.commit();
		session.close();
	}

	@Override
	public void delete(PayStub payStub) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		session.delete(payStub);
		
		tx.commit();
		session.close();
	}

}
