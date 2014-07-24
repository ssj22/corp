package net.corp.core.model;

// Generated 21 May, 2014 12:30:42 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * MdSections generated by hbm2java
 */
public class Sections implements java.io.Serializable {

	private Integer sectionId;
	private String sectionName;
	private String sectionType;
	private boolean active;
	private int createdBy;
	private Date createdDate;

	public Sections() {
	}

	public Sections(String sectionName, String sectionType, boolean active,
			int createdBy, Date createdDate) {
		this.sectionName = sectionName;
		this.sectionType = sectionType;
		this.active = active;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public Integer getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionType() {
		return this.sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
