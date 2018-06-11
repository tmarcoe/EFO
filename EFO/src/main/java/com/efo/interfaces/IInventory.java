package com.efo.interfaces;

import com.efo.entity.Inventory;

public interface IInventory {
	
	public void create(Inventory inventory);
	public Inventory retrieve(int id);
	public void update(Inventory inventory);
	public void delete(Inventory inventory);

}