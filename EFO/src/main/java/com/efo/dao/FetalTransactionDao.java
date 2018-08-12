package com.efo.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.efo.entity.ChartOfAccounts;
import com.efo.entity.EachInventory;
import com.efo.entity.GeneralLedger;
import com.efo.entity.Product;
import com.efo.entity.SalesItem;



@Transactional
@Repository
public class FetalTransactionDao {
	/*
	 * Both of these arrays must be the same size
	 */
	private final String[] accountType = {"Asset","Liability","Equity","Revenue","Expense", "Contra Asset"};
	private final boolean[] debitAccount = {true, false, false, false, true, false};

	@Value("${efo.inventory.type}")
	private String inventoryType;
	
	@Autowired
	SessionFactory sessionFactory;

	private Transaction trans;
	
	public Transaction getTrans() {
		return trans;
	}
	
	Session session() {
		return sessionFactory.getCurrentSession();
	}

	public Session beginTrans() {
		Session session = session();
		trans = session.beginTransaction();

		return session;
	}

	public void commitTrans(Session session) {
		
		trans.commit();
		trans = null;
		session.disconnect();
		session = null;
	}

	public void credit(Double amount, String account, Session session) {
		if (amount != 0) {
			ChartOfAccounts ac = (ChartOfAccounts) session.createCriteria(ChartOfAccounts.class)
					.add(Restrictions.idEq(account)).uniqueResult();
			if (isDebit(ac.getAccountType()) == false) {
				ac.setAccountBalance(ac.getAccountBalance() + amount);
			} else {
				ac.setAccountBalance(ac.getAccountBalance() - amount);
			}
			session.update(ac);
		}
	}

	public void debit(Double amount, String account, Session session) {
		if (amount != 0) {
			ChartOfAccounts ac = (ChartOfAccounts) session.createCriteria(ChartOfAccounts.class)
					.add(Restrictions.idEq(account)).uniqueResult();
			if (isDebit(ac.getAccountType()) == false) {
				ac.setAccountBalance(ac.getAccountBalance() - amount);
			} else {
				ac.setAccountBalance(ac.getAccountBalance() + amount);
			}
			session.update(ac);
		}
	}

	public void ledger(char type, Double amount, String account, String description, Session session) {
		if (amount != 0) {
			GeneralLedger gl = null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			if (type == 'C') {
				gl = new GeneralLedger(account, 0, cal.getTime(), description, 0, amount);
			} else if (type == 'D') {
				gl = new GeneralLedger(account, 0, cal.getTime(), description, amount, 0);
			}
			session.save(gl);
		}
	}

	public double getBalance(String account, Session session) {
		String hql = "FROM ChartOfAccounts WHERE account_num = :account";
		ChartOfAccounts cofa = (ChartOfAccounts) session.createQuery(hql).setString("account", account).uniqueResult();
		return cofa.getAccountBalance();
	}


	public Object lookup(String sql, Session session) {

		Object obj = session.createQuery(sql).uniqueResult();
		
		session.disconnect();
		
		return obj;
	}

	public void update(String sqlWithArgs, int limit, Session session) {
		if (limit == 0) {
			session.createQuery(sqlWithArgs).executeUpdate();
		}else{
			session.createQuery(sqlWithArgs).setMaxResults(limit).executeUpdate();
		}
	}

	public void rollback(Session session) {
		trans.rollback();
		trans = null;
	}

	@SuppressWarnings("unchecked")
	public List<Object> list(String sqlWithArgs, int limit, Session session) {
		List<Object> l = null;
		if (limit == 0) {
			l = session.createQuery(sqlWithArgs).list();
		}else{
			l = session.createQuery(sqlWithArgs).setMaxResults(limit).list();
		}
		session.disconnect();
		
		return l;
	}
	public void create(Object record, Session session) {
		session.save(record);
	}
	
	public void delete(Object record, Session session) {
		session.delete(record);
	}
	
	private boolean isDebit(String type) {
		boolean result = false;
		for (int i=0; i < accountType.length ; i++) {
			if (accountType[i].compareTo(type) == 0) {
				result = debitAccount[i];
			}
		}
		
		return result;
	}

	public void merge(Object record, Session session) {
		session.merge(record);
	}

	@SuppressWarnings("unchecked")
	public void commitStock(Set<?> items, Session session) {
		for (SalesItem item : (Set<SalesItem>) items) {
			Product product = item.getProduct();
			if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0) {
				commitEach(item, session);
			}else{
				commitFluid(item, session);
			}
		}
	}
	
	private void commitFluid(SalesItem item, Session session) {
		String hql = "UPDATE FluidInventory SET amt_in_stock = (amt_in_stock - :qty), amt_committed = (amt_committed + :qty) WHERE sku = :sku";
		session.createQuery(hql).setDouble("qty", item.getQty()).setString("sku", item.getSku()).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	private void commitEach(SalesItem item, Session session) {
		String hql = "FROM EachInventory WHERE sku = :sku AND received IS NOT null AND sold IS null ORDER BY received ";
		String upd = "UPDATE EachInventory SET sold = current_date(), sold_for = :sold_for WHERE id = :id";
		if ("LIFO".compareToIgnoreCase(inventoryType) == 0) {
			hql = hql + "DESC";
		}else{
			hql = hql + "ASC";
		}
		int qty = new Double(item.getQty()).intValue();
		List<EachInventory> updateList = session.createQuery(hql).setString("sku", item.getSku()).setMaxResults(qty).list();
		for (EachInventory each : updateList) {
			session.createQuery(upd).setDouble("sold_for", item.getSold_for()).setLong("id", each.getId()).executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	public void depleteStock(Set<?> items, Session session) {
		for (SalesItem item : (Set<SalesItem>) items) {
			Product product = item.getProduct();
			if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0) {
				depleteEach(item, session);
			}else{
				depleteFluid(item, session);
			}
		}
	}
	
	@SuppressWarnings({"unchecked", "unused"})
	private void depleteEach(SalesItem item, Session session) {
		String hql = "FROM EachInventory WHERE sku = :sku AND sold IS NOT null AND shipped IS null";
		String upd = "UPDATE EachInventory SET shipped = current_date() WHERE id = :id";
		String del = "DELETE FROM EachInventory WHERE id = :id";

		int qty = new Double(item.getQty()).intValue();
		List<EachInventory> updateList = session.createQuery(hql).setString("sku", item.getSku()).setMaxResults(qty).list();
		for (EachInventory each : updateList) {
			// session.createQuery(upd).setLong("id", each.getId()).executeUpdate();
			session.createQuery(del).setLong("id", each.getId()).executeUpdate();
	
		}
	}

	private void depleteFluid(SalesItem item, Session session) {
		String hql = "UPDATE FluidInventory SET amt_committed = (amt_committed - :qty) WHERE sku = :sku";
		session.createQuery(hql).setDouble("qty", item.getQty()).setString("sku", item.getSku()).executeUpdate();

	}
}
