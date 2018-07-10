package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.ReceivablesDao;
import com.efo.entity.Receivables;
import com.efo.interfaces.IReceivables;

@Service
public class ReceivablesService implements IReceivables {
	
	@Autowired
	ReceivablesDao receivablesDao;

	@Override
	public void create(Receivables receivables) {
		receivablesDao.create(receivables);
	}

	@Override
	public Receivables retreive(Long reference) {
		return receivablesDao.retreive(reference);
	}

	@Override
	public void update(Receivables receivables) {
		receivablesDao.update(receivables);

	}

	@Override
	public void delete(Long reference) {
		receivablesDao.delete(reference);

	}
	
	public PagedListHolder<Receivables> retreiveList() {
		return new PagedListHolder<Receivables>(receivablesDao.retreiveList());
	}

}
