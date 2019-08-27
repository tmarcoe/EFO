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

import com.efo.entity.Profiles;
import com.efo.interfaces.IProfiles;

@Transactional
@Repository
public class ProfilesDao implements IProfiles {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Profiles profiles) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(profiles);
		tx.commit();
		session.close();
	}

	@Override
	public Profiles retrieve(String name) {
		Session session = session();
		Profiles profiles = (Profiles) session.createCriteria(Profiles.class).add(Restrictions.idEq(name)).uniqueResult();
		session.close();
		
		return profiles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Profiles> retrieveRawList() {
		Session session = session();
		List<Profiles> profList = session.createCriteria(Profiles.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return profList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> retrieveNames(String type) {
		String hql = "SELECT name FROM Profiles WHERE type = :type";
		Session session = session();
		List<String> names = session.createQuery(hql).setString("type", type).list();
		
		session.close();
		
		return names;
	}

	@Override
	public void merge(Profiles profiles) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(profiles);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Profiles profiles) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(profiles);
		tx.commit();
		session.close();
	}

}
