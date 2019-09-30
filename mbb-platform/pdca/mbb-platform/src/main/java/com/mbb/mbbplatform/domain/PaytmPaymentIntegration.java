package com.mbb.mbbplatform.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "paytmpaymentintegration")
public class PaytmPaymentIntegration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type;
	private String description;
	private String receipt;
	private Long amount;
	private Long expireBy;
	private boolean emailNotify;
	private boolean smsNotify;
	private String	currency;
	private String	shortUrl;
	private String name;
	private String email;
	private Long phone;
	private String orderId;
	private String customerId;
	private String status;

	@ManyToOne
	@JoinColumn(name = "customerDetailsId")
	private CustomerDetails customerDetailsId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getExpireBy() {
		return expireBy;
	}
	public void setExpireBy(Long expireBy) {
		this.expireBy = expireBy;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isEmailNotify() {
		return emailNotify;
	}
	public void setEmailNotify(boolean emailNotify) {
		this.emailNotify = emailNotify;
	}
	public boolean isSmsNotify() {
		return smsNotify;
	}
	public void setSmsNotify(boolean smsNotify) {
		this.smsNotify = smsNotify;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public CustomerDetails getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(CustomerDetails customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}

}
