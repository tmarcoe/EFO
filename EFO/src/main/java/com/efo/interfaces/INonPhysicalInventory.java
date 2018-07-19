package com.efo.interfaces;

import com.efo.entity.NonPhysicalInventory;

public interface INonPhysicalInventory {
	
	public void create(NonPhysicalInventory inventory);
	public NonPhysicalInventory retrieve(String sku);
	public void update(NonPhysicalInventory inventory);
	public void delete(NonPhysicalInventory inventory);

}
