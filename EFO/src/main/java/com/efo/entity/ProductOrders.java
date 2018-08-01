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
import javax.persistence.OneToOne;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class ProductOrders implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Reference;
	private String invoice_num;
	private String vendor;
	private int user_id;
	@NotBlank
	private String payment_type;
	private Date order_date;
	private Date process_date;
	private Date delivery_date;
	private double total_price;
	private String status; //O = Ordered, D = Delivered, C = Canceled, X = Dispute

	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "productOrders", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItems = new HashSet<OrderItems>(0);

	@OneToOne
	(fetch=FetchType.LAZY, mappedBy = "productOrders", cascade = CascadeType.ALL)
	private Payables payables;

	public Long getReference() {
		return Reference;
	}

	public void setReference(Long reference) {
		Reference = reference;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public Date getProcess_date() {
		return process_date;
	}

	public void setProcess_date(Date process_date) {
		this.process_date = process_date;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public Set<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}

	public Payables getPayables() {
		return payables;
	}

	public void setPayables(Payables payables) {
		this.payables = payables;
	}

	
}
