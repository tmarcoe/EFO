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

import com.efo.entity.PaymentsBilled;
import com.efo.interfaces.IPaymentsBilled;


@Transactional
@Repository
public class PaymentsBilledDao implements IPaymentsBilled {


	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(PaymentsBilled payments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(payments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PaymentsBilled retreive(int id) {
		Session session = session();
		PaymentsBilled p = (PaymentsBilled) session.createCriteria(PaymentsBilled.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		return p;
	}

	@Override
	public void update(PaymentsBilled payments) {
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
		PaymentsBilled p = retreive(id);
		session.delete(p);
		tx.commit();
		session.disconnect();
	}
	
	public List<PaymentsBilled> retreiveList(String invoice_num) {
		Session session = session();
		
		@SuppressWarnings("unchecked")
		List<PaymentsBilled> pList = session.createCriteria(PaymentsBilled.class)
										  .add(Restrictions.eq("invoice_num", invoice_num))
										  .list();
		
		session.disconnect();
		return pList;
	}
	
	public Date lastestDate(String invoice_num) {
		String hql = "SELECT MAX(date_due) FROM PaymentsBilled WHERE invoice_num = :invoice_num)";
		Session session = session();
		Date maxDate = (Date) session.createQuery(hql).setString("invoice_num", invoice_num).uniqueResult();
		session.disconnect();
		
		return maxDate;
	}

}
