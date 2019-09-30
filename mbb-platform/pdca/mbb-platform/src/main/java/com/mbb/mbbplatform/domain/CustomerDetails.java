package com.mbb.mbbplatform.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customerdetails")
public class CustomerDetails {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

private String name;

private String emailId;

private Long phone;
private Long alternatePhoneNo;

private String city;
private String state;
private String pincode;
@Size(max=1000,min=0)	
private String address;
@Size(max=1000,min=0)	
private String landmark;

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public Long getAlternatePhoneNo() {
	return alternatePhoneNo;
}

public void setAlternatePhoneNo(Long alternatePhoneNo) {
	this.alternatePhoneNo = alternatePhoneNo;
}

public String getPincode() {
	return pincode;
}

public void setPincode(String pincode) {
	this.pincode = pincode;
}


public String getLandmark() {
	return landmark;
}

public void setLandmark(String landmark) {
	this.landmark = landmark;
}

public Long getId() {
return id;
}

public void setId(Long id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public Long getPhone() {
return phone;
}

public void setPhone(Long phone) {
this.phone = phone;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}


}
