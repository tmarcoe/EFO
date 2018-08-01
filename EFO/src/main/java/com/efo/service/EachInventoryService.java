package com.efo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.EachInventoryDao;
import com.efo.entity.EachInventory;
import com.efo.entity.OrderItems;
import com.efo.entity.SalesItem;
import com.efo.interfaces.IEachInventory;

@Service
public class EachInventoryService implements IEachInventory {
	
	@Autowired
	private EachInventoryDao inventoryDao;

	@Override
	public void create(EachInventory inventory) {
		inventoryDao.create(inventory);

	}

	@Override
	public EachInventory retrieve(Long id) {
		return inventoryDao.retrieve(id);
	}

	@Override
	public List<EachInventory> retrieveRawList() {
		return inventoryDao.retrieveRawList();
	}
	
	public PagedListHolder<EachInventory> retrieveList() {
		return new PagedListHolder<EachInventory>(inventoryDao.retrieveRawList());
	}

	@Override
	public void update(EachInventory inventory) {
		inventoryDao.update(inventory);

	}

	@Override
	public void delete(EachInventory inventory) {
		inventoryDao.delete(inventory);

	}
	
	public void stockShelf(EachInventory inventory, Integer qty) {
		inventoryDao.stockShelf(inventory, qty);
	}
	public void markAsDelivered(OrderItems order, int qty, Long reference) {
		inventoryDao.markAsDelivered(order, qty, reference);
	}
	
	public double getAmountOrdered(String sku) {
		return inventoryDao.getAmountOrdered(sku);
	}
	
	public double getAmountReceived(String sku) {
		
		return inventoryDao.getAmountReceived(sku);
	}

	public double getAmountCommitted(String sku) {
		
		return inventoryDao.getAmountCommitted(sku);
	}

	public void depleteStock(Set<SalesItem> items) {
		inventoryDao.depleteStock(items);
	}
}
