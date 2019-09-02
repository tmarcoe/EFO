package com.efo.interfaces;

import java.util.Date;
import java.util.List;

import com.efo.entity.Transactions;

public interface ITransactions {
	public void create(Transactions transactions);
	public Transactions retrieve(Long reference);
	public List<Transactions> retrieveRawList(Date begin, Date End);
	public void update(Transactions transactions);
	public void delete(Transactions transactions);
}
