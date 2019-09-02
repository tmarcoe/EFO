package com.efo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Expenses;
import com.efo.interfaces.IExpenses;

@Transactional
@Repository
public class ExpensesDao implements IExpenses {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Expenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(expenses);
		tx.commit();
		session.close();
	}

	@Override
	public Expenses retrieve(Long reference) {
		Session session = session();
		Expenses expenses = (Expenses) session.createCriteria(Expenses.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return expenses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expenses> retrieveRawList() {
		Session session = session();
		List<Expenses> expList = session.createCriteria(Expenses.class).list();
		session.close();
		
		return expList;
	}

	@Override
	public void merge(Expenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(expenses);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Expenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(expenses);
		tx.commit();
		session.close();
	}
	
	public Map<String, String> sumMonthlyExpenses(Date begin, Date end) {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		Double exp = 0.0;
		String headingPattern = "yyyy-MM";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);

		Map<String, String> expMap = new HashMap<String, String>();
		Session session = session();
		String cash = "SELECT SUM(amount) FROM Expenses WHERE MONTH(paid) = :month AND YEAR(paid) = :year";
		String credit = "SELECT SUM(payment_made) FROM ExpensePayments WHERE MONTH(payment_date) = :month AND YEAR(payment_date) = :year";
		
		Double[] item = new Double[2];
		while (jBegin.isBefore(jEnd) || jBegin.isEqual(jEnd)) {
			exp = (Double) session.createQuery(cash).setInteger("month", jBegin.getMonthOfYear()).setInteger("year", jBegin.getYear()).uniqueResult();
			if (exp == null) exp = 0.0;
			item[0] = exp;
			exp = (Double) session.createQuery(credit).setInteger("month", jBegin.getMonthOfYear()).setInteger("year", jBegin.getYear()).uniqueResult();
			if (exp == null) exp = 0.0;
			item[1] = exp;
				
			expMap.put(df.format(jBegin.toDate()), String.format("%.2f,%.2f", item[0], item[1]));
			jBegin = jBegin.plusMonths(1);
		}
		session.flush();
		session.close();
		
		return expMap;
	}

}
