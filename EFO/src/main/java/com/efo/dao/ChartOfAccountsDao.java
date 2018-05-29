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

import com.efo.entity.ChartOfAccounts;
import com.efo.interfaces.IChartOfAccounts;




@Transactional
@Repository
public class ChartOfAccountsDao implements IChartOfAccounts {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ChartOfAccounts accounts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(accounts);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void update(ChartOfAccounts accounts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(accounts);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ChartOfAccounts retrieve(String account) {
		Session session = session();
		Criteria crit = session.createCriteria(ChartOfAccounts.class);
		crit.add(Restrictions.idEq(account));
		ChartOfAccounts ca = (ChartOfAccounts) crit.uniqueResult();
		
		return ca;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChartOfAccounts> getRawList() {
		Session session = session();
		Criteria crit = session.createCriteria(ChartOfAccounts.class);
		List<ChartOfAccounts> accList = crit.list();
		
		session.disconnect();
		
		return accList;
	}

	@Override
	public void delete(ChartOfAccounts account) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(account);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(String account) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(ChartOfAccounts.class);
		crit.add(Restrictions.idEq(account));
		ChartOfAccounts acc = (ChartOfAccounts) crit.uniqueResult();
		session.delete(acc);
		tx.commit();
		session.disconnect();
	}

	public boolean exists(String account) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM ChartOfAccounts WHERE accountNum = :account";
		long count = (long) session.createQuery(hql).setString("account", account).uniqueResult();
		
		session.disconnect();
		
		return (count > 0);
	}
	
	public boolean exist() {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM ChartOfAccounts";
		long count = (long) session.createQuery(hql).uniqueResult();
		
		session.disconnect();
		
		return (count > 0);
	}


}
