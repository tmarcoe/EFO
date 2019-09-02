package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.efo.dao.UserDao;
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
		user.setPassword(encoder.encode(user.getPassword()));

		userDao.create(user); 		
	}

	@Override
	public User retrieve(Long user_id) {
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
	public void delete(Long user_id) {
		userDao.delete(user_id);
	}
	
	public void deleteByUserIds(Long user_id) {
		userDao.deleteByUserIds(user_id);
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
