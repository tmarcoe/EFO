package com.efo.interfaces;

import com.efo.entity.CapitalAssets;

public interface ICapitalAssets {
	public void create(CapitalAssets capitalAssets);
	public CapitalAssets retrieve(String invoice_num);
	public void update(CapitalAssets capitalAssets);
	public void delete(CapitalAssets capitalAssets);
}
