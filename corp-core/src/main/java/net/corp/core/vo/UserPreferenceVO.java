package net.corp.core.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserPreferenceVO implements Serializable {
	private Integer userId;
	private Integer materialEntryTimeSel;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMaterialEntryTimeSel() {
		return materialEntryTimeSel;
	}
	public void setMaterialEntryTimeSel(Integer materialEntryTimeSel) {
		this.materialEntryTimeSel = materialEntryTimeSel;
	}
	
}
