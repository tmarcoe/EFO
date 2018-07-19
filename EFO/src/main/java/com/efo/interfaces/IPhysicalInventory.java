package com.efo.interfaces;

import java.util.List;

import com.efo.entity.PhysicalInventory;

public interface IPhysicalInventory {
	public void create(PhysicalInventory inventory);
	public PhysicalInventory retrieve(Long id);
	public List<PhysicalInventory> retrieveRawList();
	public void update(PhysicalInventory inventory);
	public void delete(PhysicalInventory inventory);
}
