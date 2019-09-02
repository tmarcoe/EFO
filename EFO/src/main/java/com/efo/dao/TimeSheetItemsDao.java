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

import com.efo.entity.TimeSheetItems;
import com.efo.interfaces.ITimeSheetItems;

@Transactional
@Repository
public class TimeSheetItemsDao implements ITimeSheetItems {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}

	@Override
	public void create(TimeSheetItems timeSheetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(timeSheetItems);
		tx.commit();
		session.close();
	}

	@Override
	public TimeSheetItems retrieve(Long id) {
		Session session = session();
		TimeSheetItems ts = (TimeSheetItems) session.createCriteria(TimeSheetItems.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return ts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeSheetItems> retrieveRawList(Long reference) {
		Session session = session();
		List<TimeSheetItems> tsList = session.createCriteria(TimeSheetItems.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("reference", reference)).list();
		session.close();
		
		return tsList;
	}

	@Override
	public void update(TimeSheetItems timeSheetItems) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(timeSheetItems);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Long id) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.close();
	}
	
	public boolean accountNumExists(String accountNum, Long reference) {
		Long count = 0L;
		String hql = "SELECT COUNT(*) FROM TimeSheetItems WHERE reference = :reference AND account_num = :accountNum";
		Session session = session();
		count = (Long) session.createQuery(hql).setLong("reference", reference).setString("accountNum", accountNum).uniqueResult();
		session.close();
		
		return (count > 0);
	}

}
