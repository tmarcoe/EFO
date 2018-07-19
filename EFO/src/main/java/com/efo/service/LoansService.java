package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.LoansDao;
import com.efo.entity.Loans;
import com.efo.interfaces.ILoans;

@Service
public class LoansService implements ILoans {
	
	@Autowired
	private LoansDao loansDao;
	
	@Override
	public void create(Loans loans) {
		loansDao.create(loans);

	}

	@Override
	public Loans retrieve(Long trans_id) {
		return loansDao.retrieve(trans_id);
	}

	@Override
	public List<Loans> retrieveRawList() {
		return loansDao.retrieveRawList();
	}

	@Override
	public void update(Loans loans) {
		loansDao.update(loans);

	}

	@Override
	public void delete(Loans loans) {
		loansDao.delete(loans);

	}

}
