package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.OverheadExpenses;
import com.efo.interfaces.IOverheadExpenses;

@Transactional
@Repository
public class OverheadExpensesDao implements IOverheadExpenses {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	

	@Override
	public void create(OverheadExpenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(expenses);
		tx.commit();
		session.disconnect();
	}

	@Override
	public OverheadExpenses retrieve(Long id) {
		Session session = session();
		OverheadExpenses expenses = (OverheadExpenses) session.createCriteria(OverheadExpenses.class)
															  .add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return expenses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OverheadExpenses> retrieveRawList() {
		Session session = session();
		List<OverheadExpenses> expenseList = session.createCriteria(OverheadExpenses.class).list();
		session.disconnect();
		
		return expenseList;
	}

	@Override
	public void update(OverheadExpenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(expenses);
		tx.commit();
		session.disconnect();
	}

	public void merge(OverheadExpenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(expenses);
		tx.commit();
		session.disconnect();		
	}
	
	@Override
	public void delete(OverheadExpenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(expenses);
		tx.commit();
		session.disconnect();
	}

}
