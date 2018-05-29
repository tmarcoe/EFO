package com.efo.interfaces;

import com.efo.entity.EmpFinancial;

public interface IEmpFinancial {
	public void Create(EmpFinancial employee);
	public EmpFinancial retrieve(int user_id);
	public void update(EmpFinancial employee);
	public void delete(EmpFinancial employee);
}
