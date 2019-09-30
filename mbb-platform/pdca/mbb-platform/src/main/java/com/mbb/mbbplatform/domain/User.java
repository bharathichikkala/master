package com.mbb.mbbplatform.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("deprecation")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@JsonIgnore
	private String password;
	
	@Email
	private String email;
	
	private String phone;
	
	private boolean active;

	private boolean notificationStatus;

//	@ManyToOne
//	@JoinColumn(name = "facilityId")
//	private Facility facilityId;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "userfacility", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "facility_id"))
	private Set<Facility> facilities;

	public Long getId() {
		return id;
	}

	public boolean isActive() {
		return active;
	}

	public Set<Facility> getFacilities() {
		return facilities;
	}

	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public boolean isNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(boolean notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public Facility getFacilityId() {
//		return facilityId;
//	}
//
//	public void setFacilityId(Facility facilityId) {
//		this.facilityId = facilityId;
//	}

}
