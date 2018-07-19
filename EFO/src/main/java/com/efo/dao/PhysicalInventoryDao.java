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

import com.efo.entity.PhysicalInventory;
import com.efo.interfaces.IPhysicalInventory;

@Transactional
@Repository
public class PhysicalInventoryDao implements IPhysicalInventory {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(PhysicalInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PhysicalInventory retrieve(Long id) {
		Session session = session();
		PhysicalInventory inventory = (PhysicalInventory) session.createCriteria(PhysicalInventory.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return inventory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhysicalInventory> retrieveRawList() {
		Session session = session();
		List<PhysicalInventory> invList = session.createCriteria(PhysicalInventory.class).list();
		session.disconnect();
		
		return invList;
	}

	@Override
	public void update(PhysicalInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(PhysicalInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(inventory);
		tx.commit();
		session.disconnect();
	}

	public void stockShelf(PhysicalInventory inventory, Integer qty) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		for(int i=0; i < qty; i++) {
			PhysicalInventory inv = new PhysicalInventory();
			inv.setSku(inventory.getSku());
			inv.setOrdered(new Date());
			inv.setWholesale(inventory.getWholesale());
			session.save(inv);
			if (i % 20 == 0) {
		        session.flush();
		        session.clear();
			}
		}
		
		tx.commit();
		session.disconnect();
	}

}
