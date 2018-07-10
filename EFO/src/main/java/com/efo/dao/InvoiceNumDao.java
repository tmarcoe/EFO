package com.efo.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.InvoiceNum;
import com.efo.interfaces.IInvoiceNum;

@Transactional
@Repository
public class InvoiceNumDao implements IInvoiceNum {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(InvoiceNum invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(invoice_num);
		tx.commit();
		session.disconnect();
	}

	@Override
	public InvoiceNum retrieve(String invoice_num) {
		Session session = session();
		InvoiceNum inv = (InvoiceNum) session.createCriteria(InvoiceNum.class).add(Restrictions.idEq(invoice_num)).uniqueResult();
		session.disconnect();
		
		return inv;
	}

	@Override
	public void update(InvoiceNum invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(invoice_num);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(InvoiceNum invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(invoice_num);
		tx.commit();
		session.disconnect();
	}
	
	public String getNextKey() {
		LocalDate today = new LocalDate();
		String keySearch = String.format("%03d%%", today.getDayOfYear());
		Long keySeq = 0L;
		Session session = session();
		Transaction tx = session.beginTransaction();
		String hql = "FROM InvoiceNum WHERE invoice_num LIKE :keySearch ORDER BY invoice_num DESC";
		InvoiceNum inv = (InvoiceNum) session.createQuery(hql).setString("keySearch", keySearch).setMaxResults(1).uniqueResult();
		if (inv == null) {
			keySeq = 1L;
		}else{
			keySeq = Long.valueOf(inv.getInvoice_num().substring(3));
			keySeq++;
		}
		String key = String.format("%03d%05d", today.getDayOfYear(), keySeq);
		
		InvoiceNum inventory_num = new InvoiceNum(key);
		
		session.save(inventory_num);
		
		tx.commit();
		
		session.disconnect();
		
		return key;
	}

}
