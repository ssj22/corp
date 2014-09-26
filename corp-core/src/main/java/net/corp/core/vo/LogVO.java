package net.corp.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@SuppressWarnings("serial")
public class LogVO implements Serializable {
	private Integer logId;
	private String msg;
	private String vehicleNumber;
	private String transportName;
	private String vibhagName;
	private String vibhagPhone;
	private String siteName;
	private Timestamp gateInTime;
	private Timestamp gateOutTime;
	private boolean nightShift;
	private boolean valid;
	private boolean newEntry;
	private Timestamp updateDate;
	private List<LogMaterialVO> logMaterials;
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
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	public String getVibhagName() {
		return vibhagName;
	}
	public void setVibhagName(String vibhagName) {
		this.vibhagName = vibhagName;
	}
	public String getVibhagPhone() {
		return vibhagPhone;
	}
	public void setVibhagPhone(String vibhagPhone) {
		this.vibhagPhone = vibhagPhone;
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
	public List<LogMaterialVO> getLogMaterials() {
		return logMaterials;
	}
	public void setLogMaterials(List<LogMaterialVO> logMaterials) {
		this.logMaterials = logMaterials;
	}
	
}
