package com.efo.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.BillingHistory;
import com.efo.interfaces.IBillingHistory;

@Transactional
@Repository
public class BillingHistoryDao implements IBillingHistory {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(BillingHistory billingHistory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(billingHistory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public BillingHistory retrieve(int id) {
		Session session = session();
		BillingHistory billingHistory = (BillingHistory) session.createCriteria(BillingHistory.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		return billingHistory;
	}

	@Override
	public void update(BillingHistory billingHistory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(billingHistory);
		tx.commit();
		session.disconnect();

	}

	@Override
	public void delete(int id) {
		BillingHistory billingHistory = retrieve(id);
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(billingHistory);
		tx.commit();
		session.disconnect();

	}

}
