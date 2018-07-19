package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Stocks;
import com.efo.interfaces.IStocks;

@Transactional
@Repository
public class StocksDao implements IStocks {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}


	@Override
	public void create(Stocks stocks) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(stocks);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Stocks retrieve(Long id) {
		Session session = session();
		
		Stocks stock = (Stocks) session.createCriteria(Stocks.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return stock;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stocks> retrieveRawList() {
		Session session = session();
		List<Stocks> stocks = session.createCriteria(Stocks.class).list();
		session.disconnect();
		
		return stocks;
	}

	@Override
	public void update(Stocks stocks) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(stocks);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Stocks stocks) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(stocks);
		tx.commit();
		session.disconnect();
	}

}
