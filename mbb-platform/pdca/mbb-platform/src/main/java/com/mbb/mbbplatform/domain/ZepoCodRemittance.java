package com.mbb.mbbplatform.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zepocodremittance")
public class ZepoCodRemittance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String ledger;

	private String trackingId;

	private String paymentReferennceNumber;

	private String courierName;

	private String totalAmount;

	private String dueDate;

	private String deliveryDate;

	private String moneyReceivedDate;

	private String moneyReceivedFromCourierCompany;

	private String notes;

	private String transferDate;

	private String status;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	

	public void setId(Long id) {
		this.id = id;
	}

	public String getLedger() {
		return ledger;
	}
	public Long getId() {
		return id;
	}
	public void setLedger(String ledger) {
		this.ledger = ledger;
	}



	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getPaymentReferennceNumber() {
		return paymentReferennceNumber;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setPaymentReferennceNumber(String paymentReferennceNumber) {
		this.paymentReferennceNumber = paymentReferennceNumber;
	}

	

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getTotalAmount() {
		return totalAmount;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	

	public void setMoneyReceivedDate(String moneyReceivedDate) {
		this.moneyReceivedDate = moneyReceivedDate;
	}

	public String getMoneyReceivedFromCourierCompany() {
		return moneyReceivedFromCourierCompany;
	}
	public String getMoneyReceivedDate() {
		return moneyReceivedDate;
	}
	public void setMoneyReceivedFromCourierCompany(String moneyReceivedFromCourierCompany) {
		this.moneyReceivedFromCourierCompany = moneyReceivedFromCourierCompany;
	}

	
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getStatus() {
		return status;
	}
	public String getTransferDate() {
		return transferDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
