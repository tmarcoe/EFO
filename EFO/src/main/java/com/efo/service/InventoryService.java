package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.InventoryDao;
import com.efo.entity.Inventory;
import com.efo.entity.ProductOrders;
import com.efo.interfaces.IInventory;

@Service
public class InventoryService implements IInventory {
	
	@Autowired
	InventoryDao inventoryDao;

	@Override
	public void create(Inventory inventory) {
		inventoryDao.create(inventory);
	}
	public void batchCreate(ProductOrders order) {
		double perUnitPrice = (order.getAmount() / order.getQty());
		
		for (int i=0; i < order.getQty() ; i++) {
			Inventory inv = new Inventory();
			
			inv.setInvoice_num(order.getInvoice_num());
			inv.setWholesale(perUnitPrice);
			inv.setSku(order.getSku());
			inv.setOrdered(order.getOrder_date());
			
			inventoryDao.create(inv);
		}
		
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
