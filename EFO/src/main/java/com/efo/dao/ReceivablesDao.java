package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Receivables;
import com.efo.interfaces.IReceivables;

@Transactional
@Repository
public class ReceivablesDao implements IReceivables {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Receivables receivables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(receivables);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Receivables retreive(String invoice_num) {
		Session session = session();
		Receivables r = (Receivables) session.createCriteria(Receivables.class).add(Restrictions.idEq(invoice_num)).uniqueResult();
		session.disconnect();
		return r;
	}
	
	public List<Receivables> retreiveList() {
		Session session = session();
		@SuppressWarnings("unchecked")
		List<Receivables> rList = session.createCriteria(Receivables.class).list();
		return rList;
	}

	@Override
	public void update(Receivables receivables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(receivables);
		tx.commit();
		session.disconnect();
	}
	
	
	@Override
	public void delete(String invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		Receivables r = retreive(invoice_num);
		session.delete(r);
		tx.commit();
		session.disconnect();
	}

}
