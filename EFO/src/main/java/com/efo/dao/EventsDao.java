package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(events);
		tx.commit();
		
		session.disconnect();
	}

	@Override
	public Events retrieve(Long id) {
		Session session = session();
		Events events = (Events) session.createCriteria(Events.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return events;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Events> retrieveRawList(Long reference) {
		Session session = session();
		List<Events> evenList = session.createCriteria(Events.class).add(Restrictions.eq("reference", reference)).list();
		session.disconnect();
		
		return evenList;
	}

	@Override
	public void update(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(events);
		tx.commit();
		
		session.disconnect();
	}

	@Override
	public void merge(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(events);
		tx.commit();
		
		session.disconnect();
	}

	@Override
	public void delete(Events events) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(events);
		tx.commit();
		
		session.disconnect();
	}

	@Override
	public void deleteById(Long id) {
		String hql = "DELETE FROM Events WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("id", id).executeUpdate();
		tx.commit();
		
		session.disconnect();
	}
	
	public Long getEventCount(Date date) {
		String hql = "SELECT COUNT(*) FROM Events WHERE date = :date";
		Session session = session();
		Long count = (Long) session.createQuery(hql).setDate("date", date).uniqueResult();
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Events> getEvents(Date date) {
		Session session = session();
		List<Events> eventList = session.createCriteria(Events.class).add(Restrictions.eq("date", date)).list();
		session.disconnect();
		
		return eventList;
	}
}
