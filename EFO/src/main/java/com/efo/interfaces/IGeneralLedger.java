package com.efo.interfaces;

import com.efo.entity.GeneralLedger;

public interface IGeneralLedger {
	public void create(GeneralLedger generalLedger);
	public GeneralLedger retrieve(int entry_num);
	public void update(GeneralLedger generalLedger);
	public void delete(GeneralLedger generalLedger);
}
