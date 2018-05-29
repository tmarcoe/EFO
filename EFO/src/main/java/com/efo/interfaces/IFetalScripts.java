package com.efo.interfaces;

import com.efo.entity.FetalScripts;

public interface IFetalScripts {
	
	public void create(FetalScripts fetalScripts);
	public FetalScripts retrieve(int id);
	public void update(FetalScripts fetalScripts);
	public void delete(FetalScripts fetalScripts);
}
