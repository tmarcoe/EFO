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

import com.efo.entity.Loans;
import com.efo.interfaces.ILoans;

@Transactional
@Repository
public class LoansDao implements ILoans {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}

	@Override
	public void create(Loans loans) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(loans);
		tx.commit();
		session.close();
	}

	@Override
	public Loans retrieve(Long reference) {
		Session session = session();
		Loans loan = (Loans) session.createCriteria(Loans.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return loan;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Loans> retrieveRawList() {
		Session session = session();
		List<Loans> loanList = session.createCriteria(Loans.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return loanList;
	}

	@Override
	public void merge(Loans loans) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(loans);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Loans loans) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(loans);
		tx.commit();
		session.close();
	}

}
