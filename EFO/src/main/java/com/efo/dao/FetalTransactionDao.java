package com.efo.dao;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.efo.entity.ChartOfAccounts;
import com.efo.entity.GeneralLedger;



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
		return sessionFactory.openSession();
	}

	public Session beginTrans() {
		Session session = session();
		trans = session.beginTransaction();

		return session;
	}

	public void commitTrans(Session session) {
		
		trans.commit();
		trans = null;
		session.close();
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
		Date timeStamp = new Date();
		if (amount != 0) {
			GeneralLedger gl = null;
			if (type == 'C') {
				gl = new GeneralLedger(account, 0, timeStamp, description, 0, amount);
			} else if (type == 'D') {
				gl = new GeneralLedger(account, 0, timeStamp, description, amount, 0);
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

		Object obj = session.createQuery(sql).setMaxResults(1).uniqueResult();
		
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

}
