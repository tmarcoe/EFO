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

import com.efo.entity.EachInventory;
import com.efo.interfaces.IEachInventory;

@Transactional
@Repository
public class EachInventoryDao implements IEachInventory {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(EachInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public EachInventory retrieve(Long id) {
		Session session = session();
		EachInventory inventory = (EachInventory) session.createCriteria(EachInventory.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return inventory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EachInventory> retrieveRawList() {
		Session session = session();
		List<EachInventory> invList = session.createCriteria(EachInventory.class).list();
		session.disconnect();
		
		return invList;
	}

	@Override
	public void update(EachInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(EachInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(inventory);
		tx.commit();
		session.disconnect();
	}

	public void stockShelf(EachInventory inventory, Integer qty) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		for(int i=0; i < qty; i++) {
			EachInventory inv = new EachInventory();
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
