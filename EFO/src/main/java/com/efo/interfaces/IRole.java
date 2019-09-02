package com.efo.interfaces;

import com.efo.entity.Role;

public interface IRole {
	public void create(Role role);
	public void update(Role role);
	public Role retrieve(int id);
	public Role retrieve(String role);
	public void delete(Role role);
}
