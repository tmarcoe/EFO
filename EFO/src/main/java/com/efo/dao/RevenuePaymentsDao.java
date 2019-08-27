package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.RevenuePayments;
import com.efo.interfaces.IRevenuePayments;

@Transactional
@Repository
public class RevenuePaymentsDao implements IRevenuePayments {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(RevenuePayments revenuePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenuePayments);
		tx.commit();
		session.close();
	}

	@Override
	public RevenuePayments retrieve(Long id) {
		Session session = session();
		RevenuePayments revenuePayments = (RevenuePayments) session.createCriteria(RevenuePayments.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return revenuePayments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RevenuePayments> retrieveRawList(Long reference) {
		Session session = session();
		List<RevenuePayments> revList = session.createCriteria(RevenuePayments.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("reference", reference)).list();
		session.close();
		
		return revList;
	}
	
	public Double sumCreditPayments(int month, int year) {
		String hql = "SELECT SUM(payment_made) FROM revenue_payments WHERE MONTH(payment_date) = :month AND YEAR(payment_date) = :year";
		Session session = session();
		
		Double result = (Double) session.createSQLQuery(hql).setInteger("month", month).setInteger("year", year).uniqueResult();
		if (result == null) result = 0.0;
		session.close();
		
		return result;
	}

	@Override
	public void merge(RevenuePayments revenuePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenuePayments);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(RevenuePayments revenuePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenuePayments);
		tx.commit();
		session.close();
	}

}
