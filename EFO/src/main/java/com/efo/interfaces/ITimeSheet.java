package com.efo.interfaces;

import java.util.List;

import com.efo.entity.TimeSheet;

public interface ITimeSheet {
	public void create(TimeSheet timeSheet);
	public TimeSheet retrieve(Long reference);
	public List<TimeSheet> retrieveRawList();
	public void merge(TimeSheet timeSheet);
	public void delete(Long reference);
;
}
