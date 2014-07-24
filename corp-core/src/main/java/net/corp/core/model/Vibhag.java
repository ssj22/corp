package net.corp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

// Generated 21 May, 2014 12:30:42 PM by Hibernate Tools 4.0.0


@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="md_vibhag")
public class Vibhag implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "VIBHAG_ID")
	private Integer vibhagId;
	@Column(name = "VIBHAG_NAME")
	private String vibhagName;
	@Column(name = "CONTACT_PERSON")
	private String contactPerson;
	@Column(name = "ADDRESS_ID")
	private String addressId;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "VIBHAG_TYPE_ID")
	private Integer vibhagTypeId;
	@Column(name = "CREATED_DATE")
	private Integer createdDate;
	@Column(name = "CREATED_BY")
	private Integer createdBy;
	@Column(name = "PHONE_EXT")
	private Integer phoneExt;
	@Column(name = "PHONE_AREA_CD")
	private Integer phoneAreaCd;

	public Vibhag() {
	}

	public Vibhag(String phone) {
		this.phone = phone;
	}

	public Vibhag(String vibhagName, String contactPerson, String addressId,
			String phone, Integer vibhagTypeId, Integer createdDate,
			Integer createdBy, Integer phoneExt, Integer phoneAreaCd) {
		this.vibhagName = vibhagName;
		this.contactPerson = contactPerson;
		this.addressId = addressId;
		this.phone = phone;
		this.vibhagTypeId = vibhagTypeId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.phoneExt = phoneExt;
		this.phoneAreaCd = phoneAreaCd;
	}

	public Integer getVibhagId() {
		return this.vibhagId;
	}

	public void setVibhagId(Integer vibhagId) {
		this.vibhagId = vibhagId;
	}

	public String getVibhagName() {
		return this.vibhagName;
	}

	public void setVibhagName(String vibhagName) {
		this.vibhagName = vibhagName;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAddressId() {
		return this.addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getVibhagTypeId() {
		return this.vibhagTypeId;
	}

	public void setVibhagTypeId(Integer vibhagTypeId) {
		this.vibhagTypeId = vibhagTypeId;
	}

	public Integer getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Integer createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getPhoneExt() {
		return this.phoneExt;
	}

	public void setPhoneExt(Integer phoneExt) {
		this.phoneExt = phoneExt;
	}

	public Integer getPhoneAreaCd() {
		return this.phoneAreaCd;
	}

	public void setPhoneAreaCd(Integer phoneAreaCd) {
		this.phoneAreaCd = phoneAreaCd;
	}

}
