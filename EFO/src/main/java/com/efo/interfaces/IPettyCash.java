package com.efo.interfaces;

import com.efo.entity.PettyCash;

public interface IPettyCash {
	
	public void create(PettyCash pettyCash);
	public PettyCash retrieve(int id);
	public void update(PettyCash pettyCash);
	public void delete(int id);
	public void delete(PettyCash pettyCash);
}
