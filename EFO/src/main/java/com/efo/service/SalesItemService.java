package com.efo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.SalesItemDao;
import com.efo.entity.SalesItem;
import com.efo.interfaces.ISalesItem;

@Service
public class SalesItemService implements ISalesItem {

	@Autowired
	private SalesItemDao salesItemDao;
	
	@Override
	public void creat(SalesItem salesItem) {
		salesItemDao.creat(salesItem);
	}

	@Override
	public SalesItem retrieve(int item_id) {
		return salesItemDao.retrieve(item_id);
	}

	@Override
	public List<SalesItem> retrieveRawList() {
		return salesItemDao.retrieveRawList();
	}

	public PagedListHolder<SalesItem> retrieveList() {
		return new PagedListHolder<SalesItem>(salesItemDao.retrieveRawList());
	}
	
	@Override
	public void update(SalesItem salesItem) {
		salesItemDao.update(salesItem);
	}

	@Override
	public void delete(SalesItem salesItem) {
		salesItemDao.delete(salesItem);
	}
	
	public void delete(int item_id) {
		SalesItem salesItem = salesItemDao.retrieve(item_id);
		salesItemDao.delete(salesItem);
	}

}
