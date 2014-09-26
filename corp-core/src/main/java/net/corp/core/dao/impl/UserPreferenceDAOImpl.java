package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.UserPreferenceDAO;
import net.corp.core.model.UserPreference;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UserPreferenceDAOImpl extends GenericDAOImpl<UserPreference, Integer> implements UserPreferenceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserPreference> findPreferencesByUser(int userId) {
		Criteria crit = getSession().createCriteria(UserPreference.class);
		crit.add(Restrictions.eq("userId", userId));
		
		return crit.list();
	}

}
