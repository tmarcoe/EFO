package com.efo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.TimeSheetDao;
import com.efo.entity.TimeSheet;
import com.efo.interfaces.ITimeSheet;

@Service
public class TimeSheetService implements ITimeSheet {
	
	@Autowired
	private TimeSheetDao timeSheetDao;

	@Override
	public void create(TimeSheet timeSheet) {
		timeSheetDao.create(timeSheet);
	}

	@Override
	public TimeSheet retrieve(Long reference) {
		return timeSheetDao.retrieve(reference);
	}

	public TimeSheet retrieveByUserId(Long user_id) {
		return timeSheetDao.retrieveByUserId(user_id);
	}
	
	@Override
	public List<TimeSheet> retrieveRawList() {
		return timeSheetDao.retrieveRawList();
	}
	
	public PagedListHolder<TimeSheet> listSubmitted() {
		return new PagedListHolder<TimeSheet>(timeSheetDao.listSubmitted());
	}
	
	public boolean checkIfPeriodExists(Date begin, Long user_id, Long reference) {
		return timeSheetDao.checkIfPeriodExists(begin, user_id, reference);
	}

	@Override
	public void merge(TimeSheet timeSheet) {
		timeSheetDao.merge(timeSheet);	
	}

	@Override
	public void delete(Long reference) {
		timeSheetDao.delete(reference);
	}

}
