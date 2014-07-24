package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.UserAuthorizationDAO;
import net.corp.core.model.MapUserAuthorization;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UserAuthorizationDAOImpl extends GenericDAOImpl<MapUserAuthorization, Integer> implements UserAuthorizationDAO {

	/*
	 * (non-Javadoc)
	 * @see net.corp.core.dao.UserAuthorizationDAO#findAuthorizationByUser(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MapUserAuthorization> findAuthorizationByUser(int userId) {
		Criteria crit = getSession().createCriteria(MapUserAuthorization.class);
		crit.add(Restrictions.eq("user.userId", userId));
		
		return crit.list();
	}

}
