package com.efo.interfaces;

import java.util.List;

import com.efo.entity.LoanTerms;

public interface ILoanTerms {
	
	public void create(LoanTerms loanTerms);
	public LoanTerms retreive(Long reference);
	public List<LoanTerms> retreiveRawList();
	public void update(LoanTerms loanTerms);
	public void delete(LoanTerms loanTerms);

}
