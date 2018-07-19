package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.InventoryDao;
import com.efo.entity.NonPhysicalInventory;
import com.efo.interfaces.IInventory;

@Service
public class InventoryService implements IInventory {
	
	@Autowired
	InventoryDao inventoryDao;

	@Override
	public void create(NonPhysicalInventory inventory) {
		inventoryDao.create(inventory);
	}
	
	@Override
	public NonPhysicalInventory retrieve(String sku) {
		return inventoryDao.retrieve(sku);
	}
	
	public PagedListHolder<NonPhysicalInventory> retrieveList() {
		return new PagedListHolder<NonPhysicalInventory>(inventoryDao.retrieveList());
	}

	@Override
	public void update(NonPhysicalInventory inventory) {
		inventoryDao.update(inventory);
	}

	@Override
	public void delete(NonPhysicalInventory inventory) {
		inventoryDao.delete(inventory);
	}
	
	public void delete(String sku) {
		NonPhysicalInventory inventory = retrieve(sku);
		delete(inventory);
	}
	public void commitStock(String sku, double amt) {
		inventoryDao.commitInventory(sku,amt);
	}
	
	public void depleteStock(String sku, double amt) {
		inventoryDao.depleteInventory(sku,amt);
	}


}
