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

import com.efo.entity.LoanTerms;
import com.efo.interfaces.ILoanTerms;

@Transactional
@Repository
public class LoanTermsDao implements ILoanTerms {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(LoanTerms loanTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(loanTerms);
		tx.commit();
		session.close();
	}

	@Override
	public LoanTerms retreive(Long reference) {
		Session session = session();
		LoanTerms loanTerms = (LoanTerms) session.createCriteria(LoanTerms.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return loanTerms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTerms> retreiveRawList() {
		Session session = session();
		List<LoanTerms> termsList = session.createCriteria(LoanTerms.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return termsList;
	}

	@Override
	public void update(LoanTerms loanTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(loanTerms);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(LoanTerms loanTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(loanTerms);
		tx.commit();
		session.close();
	}

}
