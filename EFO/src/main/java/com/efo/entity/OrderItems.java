package com.efo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class OrderItems implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long reference;
	@NotBlank
	private String sku;
	private String product_name;
	
	@DecimalMin("0.01")
	private double wholesale;
	@DecimalMin("0.01")
	private double amt_ordered;
	private double amt_received;
	@Transient
	private double amt_this_shipment;
	private Date delivery_date;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="REFERENCE", referencedColumnName ="REFERENCE", nullable = false, insertable=false, updatable=false )
	private ProductOrders productOrders;
	
	public OrderItems() {
	}
	
	public OrderItems(String sku, String product_name) {
		this.sku = sku;
		this.product_name = product_name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public double getWholesale() {
		return wholesale;
	}

	public void setWholesale(double wholesale) {
		this.wholesale = wholesale;
	}

	public double getAmt_ordered() {
		return amt_ordered;
	}

	public void setAmt_ordered(double amt_ordered) {
		this.amt_ordered = amt_ordered;
	}

	public double getAmt_received() {
		return amt_received;
	}

	public void setAmt_received(double amt_received) {
		this.amt_received = amt_received;
	}
	
	public double getAmt_this_shipment() {
		return amt_this_shipment;
	}

	public void setAmt_this_shipment(double amt_this_shipment) {
		this.amt_this_shipment = amt_this_shipment;
	}
	
	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public ProductOrders getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(ProductOrders productOrders) {
		this.productOrders = productOrders;
	}

}
