package com.efo.interfaces;

import java.util.List;

import com.efo.entity.LoanPayments;

public interface ILoanPayments {
	
	public void create(LoanPayments loanPayments);
	public LoanPayments retreive(Long id);
	public List<LoanPayments> retreiveRawList(Long reference);
	public void update(LoanPayments loanPayments);
	public void delete(LoanPayments loanPayments);

}
