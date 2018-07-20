package com.efo.interfaces;

import java.util.List;

import com.efo.entity.EachInventory;

public interface IEachInventory {
	public void create(EachInventory inventory);
	public EachInventory retrieve(Long id);
	public List<EachInventory> retrieveRawList();
	public void update(EachInventory inventory);
	public void delete(EachInventory inventory);
}
