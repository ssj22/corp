package net.corp.core.model;

// Generated 21 May, 2014 12:30:42 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * DNotifications generated by hbm2java
 */
@SuppressWarnings("serial")
public class Notifications implements java.io.Serializable {

	private Integer id;
	private String fname;
	private String mname;
	private String lname;
	private String mobileNo;
	private Date dob;
	private Integer usrid;
	private Boolean inword;

	public Notifications() {
	}

	public Notifications(String fname, String mname, String lname,
			String mobileNo, Date dob, Integer usrid, Boolean inword) {
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.usrid = usrid;
		this.inword = inword;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return this.mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getUsrid() {
		return this.usrid;
	}

	public void setUsrid(Integer usrid) {
		this.usrid = usrid;
	}

	public Boolean getInword() {
		return this.inword;
	}

	public void setInword(Boolean inword) {
		this.inword = inword;
	}

}