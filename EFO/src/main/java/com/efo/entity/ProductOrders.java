package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;


@Entity
public class ProductOrders implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String sku;
	private String product_name;
	private String invoice_num;
	private String vendor;
	
	@DecimalMin("0.01")
	private double wholesale;
	private double amt_ordered;
	private double amt_received;
	@Transient
	private double amt_this_shipment;
	private String payment_type;
	private Date order_date;
	private Date delivery_date;
	private String status; //O = Ordered, D = Delivered, C = Canceled, X = Dispute
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SKU", nullable = false, insertable=false, updatable=false )
	private Product product;
	
	
	public ProductOrders() {
	}
	
	public ProductOrders(String sku, Date order_date, String product_name) {
		this.sku = sku;
		this.order_date = order_date;
		this.product_name = product_name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}	
}