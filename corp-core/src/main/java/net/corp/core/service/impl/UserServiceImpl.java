package net.corp.core.service.impl;

import net.corp.core.dao.AddressDAO;
import net.corp.core.dao.PrivilegeDAO;
import net.corp.core.dao.UserAuthorizationDAO;
import net.corp.core.dao.UserDAO;
import net.corp.core.exception.CorpException;
import net.corp.core.model.*;
import net.corp.core.service.MessageService;
import net.corp.core.service.UserService;
import net.corp.core.service.helper.CoreServiceHelper;
import net.corp.core.util.EncryptionUtil;
import net.corp.core.vo.AddressVO;
import net.corp.core.vo.TabsVO;
import net.corp.core.vo.UserPreferenceVO;
import net.corp.core.vo.UserVO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;

public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	
	private CoreServiceHelper coreServiceHelper;
	
	private MessageService messageService;
	
	@Override
	public boolean saveUserPreference(UserPreferenceVO prefVo) {
		try {
			UserPreference pref = getCoreServiceHelper().convertVOToModel(prefVo);
			if (pref == null) {
				return false;
			}
			else {
				getCoreServiceHelper().getUserPreferenceDao().saveOrUpdate(pref);
			}
		}
		catch(Exception e) {
			LOGGER.error("Exception while saving user preference for user " + prefVo.getUserId() + " : " + e.getMessage(), e);
		}
		return false;
	}

	@Override
	public UserPreferenceVO fetchUserPreference(Integer userId) {
		List<UserPreference> preferences = getCoreServiceHelper().getUserPreferenceDao().findPreferencesByUser(userId);
		if (preferences != null && !preferences.isEmpty()) {
			UserPreference pref = preferences.get(0);
			UserPreferenceVO prefVo = new UserPreferenceVO();
			prefVo.setUserId(pref.getUserId());
			prefVo.setMaterialEntryTimeSel(pref.getMaterialViewTimeSel());
			return prefVo;
		}
		return null;
	}

	@Override
	public List<TabsVO> fetchAllTabs() throws CorpException {
		return getCoreServiceHelper().getAllTabs();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.corp.core.service.UserService#fetchAddressById(java.lang.Integer)
	 */
	@Override
	public AddressVO fetchAddressById(Integer addressId) throws CorpException {
		AddressVO addressVo = null;
		try {
			Address address = getAddressDao().getById(addressId);
			addressVo = getCoreServiceHelper().convertModelToVO(address);
		} catch (Exception e) {
			String exStr = "Exception while retrieving Address by ID";
			LOGGER.error(exStr, e);
			throw new CorpException(exStr, e);
		}
		return addressVo;
	}

	@Override
	public Map<String, String> fetchAllPrivileges(Integer userId) {
		Map<String, String> privMap = new LinkedHashMap<String, String>();
		
		if (userId != null) {
			List<MapUserAuthorization> userAuthorizations = getUserAuthorizationDao().findAuthorizationByUser(userId);
			if (userAuthorizations != null && !userAuthorizations.isEmpty()) {
				for (MapUserAuthorization userAuth: userAuthorizations) {
					privMap.put(userAuth.getPrivilege().getPrivilegeName(), userAuth.getPrivilege().getPrivilegeName());
				}
			}
		}
		else {
			List<Privileges> privs = getCoreServiceHelper().getPrivilegeDao().findAll();
			if (privs != null && !privs.isEmpty()) {
				for (Privileges priv: privs) {
					privMap.put(priv.getPrivilegeName(), priv.getPrivilegeName());
				}
			}
		}
		
		return privMap;
	}

	@Override
	public Map<String, UserVO> fetchActiveUserNames() {
		Map<String, UserVO> userMap = new LinkedHashMap<String, UserVO>();
		List<UserVO> userVos = fetchActiveUsers();
		if (userVos != null && !userVos.isEmpty()) {
			for (UserVO userVo: userVos) {
				userMap.put(userVo.getFirstName() + " " + userVo.getLastName(), userVo);
			}
		}
		return userMap;
	}

	/*
	 * (non-Javadoc)
	 * @see net.corp.core.service.UserService#fetchActiveUsers()
	 */
	@Override
	public List<UserVO> fetchActiveUsers() {
		List<UserVO> users = null;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("active", true);
		List<Users> userList = getUserDao().findByCriteria(map);
		if (userList != null &&  !userList.isEmpty()) {
			users = new ArrayList<UserVO>();
			for (Users user: userList) {
				UserVO userVo = getCoreServiceHelper().convertModelToVO(user);
				Map<String, String> privileges = new LinkedHashMap<String, String>();
				List<MapUserAuthorization> userAuthorizations = getUserAuthorizationDao().findAuthorizationByUser(userVo.getUserId());
				if (userAuthorizations != null && !userAuthorizations.isEmpty()) {
					for (MapUserAuthorization userAuth: userAuthorizations) {
						privileges.put(userAuth.getPrivilege().getPrivilegeName(), userAuth.getPrivilege().getPrivilegeName());
					}
				}
				userVo.setPrivileges(privileges);
				users.add(userVo);
			}
		}
		return users;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.corp.core.service.UserService#fetchActiveUserByUsername(java.lang.String)
	 */
	@Override
	public UserVO fetchActiveUserByUsername(String username)
			throws CorpException {
		UserVO userVo = null;
		try {
			Users user = getUserDao().findByUsername(username, true);
			userVo = getCoreServiceHelper().convertModelToVO(user);
			
			if (userVo != null) {
				Map<String, String> privileges = new LinkedHashMap<String, String>();
				List<MapUserAuthorization> userAuthorizations = getUserAuthorizationDao().findAuthorizationByUser(userVo.getUserId());
				if (userAuthorizations != null && !userAuthorizations.isEmpty()) {
					for (MapUserAuthorization userAuth: userAuthorizations) {
						privileges.put(userAuth.getPrivilege().getPrivilegeName(), userAuth.getPrivilege().getPrivilegeName());
					}
				}
				userVo.setPrivileges(privileges);
				userVo.setAuthMachine(getCoreServiceHelper().isAuthMachine());
			}	
		} catch (Exception e) {
			String exStr = "Exception while retrieving User by username";
			LOGGER.error(exStr, e);
			//TODO: Populate Error Code and Type in CorpException
			throw new CorpException(exStr, e);
		}
		return userVo;
	}

	@Override
	public Integer saveUser(List<UserVO> users) {
		int saveCount = 0;
		if (users != null && !users.isEmpty()) {
			try {
				for (UserVO userVo: users) {
					saveUser(userVo);
					saveCount++;
				}
			} catch(Exception e) {
				String exStr = "Exception while saving Multipel Users";
				LOGGER.error(exStr, e);
			}
		}
		return saveCount;
	}

	@Override
	public Integer saveUserPermission(UserVO user, Integer userId) {
		int count = 0;
		try {
			getCoreServiceHelper().getUserAuthorizationDao().deleteByUser(user.getUserId());
			
			for (String key: user.getPrivileges().keySet()) {
				MapUserAuthorization entity = new MapUserAuthorization();
				entity.setUser(getCoreServiceHelper().convertVOToModel(user));
				Map<String, String> crit = new HashMap<String, String>();
				crit.put("privilegeName", key);
				List<Privileges> priv = getCoreServiceHelper().getPrivilegeDao().findByCriteria(crit);
				entity.setPrivilege(priv.get(0));
				entity.setCreatedBy(userId);
				entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setUpdatedBy(userId);
				entity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				getCoreServiceHelper().getUserAuthorizationDao().saveOrUpdate(entity);
				count++;
			}
			
		} catch (Exception e) {
			String exStr = "Exception while saving User Permission";
			LOGGER.error(exStr, e);
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see net.corp.core.service.UserService#saveUser(net.corp.core.vo.UserVO)
	 */
	@Override
	public boolean saveUser(UserVO userVo) throws CorpException {
		boolean rtnVal = false;
		try {
			Users user = getCoreServiceHelper().convertVOToModel(userVo);
			if (user.getAddress() != null) {
				getAddressDao().saveOrUpdate(user.getAddress());
			}
			String rawPass = userVo.getPassword();
			if (rawPass == null || rawPass.isEmpty()){
				rawPass = RandomStringUtils.randomAlphanumeric(8);
				LOGGER.info("Password for user " + userVo.getFirstName() + " " + userVo.getLastName() + " is set to : " + rawPass);
			}

			String encPass = EncryptionUtil.encodePassword(rawPass);
			user.getUserLogin().setPassword(encPass);
			user.getUserLogin().setFirstLogin(true);
			user.setActive(true);
			user.getUserLogin().setUsername(user.getPhone().toString());
			getUserDao().saveOrUpdate(user);
			
			getMessageService().sendMessage(String.valueOf(user.getPhone()), "Your password for Hotmix login is " + rawPass + " and your username is the registered phone number");
			
			userVo.setUserId(user.getUserId());
			rtnVal = true;
		} catch (Exception e) {
			String exStr = "Exception while saving User";
			LOGGER.error(exStr, e);
			//TODO: Populate Error Code and Type in CorpException
			throw new CorpException(exStr, e);
		}
		return rtnVal;
	}

	public CoreServiceHelper getCoreServiceHelper() {
		return coreServiceHelper;
	}
	public void setCoreServiceHelper(CoreServiceHelper coreServiceHelper) {
		this.coreServiceHelper = coreServiceHelper;
	}
	
	public UserDAO getUserDao() {
		return coreServiceHelper.getUserDao();
	}
	public AddressDAO getAddressDao() {
		return coreServiceHelper.getAddressDao();
	}
	public UserAuthorizationDAO getUserAuthorizationDao() {
		return coreServiceHelper.getUserAuthorizationDao();
	}
	public PrivilegeDAO getPrivilegeDao() {
		return coreServiceHelper.getPrivilegeDao();
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

}
