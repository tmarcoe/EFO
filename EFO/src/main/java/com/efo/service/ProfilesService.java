package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.ProfilesDao;
import com.efo.entity.Profiles;
import com.efo.interfaces.IProfiles;

@Service
public class ProfilesService implements IProfiles {
	
	@Autowired
	private ProfilesDao profilesDao;

	@Override
	public void create(Profiles profiles) {
		profilesDao.create(profiles);
		
	}

	@Override
	public Profiles retrieve(String name) {
		return profilesDao.retrieve(name);
	}

	@Override
	public List<Profiles> retrieveRawList() {
		return profilesDao.retrieveRawList();
	}
	
	public List<String> retrieveNames(String type) {
		
		return profilesDao.retrieveNames(type);
	}
	
	public PagedListHolder<Profiles> RetrieveList() {
		
		return new PagedListHolder<Profiles>(profilesDao.retrieveRawList());
	}

	@Override
	public void merge(Profiles profiles) {
		profilesDao.merge(profiles);
		
	}

	@Override
	public void delete(Profiles profiles) {
		profilesDao.delete(profiles);
		
	}
	
	

}
