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

import com.efo.entity.Revenues;
import com.efo.interfaces.IRevenues;

@Transactional
@Repository
public class RevenuesDao implements IRevenues {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenues);
		tx.commit();
		session.close();
	}

	@Override
	public Revenues retrieve(Long reference) {
		Session session = session();
		Revenues revenues = (Revenues) session.createCriteria(Revenues.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return revenues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Revenues> retrieveRawList() {
		Session session = session();
		List<Revenues> revList = session.createCriteria(Revenues.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return revList;
	}
	

	public Map<String, String> sumCashRevenue(Date begin, Date end) {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		Double rev = 0.0;
		String headingPattern = "yyyy-MM";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);
		Map<String, String> revMap = new HashMap<String, String>();
		String cash = "SELECT SUM(amount) FROM Revenues WHERE MONTH(received) = :month AND YEAR(received) = :year";
		String credit = "SELECT SUM(payment_made) FROM RevenuePayments WHERE MONTH(payment_date) = :month AND YEAR(payment_date) = :year";

		Session session = session();

		Double[] item = new Double[2];
		while (jBegin.isBefore(jEnd) || jBegin.isEqual(jEnd)) {
			rev =  (Double) session.createSQLQuery(cash).setInteger("month", jBegin.getMonthOfYear())
											      .setInteger("year", jBegin.getYear()).uniqueResult();
			if (rev ==  null) rev = 0.0;
			item[0] = rev;
			rev =  (Double) session.createQuery(credit).setInteger("month", jBegin.getMonthOfYear())
				      .setInteger("year", jBegin.getYear()).uniqueResult();
			
			if (rev ==  null) rev = 0.0;
			item[1] = rev;
			String key = df.format(jBegin.toDate());
			revMap.put(key, String.format("%.2f,%.2f", item[0], item[1]));
			jBegin = jBegin.plusMonths(1);
		}
		session.flush();
		session.close();
		
		return revMap;
	}

	@Override
	public void merge(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenues);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenues);
		tx.commit();
		session.close();
	}

}
