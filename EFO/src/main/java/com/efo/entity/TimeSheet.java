package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TimeSheet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private Long user_id;
	private Date begin_period;
	private Date submitted;
	private Date approved;
	private Date rejected;
	private String comments;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "timeSheet", cascade = CascadeType.ALL)
	private Set<TimeSheetItems> timeSheetItems = new HashSet<TimeSheetItems>(0);

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getBegin_period() {
		return begin_period;
	}

	public void setBegin_period(Date begin_period) {
		this.begin_period = begin_period;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public Date getApproved() {
		return approved;
	}

	public void setApproved(Date approved) {
		this.approved = approved;
	}

	public Date getRejected() {
		return rejected;
	}

	public void setRejected(Date rejected) {
		this.rejected = rejected;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<TimeSheetItems> getTimeSheetItems() {
		return timeSheetItems;
	}

	public void setTimeSheetItems(Set<TimeSheetItems> timeSheetItems) {
		this.timeSheetItems = timeSheetItems;
	}
	

}
