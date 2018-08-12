package com.efo.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.efo.entity.EachInventory;
import com.efo.entity.Product;
import com.efo.entity.SalesItem;
import com.efo.interfaces.IEachInventory;

@Transactional
@Repository
public class EachInventoryDao implements IEachInventory {

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${efo.inventory.type}")
	private String inventoryType;

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
			inv.setInvoice_num(inventory.getInvoice_num());
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
	
	@SuppressWarnings("unchecked")
	public void markAsDelivered(String sku, int qty) {

		String upd = "UPDATE EachInventory SET received = current_date() WHERE id = :id";
		String hql = "SELECT id FROM EachInventory WHERE received IS null AND sku = :sku";

		Session session = session();
		Transaction tx = session.beginTransaction();
		List<Long> lst = session.createQuery(hql).setString("sku", sku).setMaxResults(qty).list();
		
		for (Long id : lst) {
			session.createQuery(upd).setLong("id", id).executeUpdate();
		}

		
		tx.commit();
		session.disconnect();
	}
	
	public double getAmountOrdered(String sku) {
		String hql = "SELECT COUNT(*) FROM EachInventory WHERE sku = :sku AND ordered IS NOT null AND received IS null";
		Session session = session();
		long amount = (long) session.createQuery(hql).setString("sku", sku).uniqueResult();
		session.disconnect();
		
		return new Long(amount).doubleValue();
	}
	
	public double getAmountReceived(String sku) {
		String hql = "SELECT COUNT(*) FROM EachInventory WHERE sku = :sku AND received IS NOT null AND sold IS null";
		Session session = session();
		long amount = (long) session.createQuery(hql).setString("sku", sku).uniqueResult();
		session.disconnect();
		
		return new Long(amount).doubleValue();
	}

	public double getAmountCommitted(String sku) {
		String hql = "SELECT COUNT(*) FROM EachInventory WHERE sku = :sku AND sold IS NOT null AND shipped IS null";
		Session session = session();
		long amount = (long) session.createQuery(hql).setString("sku", sku).uniqueResult();
		session.disconnect();
		
		return new Long(amount).doubleValue();
	}


	public void depleteStock(Set<SalesItem> items) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		for (SalesItem item : (Set<SalesItem>) items) {
			Product product = item.getProduct();
			if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0) {
				depleteEach(item, session);
			}else{
				depleteFluid(item, session);
			}
		}
		
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	private void depleteEach(SalesItem item, Session session) {
		String hql = "FROM EachInventory WHERE sku = :sku AND sold IS NOT null AND shipped IS null";
		String upd = "UPDATE EachInventory SET shipped = current_date() WHERE id = :id";

		int qty = new Double(item.getQty()).intValue();
		List<EachInventory> updateList = session.createQuery(hql).setString("sku", item.getSku()).setMaxResults(qty).list();
		for (EachInventory each : updateList) {
			session.createQuery(upd).setLong("id", each.getId()).executeUpdate();
		}
	}

	private void depleteFluid(SalesItem item, Session session) {
		String hql = "UPDATE FluidInventory SET amt_committed = (amt_committed - :qty) WHERE sku = :sku";
		session.createQuery(hql).setDouble("qty", item.getQty()).setString("sku", item.getSku()).executeUpdate();

	}

}
