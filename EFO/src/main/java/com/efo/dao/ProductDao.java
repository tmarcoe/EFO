package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Product;
import com.efo.interfaces.IProduct;

@Transactional
@Repository
public class ProductDao implements IProduct {
	

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Product product) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(product);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Product retrieve(String sku) {
		Session session = session();
		Product product = (Product) session.createCriteria(Product.class).add(Restrictions.idEq(sku)).uniqueResult();
		session.disconnect();
		
		return product;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> retrieveList() {
		Session session = session();
		List<Product> prodList = session.createCriteria(Product.class).list();
		session.disconnect();
		
		return prodList;
	}

	@Override
	public void update(Product product) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(product);
		tx.commit();
		session.disconnect();
	}

	public void merge(Product product) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(product);
		tx.commit();
		session.disconnect();
	}
	
	@Override
	public void delete(Product product) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(product);
		tx.commit();
		session.disconnect();
	}
	
	public void delete(String sku) {
		String hql = "DELETE FROM Product WHERE sku = :sku";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("sku", sku).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
