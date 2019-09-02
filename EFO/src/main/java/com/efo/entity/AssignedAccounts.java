package com.efo.entity;

import java.io.Serializable;

public class AssignedAccounts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Long user_id;
	protected String account_number;
	
	public AssignedAccounts() {}
	
	public AssignedAccounts(Long user_id, String account_number) {
		this.user_id = user_id;
		this.account_number = account_number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account_number == null) ? 0 : account_number.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignedAccounts other = (AssignedAccounts) obj;
		if (account_number == null) {
			if (other.account_number != null)
				return false;
		} else if (!account_number.equals(other.account_number))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	
}
