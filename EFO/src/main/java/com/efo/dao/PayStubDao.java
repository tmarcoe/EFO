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

import com.efo.entity.PayStub;
import com.efo.interfaces.IPayStub;

@Transactional
@Repository
public class PayStubDao implements IPayStub {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(PayStub payStub) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		session.save(payStub);
		
		tx.commit();
		session.close();
	}

	@Override
	public PayStub retrieve(Long id) {
		Session session = session();
		PayStub payStub = (PayStub) session.createCriteria(PayStub.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return payStub;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayStub> retrieveRawList() {
		Session session = session();
		
		List<PayStub> stubList = session.createCriteria(PayStub.class).list();
		
		session.close();
		
		return stubList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayStub> retrievePeriod(Date begin) {
		Session session = session();
		
		List<PayStub> stubList = session.createCriteria(PayStub.class).add(Restrictions.eq("begin_period", begin)).list();
		
		session.close();
		
		return stubList;
	}
	
	public Object[] totalExpenses(Date begin) {
		String hql = "SELECT SUM(amount_earned), SUM(fed_wh), SUM(medicare), SUM(fica), SUM(fed_unemployment),"
				   + " SUM(st_wh), SUM(st_unemployment) FROM PayStub WHERE DATE(begin_period) = DATE(:begin) AND processed IS null";
		Session session = session();
		Object[] total = (Object[]) session.createQuery(hql).setDate("begin", begin).uniqueResult();
		if (total == null) {
			total = new Object[7];
			total[0] = 0.0;
			total[1] = 0.0;
			total[2] = 0.0;
			total[3] = 0.0;
			total[4] = 0.0;
			total[5] = 0.0;
			total[6] = 0.0;
		}
		
		if (total[0] == null) total[0] = 0.0;
		if (total[1] == null) total[1] = 0.0;
		if (total[2] == null) total[2] = 0.0;
		if (total[3] == null) total[3] = 0.0;
		if (total[4] == null) total[4] = 0.0;
		if (total[5] == null) total[5] = 0.0;
		if (total[6] == null) total[6] = 0.0;
		
		session.close();
		
		return total;
	}

	@Override
	public void update(PayStub payStub) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		session.update(payStub);
		
		tx.commit();
		session.close();
	}

	@Override
	public void delete(PayStub payStub) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		session.delete(payStub);
		
		tx.commit();
		session.close();
	}

}
