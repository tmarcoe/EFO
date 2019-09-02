package com.efo.interfaces;

import java.util.List;

import com.efo.entity.TimeSheetItems;

public interface ITimeSheetItems {
	public void create(TimeSheetItems timeSheetItems);
	public TimeSheetItems retrieve(Long id);
	public List<TimeSheetItems> retrieveRawList(Long reference);
	public void update(TimeSheetItems timeSheetItems);
	public void delete(Long id);

}
