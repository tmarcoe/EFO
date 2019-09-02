package com.efo.component;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.entity.Role;
import com.efo.service.RoleService;

@Component
public class RoleUtilities {
	
	@Autowired
	private RoleService roleService;
	
	public String roleToString(Set<Role> roles) {
		String result = "";
		
		for(Role role : roles) {
			if (result.length() == 0) {
				result = String.valueOf(role.getId());
			}else{
				result += ";" + String.valueOf(role.getId());
			}
		}
		
		return result;
	}
	
	public Set<Role> stringToRole(String roleString) {

		Set<Role> result = new HashSet<Role>();
		
		String[] roles = roleString.split(";");
		for (int i=0; i < roles.length; i++) {
			if ( "".compareTo(roles[i]) == 0) continue;
			result.add(roleService.retrieve(Integer.valueOf(roles[i])));
		}
		
		return result;
	}

}
