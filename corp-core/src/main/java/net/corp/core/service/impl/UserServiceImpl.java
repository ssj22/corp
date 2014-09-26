package net.corp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.corp.core.dao.AddressDAO;
import net.corp.core.dao.PrivilegeDAO;
import net.corp.core.dao.RolePrivilegeDAO;
import net.corp.core.dao.UserAuthorizationDAO;
import net.corp.core.dao.UserDAO;
import net.corp.core.exception.CorpException;
import net.corp.core.model.Address;
import net.corp.core.model.MapRolePrivileges;
import net.corp.core.model.MapUserAuthorization;
import net.corp.core.model.UserPreference;
import net.corp.core.model.Users;
import net.corp.core.service.UserService;
import net.corp.core.service.helper.CoreServiceHelper;
import net.corp.core.vo.AddressVO;
import net.corp.core.vo.TabsVO;
import net.corp.core.vo.UserPreferenceVO;
import net.corp.core.vo.UserVO;

import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	
	private CoreServiceHelper coreServiceHelper;
	
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
			//TODO: Populate Error Code and Type in CorpException
			throw new CorpException(exStr, e);
		}
		return addressVo;
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
				List<String> privileges = new ArrayList<String>();
				List<MapUserAuthorization> userAuthorizations = getUserAuthorizationDao().findAuthorizationByUser(userVo.getUserId());
				if (userAuthorizations != null) {
					for (MapUserAuthorization userAuth: userAuthorizations) {
						Integer authId = userAuth.getAuthorizationId();
						if (userAuth.isRole()) {
							List<MapRolePrivileges> rolePrivileges = getRolePrivilegeDao().findPrivilegesByRole(authId);
							if (rolePrivileges != null) {
								for (MapRolePrivileges rolePriv: rolePrivileges) {
									privileges.add(rolePriv.getPrivilege().getPrivilegeName());
								}
							}
						}
						else {
							//PrivilegeVO privilegeVo = getCoreServiceHelper().convertModelToVO(getPrivilegeDao().getById(authId));
							privileges.add(getPrivilegeDao().getById(authId).getPrivilegeName());
							//privileges.add(privilegeVo);
						}
					}
				}
				userVo.setPrivileges(privileges);
				userVo.getPrivileges().add("HOME");
			}	
		} catch (Exception e) {
			String exStr = "Exception while retrieving User by username";
			LOGGER.error(exStr, e);
			//TODO: Populate Error Code and Type in CorpException
			throw new CorpException(exStr, e);
		}
		return userVo;
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
			getAddressDao().saveOrUpdate(user.getAddress());
			getUserDao().saveOrUpdate(user);
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
	public RolePrivilegeDAO getRolePrivilegeDao() {
		return coreServiceHelper.getRolePrivilegeDao();
	}
	public UserAuthorizationDAO getUserAuthorizationDao() {
		return coreServiceHelper.getUserAuthorizationDao();
	}
	public PrivilegeDAO getPrivilegeDao() {
		return coreServiceHelper.getPrivilegeDao();
	}

}
