package com.efo.interfaces;

import com.efo.entity.PettyCashVoucher;

public interface IPettyCashVoucher {
	
	public void create(PettyCashVoucher pettyCash);
	public PettyCashVoucher retrieve(int id);
	public void update(PettyCashVoucher pettyCash);
	public void delete(int id);
	public void delete(PettyCashVoucher pettyCash);
}
