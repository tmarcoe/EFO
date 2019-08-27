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

import com.efo.entity.TimeReportingAccounts;
import com.efo.interfaces.ITimeReportingAccounts;

@Transactional
@Repository
public class TimeReportingAccountsDao implements ITimeReportingAccounts {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(TimeReportingAccounts timeReportingAccounts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(timeReportingAccounts);
		tx.commit();
		session.close();
	}

	@Override
	public TimeReportingAccounts retrieve(String account_number) {
		Session session = session();
		TimeReportingAccounts account = (TimeReportingAccounts) session.createCriteria(TimeReportingAccounts.class).add(Restrictions.idEq(account_number)).uniqueResult();
		session.close();
		
		return account;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeReportingAccounts> retrieveRawList(String department) {
		String hql ="FROM TimeReportingAccounts WHERE department = :department OR department = ''";
		Session session = session();
		
		List<TimeReportingAccounts> accountsList = session.createQuery(hql)
														  .setString("department", department)
														  .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return accountsList;
	}

	@Override
	public void merge(TimeReportingAccounts timeReportingAccounts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(timeReportingAccounts);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(String account_number) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.close();
	}

}
