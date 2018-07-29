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

import com.efo.entity.OrderItems;
import com.efo.interfaces.IOrdersItem;

@Transactional
@Repository
public class OrderItemsDao implements IOrdersItem {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(OrderItems orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(orders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public OrderItems retrieve(Long reference) {
		Session session = session();
		OrderItems orders = (OrderItems) session.createCriteria(OrderItems.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return orders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItems> retrieveRawList() {
		Session session = session();
		List<OrderItems> orderList = session.createCriteria(OrderItems.class).list();
		session.disconnect();
		
		return orderList;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderItems> retrieveOpenOrders() {
		Session session = session();
		Criterion delivered = Restrictions.ne("status", "D");
		Criterion canceled = Restrictions.ne("status", "C");
		List<OrderItems> orderList = session.createCriteria(OrderItems.class).add(Restrictions.and(delivered, canceled)).list();
		session.disconnect();
		
		return orderList;
	}
	
	public void setStatus(Long reference, String status) {
		String hql = "UPDATE OrderItems SET status = :status WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("status", status).setLong("reference", reference).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	@Override
	public void update(OrderItems orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(orders);
		tx.commit();
		session.disconnect();
	}
	
	public void merge(OrderItems orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(orders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(OrderItems orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(orders);
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderItems> getPeriodOrders(Date begin, Date end) {
		String hql ="FROM OrderItems WHERE order_date BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		 List<OrderItems> orderList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		 session.disconnect();
		 
		 return orderList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTotalWholesaleByPeriod(Date begin, Date end) {
		String hql = "SELECT MONTH(order_date), SUM(wholesale) FROM OrderItems "
				   + "WHERE order_date BETWEEN DATE(:begin) AND DATE(:end) AND payment_type = 'Cash' ORDER BY MONTH(order_date)";
		Session session = session();
		List<Object[]> totalOrders = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		session.disconnect();
		
		return totalOrders;
	}
	
}
