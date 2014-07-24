package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.RolePrivilegeDAO;
import net.corp.core.model.MapRolePrivileges;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class RolePrivilegeDAOImpl extends GenericDAOImpl<MapRolePrivileges, Integer> implements RolePrivilegeDAO {

	/*
	 * (non-Javadoc)
	 * @see net.corp.core.dao.RolePrivilegeDAO#findPrivilegesByRole(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MapRolePrivileges> findPrivilegesByRole(int roleId) {
		Criteria crit = getSession().createCriteria(MapRolePrivileges.class);
		crit.add(Restrictions.eq("role.roleId", roleId));
		
		return crit.list();
	}

}
