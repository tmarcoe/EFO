package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.InvestorDao;
import com.efo.entity.Investor;
import com.efo.entity.User;
import com.efo.interfaces.IInvestor;

@Service
public class InvestorService implements IInvestor {
	
	@Autowired
	InvestorDao investorDao;

	@Override
	public void create(Investor investor) {
		investorDao.create(investor);
	}

	@Override
	public Investor retrieve(Long user_id) {
		return investorDao.retrieve(user_id);
	}

	@Override
	public List<Investor> retrieveRawList() {
		return investorDao.retrieveRawList();
	}
	
	public PagedListHolder<Investor> retrieveList() {
		
		return new PagedListHolder<Investor>(investorDao.retrieveRawList());
	}
	
	public PagedListHolder<User> retrieveEditList() {
		return new PagedListHolder<User>(investorDao.retrieveEditList());
	}
	
	public List<Investor> queryInvestor(String name) {
		return investorDao.queryInvestor(name);
	}

	@Override
	public void update(Investor investor) {
		investorDao.update(investor);
	}

	@Override
	public void delete(Investor investor) {
		investorDao.delete(investor);
	}

}
