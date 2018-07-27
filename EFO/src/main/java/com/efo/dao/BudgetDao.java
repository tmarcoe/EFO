package com.efo.dao;

import java.util.Date;
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
		Budget budget = (Budget) session.createCriteria(Budget.class)
									    .add(Restrictions.eq("category", category))
										 .add(Restrictions.isNull("submission_date"))
									    .setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return budget;
	}
	
	public boolean categoryExists(String category) {
		Session session = session();
		Long rowCount = (Long) session.createCriteria(Budget.class)
									  .add(Restrictions.eq("category", category))
									  .add(Restrictions.isNull("submission_date"))
									  .setProjection(Projections.rowCount())
        .uniqueResult();
		session.disconnect();
		
		return rowCount > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Budget> retrieveRawList(String parent, int user_id) {
		Session session = session();
		List<Budget> bgList = session.createCriteria(Budget.class)
									 .add(Restrictions.eq("parent", parent))
									 .add(Restrictions.isNull("submission_date")).list();
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
		String hql = "SELECT SUM(amount) FROM Budget WHERE user_id = :user_id AND parent = :parent AND submission_date IS null";
		Session session = session();
		Double sum = (Double) session.createQuery(hql).setInteger("user_id", user_id).setString("parent", parent).uniqueResult();
		
		return sum;
	}
	
	@SuppressWarnings("unchecked")
	public List<Budget> listBudgetsForApproval() {
		String hql = "FROM Budget WHERE submission_date IS NOT null AND approval_date IS null GROUP BY department";
		Session session = session();
		List<Budget> budgetList = session.createQuery(hql).list();
		session.disconnect();
		
		return budgetList;
	}

	public void submitBudget(String department) {
		String hql = "SELECT MIN(creation_date) FROM Budget WHERE submission_date IS null and department = :department";
		String upd = "UPDATE Budget SET creation_date = :creation_date, submission_date = current_date() "
				   + "WHERE submission_date IS null and department = :department";
		Session session = session();
		Transaction tx = session.beginTransaction();
		Date creation_date = (Date) session.createQuery(hql).setString("department", department).uniqueResult();
		session.createQuery(upd).setDate("creation_date", creation_date).setString("department", department).executeUpdate();
		tx.commit();
		session.disconnect();
		
	}
	
	public void approveBudget(String department) {
		String hql = "UPDATE Budget SET approval_date = current_date() WHERE department = :department AND approval_date IS null";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("department", department).executeUpdate();
		tx.commit();
		session.disconnect();
	}
}
