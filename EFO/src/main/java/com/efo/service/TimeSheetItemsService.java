package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.TimeSheetItemsDao;
import com.efo.entity.TimeSheetItems;
import com.efo.interfaces.ITimeSheetItems;

@Service
public class TimeSheetItemsService implements ITimeSheetItems {
	
	@Autowired
	private TimeSheetItemsDao timeSheetItemsDao;

	@Override
	public void create(TimeSheetItems timeSheetItems) {
		timeSheetItemsDao.create(timeSheetItems);
	}

	@Override
	public TimeSheetItems retrieve(Long id) {
		return timeSheetItemsDao.retrieve(id);
	}

	@Override
	public List<TimeSheetItems> retrieveRawList(Long reference) {
		return timeSheetItemsDao.retrieveRawList(reference);
	}

	@Override
	public void update(TimeSheetItems timeSheetItems) {
		timeSheetItemsDao.update(timeSheetItems);
	}

	@Override
	public void delete(Long id) {
		timeSheetItemsDao.delete(id);
	}

	public boolean accountNumExists(String accountNum, Long reference) {
		return timeSheetItemsDao.accountNumExists(accountNum, reference);
	}
}
