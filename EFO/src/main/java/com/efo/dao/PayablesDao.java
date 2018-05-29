package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Payables;
import com.efo.interfaces.IPayables;


@Transactional
@Repository
public class PayablesDao implements IPayables {


	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Payables payables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(payables);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Payables retreive(String invoice_num) {
		Session session = session();
		Payables p = (Payables) session.createCriteria(Payables.class).add(Restrictions.idEq(invoice_num)).uniqueResult();
		session.disconnect();
		
		return p;
	}

	@Override
	public void update(Payables payables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(payables);
		tx.commit();
		session.disconnect();
	}
	
	public void merge(Payables payables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(payables);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(String invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		Payables p = retreive(invoice_num);
		session.delete(p);
		tx.commit();
		session.disconnect();
	}

	public List<Payables> retreiveList() {
		Session session = session();
		
		@SuppressWarnings("unchecked")
		List<Payables> p = session.createCriteria(Payables.class).list();
		
		session.disconnect();
		
		return p;
	}
}
