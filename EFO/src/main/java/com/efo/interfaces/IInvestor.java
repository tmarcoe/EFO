package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Investor;

public interface IInvestor {
	public void create(Investor investor);
	public Investor retrieve(Long user_id);
	public List<Investor> retrieveRawList();
	public void update(Investor investor);
	public void delete(Investor investor);

}
