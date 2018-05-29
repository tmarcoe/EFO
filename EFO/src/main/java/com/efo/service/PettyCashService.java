package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PettyCashDao;
import com.efo.entity.PettyCash;
import com.efo.interfaces.IPettyCash;

@Service
public class PettyCashService implements IPettyCash {
	
	@Autowired
	private PettyCashDao pettyCashDao;

	@Override
	public void create(PettyCash pettyCash) {
		pettyCashDao.create(pettyCash);
	}

	@Override
	public PettyCash retrieve(int id) {
		return pettyCashDao.retrieve(id);
	}

	@Override
	public void update(PettyCash pettyCash) {
		pettyCashDao.update(pettyCash);
	}

	@Override
	public void delete(int id) {
		pettyCashDao.delete(id);
	}

	@Override
	public void delete(PettyCash pettyCash) {
		pettyCashDao.delete(pettyCash);
	}
	
	public PagedListHolder<PettyCash> retrieveList() {
		return new PagedListHolder<PettyCash>(pettyCashDao.retrieveList());
	}
}
