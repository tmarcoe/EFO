package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(budget);
		tx.commit();
		session.close();
	}

	@Override
	public Budget retrieve(Long reference) {
		Session session = session();
		Budget budget = (Budget) session.createCriteria(Budget.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return budget;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Budget> retrieveRawList(String department) {
		Session session = session();
		List<Budget> bList = session.createCriteria(Budget.class)
								    .add(Restrictions.eq("department", department))
								    .add(Restrictions.isNull("submitted"))
								    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
								    .list();
		session.close();
		
		return bList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Budget> listBudgetsForApproval() {
		Session session = session();
		List<Budget> bList = session.createCriteria(Budget.class)
								    .add(Restrictions.isNull("approved"))
								    .add(Restrictions.isNull("rejected"))
								    .add(Restrictions.isNotNull("submitted"))
								    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
								    .list();
		session.close();
		
		return bList;
	}

	@Override
	public void update(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budget);
		tx.commit();
		session.close();
	}
	
	public void approveBudget(Long reference) {
		String hql = "UPDATE Budget SET approved = current_date() WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("reference", reference).executeUpdate();
		tx.commit();
		session.close();
	}
	
	public void rejectBudget(Long reference, String reason) {
		String hql = "UPDATE Budget SET rejected = current_date(), reason = :reason, submitted = NULL WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("reference", reference).setString("reason", reason).executeUpdate();
		tx.commit();
		session.close();		
	}
	
	public void submitBudget(Long reference) {
		String hql = "UPDATE Budget SET submitted = current_date(), rejected = NuLL WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("reference", reference).executeUpdate();
		tx.commit();
		session.close();
	}

	@Override
	public void merge(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(budget);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(budget);
		tx.commit();
		session.close();
	}

}
