package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Events;

public interface IEvents {
	public void create(Events events);
	public Events retrieve(Long id);
	public List<Events> retrieveRawList(Long reference);
	public void update(Events events);
	public void merge(Events events);
	public void delete(Events events);
	public void deleteById(Long id);

}
