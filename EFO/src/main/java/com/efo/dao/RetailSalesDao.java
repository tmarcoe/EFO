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

import com.efo.entity.RetailSales;
import com.efo.interfaces.IRetailSales;

@Transactional
@Repository
public class RetailSalesDao implements IRetailSales {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(RetailSales sales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(sales);
		tx.commit();
		session.disconnect();
	}

	@Override
	public RetailSales retrieve(Long reference) {
		Session session = session();
		RetailSales sales = (RetailSales) session.createCriteria(RetailSales.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return sales;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RetailSales> retrieveRawList() {
		Session session = session();
		List<RetailSales> salesList = session.createCriteria(RetailSales.class).list();
		session.disconnect();
		
		return salesList;
	}

	@Override
	public void update(RetailSales sales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(sales);
		tx.commit();
		session.disconnect();
	}
	
	public void merge(RetailSales sales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(sales);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(RetailSales sales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(sales);
		tx.commit();
		session.disconnect();
	}
	
	public RetailSales getOpenInvoice(int user_id) {
		String hql = "FROM RetailSales WHERE ordered IS NOT null AND processed IS null AND user_id = :user_id";
		Session session = session();
		RetailSales sales = (RetailSales) session.createQuery(hql).setInteger("user_id", user_id).setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return sales;
	}
	
	@SuppressWarnings("unchecked")
	public List<RetailSales> getProcessedOrders() {
		String hql = "FROM RetailSales WHERE ordered IS NOT null AND processed IS NOT null AND shipped IS null";
		Session session = session();
		List<RetailSales> salesList = session.createQuery(hql).list();
		session.disconnect();
		
		return salesList;
	}
	
	public void cancelSales(Long reference) {
		String removeItems = "DELETE FROM SalesItem WHERE reference = :reference";
		String removeSales = "DELETE FROM RetailSales WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(removeItems).setLong("reference", reference).executeUpdate();
		session.createQuery(removeSales).setLong("reference", reference).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countProductsByPeriod(Date begin, Date end) {
		String hql = "SELECT MONTH(r.ordered), SUM(s.qty) FROM RetailSales r, SalesItem s "
				   + "WHERE r.ordered BETWEEN DATE(:begin) AND DATE(:end) AND r.reference = s.reference GROUP BY MONTH(r.ordered)";
		Session session = session();
		List<Object[]> qtyList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		session.disconnect();
		
		return qtyList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTotalSaleByPeriod(Date begin, Date end) {
		String hql = "SELECT MONTH(ordered), SUM(total_price) FROM RetailSales "
				   + "WHERE ordered BETWEEN DATE(:begin) AND DATE(:end) AND payment_type = 'Cash' GROUP BY MONTH(ordered)";
		Session session = session();
		List<Object[]> soldList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		session.disconnect();
		
		return soldList;
	}
	

}
