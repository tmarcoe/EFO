package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.CapitalAssetsDao;
import com.efo.entity.CapitalAssets;
import com.efo.interfaces.ICapitalAssets;

@Service
public class CapitalAssetsService implements ICapitalAssets {
	
	@Autowired
	private CapitalAssetsDao assetsDao;

	@Override
	public void create(CapitalAssets capitalAssets) {
		assetsDao.create(capitalAssets);
	}

	@Override
	public CapitalAssets retrieve(Long id) {
		return assetsDao.retrieve(id);
	}
	
	public PagedListHolder<CapitalAssets> retrieveList() {
		return new PagedListHolder<CapitalAssets>(assetsDao.retrieveRawList());
	}
	
	public List<CapitalAssets> retrieveRawList() {
		return assetsDao.retrieveRawList();
	}
	
	@Override
	public void update(CapitalAssets capitalAssets) {
		assetsDao.update(capitalAssets);
	}

	@Override
	public void delete(CapitalAssets capitalAssets) {
		assetsDao.delete(capitalAssets);
	}

}
