package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PhysicalInventoryDao;
import com.efo.entity.PhysicalInventory;
import com.efo.interfaces.IPhysicalInventory;

@Service
public class PhysicalInventoryService implements IPhysicalInventory {
	
	@Autowired
	private PhysicalInventoryDao inventoryDao;

	@Override
	public void create(PhysicalInventory inventory) {
		inventoryDao.create(inventory);

	}

	@Override
	public PhysicalInventory retrieve(Long id) {
		return inventoryDao.retrieve(id);
	}

	@Override
	public List<PhysicalInventory> retrieveRawList() {
		return inventoryDao.retrieveRawList();
	}
	
	public PagedListHolder<PhysicalInventory> retrieveList() {
		return new PagedListHolder<PhysicalInventory>(inventoryDao.retrieveRawList());
	}

	@Override
	public void update(PhysicalInventory inventory) {
		inventoryDao.update(inventory);

	}

	@Override
	public void delete(PhysicalInventory inventory) {
		inventoryDao.delete(inventory);

	}
	
	public void stockShelf(PhysicalInventory inventory, Integer qty) {
		inventoryDao.stockShelf(inventory, qty);
	}

}
