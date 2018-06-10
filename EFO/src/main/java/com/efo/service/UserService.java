package com.efo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.efo.dao.UserDao;
import com.efo.entity.Role;
import com.efo.entity.User;
import com.efo.interfaces.IUser;


@Service
public class UserService implements IUser{
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public void create(User user) {
		Set<Role> role = new HashSet<Role>();
	
		role.add(roleService.retrieve("USER"));
		user.setRoles(role);
		user.setPassword(encoder.encode(user.getPassword()));
		user.getCommon().setUser_id(user.getUser_id());
		
		userDao.create(user); 
		
		
	}

	@Override
	public User retrieve(int user_id) {
		return userDao.retrieve(user_id);
	}

	@Override
	public User retrieve(String username) {
		return userDao.retrieve(username);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void delete(int user_id) {
		userDao.delete(user_id);
	}

	public PagedListHolder<User> retrieveList() {
		return new PagedListHolder<User>(userDao.retrieveList());
	}

	public boolean exists(String username) {
		return userDao.exists(username);
	}
	
	public void merge(User userProfile) {
		userDao.merge(userProfile);
	}
	
	public void updatePassword(User user) {
		userDao.updatePassword(user);
	}
	
	public PagedListHolder<User> choose() {
		return new PagedListHolder<User>(userDao.choose());
	}

}
