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
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Customer customer) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(customer);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Customer retrieve(int user_id) {
		Session session = session();
		Customer customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.disconnect();
		
		return customer;
	}

	@Override
	public void update(Customer customer) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(customer);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(int user_id) {
		Customer customer = retrieve(user_id);
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(customer);
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> retrieveEditList() {
		String sql = "FROM User AS u WHERE EXISTS (FROM Customer AS c WHERE c.user_id = u.user_id)";
	
		Session session = session();
		List<User> chooseList = session.createQuery(sql).list();
		session.disconnect();
	
	return chooseList;

	}
	@SuppressWarnings("unchecked")
	public List<Customer> queryCustomer(String name) {
		String sql = "FROM Customer WHERE CONCAT(firstname, ' ', lastname) LIKE :name";
		name = "%" + name + "%";
		Session session = session();
		List<Customer> customer = session.createQuery(sql).setString("name", name).list();
		session.disconnect();
		
		return customer;
	}

}
