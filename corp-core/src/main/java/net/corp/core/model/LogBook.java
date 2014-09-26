package net.corp.core.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="d_log_book")
public class LogBook implements Serializable {
	
	@Id
	@Column(name = "LOG_ID")
	@GeneratedValue
	private Integer logId;
	
	@Column(name = "RAW_MSG")
	private String msg;
	
	@JoinColumn(name = "VEHICLE_ID")
	@ManyToOne
	private Vehicles vehicle;
	
	@JoinColumn(name = "TRANSPORTER_ID")
	@ManyToOne
	private PrimaryGroup transport;
	
	@JoinColumn(name = "VIBHAG_ID")
	@ManyToOne
	private Vibhag vibhag;
	
	@Column(name = "SITE_NAME")
	private String siteName;
	
	@Column(name = "GATE_IN_TIME")
	private Timestamp gateInTime;
	
	@Column(name = "GATE_OUT_TIME")
	private Timestamp gateOutTime;
	
	@Column(name = "NIGHT_SHIFT")
	private boolean nightShift;
	
	@Column(name = "VALID")
	private boolean valid;
	
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
	
	@Column(name = "NEW_ENTRY")
	private boolean newEntry;
	
	@Column(name = "PHONE")
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isNewEntry() {
		return newEntry;
	}

	public void setNewEntry(boolean newEntry) {
		this.newEntry = newEntry;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Vehicles getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicles vehicle) {
		this.vehicle = vehicle;
	}

	public PrimaryGroup getTransport() {
		return transport;
	}

	public void setTransport(PrimaryGroup transport) {
		this.transport = transport;
	}

	public Vibhag getVibhag() {
		return vibhag;
	}

	public void setVibhag(Vibhag vibhag) {
		this.vibhag = vibhag;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Timestamp getGateInTime() {
		return gateInTime;
	}

	public void setGateInTime(Timestamp gateInTime) {
		this.gateInTime = gateInTime;
	}

	public Timestamp getGateOutTime() {
		return gateOutTime;
	}

	public void setGateOutTime(Timestamp gateOutTime) {
		this.gateOutTime = gateOutTime;
	}

	public boolean isNightShift() {
		return nightShift;
	}

	public void setNightShift(boolean nightShift) {
		this.nightShift = nightShift;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
}
