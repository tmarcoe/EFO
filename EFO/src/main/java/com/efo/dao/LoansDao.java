package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Loans;
import com.efo.interfaces.ILoans;

@Transactional
@Repository
public class LoansDao implements ILoans {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Loans loans) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(loans);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Loans retrieve(Long trans_id) {
		Session session = session();
		Loans loans = (Loans) session.createCriteria(Loans.class).add(Restrictions.idEq(trans_id)).uniqueResult();
		session.disconnect();
		
		return loans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Loans> retrieveRawList() {
		Session session = session();
		List<Loans> loans = session.createCriteria(Loans.class).list();
		session.disconnect();
		
		return loans;
	}

	@Override
	public void update(Loans loans) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(loans);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Loans loans) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(loans);
		tx.commit();
		session.disconnect();
	}

}
