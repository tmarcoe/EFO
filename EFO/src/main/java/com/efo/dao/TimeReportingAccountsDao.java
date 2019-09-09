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
	
	public void deleteByUserIds(Long user_id) {
		Session session = session();
		String hql = "DELETE FROM assigned_accounts WHERE user_id = :user_id";
		Transaction tx = session.beginTransaction();
		
		session.createSQLQuery(hql).setLong("user_id", user_id).executeUpdate();
		tx.commit();
		session.close();
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
	
	public boolean accountExists(String account_number) {
		Long count = 0L;
		String hql = "SELECT COUNT(*) FROM TimeReportingAccounts WHERE account_number = :account_number";
		Session session = session();
		
		count = (Long) session.createQuery(hql).setString("account_number", account_number).uniqueResult();
		
		session.close();
		
		return (count > 0L);
	}

}
