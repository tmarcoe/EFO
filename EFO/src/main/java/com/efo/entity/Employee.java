package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Id
	@GeneratedValue(generator = "generator")
	private Integer user_id;
	
	private String salutation;
	private String firstname;
	private String lastname;
	private String maleFemale;
	private String home_phone;
	private String cell_phone;
	private String emer_contact;
	private String emer_ph;

	private String company;
	private String division;
	private String supervisor;
	private String extension;
	private String office_loc;
	private String position;
	
	private boolean dnr;
	/************************************************
	 * Employment type
	 * F - Full time hourly
	 * S - Salary Non-exempt
	 * E - Salary Exempt
	 * P - Part time
	 * C - contract
	 * T - Temporary
	 ************************************************/
	private String emp_type;
	private Date start_date;
	private Date end_date;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private EmpFinancial emp_financial;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private User user;
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMaleFemale() {
		return maleFemale;
	}

	public void setMaleFemale(String maleFemale) {
		this.maleFemale = maleFemale;
	}

	public String getHome_phone() {
		return home_phone;
	}

	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getEmer_contact() {
		return emer_contact;
	}

	public void setEmer_contact(String emer_contact) {
		this.emer_contact = emer_contact;
	}

	public String getEmer_ph() {
		return emer_ph;
	}

	public void setEmer_ph(String emer_ph) {
		this.emer_ph = emer_ph;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getOffice_loc() {
		return office_loc;
	}

	public void setOffice_loc(String office_loc) {
		this.office_loc = office_loc;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isDnr() {
		return dnr;
	}

	public void setDnr(boolean dnr) {
		this.dnr = dnr;
	}

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public EmpFinancial getEmp_financial() {
		return emp_financial;
	}

	public void setEmp_financial(EmpFinancial emp_financial) {
		this.emp_financial = emp_financial;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
