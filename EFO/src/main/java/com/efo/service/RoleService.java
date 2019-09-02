package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.RoleDao;
import com.efo.entity.Role;
import com.efo.interfaces.IRole;

@Service
public class RoleService implements IRole{

	@Autowired
	RoleDao roleDao;
	
	@Override
	public void create(Role role) {
		roleDao.create(role);
	}

	@Override
	public Role retrieve(int role_id) {
		return roleDao.retrieve(role_id);
	}

	@Override
	public Role retrieve(String role) {
		return roleDao.retrieve(role);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}
	
	public List<Role> retrieveRawList() {
		return roleDao.retrieveList();
	}
	
	public PagedListHolder<Role> retrieveList() {
		return new PagedListHolder<Role>(roleDao.retrieveList());
	}

}
