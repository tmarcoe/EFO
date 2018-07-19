package com.efo.component;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.entity.SalesItem;
import com.efo.service.NonPhysicalInventoryService;
import com.efo.service.ProductService;
import com.efo.service.SalesItemService;

@Component
public class SalesItemProcessor {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	SalesItemService salseItemService;
	
	@Autowired
	NonPhysicalInventoryService inventoryService;
	
	public void commitItems(Set<SalesItem> items) {
		for (SalesItem item : items) {
			inventoryService.commitStock(item.getSku(),item.getQty());
		}
	}
	
	public void shipItems(Set<SalesItem> items) {
		for (SalesItem item : items) {
			inventoryService.depleteStock(item.getSku(),item.getQty());
		}
	}

}
