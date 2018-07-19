package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.NonPhysicalInventory;
import com.efo.interfaces.INonPhysicalInventory;

@Transactional
@Repository
public class NonPhysicalInventoryDao implements INonPhysicalInventory {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(NonPhysicalInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(inventory);
		tx.commit();
		session.disconnect();
	}
	
	@Override
	public NonPhysicalInventory retrieve(String sku) {
		Session session = session();
		
		NonPhysicalInventory inventory = (NonPhysicalInventory) session.createCriteria(NonPhysicalInventory.class).add(Restrictions.idEq(sku)).uniqueResult();
		
		session.disconnect();
		
		return inventory;
	}
	
	@SuppressWarnings("unchecked")
	public List<NonPhysicalInventory> retrieveList() {
		Session session = session();
		
		List<NonPhysicalInventory> invList = session.createCriteria(NonPhysicalInventory.class).list();
		session.disconnect();
		
		return invList;
	}

	@Override
	public void update(NonPhysicalInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(inventory);
		tx.commit();
		session.disconnect();
	}
	
	public void commitInventory(String sku, double amt) {
		String hql = "UPDATE Inventory SET amt_in_stock = amt_in_stock - :amt, "
				   + "amt_committed = amt_committed + :amt WHERE sku = :sku";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setDouble("amt", amt).setString("sku", sku).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	public void depleteInventory(String sku, double amt) {
		String hql = "UPDATE Inventory SET amt_committed = amt_committed - :amt WHERE sku = :sku";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setDouble("amt", amt).setString("sku", sku).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	@Override
	public void delete(NonPhysicalInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(inventory);
		tx.commit();
		session.disconnect();
	}

}
