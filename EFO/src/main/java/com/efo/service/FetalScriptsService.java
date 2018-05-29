package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.FetalScriptsDao;
import com.efo.entity.FetalScripts;
import com.efo.interfaces.IFetalScripts;


@Service
public class FetalScriptsService implements IFetalScripts {

	@Autowired
	FetalScriptsDao fetalScriptsDao;
	
	@Override
	public void create(FetalScripts fetalScripts) {
		fetalScriptsDao.create(fetalScripts);
	}

	@Override
	public FetalScripts retrieve(int id) {
		return fetalScriptsDao.retrieve(id);
	}
	
	public FetalScripts retrieve(String file_name) {
		return fetalScriptsDao.retrieve(file_name);
	}
	
	public PagedListHolder<FetalScripts> retrieveList() {
		return new PagedListHolder<FetalScripts>(fetalScriptsDao.retrieveList());
	}

	@Override
	public void update(FetalScripts fetalScripts) {
		fetalScriptsDao.update(fetalScripts);
	}

	@Override
	public void delete(FetalScripts fetalScripts) {
		fetalScriptsDao.delete(fetalScripts);
	}

}
