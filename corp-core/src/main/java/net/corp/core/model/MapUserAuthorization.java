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

@SuppressWarnings("serial")
@Entity
@Table(name = "mp_user_authorization")
public class MapUserAuthorization implements Serializable {
	
	@Id
	@Column(name="USER_AUTHORIZATION_ID")
	@GeneratedValue
	private Integer userAuthorizationId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private Users user;
	
	@Column(name="AUTHORIZATION_ID")
	private Integer authorizationId;
	
	@Column(name="ROLE")
	private boolean role;
	
	@Column(name = "CREATED_BY")
    private int createdBy;
	
	@Column(name = "CREATED_DATE")
    private Timestamp createdDate;

	@Column(name = "UPDATED_BY")
    private int updatedBy;
	
	@Column(name = "UPDATED_DATE")
    private Timestamp updatedDate;

	/**
	 * @return the userAuthorizationId
	 */
	public Integer getUserAuthorizationId() {
		return userAuthorizationId;
	}

	/**
	 * @param userAuthorizationId the userAuthorizationId to set
	 */
	public void setUserAuthorizationId(Integer userAuthorizationId) {
		this.userAuthorizationId = userAuthorizationId;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the authorizationId
	 */
	public Integer getAuthorizationId() {
		return authorizationId;
	}

	/**
	 * @param authorizationId the authorizationId to set
	 */
	public void setAuthorizationId(Integer authorizationId) {
		this.authorizationId = authorizationId;
	}

	/**
	 * @return the role
	 */
	public boolean isRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(boolean role) {
		this.role = role;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
