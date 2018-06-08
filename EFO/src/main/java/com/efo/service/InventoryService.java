package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.InventoryDao;
import com.efo.entity.Inventory;
import com.efo.interfaces.IInventory;

@Service
public class InventoryService implements IInventory {
	
	@Autowired
	InventoryDao inventoryDao;

	@Override
	public void create(Inventory inventory) {
		inventoryDao.create(inventory);
	}

	@Override
	public Inventory retrieve(int id) {
		return inventoryDao.retrieve(id);
	}
	
	public PagedListHolder<Inventory> retrieveList() {
		return new PagedListHolder<Inventory>(inventoryDao.retrieveList());
	}

	@Override
	public void update(Inventory inventory) {
		inventoryDao.update(inventory);
	}

	@Override
	public void delete(Inventory inventory) {
		inventoryDao.delete(inventory);
	}
	
	public void delete(int id) {
		Inventory inventory = retrieve(id);
		delete(inventory);
	}

}
