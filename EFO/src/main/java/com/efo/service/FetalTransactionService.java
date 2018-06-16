package com.efo.service;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.efo.entity.PaymentsReceived;
import com.efo.entity.PettyCash;
import com.efo.entity.PettyCashVoucher;
import com.efo.entity.ProductOrders;
import com.efo.entity.Receivables;
import com.efo.dao.FetalTransactionDao;
import com.efo.entity.Inventory;
import com.efo.entity.Payables;
import com.efo.entity.PaymentsPaid;
import com.ftl.helper.FetalTransaction;
import com.ftl.helper.VariableType;


@Service
public class FetalTransactionService extends FetalTransaction {
	private static Logger logger = Logger.getLogger(FetalTransactionService.class);
	
	@Value("${logging.file}")
	private String logFile;

	@Autowired
	private FetalTransactionDao transDao;

	private Session session;


	@Value("${fetal.properiesFile}")
	private String filePath;
	
	public void purchaseInventory(ProductOrders order, Inventory inventory ) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Purchase of Inventory (SKU: " + order.getSku() + ")");
			publish("order", VariableType.DAO, order);
			publish("payables", VariableType.DAO, new Payables());
			publish("inventory", VariableType.DAO, inventory);
			loadRule("retailpurchase.trans");
			}
		finally {
			closeFetal();
		}
	}
	
	public void replenishPettyCash(PettyCash pettyCash, double pcAmount) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Replenising Petty cash to " + pcAmount);
			publish("pettyCash", VariableType.DAO, pettyCash);
			publish("pcAmount", VariableType.DECIMAL, pcAmount);
			loadRule("replenishpc.trans");
			}
		finally {
			closeFetal();
		}
	}

	public void addPettyCash(PettyCashVoucher pettyCashVoucher) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Petty Cash: " + pettyCashVoucher.getReason());
			publish("pettyCashVoucher", VariableType.DAO, pettyCashVoucher);
			loadRule("pcdisbursement.trans");
		}
		finally {
			closeFetal();
		}
		
	}

	public void pettyCashAdjustment(PettyCashVoucher pettyCashVoucher, double adjustAmount) throws IOException{
		try {
			initTransaction(filePath);
			setDescription("Adjustment for error");
			publish("pettyCashVoucher", VariableType.DAO, pettyCashVoucher);
			publish("adjustAmount", VariableType.DECIMAL, adjustAmount);
			loadRule("pcadjustment.trans");
		}
		finally {
			closeFetal();
		}
	}
	
	public void transferPC(PettyCashVoucher oldPc, String fromAccount) throws IOException{
		try {
			initTransaction(filePath);
			setDescription("Changing Petty Cash Type");
			publish("pettyCash", VariableType.DAO, oldPc);
			publish("toAccount", VariableType.STRING, fromAccount);
			loadRule("transferpc.trans");
		}
		finally{
			closeFetal();
		}
	}

	public void addAp(Payables payables) throws IOException {
		try {
			initTransaction(filePath);
			publish("payables", VariableType.DAO, payables);
			loadRule("ap.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
		}
		finally {
			closeFetal();
		}

	}
	
	public void adjustAp(Payables payables, double amount) throws IOException {
		try {
			initTransaction(filePath);
			publish("payables", VariableType.DAO, payables);
			publish("adjustment", VariableType.DECIMAL, amount);
			loadRule("adjustap.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
		}
		finally {
			closeFetal();
		}

	}
	public void disbursePayment(PaymentsPaid payments, Payables payables) throws IOException {
		try {
			initTransaction(filePath);
			publish("payables", VariableType.DAO, payables);
			publish("payment", VariableType.DAO, payments);
			loadRule("paymentpaid.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
			
		} finally {
			closeFetal();
		}
	}
	
	public void createAccount(String account) throws IOException {
		
		try {
			initTransaction(filePath);
			publish("account", VariableType.STRING, account);
			loadRule("adjustap.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
		}
		finally {
			closeFetal();
		}

	}
	public void addAr(Receivables receivables) throws IOException {
		try {
			initTransaction(filePath);
			publish("receivables", VariableType.DAO, receivables);
			loadRule("ar.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
			
		} finally{
			closeFetal();
		}
	}
	
	public void adjustAr(Receivables receivables, double amount) throws IOException {
		
		try {
			initTransaction(filePath);
			publish("receivables", VariableType.DAO, receivables);
			publish("adjustment", VariableType.DECIMAL, amount);
			loadRule("adjustar.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
			
		} finally{
			closeFetal();
		}
	}
	
	public void receivePayment(PaymentsReceived payments, Receivables receivables) throws IOException {
		try {
			initTransaction(filePath);
			publish("payment", VariableType.DAO, payments);
			publish("receivable", VariableType.DAO, receivables);
			loadRule("paymentreceived.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
			
		} finally {
			closeFetal();
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
	}

	@Override
	public Object lookup(String sql, Object... args) {
		String sqlWithArgs = String.format(sql, args);

		return transDao.lookup(sqlWithArgs);
	}

	@Override
	public Set<Object> list(String sql, Object... args) {
		String sqlWithArgs = String.format(sql, args);
		List<Object> l = transDao.list(sqlWithArgs);

		return new HashSet<Object>(l);
	}

	@Override
	public void update(String sql, Object... args) {
		String sqlWithArgs = String.format(sql, args);
		transDao.update(sqlWithArgs);
	}

	
	@Override
	public void fetalLogger(String clss, String msg) {
		String errorMsg = "Error in class " + clss + ": " + msg;
		logger.error(errorMsg);
		
	}

	@Override
	public void insert(String sql, Object record) {
		transDao.create(record, session);
	}

	@Override
	public void delete(String sql, Object record) {
		transDao.delete(record, session);
	}

}
