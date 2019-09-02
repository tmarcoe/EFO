package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Customer;
import com.efo.entity.User;
import com.efo.interfaces.ICustomer;

@Transactional
@Repository
public class CustomerDao implements ICustomer {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Customer customer) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(customer);
		tx.commit();
		session.close();
	}

	@Override
	public Customer retrieve(Long user_id) {
		Session session = session();
		Customer customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.close();
		
		return customer;
	}

	@Override
	public void update(Customer customer) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(customer);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Long user_id) {
		Customer customer = retrieve(user_id);
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(customer);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> retrieveEditList() {
		String sql = "FROM User AS u WHERE EXISTS (FROM Customer AS c WHERE c.user_id = u.user_id)";
	
		Session session = session();
		List<User> chooseList = session.createQuery(sql).list();
		session.close();
	
	return chooseList;

	}
	@SuppressWarnings("unchecked")
	public List<Customer> queryCustomer(String name) {
		String sql = "FROM Customer WHERE CONCAT(firstname, ' ', lastname) LIKE :name";
		name = "%" + name + "%";
		Session session = session();
		List<Customer> customer = session.createQuery(sql).setString("name", name).list();
		session.close();
		
		return customer;
	}

	public Object[] getMaleFemaleCount() {
		String hql = "SELECT COUNT(*) AS total, " +
					"SUM(CASE WHEN male_female = 'M' THEN 1 ELSE 0 END) AS males, " +
					"SUM(CASE WHEN male_female = 'F' THEN 1 ELSE 0 END) AS females " +
					"FROM customer";
		Session session = session();
		Object[] counts = (Object[]) session.createSQLQuery(hql).uniqueResult();
		session.close();
		
		return counts;
	}
}
