package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
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
	public void create(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(orders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ProductOrders retrieve(Long reference) {
		Session session = session();
		ProductOrders orders = (ProductOrders) session.createCriteria(ProductOrders.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return orders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductOrders> retrieveRawList() {
		Session session = session();
		List<ProductOrders> orderList = session.createCriteria(ProductOrders.class).list();
		session.disconnect();
		
		return orderList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductOrders> retrieveOpenOrders() {
		Session session = session();
		Criterion delivered = Restrictions.ne("status", "D");
		Criterion canceled = Restrictions.ne("status", "C");
		List<ProductOrders> orderList = session.createCriteria(ProductOrders.class).add(Restrictions.and(delivered, canceled)).list();
		session.disconnect();
		
		return orderList;
	}
	
	public void setStatus(Long reference, String status) {
		String hql = "UPDATE ProductOrders SET status = :status WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("status", status).setLong("reference", reference).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	@Override
	public void update(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(orders);
		tx.commit();
		session.disconnect();
	}
	
	public void merge(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(orders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(orders);
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductOrders> getPeriodOrders(Date begin, Date end) {
		String hql ="FROM ProductOrders WHERE order_date BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		 List<ProductOrders> orderList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		 session.disconnect();
		 
		 return orderList;
	}
	
}
