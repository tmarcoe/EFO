package com.efo.interfaces;

import java.util.List;

import com.efo.entity.SalesItem;

public interface ISalesItem {
	
	public void creat(SalesItem salesItem);
	public SalesItem retrieve(int item_id);
	public List<SalesItem> retrieveRawList();
	public void update(SalesItem salesItem);
	public void delete(SalesItem salesItem);

}
