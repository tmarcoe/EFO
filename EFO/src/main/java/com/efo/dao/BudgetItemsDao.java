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

import com.efo.entity.BudgetItems;
import com.efo.interfaces.IBudgetItems;

@Transactional
@Repository
public class BudgetItemsDao implements IBudgetItems {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(BudgetItems budgetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(budgetItems);
		tx.commit();
		session.close();
	}

	@Override
	public BudgetItems retrieve(Long id) {
		Session session = session();
		BudgetItems budgetItems = (BudgetItems) session.createCriteria(BudgetItems.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return budgetItems;
	}
	
	public BudgetItems retrieveByCategory(Long reference, String category) {
		Session session = session();
		BudgetItems budgetItems = (BudgetItems) session.createCriteria(BudgetItems.class)
									    .add(Restrictions.eq("category", category))
									    .add(Restrictions.eq("reference", reference))
									    .setMaxResults(1).uniqueResult();
		session.close();
		
		return budgetItems;
	}
	
	public boolean categoryExists(Long reference, String category) {
		Session session = session();
		Long rowCount = (Long) session.createCriteria(BudgetItems.class)
									  .add(Restrictions.eq("category", category))
									  .add(Restrictions.eq("reference", reference))
									  .setProjection(Projections.rowCount())
									  .uniqueResult();
		session.close();
		
		return rowCount > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetItems> retrieveRawList(Long reference, String parent) {
		Session session = session();
		List<BudgetItems> bgList = session.createCriteria(BudgetItems.class)
									 .add(Restrictions.eq("parent", parent))
									 .add(Restrictions.eq("reference", reference)).list();
		session.close();
		
		return bgList;
	}
	
	public boolean hasChildren(Long reference, String parent) {
		String hql = "SELECT COUNT(*) FROM BudgetItems WHERE reference = :reference AND parent = :parent";
		Session session = session();
		Long count = (Long) session.createQuery(hql).setLong("reference", reference).setString("parent", parent).uniqueResult();
		session.close();
		
		return count > 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> budgetTree(Long reference) {
		Session session = session();
		String cnt = "SELECT COUNT(*) FROM BudgetItems WHERE reference = :reference AND parent = :parent";
		String hql = "WITH RECURSIVE budget_path (category, level, favored_bid, parent, justification, amount, path) AS "
				   + "(Select category, level, favored_bid, parent, justification, amount, category as path "
				   + "FROM budget_items WHERE parent = '' AND reference = :reference "
				   + "UNION ALL "
				   + "SELECT b.category, b.level, b.favored_bid, b.parent, b.justification, b.amount, concat(bp.path, '>', b.category) "
				   + "FROM budget_path as bp JOIN budget_items as b "
				   + "on bp.category = b.parent AND reference = :reference"
				   + ") SELECT * FROM budget_path ORDER BY path";
		
		List<Object[]> tree = session.createSQLQuery(hql).setLong("reference", reference).list();
		for (Object[] item : tree) {
			item[6] = session.createQuery(cnt).setLong("reference", reference).setString("parent", (String) item[0]).uniqueResult();
		}
		
		session.close();
		
		return tree;
	}

	@Override
	public void update(BudgetItems budgetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budgetItems);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(BudgetItems budgetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(budgetItems);
		tx.commit();
		session.close();
	}
	
	public void deleteById(Long id) {
		String hql = "DELETE FROM BudgetItems WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("id", id).executeUpdate();
		tx.commit();
		session.close();
	}
		
	public Double sumChildren(Long reference, String parent) {
		String hql = "SELECT SUM(amount) FROM BudgetItems WHERE reference = :reference AND parent = :parent";
		Session session = session();
		Double sum = (Double) session.createQuery(hql).setLong("reference", reference).setString("parent", parent).uniqueResult();
		session.close();
		
		return sum;
	}
	

	
}
