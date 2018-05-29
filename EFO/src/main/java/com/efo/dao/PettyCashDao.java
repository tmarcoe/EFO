package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.PettyCash;
import com.efo.interfaces.IPettyCash;

@Transactional
@Repository
public class PettyCashDao implements IPettyCash {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(PettyCash pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(pettyCash);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PettyCash retrieve(int id) {
		Session session = session();
		PettyCash pettyCash = (PettyCash) session.createCriteria(PettyCash.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return pettyCash;
	}
	
	@SuppressWarnings("unchecked")
	public List<PettyCash> retrieveList() {
		Session session = session();
		List<PettyCash> pcList = session.createCriteria(PettyCash.class).list();	
		session.disconnect();
		
		return pcList;
	}

	@Override
	public void update(PettyCash pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(pettyCash);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(int id) {
		PettyCash pettyCash = retrieve(id);
		delete(pettyCash);
	}

	@Override
	public void delete(PettyCash pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();

		tx.commit();
		session.disconnect();
	}

}
