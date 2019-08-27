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

import com.efo.entity.ExpenseTerms;
import com.efo.interfaces.IExpenseTerms;

@Transactional
@Repository
public class ExpenseTermsDao implements IExpenseTerms {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(ExpenseTerms expenseTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.close();
	}

	@Override
	public ExpenseTerms retrieve(Long reference) {
		Session session = session();
		ExpenseTerms revenueTerms = (ExpenseTerms) session.createCriteria(ExpenseTerms.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return revenueTerms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseTerms> retrieveRawList() {
		Session session = session();
		List<ExpenseTerms> expenseList = session.createCriteria(ExpenseTerms.class).list();
		session.close();
		
		return expenseList;
	}

	@Override
	public void merge(ExpenseTerms expenseTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.close();
	}

	@Override
	public void delete(ExpenseTerms expenseTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.close();
	}

}
