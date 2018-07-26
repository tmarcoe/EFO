package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Budget;
import com.efo.interfaces.IBudget;

@Transactional
@Repository
public class BudgetDao implements IBudget {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(budget);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Budget retrieve(Long id) {
		Session session = session();
		Budget budget = (Budget) session.createCriteria(Budget.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return budget;
	}
	
	public Budget retrieveByCategory(String category) {
		Session session = session();
		Budget budget = (Budget) session.createCriteria(Budget.class).add(Restrictions.eq("category", category)).setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return budget;
	}
	
	public boolean categoryExists(String category) {
		Session session = session();
		Long rowCount = (Long) session.createCriteria(Budget.class).add(Restrictions.eq("category", category)).setProjection(Projections.rowCount())
        .uniqueResult();
		session.disconnect();
		
		return rowCount > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Budget> retrieveRawList(String parent, int user_id) {
		Session session = session();
		List<Budget> bgList = session.createCriteria(Budget.class).add(Restrictions.eq("parent", parent)).list();
		session.disconnect();
		
		return bgList;
	}

	@Override
	public void update(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budget);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budget);
		tx.commit();
		session.disconnect();
	}
	
	public Double sumChildren(int user_id, String parent) {
		String hql = "SELECT SUM(amount) FROM Budget WHERE user_id = :user_id AND parent = :parent";
		Session session = session();
		Double sum = (Double) session.createQuery(hql).setInteger("user_id", user_id).setString("parent", parent).uniqueResult();
		
		return sum;
	}

}
