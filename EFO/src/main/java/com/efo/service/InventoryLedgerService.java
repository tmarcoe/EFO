package com.efo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.InventoryLedgerDao;
import com.efo.entity.InventoryLedger;
import com.efo.interfaces.IInventoryLedger;

@Service
public class InventoryLedgerService implements IInventoryLedger{

	@Autowired
	private InventoryLedgerDao inventoryLedgerDao;
	
	@Override
	public void create(InventoryLedger ledger) {
		inventoryLedgerDao.create(ledger);
	}

	@Override
	public InventoryLedger retrieve(Long id) {
		return inventoryLedgerDao.retrieve(id);
	}

	@Override
	public List<InventoryLedger> retrieveRawList(Date begin, Date end) {
		return inventoryLedgerDao.retrieveRawList(begin, end);
	}
	
	public PagedListHolder<InventoryLedger> retrieveList(Date begin, Date end) {
		return new PagedListHolder<InventoryLedger>(inventoryLedgerDao.retrieveRawList(begin, end));
	}

	@Override
	public void update(InventoryLedger ledger) {
		inventoryLedgerDao.update(ledger);
	}

	@Override
	public void delete(InventoryLedger ledger) {
		inventoryLedgerDao.delete(ledger);
	}

}
