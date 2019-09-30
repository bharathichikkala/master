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
@Table(name = "rentalproducts")
public class RentalProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDate orderDate;
	
	private LocalDate rentalStartDate;
	
	private LocalDate rentalEndDate;



	private String rentalId;

	private String deliveredBy;

	private String invoiceNumber;

	@CreationTimestamp
	private ZonedDateTime createdTime;

	@UpdateTimestamp
	private ZonedDateTime updatedTime;

	private Long rentalDays;

	private Double rentalAmount;

	private String skucode;

	private Long extension;

	private String productName;
	@ManyToOne
	@JoinColumn(name = "dispatchStatusId")
	private DispatchStatus dispatchStatusId;

	@Size(max = 1000, min = 0)
	private String comments;

	@Size(max = 1000, min = 0)
	private String convertedComments;

	private LocalDate requestedDate;

	private String barCode;
	@ManyToOne
	@JoinColumn(name = "customerDetailsId")
	private CustomerDetails customerDetailsId;

	@ManyToOne
	@JoinColumn(name = "rentalServiceType")
	private RentalServiceTypes rentalServiceType;

	@ManyToOne
	@JoinColumn(name = "dispatchId")
	private Dispatch dispatchId;
	
	private String dispatchInvoiceId;

	private Double depositAmount;
	
	private String doctorDetails;

	public String getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public String getDispatchInvoiceId() {
		return dispatchInvoiceId;
	}

	public void setDispatchInvoiceId(String dispatchInvoiceId) {
		this.dispatchInvoiceId = dispatchInvoiceId;
	}

		public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getDoctorDetails() {
		return doctorDetails;
	}

	public void setDoctorDetails(String doctorDetails) {
		this.doctorDetails = doctorDetails;
	}

	public LocalDate getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(LocalDate rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}

	public LocalDate getRentalEndDate() {
		return rentalEndDate;
	}

	public void setRentalEndDate(LocalDate rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}

	public Long getExtension() {
		return extension;
	}

	public void setExtension(Long extension) {
		this.extension = extension;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
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

	public String getRentalId() {
		return rentalId;
	}

	public RentalServiceTypes getRentalServiceType() {
		return rentalServiceType;
	}

	public void setRentalServiceType(RentalServiceTypes rentalServiceType) {
		this.rentalServiceType = rentalServiceType;
	}

	public void setRentalId(String rentalId) {
		this.rentalId = rentalId;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Long getRentalDays() {
		return rentalDays;
	}

	public void setRentalDays(Long rentalDays) {
		this.rentalDays = rentalDays;
	}

	public Double getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(Double rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public String getSkucode() {
		return skucode;
	}

	public void setSkucode(String skucode) {
		this.skucode = skucode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public DispatchStatus getDispatchStatusId() {
		return dispatchStatusId;
	}

	public void setDispatchStatusId(DispatchStatus dispatchStatusId) {
		this.dispatchStatusId = dispatchStatusId;
	}

	public String getConvertedComments() {
		return convertedComments;
	}

	public void setConvertedComments(String convertedComments) {
		this.convertedComments = convertedComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public CustomerDetails getCustomerDetailsId() {
		return customerDetailsId;
	}

	public void setCustomerDetailsId(CustomerDetails customerDetailsId) {
		this.customerDetailsId = customerDetailsId;
	}

	public Dispatch getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Dispatch dispatchId) {
		this.dispatchId = dispatchId;
	}

}
