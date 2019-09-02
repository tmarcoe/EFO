package com.efo.component;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.entity.TimeReportingAccounts;
import com.efo.service.TimeReportingAccountsService;

@Component
public class AccountUtilities {

	@Autowired
	private TimeReportingAccountsService tsService;

	public String accountsToString(Set<TimeReportingAccounts> accounts) {
		String result = "";

		for (TimeReportingAccounts account : accounts) {
			if (result.length() == 0) {
				result = account.getAccount_number();
			} else {
				result += ";" + account.getAccount_number();
			}
		}

		return result;
	}
	
	public Set<TimeReportingAccounts> stringToAccounts(String accountString) {
		Set<TimeReportingAccounts> result = new HashSet<TimeReportingAccounts>();
		
		String[] accounts = accountString.split(";");
		for (int i=0; i < accounts.length ; i++) {
			if ("".compareToIgnoreCase(accounts[i]) == 0) continue;
			result.add(tsService.retrieve(accounts[i]));
		}
		
		return result;
	}
}
