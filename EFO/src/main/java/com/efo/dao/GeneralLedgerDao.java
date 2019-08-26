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

import com.efo.entity.GeneralLedger;
import com.efo.interfaces.IGeneralLedger;

@Transactional
@Repository
public class GeneralLedgerDao implements IGeneralLedger {

	@Autowired
	SessionFactory sessionFactory;
	
	Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(GeneralLedger generalLedger) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(generalLedger);
		tx.commit();
		session.close();
	}

	@Override
	public GeneralLedger retrieve(int entry_num) {
		Session session = session();
		GeneralLedger gl = (GeneralLedger) session.createCriteria(GeneralLedger.class).add(Restrictions.idEq(entry_num)).uniqueResult();
		session.close();
		return gl;
	}

	@Override
	public void update(GeneralLedger generalLedger) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(generalLedger);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(GeneralLedger generalLedger) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(generalLedger);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<GeneralLedger> getList(Date begin, Date end) {
		String hql = "FROM GeneralLedger WHERE DATE(entryDate) BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		List<GeneralLedger> glList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		session.close();
		
		return glList;
	}

}
