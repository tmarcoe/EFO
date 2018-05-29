package com.efo.interfaces;

import com.efo.entity.Vendor;

public interface IVendor {
	public void create(Vendor vendor);
	public Vendor retrieve(int user_id);
	public void update(Vendor vendor);
	public void delete(int user_id);
}
