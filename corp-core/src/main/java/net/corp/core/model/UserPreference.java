package net.corp.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table (name = "d_user_preference")
public class UserPreference implements Serializable {
	
	@Id
	@Column(name = "PREFERENCE_ID")
	@GeneratedValue
	private Integer preferenceId;
	
	@Column(name = "USER_ID")
    private Integer userId;
	
	@Column(name = "MATERIAL_VIEW_TIME_SEL")
    private Integer materialViewTimeSel;

	public Integer getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(Integer preferenceId) {
		this.preferenceId = preferenceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMaterialViewTimeSel() {
		return materialViewTimeSel;
	}

	public void setMaterialViewTimeSel(Integer materialViewTimeSel) {
		this.materialViewTimeSel = materialViewTimeSel;
	}
	
}
