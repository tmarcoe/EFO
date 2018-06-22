package com.efo.dao;

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
	public RetailSales retrieve(Long invoice_num) {
		Session session = session();
		RetailSales sales = (RetailSales) session.createCriteria(RetailSales.class).add(Restrictions.idEq(invoice_num)).uniqueResult();
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
	
	public void cancelSales(Long invoice_num) {
		String removeItems = "DELETE FROM SalesItem WHERE invoice_num = :invoice_num";
		String removeSales = "DELETE FROM RetailSales WHERE invoice_num = :invoice_num";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(removeItems).setLong("invoice_num", invoice_num).executeUpdate();
		session.createQuery(removeSales).setLong("invoice_num", invoice_num).executeUpdate();
		tx.commit();
		session.disconnect();
	}
}
