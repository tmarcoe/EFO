package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.CapitalAssets;
import com.efo.interfaces.ICapitalAssets;


@Transactional
@Repository
public class CapitalAssetsDao implements ICapitalAssets {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(CapitalAssets capitalAssets) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(capitalAssets);
		tx.commit();
		session.disconnect();
	}

	@Override
	public CapitalAssets retrieve(Long id) {
		Session session = session();
		CapitalAssets assets = (CapitalAssets) session.createCriteria(CapitalAssets.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return assets;
	}
	@SuppressWarnings("unchecked")
	public List<CapitalAssets> retrieveRawList() {
		Session session = session();
		List<CapitalAssets> assetsList = session.createCriteria(CapitalAssets.class).list();
		session.disconnect();
		
		return assetsList;
	}
	
	@Override
	public void update(CapitalAssets capitalAssets) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(capitalAssets);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(CapitalAssets capitalAssets) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(capitalAssets);
		tx.commit();
		session.disconnect();
	}

}
