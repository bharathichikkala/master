package com.mbb.mbbplatform.domain;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zeposrcodremittance")
public class ZepoSRCodRemittance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String crfid;

	private String utr;

	private String codpayable;

	private String status;

	private String remarks;

	private Double remittedValue;

	private String accountType;

	private String createdAt;

	private String rechargeValue;

	private String reversalValue;

	private String shippingAggregator;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	

	

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrfid() {
		return crfid;
	}
	public Long getId() {
		return id;
	}
	public void setCrfid(String crfid) {
		this.crfid = crfid;
	}

	

	public void setUtr(String utr) {
		this.utr = utr;
	}

	public String getCodpayable() {
		return codpayable;
	}
	public String getUtr() {
		return utr;
	}
	public void setCodpayable(String codpayable) {
		this.codpayable = codpayable;
	}

	

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public void setRemittedValue(Double remittedValue) {
		this.remittedValue = remittedValue;
	}

	public String getAccountType() {
		return accountType;
	}
	public Double getRemittedValue() {
		return remittedValue;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getRechargeValue() {
		return rechargeValue;
	}
	public String getCreatedAt() {
		return createdAt;
	}

	public void setRechargeValue(String rechargeValue) {
		this.rechargeValue = rechargeValue;
	}


	public void setReversalValue(String reversalValue) {
		this.reversalValue = reversalValue;
	}

	public String getShippingAggregator() {
		return shippingAggregator;
	}
	public String getReversalValue() {
		return reversalValue;
	}

	public void setShippingAggregator(String shippingAggregator) {
		this.shippingAggregator = shippingAggregator;
	}

	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	

}

