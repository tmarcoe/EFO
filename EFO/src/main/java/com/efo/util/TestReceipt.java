package com.efo.util;

import java.util.HashSet;
import java.util.Set;

import com.efo.entity.SalesItem;

public class TestReceipt {
	
	public Set<SalesItem> getSalesItems() {
		Set<SalesItem> items = new HashSet<SalesItem>();
		
		SalesItem item = new SalesItem();
		item.setReference(1L);
		item.setSku("047213818367");
		item.setQty(1.0);
		items.add(item);
		
		item = new SalesItem();
		item.setReference(2L);
		item.setSku("047213842690");
		item.setQty(2.0);
		items.add(item);

		item = new SalesItem();
		item.setReference(3L);
		item.setSku("047213958117");
		item.setQty(1.0);
		items.add(item);

		item = new SalesItem();
		item.setReference(4L);
		item.setSku("3700591213007");
		item.setQty(1.0);
		items.add(item);

		return items;
	}

}
