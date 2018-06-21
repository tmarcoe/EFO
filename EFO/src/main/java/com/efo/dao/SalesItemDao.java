package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.SalesItem;
import com.efo.interfaces.ISalesItem;

@Transactional
@Repository
public class SalesItemDao implements ISalesItem {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void creat(SalesItem salesItem) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(salesItem);
		
		tx.commit();
		session.disconnect();
	}

	@Override
	public SalesItem retrieve(int item_id) {
		Session session = session();
		SalesItem salesItem = (SalesItem) session.createCriteria(SalesItem.class).add(Restrictions.idEq(item_id)).uniqueResult();
		session.disconnect();
		
		return salesItem;
	}
	
	public SalesItem getItemBySku(int invoice_num, String sku) {
		Session session = session();
		SalesItem item = (SalesItem) session.createCriteria(SalesItem.class)
											.add(Restrictions
											.and(Restrictions.eq("invoice_num", invoice_num), Restrictions.eq("sku", sku)))
											.setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesItem> retrieveRawList() {
		Session session = session();
		List<SalesItem> itemList = session.createCriteria(SalesItem.class).list();
		session.disconnect();
		
		return itemList;
	}

	@Override
	public void update(SalesItem salesItem) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(salesItem);
		tx.commit();
		session.disconnect();
	}
	
	public void addQuantity(SalesItem salesItem, double qty) {
		String hql = "UPDATE SalesItem SET qty = qty + :qty WHERE item_id = :item_id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setDouble("qty", qty).setInteger("item_id", salesItem.getItem_id()).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	public void deleteSalesItem(int item_id) {
		String hql = "DELETE FROM SalesItem WHERE item_id = :item_id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("item_id", item_id).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(SalesItem salesItem) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(salesItem);
		tx.commit();
		session.disconnect();
	}

}
