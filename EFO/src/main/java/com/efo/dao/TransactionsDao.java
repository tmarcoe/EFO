package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Transactions;
import com.efo.interfaces.ITransactions;

@Transactional
@Repository
public class TransactionsDao implements ITransactions {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Transactions transactions) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(transactions);
		tx.commit();
		session.close();
	}

	@Override
	public Transactions retrieve(Long reference) {
		Session session = session();
		Transactions transactions = (Transactions) session.createCriteria(Transactions.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return transactions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transactions> retrieveRawList(Date begin, Date end) {
		String hql = "FROM Transactions WHERE DATE(timestamp) BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		List<Transactions> transList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return transList;
	}

	@Override
	public void update(Transactions transactions) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(transactions);
		tx.commit();
		session.close();

	}

	@Override
	public void delete(Transactions transactions) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(transactions);
		tx.commit();
		session.close();

	}
	
	public boolean overheadExists(String name, String profileName)  {
		String hql = "SELECT COUNT(*) FROM Transactions WHERE payment_name = :name AND name = :profileName";
		Session session = session();
		Long count = (Long) session.createQuery(hql).setString("name", name).setString("profileName", profileName).uniqueResult();
		session.close();
		
		return (count > 0);
	}

}
