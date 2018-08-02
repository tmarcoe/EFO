package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ProductOrders;
import com.efo.interfaces.IProductOrders;

@Transactional
@Repository
public class ProductOrdersDao implements IProductOrders {
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(ProductOrders productOrders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(productOrders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ProductOrders retrieve(Long reference) {
		Session session = session();
		ProductOrders order = (ProductOrders) session.createCriteria(ProductOrders.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductOrders> retrieveRawList() {
		Session session = session();
		List<ProductOrders> orders = session.createCriteria(ProductOrders.class).list();
		session.disconnect();
		
		return orders;
	}
	
	public ProductOrders findOpenOrder(int user_id) {
		String hql = "FROM ProductOrders WHERE process_date IS null AND user_id = :user_id";
		Session session = session();
		ProductOrders orders = (ProductOrders) session.createQuery(hql)
													  .setInteger("user_id", user_id)
													  .setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return orders;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductOrders> retrieveProcessedOrders(int user_id) {
		String hql = "FROM ProductOrders WHERE process_date IS NOT null AND delivery_date IS null AND user_id = :user_id";
		Session session = session();
		List<ProductOrders> orderList = session.createQuery(hql).setInteger("user_id", user_id).list();
		session.disconnect();
		
		return orderList;
	}

	@Override
	public void update(ProductOrders productOrders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(productOrders);
		tx.commit();
		session.disconnect();
	}
	
	public void merge(ProductOrders productOrders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(productOrders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(ProductOrders productOrders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(productOrders);
		tx.commit();
		session.disconnect();
	}
}
