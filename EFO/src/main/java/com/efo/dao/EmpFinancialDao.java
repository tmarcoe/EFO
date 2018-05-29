package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.EmpFinancial;
import com.efo.entity.User;
import com.efo.interfaces.IEmpFinancial;



@Transactional
@Repository
public class EmpFinancialDao implements IEmpFinancial {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void Create(EmpFinancial employee) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(employee);
		tx.commit();
		session.disconnect();
	}

	@Override
	public EmpFinancial retrieve(int user_id) {
		Session session = session();
		EmpFinancial emp = (EmpFinancial) session.createCriteria(EmpFinancial.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.disconnect();
		
		return emp;
	}

	@Override
	public void update(EmpFinancial employee) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(employee);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(EmpFinancial employee) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(employee);
		tx.commit();
		session.disconnect();
	}

	@SuppressWarnings("unchecked")
	public List<User> employeeList() {
		Session session = session();
		List<User> empList = session.createCriteria(EmpFinancial.class).add(Restrictions.isNull("endDate")).list();
		session.disconnect();
		
		return empList;
	}

}
