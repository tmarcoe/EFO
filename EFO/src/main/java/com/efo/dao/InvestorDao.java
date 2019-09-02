package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Investor;
import com.efo.entity.User;
import com.efo.interfaces.IInvestor;

@Transactional
@Repository
public class InvestorDao implements IInvestor {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
 	@Override
	public void create(Investor investor) {
 		Session session = session();
 		Transaction tx = session.beginTransaction();
 		session.save(investor);
 		tx.commit();
 		session.close();
	}

	@Override
	public Investor retrieve(Long user_id) {
 		Session session = session();
 		Investor investor = (Investor) session.createCriteria(Investor.class).add(Restrictions.idEq(user_id)).uniqueResult();
 		session.close();
 		
		return investor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Investor> retrieveRawList() {
 		Session session = session();
 		List<Investor> investList = session.createCriteria(Investor.class).list();
 		session.close();
 		
		return investList;
	}

	@SuppressWarnings("unchecked")
	public List<User> retrieveEditList() {
		String sql = "FROM User AS u WHERE EXISTS (FROM Investor AS i WHERE i.user_id = u.user_id)";
	
		Session session = session();
		List<User> chooseList = session.createQuery(sql).list();
		session.close();
	
	return chooseList;

	}
	
	@SuppressWarnings("unchecked")
	public List<Investor> queryInvestor(String name) {
		String sql = "FROM Investor WHERE CONCAT(firstname, ' ', lastname) LIKE :name";
		name = "%" + name + "%";
		Session session = session();
		List<Investor> investor = session.createQuery(sql).setString("name", name).list();
		session.close();
		
		return investor;
	}
	
	@Override
	public void update(Investor investor) {
 		Session session = session();
 		Transaction tx = session.beginTransaction();
 		session.update(investor);
 		tx.commit();
 		session.close();
	}

	@Override
	public void delete(Investor investor) {
 		Session session = session();
 		Transaction tx = session.beginTransaction();
 		session.delete(investor);
 		tx.commit();
 		session.close();
	}

}
