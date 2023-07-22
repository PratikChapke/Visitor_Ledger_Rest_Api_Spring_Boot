package com.visitorledger.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "please enter your name")
	private String name;

	@Email
	private String email;

	@NotNull(message = "please enter your age")
	@Min(value = 0)
	@Max(value = 100)
	private int age;

	private String gender;

	@NotNull(message = "Mobile number is required")
	@Size(min = 10, max = 13, message = "please enter a valid mobile number")
	private String contactNumber;

	private String reasonForMeeting;

	private String visitorOrganization;

	@ManyToOne(cascade = CascadeType.ALL)
	private Employee employee;

	private LocalTime inTime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIME)
	private LocalTime outTime;

	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private LocalDate date;

	@Transient
	private String whomToMeet;

	@Column(columnDefinition = "TEXT")
	private String imgPath;

	private int uId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getReasonForMeeting() {
		return reasonForMeeting;
	}

	public void setReasonForMeeting(String reasonForMeeting) {
		this.reasonForMeeting = reasonForMeeting;
	}

	public String getVisitorOrganization() {
		return visitorOrganization;
	}

	public void setVisitorOrganization(String visitorOrganization) {
		this.visitorOrganization = visitorOrganization;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalTime getInTime() {
		return inTime;
	}

	@PrePersist
	public void prePersist() {
		if (inTime == null) {
			inTime = LocalTime.now();
		}
	}

	public String getWhomToMeet() {
		return whomToMeet;
	}

	public void setWhomToMeet(String whomToMeet) {
		this.whomToMeet = whomToMeet;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setInTime(LocalTime inTime) {
		this.inTime = inTime;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public LocalTime getOutTime() {
		return outTime;
	}

	public void setOutTime(LocalTime outTime) {
		this.outTime = outTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Visitor [employee=" + employee + "]";
	}
}
