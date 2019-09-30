package com.mbb.mbbplatform.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "demoproducts")
public class DemoProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String demoId;
	private String skuCode;
	private String productName;
	private Double demoPrice;
	private LocalDate orderDate;
	@Size(max=1000,min=0)	
	private String comments;
	@Size(max=1000,min=0)	
	private String rejectionComments;
	private String unicommerceReferenceNumber;

	private String qrCode;
	@CreationTimestamp
	private ZonedDateTime createdTime;
	
	@UpdateTimestamp
	private ZonedDateTime updatedTime;
	@ManyToOne
	@JoinColumn(name = "dispatchId")
	private Dispatch dispatchId;

	@ManyToOne
	@JoinColumn(name = "dispatcstatusId")
	private DispatchStatus dispatcstatusId;
	public DispatchStatus getDispatcstatusId() {
		return dispatcstatusId;
	}
	public void setDispatcstatusId(DispatchStatus dispatcstatusId) {
		this.dispatcstatusId = dispatcstatusId;
	}
	@ManyToOne
	@JoinColumn(name = "customerDetailsId")
	private CustomerDetails customerDetailsId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnicommerceReferenceNumber() {
		return unicommerceReferenceNumber;
	}
	public void setUnicommerceReferenceNumber(String unicommerceReferenceNumber) {
		this.unicommerceReferenceNumber = unicommerceReferenceNumber;
	}
	public String getRejectionComments() {
		return rejectionComments;
	}
	public void setRejectionComments(String rejectionComments) {
		this.rejectionComments = rejectionComments;
	}
	public ZonedDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(ZonedDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public ZonedDateTime getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(ZonedDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getDemoId() {
		return demoId;
	}
	public void setDemoId(String demoId) {
		this.demoId = demoId;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getDemoPrice() {
		return demoPrice;
	}
	public void setDemoPrice(Double demoPrice) {
		this.demoPrice = demoPrice;
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Dispatch getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(Dispatch dispatchId) {
		this.dispatchId = dispatchId;
	}
	public CustomerDetails getCustomerDetailsId() {
		return customerDetailsId;
	}
	public void setCustomerDetailsId(CustomerDetails customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}
	
}
