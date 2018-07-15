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
	public Payables retreive(Long reference) {
		Session session = session();
		Payables p = (Payables) session.createCriteria(Payables.class).add(Restrictions.idEq(reference)).uniqueResult();
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
	public void delete(Long reference) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		Payables p = retreive(reference);
		session.delete(p);
		tx.commit();
		session.disconnect();
	}

	@SuppressWarnings("unchecked")
	public List<Payables> retreiveList() {
		String hql = "FROM Payables";
		Session session = session();
		List<Payables> p = session.createQuery(hql).list();
		session.disconnect();
		
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public List<Payables> getPeriodPayables(Date begin, Date end) {
		String hql ="FROM Payables WHERE date_begin BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		 List<Payables> payablesList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		 session.disconnect();
		 
		return payablesList;
	}
}
