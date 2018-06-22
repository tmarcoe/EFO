package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.PaymentsReceived;
import com.efo.interfaces.IPaymentsReceived;

@Transactional
@Repository
public class PaymentsReceivedDao implements IPaymentsReceived {


	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(PaymentsReceived payments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(payments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PaymentsReceived retreive(int id) {
		Session session = session();
		PaymentsReceived p = (PaymentsReceived) session.createCriteria(PaymentsReceived.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		return p;
	}

	@Override
	public void update(PaymentsReceived payments) {
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
		PaymentsReceived p = retreive(id);
		session.delete(p);
		tx.commit();
		session.disconnect();			
	}

	public List<PaymentsReceived> retreiveList(Long invoice_num) {
		Session session = session();
		
		@SuppressWarnings("unchecked")
		List<PaymentsReceived> rList = session.createCriteria(PaymentsReceived.class).add(Restrictions.eq("invoice_num", invoice_num)).list();
		
		session.disconnect();
		
		return rList;
	}
}
