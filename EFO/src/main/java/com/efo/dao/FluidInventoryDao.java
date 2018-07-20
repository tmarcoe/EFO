package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.FluidInventory;
import com.efo.interfaces.IFluidInventory;

@Transactional
@Repository
public class FluidInventoryDao implements IFluidInventory {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(FluidInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(inventory);
		tx.commit();
		session.disconnect();
	}
	
	@Override
	public FluidInventory retrieve(String sku) {
		Session session = session();
		
		FluidInventory inventory = (FluidInventory) session.createCriteria(FluidInventory.class).add(Restrictions.idEq(sku)).uniqueResult();
		
		session.disconnect();
		
		return inventory;
	}
	
	@SuppressWarnings("unchecked")
	public List<FluidInventory> retrieveList() {
		Session session = session();
		
		List<FluidInventory> invList = session.createCriteria(FluidInventory.class).list();
		session.disconnect();
		
		return invList;
	}

	@Override
	public void update(FluidInventory inventory) {
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
	public void delete(FluidInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(inventory);
		tx.commit();
		session.disconnect();
	}

}
