package com.efo.interfaces;

import com.efo.entity.User;

public interface IUser {
	public void create(User userProfile);
	public User retrieve(int user_id);
	public User retrieve(String username);
	public void update(User userProfile);
	public void delete(User userProfile);
	public void delete(int user_id);
}
