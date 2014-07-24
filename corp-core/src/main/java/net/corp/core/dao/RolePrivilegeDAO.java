package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.MapRolePrivileges;

public interface RolePrivilegeDAO extends GenericDAO<MapRolePrivileges, Integer> {
	/**
	 * API to retrieve list of Privileges mapped to a role 
	 * @param roleId
	 * @return List of Mapping between Roles and Privileges
	 */
	List<MapRolePrivileges> findPrivilegesByRole(int roleId);
}
