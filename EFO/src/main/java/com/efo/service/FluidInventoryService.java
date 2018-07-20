package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.FluidInventoryDao;
import com.efo.entity.FluidInventory;
import com.efo.interfaces.IFluidInventory;

@Service
public class FluidInventoryService implements IFluidInventory {
	
	@Autowired
	FluidInventoryDao inventoryDao;

	@Override
	public void create(FluidInventory inventory) {
		inventoryDao.create(inventory);
	}
	
	@Override
	public FluidInventory retrieve(String sku) {
		return inventoryDao.retrieve(sku);
	}
	
	public PagedListHolder<FluidInventory> retrieveList() {
		return new PagedListHolder<FluidInventory>(inventoryDao.retrieveList());
	}

	@Override
	public void update(FluidInventory inventory) {
		inventoryDao.update(inventory);
	}

	@Override
	public void delete(FluidInventory inventory) {
		inventoryDao.delete(inventory);
	}
	
	public void delete(String sku) {
		FluidInventory inventory = retrieve(sku);
		delete(inventory);
	}
	public void commitStock(String sku, double amt) {
		inventoryDao.commitInventory(sku,amt);
	}
	
	public void depleteStock(String sku, double amt) {
		inventoryDao.depleteInventory(sku,amt);
	}


}
