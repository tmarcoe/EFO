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
	private Integer user_id;
	private String tax_id;
	private int	st_exempt;
	private int fd_exempt;
	private double hourlyRate;
	private double fTaxPrcnt;
	private double sTaxPrcnt;
	private double fUnPrcnt;
	private double sUnPrcnt;
	private double medPrcnt;
	private double ssiPrcnt;
	private double retirePrcnt;
	private double garnishment;
	private double other;
	private String otherExpl;
	private String payMethod;
	private String accountNum;
	private String routingNum;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Employee employee;
	
	@Column(name="USER_ID")
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	@Column(name="TAX_ID")
	public String getTax_id() {
		return tax_id;
	}
	public void setTax_id(String taxId) {
		this.tax_id = taxId;
	}
	
	@Column(name="ST_EXEMPT")
	public int getSt_exempt() {
		return st_exempt;
	}
	public void setSt_exempt(int st_exempt) {
		this.st_exempt = st_exempt;
	}
	
	@Column(name="FD_EXEMPT")
	public int getFd_exempt() {
		return fd_exempt;
	}
	public void setFd_exempt(int fd_exempt) {
		this.fd_exempt = fd_exempt;
	}
	@Column(name="HOURLY_RATE")
	public double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	@Column(name="F_TAX_PRCNT")
	public double getfTaxPrcnt() {
		return fTaxPrcnt;
	}
	public void setfTaxPrcnt(double fTaxPrcnt) {
		this.fTaxPrcnt = fTaxPrcnt;
	}
	
	@Column(name="S_TAX_PRCNT")
	public double getsTaxPrcnt() {
		return sTaxPrcnt;
	}
	public void setsTaxPrcnt(double sTaxPrcnt) {
		this.sTaxPrcnt = sTaxPrcnt;
	}
	
	@Column(name="F_UN_PRCNT")
	public double getfUnPrcnt() {
		return fUnPrcnt;
	}
	public void setfUnPrcnt(double fUnPrcnt) {
		this.fUnPrcnt = fUnPrcnt;
	}
	
	@Column(name="S_UN_PRCNT")
	public double getsUnPrcnt() {
		return sUnPrcnt;
	}
	public void setsUnPrcnt(double sUnPrcnt) {
		this.sUnPrcnt = sUnPrcnt;
	}
	
	@Column(name="MED_PRCNT")
	public double getMedPrcnt() {
		return medPrcnt;
	}
	public void setMedPrcnt(double medPrcnt) {
		this.medPrcnt = medPrcnt;
	}
	
	@Column(name="SSI_PRCNT")
	public double getSsiPrcnt() {
		return ssiPrcnt;
	}
	public void setSsiPrcnt(double ssiPrcnt) {
		this.ssiPrcnt = ssiPrcnt;
	}
	
	@Column(name="RETIRE_PRCNT")
	public double getRetirePrcnt() {
		return retirePrcnt;
	}
	public void setRetirePrcnt(double retirePrcnt) {
		this.retirePrcnt = retirePrcnt;
	}
	
	@Column(name="GARNISHMENT")
	public double getGarnishment() {
		return garnishment;
	}
	public void setGarnishment(double garnishment) {
		this.garnishment = garnishment;
	}
	
	@Column(name="OTHER")
	public double getOther() {
		return other;
	}
	public void setOther(double other) {
		this.other = other;
	}
	
	@Column(name="OTHER_EXPL")
	public String getOtherExpl() {
		return otherExpl;
	}
	public void setOtherExpl(String otherExpl) {
		this.otherExpl = otherExpl;
	}
	
	@Column(name="PAY_METHOD")
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
	@Column(name="ACCOUNT_NUM")
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	@Column(name="ROUTING_NUM")
	public String getRoutingNum() {
		return routingNum;
	}
	public void setRoutingNum(String routingNum) {
		this.routingNum = routingNum;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
		
	
		
}
