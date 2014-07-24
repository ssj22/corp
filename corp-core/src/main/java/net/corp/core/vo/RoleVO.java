package net.corp.core.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class RoleVO implements Serializable {
	private Integer roleId;
	private Integer parentRoleId;
	private String roleType;
	private boolean active;
	private String roleName;
	
	private List<PrivilegeVO> privileges;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<PrivilegeVO> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<PrivilegeVO> privileges) {
		this.privileges = privileges;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getParentRoleId() {
		return parentRoleId;
	}
	public void setParentRoleId(Integer parentRoleId) {
		this.parentRoleId = parentRoleId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
