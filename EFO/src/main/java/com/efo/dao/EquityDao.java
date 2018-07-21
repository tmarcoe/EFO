package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Equity;
import com.efo.interfaces.IEquity;

@Transactional
@Repository
public class EquityDao implements IEquity {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}


	@Override
	public void create(Equity stocks) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(stocks);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Equity retrieve(Long id) {
		Session session = session();
		
		Equity stock = (Equity) session.createCriteria(Equity.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return stock;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Equity> retrieveRawList() {
		Session session = session();
		List<Equity> stocks = session.createCriteria(Equity.class).list();
		session.disconnect();
		
		return stocks;
	}

	@Override
	public void update(Equity stocks) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(stocks);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Equity stocks) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(stocks);
		tx.commit();
		session.disconnect();
	}

}
