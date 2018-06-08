package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Inventory;
import com.efo.interfaces.IInventory;

@Transactional
@Repository
public class InventoryDao implements IInventory {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Inventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Inventory retrieve(int id) {
		Session session = session();
		
		Inventory inventory = (Inventory) session.createCriteria(Inventory.class).add(Restrictions.idEq(id)).uniqueResult();
		
		session.disconnect();
		
		return inventory;
	}
	
	@SuppressWarnings("unchecked")
	public List<Inventory> retrieveList() {
		Session session = session();
		
		List<Inventory> invList = session.createCriteria(Inventory.class).list();
		session.disconnect();
		
		return invList;
	}

	@Override
	public void update(Inventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Inventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(inventory);
		tx.commit();
		session.disconnect();
	}

}
