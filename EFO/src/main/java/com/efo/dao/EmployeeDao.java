package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Employee;
import com.efo.entity.User;
import com.efo.interfaces.IEmployee;

@Transactional
@Repository
public class EmployeeDao implements IEmployee {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Employee employee) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(employee);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Employee retrieve(int user_id) {
		Session session = session();
		Employee employee = (Employee) session.createCriteria(Employee.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.disconnect();
		return employee;
	}

	@Override
	public void update(Employee employee) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(employee);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(int user_id) {
		Employee employee = retrieve(user_id);
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(employee);
		tx.commit();
		session.disconnect();
	}

	@SuppressWarnings("unchecked")
	public List<User> retrieveEditList() {
		String sql = "FROM User AS u WHERE EXISTS (FROM Employee AS e WHERE e.user_id = u.user_id)";
	
		Session session = session();
		List<User> chooseList = session.createQuery(sql).list();
		session.disconnect();
	
	return chooseList;

	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> queryEmployee(String name) {
		String sql = "FROM Employee WHERE CONCAT(firstname, ' ', lastname) LIKE :name";
		name = "%" + name + "%";
		Session session = session();
		List<Employee> employee = session.createQuery(sql).setString("name", name).list();
		session.disconnect();
		
		return employee;
	}

}
