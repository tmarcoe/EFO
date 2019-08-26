package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Role;
import com.efo.interfaces.IRole;

@Transactional
@Repository
public class RoleDao implements IRole {

	@Autowired
	SessionFactory sessionFactory;
	
	Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Role role) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(role);
		tx.commit();
		session.close();
	}

	@Override
	public void update(Role role) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(role);
		tx.commit();
		session.close();
	}

	@Override
	public Role retrieve(int id) {
		Session session = session();
		Role r = (Role) session.createCriteria(Role.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return r;
	}
	
	@Override
	public Role retrieve(String role) {
		Session session = session();
		
		Role r = (Role) session.createCriteria(Role.class).add(Restrictions.eq("role", role)).uniqueResult();
		
		session.close();
		
		return r;
	}

	@Override
	public void delete(Role role) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(role);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Role> retrieveList() {
		Session session = session();
		List<Role> roleList = session.createCriteria(Role.class).list();
		session.close();
		
		return roleList;
	}

}
