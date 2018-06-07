package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.PettyCashDao;
import com.efo.entity.PettyCash;
import com.efo.interfaces.IPettyCash;

@Service
public class PettyCashService implements IPettyCash {
	
	@Autowired
	PettyCashDao pettyCashDao;

	@Override
	public void saveOrUpdate(PettyCash pettyCash) {
		pettyCashDao.saveOrUpdate(pettyCash);
	}

	@Override
	public PettyCash retrieve(int id) {
		return pettyCashDao.retrieve(id);
	}
	
	public boolean exists() {
		return pettyCashDao.exists();
	}

}
