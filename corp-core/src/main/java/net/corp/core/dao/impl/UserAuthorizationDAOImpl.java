package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.UserAuthorizationDAO;
import net.corp.core.model.MapUserAuthorization;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
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

	@Override
	public void deleteByUser(Integer userId) {
		SQLQuery query = getSession().createSQLQuery("delete from mp_user_authorization where USER_ID = " + userId);
		query.executeUpdate();
	}

}
