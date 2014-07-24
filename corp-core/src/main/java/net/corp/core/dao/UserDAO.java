package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.Users;

public interface UserDAO extends GenericDAO<Users, Integer> {
	/**
	 * API to find User by username
	 * 
	 * @param username - Login username
	 * @param active - Option to bring Active/Inactive users selectively
	 * @return User object
	 */
	Users findByUsername(String username, boolean active);
	
	/**
	 * API to find User by full registered name
	 * @param fullname - Full Name of User
	 * @param active - Option to bring Active/Inactive users selectively
	 * @param guest - Option to bring Guest/Regular users selectively
	 * @return List of Users
	 */
	List<Users> findByName(String fullname, boolean active, boolean guest);
	
	/**
	 * API to find User by First Name and/or Last Name
	 * @param firstName - First Name of User
	 * @param lastName - Last Name of User
	 * @param active - Option to bring Active/Inactive users selectively
	 * @param guest - Option to bring Guest/Regular users selectively
	 * @return List of Users
	 */
	List<Users> findByFirstLastName(String firstName, String lastName, boolean active, boolean guest);
	
	/**
	 * API to find root user
	 * @param active - Option to bring Active/Inactive users selectively
	 * @return Root User object
	 */
	List<Users> findRootUser(boolean active); 
}
