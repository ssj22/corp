package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.UserPreference;

public interface UserPreferenceDAO extends GenericDAO<UserPreference, Integer> {
	
	List<UserPreference> findPreferencesByUser(int userId);
}
