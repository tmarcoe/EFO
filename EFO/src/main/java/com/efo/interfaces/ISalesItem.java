package com.efo.interfaces;

import java.util.List;

import com.efo.entity.SalesItem;

public interface ISalesItem {
	
	public void create(SalesItem salesItem);
	public SalesItem retrieve(Long item_id);
	public List<SalesItem> retrieveRawList();
	public void update(SalesItem salesItem);
	public void delete(SalesItem salesItem);

}
