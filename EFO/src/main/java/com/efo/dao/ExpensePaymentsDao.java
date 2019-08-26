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

import com.efo.entity.ExpensePayments;
import com.efo.interfaces.IExpensePayments;

@Transactional
@Repository
public class ExpensePaymentsDao implements IExpensePayments {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(ExpensePayments expensePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(expensePayments);
		tx.commit();
		session.close();
	}

	@Override
	public ExpensePayments retrieve(Long id) {
		Session session = session();
		ExpensePayments expensePayments = (ExpensePayments) session.createCriteria(ExpensePayments.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return expensePayments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpensePayments> retrieveRawList(Long reference) {
		Session session = session();
		List<ExpensePayments> expList = session.createCriteria(ExpensePayments.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("reference", reference)).list();
		session.close();
		
		return expList;
	}

	@Override
	public void merge(ExpensePayments expensePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(expensePayments);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(ExpensePayments expensePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(expensePayments);
		tx.commit();
		session.close();
	}
	
	public Double sumMontlyPayments(int month, int year) {
		Session session = session();
		String hql = "SELECT SUM(payment_made) FROM ExpensePayments WHERE MONTH(payment_date) = :month AND YEAR(payment_date) = :year";
		
		Double sum = (Double) session.createQuery(hql).setInteger("month", month).setInteger("year", year).uniqueResult();
		
		if (sum == null) sum = 0.0;
		
		session.close();
		
		return sum;
	}

}
