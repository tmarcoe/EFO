package com.efo.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.efo.dao.FetalTransactionDao;
import com.efo.entity.Profiles;
import com.efo.entity.Transactions;
import com.ftl.helper.FetalTransaction;
import com.ftl.helper.VariableType;


@Service
public class FetalTransactionService extends FetalTransaction {
	private static Logger logger = Logger.getLogger(FetalTransactionService.class);
	
	@Value("${logging.file}")
	private String logFile;
	
	@Value("${efo.sales.taxRate}")
	private String federalTaxRate;

	@Autowired
	private FetalTransactionDao transDao;

	private Session session;


	@Value("${fetal.properiesFile}")
	private String filePath;
	
	public Double calculatePayments(double total, double down, double interest, long payments) throws Exception {
		Double result = 0.0;
		try {
			initTransaction(filePath);
			publish("total", VariableType.DECIMAL, total);
			publish("down", VariableType.DECIMAL, down);
			publish("interest", VariableType.DECIMAL, interest);
			publish("payments", VariableType.NUMBER, payments);
			loadRule("calculators/calcpayment.trans");
			result = (Double) getValue("eachPayment");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.close();
			}
		}
		
		return result;
	}
	
	public void execTransaction(Profiles profile, Transactions transactions, Object... values) throws Exception {
		
		try {
			initTransaction(filePath);
			publish("transactions", VariableType.DAO, transactions);
			if ("".compareTo(profile.getVariables()) !=0 ) {
				String[] parms = profile.getVariables().split(";");
				if (values != null) {
					if (parms.length != values.length) {
						throw new Exception("Mismatch between parameters and values");
					}
					for (int i=0; i < parms.length; i++) {
						String[] parmComponents = parms[i].split(",");
						publish(parmComponents[0], StringToEnum(parmComponents[1]), values[i]);
					}
				}
			}
			loadRule(profile.getScript());
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.close();
			}
		}
		
	}

	/******************************************************
	 * Overridden methods
	 ******************************************************/

	@Override
	public void beginTrans() {
		session = transDao.beginTrans();
	}

	@Override
	public void commitTrans() {
		if (this.getErrorCount() > 0) {
			rollback();
		} else {
			transDao.commitTrans(session);
		}
	}

	@Override
	public void credit(Double amount, String account) {

		transDao.credit(amount, account, session);
	}

	@Override
	public void debit(Double amount, String account) {

		transDao.debit(amount, account, session);
	}

	@Override
	public void ledger(char type, Double amount, String account, String description) {

		transDao.ledger(type, amount, account, description, session);
	}

	@Override
	public double getBalance(String account) {

		return transDao.getBalance(account, session);
	}

	@Override
	public void rollback() {
		transDao.rollback(session);
		session = null;
	}

	@Override
	public Object lookup(String sql, Object... args) {
		String sqlWithArgs = String.format(sql, args);

		return transDao.lookup(sqlWithArgs, session);
	}

	@Override
	public Set<Object> list(String sql, int limit, Object... args) {
		String sqlWithArgs = String.format(sql, args);
		List<Object> l = transDao.list(sqlWithArgs, limit, session);

		return new HashSet<Object>(l);
	}

	@Override
	public void update(String sql,int limit, Object... args) {
		String sqlWithArgs = String.format(sql, args);
		transDao.update(sqlWithArgs, limit, session);
	}

	
	@Override
	public void fetalLogger(String clss, String msg) {
		String errorMsg = "Error in class " + clss + ": " + msg;
		logger.error(errorMsg);
		
	}

	@Override
	public void insert(Object record) {
		transDao.create(record, session);
	}

	@Override
	public void delete(Object record) {
		transDao.delete(record, session);
	}

	@Override
	public void merge(Object record) {
		transDao.merge(record, session);
	}

	@Override
	public void inventoryLedger(char type, Double qty, Double amount, String description) {
	
	}
	
	private VariableType StringToEnum(String param) {
		VariableType result = VariableType.NUMBER;
		
		switch (param.toLowerCase()) {
			case "number":
				result = VariableType.NUMBER;
				break;
				
			case "decimal":
				result = VariableType.DECIMAL;
				break;
				
			case "string":
				result = VariableType.STRING;
				break;
				
			case "date":
				result = VariableType.DATE;
				break;
				
			case "dao":
				result = VariableType.DAO;
				break;
				
			case "object":
				result = VariableType.OBJECT;
				break;
				
			case "boolean":
				result = VariableType.BOOLEAN;
				break;

			default:
				break;	
		}
		
		return result;
	}

}
