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
@Table(name = "mp_role_privileges")
public class MapRolePrivileges implements Serializable {
	
	@Id
	@Column(name="ROLE_PRIVILEGE_ID")
	@GeneratedValue
	private Integer rolePrivilegeId;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private Roles role;
	
	@ManyToOne
	@JoinColumn(name="PRIVILEGE_ID")
	private Privileges privilege;
	
	@Column(name = "CREATED_BY")
    private int createdBy;
	
	@Column(name = "CREATED_DATE")
    private Timestamp createdDate;

	@Column(name = "UPDATED_BY")
    private int updatedBy;
	
	@Column(name = "UPDATED_DATE")
    private Timestamp updatedDate;

	/**
	 * @return the rolePrivilegeId
	 */
	public Integer getRolePrivilegeId() {
		return rolePrivilegeId;
	}

	/**
	 * @param rolePrivilegeId the rolePrivilegeId to set
	 */
	public void setRolePrivilegeId(Integer rolePrivilegeId) {
		this.rolePrivilegeId = rolePrivilegeId;
	}

	/**
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * @return the privilege
	 */
	public Privileges getPrivilege() {
		return privilege;
	}

	/**
	 * @param privilege the privilege to set
	 */
	public void setPrivilege(Privileges privilege) {
		this.privilege = privilege;
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
