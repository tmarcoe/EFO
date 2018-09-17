package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.InventoryLedger;
import com.efo.interfaces.IInventoryLedger;

@Transactional
@Repository
public class InventoryLedgerDao implements IInventoryLedger {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(InventoryLedger ledger) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(ledger);
		tx.commit();
		session.disconnect();
	}

	@Override
	public InventoryLedger retrieve(Long id) {
		Session session = session();
		InventoryLedger ledger = (InventoryLedger) session.createCriteria(InventoryLedger.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return ledger;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryLedger> retrieveRawList(Date begin, Date end) {
		String hql = "FROM InventoryLedger WHERE DATE(timestamp) BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		 List<InventoryLedger> ledgerList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		 session.disconnect();
		 
		return ledgerList;
	}

	@Override
	public void update(InventoryLedger ledger) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(ledger);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(InventoryLedger ledger) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(ledger);
		tx.commit();
		session.disconnect();
	}

}
