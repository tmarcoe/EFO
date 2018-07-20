package com.efo.interfaces;

import com.efo.entity.FluidInventory;

public interface IFluidInventory {
	
	public void create(FluidInventory inventory);
	public FluidInventory retrieve(String sku);
	public void update(FluidInventory inventory);
	public void delete(FluidInventory inventory);

}
