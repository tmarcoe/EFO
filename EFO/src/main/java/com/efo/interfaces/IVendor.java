package com.efo.interfaces;

import com.efo.entity.Vendor;

public interface IVendor {
	public void create(Vendor vendor);
	public Vendor retrieve(Long user_id);
	public void update(Vendor vendor);
	public void delete(Long user_id);
}
