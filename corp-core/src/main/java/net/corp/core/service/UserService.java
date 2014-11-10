package net.corp.core.service;

import java.util.List;
import java.util.Map;

import net.corp.core.exception.CorpException;
import net.corp.core.vo.AddressVO;
import net.corp.core.vo.TabsVO;
import net.corp.core.vo.UserPreferenceVO;
import net.corp.core.vo.UserVO;

public interface UserService {
	/**
	 * API to fetch Active Users by UserName
	 * @param username
	 * @return User object
	 */
	UserVO fetchActiveUserByUsername(String username) throws CorpException;
	
	/**
	 * API to persist User
	 * @param userVo - Value object for User
	 * @return true if Save successful, false otherwise
	 */
	boolean saveUser(UserVO userVo) throws CorpException;
	
	/**
	 * API to fetch Address by Id
	 * @param addressId - primary key
	 * @return Address object
	 * @throws CorpException
	 */
	AddressVO fetchAddressById(Integer addressId) throws CorpException;
	
	/**
	 * Fetch a list of available Tabs
	 * @return
	 * @throws CorpException
	 */
	List<TabsVO> fetchAllTabs() throws CorpException;
	
	UserPreferenceVO fetchUserPreference(Integer userId);
	
	boolean saveUserPreference(UserPreferenceVO prefVo);

	List<UserVO> fetchActiveUsers();
	
	Map<String, UserVO> fetchActiveUserNames();

	Integer saveUser(List<UserVO> users);

	Map<String, String> fetchAllPrivileges(Integer userId);

	Integer saveUserPermission(UserVO user, Integer userId);
}
