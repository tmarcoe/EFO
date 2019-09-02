package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ChartOfAccounts;




public interface IChartOfAccounts {
	public void create(ChartOfAccounts accounts);
	public void update(ChartOfAccounts accounts);
	public ChartOfAccounts retrieve(String account);
	public void delete(ChartOfAccounts account);
	public void delete(String account);
	List<ChartOfAccounts> getRawList();
}
