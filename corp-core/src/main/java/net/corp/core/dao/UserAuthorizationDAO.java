package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.MapUserAuthorization;

public interface UserAuthorizationDAO extends GenericDAO<MapUserAuthorization, Integer> {
	/**
	 * API returns Authorization for a user
	 * @param userId
	 * @return List of User Authorization Mapping
	 */
	List<MapUserAuthorization> findAuthorizationByUser(int userId);
}
