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
	public OrderItems retrieve(Long id) {
		Session session = session();
		OrderItems orders = (OrderItems) session.createCriteria(OrderItems.class).add(Restrictions.idEq(id)).uniqueResult();
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
	public List<OrderItems> retrieveChildItems(Long reference) {
		Session session = session();
		List<OrderItems> items = session.createCriteria(OrderItems.class)
										.add(Restrictions.eq("reference", reference))
										.list();
		session.disconnect();
		
		return items;
	}
	
	public OrderItems retrieveItemBySku(Long reference, String sku) {
		String hql = "FROM OrderItems WHERE reference = :reference AND sku = :sku";
		Session session = session();
		OrderItems item = (OrderItems) session.createQuery(hql).setLong("reference", reference).setString("sku", sku).setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderItems> retrieveOpenItems(Long reference) {
		String hql = "FROM OrderItems WHERE reference = :reference AND amt_ordered > amt_received";
		Session session = session();
		List<OrderItems> orderList = session.createQuery(hql).setLong("reference", reference).list();
		session.disconnect();
		
		return orderList;
	}
	
	public void addItems(double amt_ordered, double price, Long reference, String sku ) {
		String hql = "UPDATE OrderItems SET amt_ordered = amt_ordered + :amt_ordered, wholesale = wholesale + :price "
				   + "WHERE reference = :reference AND sku = :sku";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setDouble("amt_ordered", amt_ordered)
								.setDouble("price", price).setLong("reference", reference)
								.setString("sku", sku).executeUpdate();
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
	
	public void deleteById(Long id) {
		String hql = "DELETE FROM OrderItems WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("id", id).executeUpdate();
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
	
	public void receiveOrder(Long id, double qty) {
		String hql = "UPDATE OrderItems SET amt_received = amt_received + :qty, delivery_date = current_date() "
				   + "WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setDouble("qty", qty).setLong("id", id).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	public boolean hasOutstandingDeliveries(Long reference) {
		String hql = "SELECT COUNT(*) FROM OrderItems WHERE amt_received < amt_ordered AND reference = :reference";
		Session session = session();
		Long count = (Long) session.createQuery(hql).setLong("reference", reference).uniqueResult();
		
		return count > 0;
	}
	
}
