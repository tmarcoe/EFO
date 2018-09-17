package com.efo.interfaces;

import java.util.Date;
import java.util.List;

import com.efo.entity.InventoryLedger;

public interface IInventoryLedger {
	public void create(InventoryLedger ledger);
	public InventoryLedger retrieve(Long id);
	public List<InventoryLedger> retrieveRawList(Date begin, Date end);
	public void update(InventoryLedger ledger);
	public void delete(InventoryLedger ledger);
}
