package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.PettyCashVoucher;
import com.efo.interfaces.IPettyCashVoucher;

@Transactional
@Repository
public class PettyCashVoucherDao implements IPettyCashVoucher {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(PettyCashVoucher pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(pettyCash);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PettyCashVoucher retrieve(int id) {
		Session session = session();
		PettyCashVoucher pettyCash = (PettyCashVoucher) session.createCriteria(PettyCashVoucher.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return pettyCash;
	}
	
	@SuppressWarnings("unchecked")
	public List<PettyCashVoucher> retrieveList() {
		Session session = session();
		List<PettyCashVoucher> pcList = session.createCriteria(PettyCashVoucher.class).list();	
		session.disconnect();
		
		return pcList;
	}

	@Override
	public void update(PettyCashVoucher pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(pettyCash);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(int id) {
		PettyCashVoucher pettyCash = retrieve(id);
		delete(pettyCash);
	}

	@Override
	public void delete(PettyCashVoucher pettyCash) {
		Session session = session();
		Transaction tx = session.beginTransaction();

		tx.commit();
		session.disconnect();
	}

}
