package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.User;
import com.efo.interfaces.IUser;



@Transactional
@Repository
public class UserDao implements IUser {
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	private Session session() {
		return sessionFactory.openSession();
	}

	@Override
	public void create(User user) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		session.close();
	}

	@Override
	public User retrieve(Long user_id) {
		Session session = session();
		User user = (User) session.createCriteria(User.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.close();
	
		return user;
	}

	@Override
	public User retrieve(String username) {
		Session session = session();
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
		session.close();
		
		return user;
	}
	
	@Override
	public void update(User userProfile) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(userProfile);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(User userProfile) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(userProfile);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Long user_id) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add(Restrictions.idEq(user_id)).uniqueResult();
		session.delete(user);
		tx.commit();
		session.close();
	}

	public void deleteByUserIds(Long user_id) {
		Session session = session();
		String hql = "DELETE FROM user_role WHERE user_id = :user_id";
		Transaction tx = session.beginTransaction();
		
		session.createSQLQuery(hql).setLong("user_id", user_id).executeUpdate();
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> retrieveList() {
		Session session = session();
		List<User> userList = session.createCriteria(User.class).list();
		session.close();
		
		return userList;
	}

	public boolean exists(String username) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM User WHERE username = :username";
		long count = (long) session.createQuery(hql).setString("username", username).uniqueResult();
		session.close();
		
		return (count > 0);
	}


	public void updatePassword(User user) {
		Session session = session();
		String hqlUpdate = "UPDATE User SET password = :password, temp_pw = true WHERE user_id = :user_id";
		Transaction tx = session.beginTransaction();
		
		
		session.createQuery(hqlUpdate)
				.setString("password", user.getPassword())
				.setLong("user_id", user.getUser_id()).executeUpdate();
		tx.commit();
		session.close();

	}

	public void merge(User userProfile) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(userProfile);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> choose() {
		String sql = "FROM User AS u WHERE "+ 
					 "NOT EXISTS (FROM Employee AS e WHERE e.user_id = u.user_id) AND " +
					 "NOT EXISTS (FROM Customer AS c WHERE c.user_id = u.user_id) AND " +
					 "NOT EXISTS (FROM Vendor AS v WHERE v.user_id = u.user_id)";
		
		Session session = session();
		List<User> chooseList = session.createQuery(sql).list();
		session.close();
		
		return chooseList;
	}
}
