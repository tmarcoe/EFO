package com.efo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Events;
import com.efo.interfaces.IEvents;

@Transactional
@Repository
public class EventsDao implements IEvents {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(events);
		tx.commit();
		
		session.close();
	}

	@Override
	public Events retrieve(Long id) {
		Session session = session();
		Events events = (Events) session.createCriteria(Events.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return events;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Events> retrieveRawList(Long reference) {
		Session session = session();
		List<Events> evenList = session.createCriteria(Events.class).add(Restrictions.eq("reference", reference)).list();
		session.close();
		
		return evenList;
	}

	@Override
	public void update(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(events);
		tx.commit();
		
		session.close();
	}

	@Override
	public void merge(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(events);
		tx.commit();
		
		session.close();
	}

	@Override
	public void delete(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(events);
		tx.commit();
		
		session.close();
	}

	@Override
	public void deleteById(Long id) {
		String hql = "DELETE FROM Events WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("id", id).executeUpdate();
		tx.commit();
		
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Long> getEventCount(Date begin, Date end) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Long> countMap = new HashMap<String, Long>();
		String hql = "SELECT date, COUNT(*) FROM Events WHERE DATE(date) BETWEEN DATE(:begin) AND DATE(:end) GROUP BY DATE(date)";
		Session session = session();
		List<Object[]> items = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		for (Object[] item : items) {
			String dateStr = df.format((Date) item[0]);
			countMap.put(dateStr, (Long) item[1]);
		}
		session.close();
		
		return countMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<Events> getEvents(Date date) {
		Session session = session();
		List<Events> eventList = session.createCriteria(Events.class).add(Restrictions.eq("date", date)).addOrder(Order.asc("date")).list();
		session.close();
		
		return eventList;
	}
}
