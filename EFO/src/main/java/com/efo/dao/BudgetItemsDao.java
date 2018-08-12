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

import com.efo.entity.BudgetItems;
import com.efo.interfaces.IBudgetItems;

@Transactional
@Repository
public class BudgetItemsDao implements IBudgetItems {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(BudgetItems budgetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(budgetItems);
		tx.commit();
		session.disconnect();
	}

	@Override
	public BudgetItems retrieve(Long id) {
		Session session = session();
		BudgetItems budgetItems = (BudgetItems) session.createCriteria(BudgetItems.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return budgetItems;
	}
	
	public BudgetItems retrieveByCategory(String category) {
		Session session = session();
		BudgetItems budgetItems = (BudgetItems) session.createCriteria(BudgetItems.class)
									    .add(Restrictions.eq("category", category))
										 .add(Restrictions.isNull("submission_date"))
									    .setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return budgetItems;
	}
	
	public boolean categoryExists(String category) {
		Session session = session();
		Long rowCount = (Long) session.createCriteria(BudgetItems.class)
									  .add(Restrictions.eq("category", category))
									  .add(Restrictions.isNull("submission_date"))
									  .setProjection(Projections.rowCount())
        .uniqueResult();
		session.disconnect();
		
		return rowCount > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetItems> retrieveRawList(String parent, int user_id) {
		Session session = session();
		List<BudgetItems> bgList = session.createCriteria(BudgetItems.class)
									 .add(Restrictions.eq("parent", parent))
									 .add(Restrictions.isNull("submission_date")).list();
		session.disconnect();
		
		return bgList;
	}

	@Override
	public void update(BudgetItems budgetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budgetItems);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(BudgetItems budgetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budgetItems);
		tx.commit();
		session.disconnect();
	}
	
	public Double sumChildren(int user_id, String parent) {
		String hql = "SELECT SUM(amount) FROM BudgetItems WHERE user_id = :user_id AND parent = :parent AND submission_date IS null";
		Session session = session();
		Double sum = (Double) session.createQuery(hql).setInteger("user_id", user_id).setString("parent", parent).uniqueResult();
		
		return sum;
	}
	
	@SuppressWarnings("unchecked")
	public List<BudgetItems> listBudgetsForApproval() {
		String hql = "FROM BudgetItems WHERE submission_date IS NOT null AND approval_date IS null GROUP BY department";
		Session session = session();
		List<BudgetItems> budgetList = session.createQuery(hql).list();
		session.disconnect();
		
		return budgetList;
	}

	public void submitBudget(String department) {
		String hql = "SELECT MIN(creation_date) FROM BudgetItems WHERE submission_date IS null and department = :department";
		String upd = "UPDATE BudgetItems SET creation_date = :creation_date, submission_date = current_date() "
				   + "WHERE submission_date IS null and department = :department";
		Session session = session();
		Transaction tx = session.beginTransaction();
		Date creation_date = (Date) session.createQuery(hql).setString("department", department).uniqueResult();
		session.createQuery(upd).setDate("creation_date", creation_date).setString("department", department).executeUpdate();
		tx.commit();
		session.disconnect();
		
	}
	
	public void approveBudget(String department) {
		String hql = "UPDATE BudgetItems SET approval_date = current_date() WHERE department = :department AND approval_date IS null";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("department", department).executeUpdate();
		tx.commit();
		session.disconnect();
	}
}
