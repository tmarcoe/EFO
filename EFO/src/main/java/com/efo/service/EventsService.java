package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.EventsDao;
import com.efo.entity.Events;
import com.efo.interfaces.IEvents;

@Service
public class EventsService implements IEvents {
	
	@Autowired
	private EventsDao eventsDao;

	@Override
	public void create(Events events) {
		eventsDao.create(events);

	}

	@Override
	public Events retrieve(Long id) {
		return eventsDao.retrieve(id);
	}

	@Override
	public List<Events> retrieveRawList(Long reference) {
		return eventsDao.retrieveRawList(reference);
	}

	@Override
	public void update(Events events) {
		eventsDao.update(events);
	}

	@Override
	public void merge(Events events) {
		eventsDao.merge(events);

	}

	@Override
	public void delete(Events events) {
		eventsDao.delete(events);

	}

	@Override
	public void deleteById(Long id) {
		eventsDao.deleteById(id);

	}

}
