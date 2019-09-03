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

import com.efo.entity.TimeSheet;
import com.efo.interfaces.ITimeSheet;

@Transactional
@Repository
public class TimeSheetDao implements ITimeSheet {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(TimeSheet timeSheet) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(timeSheet);
		
		tx.commit();
		session.close();
	}

	@Override
	public TimeSheet retrieve(Long reference) {
		Session session = session();
		TimeSheet timeSheet = (TimeSheet) session.createCriteria(TimeSheet.class).add(Restrictions.idEq(reference)).uniqueResult();
		
		session.close();
		return timeSheet;
	}
	
	public TimeSheet retrieveByUserId(Long user_id) {
		String hql = "FROM TimeSheet WHERE submitted IS null AND user_id = :user_id";
		Session session = session();
		TimeSheet ts = (TimeSheet) session.createQuery(hql).setLong("user_id", user_id).setMaxResults(1).uniqueResult();
		
		session.close();
		
		return ts;
	}
	
	public boolean checkIfPeriodExists(Date begin, Long user_id, Long reference) {
		String hql = "SELECT COUNT(*) FROM TimeSheet WHERE begin_period = :begin AND user_id = :user_id AND reference != :reference";
		Session session = session();
		
		Long count = (Long) session.createQuery(hql).setDate("begin", begin).setLong("user_id", user_id).setLong("reference", reference).uniqueResult();
		session.close();
		
		return (count > 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<TimeSheet> listSubmitted() {
		String hql = "FROM TimeSheet WHERE submitted IS NOT null AND approved IS null AND rejected IS null";
		Session session = session();
		
		List<TimeSheet> tsList = session.createQuery(hql).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		session.close();
		
		return tsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeSheet> retrieveRawList() {
		Session session = session();
		
		List<TimeSheet> tmList = session.createCriteria(TimeSheet.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return tmList;
	}

	@Override
	public void merge(TimeSheet timeSheet) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(timeSheet);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Long reference) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.close();
	}
	
}
