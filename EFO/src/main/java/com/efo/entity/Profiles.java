package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Profiles implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique=true)
	private String name;
	
	@NotBlank
	@Column(length = 64)
	private String script;
	private boolean exclude;
	
	@NotBlank
	private String type;
	private boolean depreciation;
	private boolean show_credit_terms;
	@Column(length = 1024)
	private String variables;
	private Date created;
	private boolean active;
	@Column(length = 1024)
	private String descr;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "profiles", cascade = CascadeType.ALL)
	private Set<Transactions> transactions = new HashSet<Transactions>(0);
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public boolean isExclude() {
		return exclude;
	}
	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isDepreciation() {
		return depreciation;
	}
	public void setDepreciation(boolean depreciation) {
		this.depreciation = depreciation;
	}
	public boolean isShow_credit_terms() {
		return show_credit_terms;
	}
	public void setShow_credit_terms(boolean show_credit_terms) {
		this.show_credit_terms = show_credit_terms;
	}
	public String getVariables() {
		return variables;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Set<Transactions> getTransactions() {
		return transactions;
	}
	public void setTransactions(Set<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	
}
