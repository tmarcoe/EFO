package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Profiles;

public interface IProfiles {
	public void create(Profiles profiles);
	public Profiles retrieve(String name);
	public List<Profiles> retrieveRawList();
	public void merge(Profiles profiles);
	public void delete(Profiles profiles);

}
