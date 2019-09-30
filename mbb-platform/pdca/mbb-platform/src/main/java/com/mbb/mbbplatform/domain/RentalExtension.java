package com.mbb.mbbplatform.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rentalextension")
public class RentalExtension {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDate extendedDate ;
	
	private LocalDate extensionEndDate ;

	
	private String invoiceNumber ;
	
	private String comments ;


	private Long days ;

	private Double price ;
	
	@ManyToOne
	@JoinColumn(name = "rentalProductId")
	private RentalProducts rentalProductId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getExtendedDate() {
		return extendedDate;
	}

	public LocalDate getExtensionEndDate() {
		return extensionEndDate;
	}

	public void setExtensionEndDate(LocalDate extensionEndDate) {
		this.extensionEndDate = extensionEndDate;
	}

	public void setExtendedDate(LocalDate extendedDate) {
		this.extendedDate = extendedDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public RentalProducts getRentalProductId() {
		return rentalProductId;
	}

	public void setRentalProductId(RentalProducts rentalProductId) {
		this.rentalProductId = rentalProductId;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
