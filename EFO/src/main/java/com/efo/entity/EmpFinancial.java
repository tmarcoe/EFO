package com.efo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class EmpFinancial implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "employee"))
	@Id
	@GeneratedValue(generator = "generator")
	private Long user_id;
	@Column(length = 20)
	private String ein;
	@Column(length = 20)
	private String ssn;
	private Long exemptions;
	@Column(length = 2)
	private String status;
	
	private Double pay_rate;			// Salary - pay rate per year ; Hourly - pay rate per hour
	private Double fed;					// Federal tax
	private Double state;				// State tax
	private Double city;				// City tax
	private Double fed_unemployment;	// Federal unemployement
	private Double st_unemployment;	// State unemployement
	private Double ss_tax;
	private Double fica;
	private Double medical;				// Company medical plan
	private Double retirement;			// Company retirement plan
	private Double union_dues;
	private Double garnishment;
	@Column(length = 1)
	private String pay_method;
	@Column(length = 24)
	private String account_num; // Direct deposit account
	@Column(length = 24)
	private String routing_num;
	private String fed_trans;	// Federal transaction file for adding employee;
	private String st_trans;	// State transaction file for adding employee;
	private String city_trans;	// City transaction file for adding employee;

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Employee employee;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Long getExemptions() {
		return exemptions;
	}

	public void setExemptions(Long exemptions) {
		this.exemptions = exemptions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPay_rate() {
		return pay_rate;
	}

	public void setPay_rate(Double pay_rate) {
		this.pay_rate = pay_rate;
	}

	public Double getFed() {
		return fed;
	}

	public void setFed(Double fed) {
		this.fed = fed;
	}

	public Double getState() {
		return state;
	}

	public void setState(Double state) {
		this.state = state;
	}

	public Double getCity() {
		return city;
	}

	public void setCity(Double city) {
		this.city = city;
	}

	public Double getFed_unemployment() {
		return fed_unemployment;
	}

	public void setFed_unemployment(Double fed_unemployment) {
		this.fed_unemployment = fed_unemployment;
	}

	public Double getSt_unemployment() {
		return st_unemployment;
	}

	public void setSt_unemployment(Double st_unemployment) {
		this.st_unemployment = st_unemployment;
	}

	public Double getSs_tax() {
		return ss_tax;
	}

	public void setSs_tax(Double ss_tax) {
		this.ss_tax = ss_tax;
	}

	public Double getFica() {
		return fica;
	}

	public void setFica(Double fica) {
		this.fica = fica;
	}

	public Double getMedical() {
		return medical;
	}

	public void setMedical(Double medical) {
		this.medical = medical;
	}

	public Double getRetirement() {
		return retirement;
	}

	public void setRetirement(Double retirement) {
		this.retirement = retirement;
	}

	public Double getUnion_dues() {
		return union_dues;
	}

	public void setUnion_dues(Double union_dues) {
		this.union_dues = union_dues;
	}

	public Double getGarnishment() {
		return garnishment;
	}

	public void setGarnishment(Double garnishment) {
		this.garnishment = garnishment;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public String getRouting_num() {
		return routing_num;
	}

	public void setRouting_num(String routing_num) {
		this.routing_num = routing_num;
	}

	public String getFed_trans() {
		return fed_trans;
	}

	public void setFed_trans(String fed_trans) {
		this.fed_trans = fed_trans;
	}

	public String getSt_trans() {
		return st_trans;
	}

	public void setSt_trans(String st_trans) {
		this.st_trans = st_trans;
	}

	public String getCity_trans() {
		return city_trans;
	}

	public void setCity_trans(String city_trans) {
		this.city_trans = city_trans;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
