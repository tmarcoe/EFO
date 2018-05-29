package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.PaymentsPaid;
import com.efo.interfaces.IPaymentsPaid;


@Transactional
@Repository
public class PaymentsPaidDao implements IPaymentsPaid {


	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(PaymentsPaid payments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(payments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PaymentsPaid retreive(int id) {
		Session session = session();
		PaymentsPaid p = (PaymentsPaid) session.createCriteria(PaymentsPaid.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		return p;
	}

	@Override
	public void update(PaymentsPaid payments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(payments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(int id) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		PaymentsPaid p = retreive(id);
		session.delete(p);
		tx.commit();
		session.disconnect();
	}
	
	public List<PaymentsPaid> retreiveList(String invoice_num) {
		Session session = session();
		
		@SuppressWarnings("unchecked")
		List<PaymentsPaid> pList = session.createCriteria(PaymentsPaid.class).list();
		
		session.disconnect();
		return pList;
	}

}
