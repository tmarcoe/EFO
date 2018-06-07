package com.efo.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
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
	public void saveOrUpdate(PettyCash pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(pettyCash);
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
	
	public boolean exists() {
		Long count;
		Session session = session();
		
		count = (Long) session.createCriteria(PettyCash.class).setProjection(Projections.rowCount()).uniqueResult();
		
		return count > 0;
	}

}
