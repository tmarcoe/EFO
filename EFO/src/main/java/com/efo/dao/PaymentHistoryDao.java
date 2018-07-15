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

import com.efo.entity.PaymentHistory;
import com.efo.interfaces.IPaymentHistory;

@Transactional
@Repository
public class PaymentHistoryDao implements IPaymentHistory {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(PaymentHistory payment) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.disconnect();
	}

	@Override
	public PaymentHistory retrieve(Long id) {
		Session session = session();
		PaymentHistory payment = (PaymentHistory) session.createCriteria(PaymentHistory.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return payment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> retrieveRawList(Long reference) {
		Session session = session();
		List<PaymentHistory> historyList = session.createCriteria(PaymentHistory.class).add(Restrictions.eq("reference", reference)).list();
		session.disconnect();
		
		return historyList;
	}

	@Override
	public void update(PaymentHistory payment) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(payment);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(PaymentHistory payment) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(payment);
		tx.commit();
		session.disconnect();
	}
	

	@SuppressWarnings("unchecked")
	public List<Object[]> totalPayentsByPeriod(Date from, Date to) {
		String hql = "SELECT MONTH(date_paid), SUM(amount_paid) FROM PaymentHistory "
				   + "WHERE DATE(date_paid) BETWEEN DATE(:from) AND DATE(:to) GROUP BY MONTH(date_paid)";
		Session session = session();
		List<Object[]> overheadList = session.createQuery(hql).setDate("from", from).setDate("to", to).list();
		session.disconnect();
		
		return overheadList;
	}

}
