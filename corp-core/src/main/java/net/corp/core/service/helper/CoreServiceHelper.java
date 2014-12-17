package net.corp.core.service.helper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.corp.core.dao.AddressDAO;
import net.corp.core.dao.LogDAO;
import net.corp.core.dao.LogMaterialDAO;
import net.corp.core.dao.MaterialDAO;
import net.corp.core.dao.PrimaryGroupDAO;
import net.corp.core.dao.PrivilegeDAO;
import net.corp.core.dao.RoleDAO;
import net.corp.core.dao.StockItemDAO;
import net.corp.core.dao.UserAuthorizationDAO;
import net.corp.core.dao.UserDAO;
import net.corp.core.dao.UserPreferenceDAO;
import net.corp.core.dao.VehicleDAO;
import net.corp.core.dao.VibhagDAO;
import net.corp.core.model.Address;
import net.corp.core.model.LogBook;
import net.corp.core.model.LogMaterial;
import net.corp.core.model.Materials;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.Privileges;
import net.corp.core.model.Roles;
import net.corp.core.model.StockItems;
import net.corp.core.model.UserLogin;
import net.corp.core.model.UserPreference;
import net.corp.core.model.Users;
import net.corp.core.model.Vehicles;
import net.corp.core.model.Vibhag;
import net.corp.core.util.PropertyUtil;
import net.corp.core.vo.AddressVO;
import net.corp.core.vo.LogMaterialVO;
import net.corp.core.vo.LogVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.PrivilegeVO;
import net.corp.core.vo.RoleVO;
import net.corp.core.vo.TabsVO;
import net.corp.core.vo.UserPreferenceVO;
import net.corp.core.vo.UserVO;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

public class CoreServiceHelper {
	private static final Logger log = Logger.getLogger(CoreServiceHelper.class);
	
	private UserDAO userDao;
	private AddressDAO addressDao;
	private PrivilegeDAO privilegeDao;
	private RoleDAO roleDao;
	private UserAuthorizationDAO userAuthorizationDao;
	private MaterialDAO materialDao;
	private VehicleDAO vehicleDao;
	private StockItemDAO stockItemDao;
	private VibhagDAO vibhagDao;
	private PrimaryGroupDAO primaryGroupDao;
	private UserPreferenceDAO userPreferenceDao;
	private LogDAO logDao;
	private LogMaterialDAO logMaterialDao;
	
	public LogDAO getLogDao() {
		return logDao;
	}
	public void setLogDao(LogDAO logDao) {
		this.logDao = logDao;
	}
	public LogMaterialDAO getLogMaterialDao() {
		return logMaterialDao;
	}
	public void setLogMaterialDao(LogMaterialDAO logMaterialDao) {
		this.logMaterialDao = logMaterialDao;
	}
	public UserPreferenceDAO getUserPreferenceDao() {
		return userPreferenceDao;
	}
	public void setUserPreferenceDao(UserPreferenceDAO userPreferenceDao) {
		this.userPreferenceDao = userPreferenceDao;
	}
	public StockItemDAO getStockItemDao() {
		return stockItemDao;
	}
	public void setStockItemDao(StockItemDAO stockItemDao) {
		this.stockItemDao = stockItemDao;
	}
	public VibhagDAO getVibhagDao() {
		return vibhagDao;
	}
	public void setVibhagDao(VibhagDAO vibhagDao) {
		this.vibhagDao = vibhagDao;
	}
	public PrimaryGroupDAO getPrimaryGroupDao() {
		return primaryGroupDao;
	}
	public void setPrimaryGroupDao(PrimaryGroupDAO primaryGroupDao) {
		this.primaryGroupDao = primaryGroupDao;
	}
	public VehicleDAO getVehicleDao() {
		return vehicleDao;
	}
	public void setVehicleDao(VehicleDAO vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	public MaterialDAO getMaterialDao() {
		return materialDao;
	}
	public void setMaterialDao(MaterialDAO materialDao) {
		this.materialDao = materialDao;
	}
	public UserAuthorizationDAO getUserAuthorizationDao() {
		return userAuthorizationDao;
	}
	public void setUserAuthorizationDao(UserAuthorizationDAO userAuthorizationDao) {
		this.userAuthorizationDao = userAuthorizationDao;
	}
	public RoleDAO getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}
	public PrivilegeDAO getPrivilegeDao() {
		return privilegeDao;
	}
	public void setPrivilegeDao(PrivilegeDAO privilegeDao) {
		this.privilegeDao = privilegeDao;
	}
	public UserDAO getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	public AddressDAO getAddressDao() {
		return addressDao;
	}
	public void setAddressDao(AddressDAO addressDao) {
		this.addressDao = addressDao;
	}
	
	public LogVO convertModelToVO(List<LogMaterial> logMaterials) {
		LogVO logVo = new LogVO();
		List<LogMaterialVO> logMaterialVos = new ArrayList<LogMaterialVO>();
		logVo.setLogMaterials(logMaterialVos);
		LogBook logbook = logMaterials.get(0).getLog(); 
		BeanUtils.copyProperties(logbook, logVo);
		
		logVo.setTransportName(logbook.getTransport().getVendorName());
		logVo.setVehicleNumber(logbook.getVehicle().getVehicleNumber());
		logVo.setVibhagName(logbook.getVibhag().getVibhagName());
		logVo.setVibhagPhone(logbook.getVibhag().getVibhagName());
		
		for (LogMaterial logMaterial: logMaterials) {
			LogMaterialVO logMaterialVo = new LogMaterialVO();
			BeanUtils.copyProperties(logMaterial, logMaterialVo);
			logMaterialVo.setLogId(logMaterial.getLog().getLogId());
			logMaterialVo.setMaterialId(logMaterial.getMaterial().getMaterialId());
			logMaterialVo.setStockItemName(logMaterial.getItem().getStockItemname());
		}
		
		return logVo;
	}
	
	
	public LogVO convertModelToVO(LogBook logbook) {
		LogVO logVo = new LogVO();
		BeanUtils.copyProperties(logbook, logVo);
		if (logbook.getTransport() != null) {
			logVo.setTransportName(logbook.getTransport().getVendorName());
		}
		if (logbook.getVehicle() != null) {
			logVo.setVehicleNumber(logbook.getVehicle().getVehicleNumber());
		}
		if (logbook.getVibhag() != null) {
			logVo.setVibhagName(logbook.getVibhag().getVibhagName());
		}
		if (logbook.getTransport() != null) {
			logVo.setVibhagPhone(logbook.getVibhag().getVibhagName());
		}
		
		return logVo;
	}
	
	/*--------------------Utility Methods-----------------------*/
	
	public MaterialsVO convertModelToVO(Materials materials) {
		MaterialsVO materialsVo = new MaterialsVO();
		BeanUtils.copyProperties(materials, materialsVo);
		
		if (materials.getVehicleId() != null) {
			Vehicles vehicle = getVehicleDao().getById(materials.getVehicleId());
			if (vehicle != null) {
				materialsVo.setVehicleNumber(vehicle.getVehicleNumber().replace(" ", "-"));
			}
		}
		
		if (materials.getVibhagId() != null) {
			Vibhag vibhag = getVibhagDao().getById(materials.getVibhagId());
			if (vibhag != null) {
				materialsVo.setVibhagName(vibhag.getVibhagName());
			}
		}
		
		if (materials.getVendorId() != null) {
			PrimaryGroup vendor = getPrimaryGroupDao().getById(materials.getVendorId());
			if (vendor != null) {
				materialsVo.setVendorName(vendor.getVendorName());
			}
		}
		
		if (materials.getTransportId() != null) {
			PrimaryGroup pg = getPrimaryGroupDao().getById(materials.getTransportId());
			if (pg != null) {
				materialsVo.setTransporterName(pg.getVendorName());
			}
		}
		
		if (materials.getStockId() != null) {
			StockItems stockItems = getStockItemDao().getById(materials.getStockId());
			if (stockItems != null) {
				materialsVo.setStockName(stockItems.getStockItemname());
				materialsVo.setInAddlInd(stockItems.getItem().isInAddlInd());
				materialsVo.setOutAddlInd(stockItems.getItem().isOutAddlInd());
				materialsVo.setInvoiceInd(stockItems.getItem().isInvoiceInd());
				materialsVo.setHtCorrectionInd(stockItems.getItem().isHtCorrectionInd());
				materialsVo.setKlInd(stockItems.getItem().isKlInd());
				materialsVo.setQtyInd(stockItems.getItem().isQtyInd());
			}
		}
		
		return materialsVo;
	}
	
	public RoleVO convertModelToVO(Roles role) {
		RoleVO roleVo = new RoleVO();
		
		roleVo.setActive(role.isActive());
		roleVo.setParentRoleId(role.getParentRoleId());
		roleVo.setRoleId(role.getRoleId());
		roleVo.setRoleType(role.getRoleType());
		roleVo.setRoleName(role.getRoleName());
		
		return roleVo;
	}
	
	public PrivilegeVO convertModelToVO(Privileges privilege) {
		PrivilegeVO privilegeVo = new PrivilegeVO();
		
		privilegeVo.setActive(privilege.isActive());
		privilegeVo.setPrivilegeId(privilege.getPrivilegeId());
		privilegeVo.setPrivilegeType(privilege.getPrivilegeType());
		privilegeVo.setPrivilegeName(privilege.getPrivilegeName());
		return privilegeVo;
	}
	
	public AddressVO convertModelToVO(Address address) {
		AddressVO addressVo = new AddressVO();
		
		addressVo.setActive(address.isActive());
		addressVo.setAddressId(address.getAddressId());
		addressVo.setAddressLine1(address.getAddressLine1());
		addressVo.setAddressLine2(address.getAddressLine2());
		addressVo.setAddressLine3(address.getAddressLine3());
		addressVo.setAddressLine4(address.getAddressLine4());
		addressVo.setCity(address.getCity());
		addressVo.setState(address.getState());
		addressVo.setDistrict(address.getDistrict());
		addressVo.setPincode(address.getPincode());
		addressVo.setCreatedBy(address.getCreatedBy());
		addressVo.setCreatedDate(address.getCreatedDate());
		addressVo.setUpdatedBy(address.getUpdatedBy());
		addressVo.setUpdatedDate(address.getUpdatedDate());
		return addressVo;
	}
	
	public List<LogMaterial> convertVOToModel(LogVO logVo) {
		List<LogMaterial> logMaterials = new ArrayList<LogMaterial>();
		LogBook logbook = null;
		Integer id = logVo.getLogId();
		if (id != null) {
			logbook = getLogDao().getById(id);
		}
		else {
			logbook = new LogBook();
		}
		
		BeanUtils.copyProperties(logVo, logbook);
		logbook.setLogId(id);
		logbook.setValid(true);
		
		if (logVo.getTransportName() == null) {
			logbook.setValid(false);	
		}
		else {
			PrimaryGroup pg = getPrimaryGroupDao().findPrimaryGroupByName(logVo.getTransportName());
			logbook.setTransport(pg);
		}
		
		if (logVo.getVehicleNumber() == null) {
			logbook.setValid(false);
		}	
		else {	
			Vehicles vehicle = getVehicleDao().findVehicleByNumber(logVo.getVehicleNumber());
			logbook.setVehicle(vehicle);
		}
		
		if (logVo.getPhone() == null) {
			logbook.setValid(false);
		}
		else {
			Vibhag vibhag = getVibhagDao().findVibhagByPhone(logVo.getPhone());
			logbook.setVibhag(vibhag);
			logbook.setPhone(logVo.getPhone());
		}
		
		logbook.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		if (logVo.getLogMaterials() != null && !logVo.getLogMaterials().isEmpty()) {
			for (LogMaterialVO logMaterialVo: logVo.getLogMaterials()) {
				logMaterials.add(convertVOToModel(logMaterialVo, logbook));
			}
		}
		
		return logMaterials;
	}
	
	public LogMaterial convertVOToModel(LogMaterialVO logMaterialVo, LogBook logBook) {
		LogMaterial logMaterial = null;
		
		Integer mid = logMaterialVo.getLogMaterialId();
		if (mid != null) {
			logMaterial = getLogMaterialDao().getById(mid);
		}
		else {
			logMaterial = new LogMaterial();
		}
		
		BeanUtils.copyProperties(logMaterialVo, logMaterial);
		logMaterial.setLogMaterialId(mid);
		
		if (logMaterialVo.getStockItemName() != null) {
			StockItems stockItem = getStockItemDao().findStockByName(logMaterialVo.getStockItemName());
			logMaterial.setItem(stockItem);
		}
		
		if (logBook != null) {
			logMaterial.setLog(logBook);
		}
				
		logMaterial.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		return logMaterial;
	}
	
	public Address convertVOToModel(AddressVO addressVo) {
		Address address = null;
		
		if (addressVo.getAddressId() == null) {
			address = new Address();
			address.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			address.setCreatedBy(addressVo.getCreatedBy());
		}
		else {
			address = getAddressDao().getById(addressVo.getAddressId());
		}
		
		address.setActive(addressVo.isActive());
		address.setAddressLine1(addressVo.getAddressLine1());
		address.setAddressLine2(addressVo.getAddressLine2());
		address.setAddressLine3(addressVo.getAddressLine3());
		address.setAddressLine4(addressVo.getAddressLine4());
		address.setCity(addressVo.getCity());
		address.setState(addressVo.getState());
		address.setDistrict(addressVo.getDistrict());
		address.setPincode(addressVo.getPincode());
		
		address.setUpdatedBy(addressVo.getUpdatedBy());
		address.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		
		return address;
	}
	
	public UserPreference convertVOToModel(UserPreferenceVO userPrefVo) {
		UserPreference userPref = null;
		
		if (userPrefVo.getUserId() == null) {
			return null;
		}
		
		List<UserPreference> prefs = getUserPreferenceDao().findPreferencesByUser(userPrefVo.getUserId());
		if (prefs != null && !prefs.isEmpty()) {
			userPref = prefs.get(0);
		}
		else {
			userPref = new UserPreference();
			userPref.setUserId(userPrefVo.getUserId());
		}
		
		userPref.setMaterialViewTimeSel(userPrefVo.getMaterialEntryTimeSel());
		
		return userPref;
	}
	
	public Materials convertVOToModel(MaterialsVO materialVo) {
		Materials materials = null;
		
		if (materialVo.getMaterialId() == null) {
			materials = new Materials();
		}
		else {
			materials = getMaterialDao().getById(materialVo.getMaterialId());
		}
		BeanUtils.copyProperties(materialVo, materials);
		
		if (materialVo.getVehicleNumber() != null) {
			Vehicles vehicle = getVehicleDao().findVehicleByNumber(materialVo.getVehicleNumber().replace("-", " "));
			if (vehicle != null && ((materialVo.getVehicleId() == null) || (vehicle.getVehicleId() != materialVo.getVehicleId()))) {
				materials.setVehicleId(vehicle.getVehicleId());
			}
		}
		
		if (materialVo.getVibhagName() != null) {
			Vibhag vibhag = getVibhagDao().findVibhagByName(materialVo.getVibhagName());
			if (vibhag != null && ((materialVo.getVibhagId() == null) || (vibhag.getVibhagId() != materialVo.getVibhagId()))) {
				materials.setVibhagId(vibhag.getVibhagId());
			}
		}
		
		if (materialVo.getVendorName() != null) {
			PrimaryGroup vendor = getPrimaryGroupDao().findPrimaryGroupByName(materialVo.getVendorName());
			if (vendor != null && ((materialVo.getVendorId() == null) || (vendor.getVendorId() != materialVo.getVendorId()))) {
				materials.setVendorId(vendor.getVendorId());
			}
		}
		
		if (materialVo.getTransporterName() != null) {
			PrimaryGroup pg = getPrimaryGroupDao().findPrimaryGroupByName(materialVo.getTransporterName());
			if (pg != null && ((materialVo.getVendorId() == null) || (pg.getVendorId() != materialVo.getTransportId()))) {
				materials.setTransportId(pg.getVendorId());
			}
		}
		
		if (materialVo.getStockName() != null) {
			StockItems stockItems = getStockItemDao().findStockByName(materialVo.getStockName());
			if (stockItems != null && ((materialVo.getStockId() == null) || (stockItems.getStockId() != materialVo.getStockId()))) {
				materials.setStockId(stockItems.getStockId());
			}
		}
		
		return materials;
	}
	
	public UserVO convertModelToVO(Users user) {
		UserVO userVo = new UserVO();
		
		userVo.setUserId(user.getUserId());
		
		// User flags
		userVo.setActive(user.isActive());
		userVo.setAdminUser(user.isAdmin());
				
		// User credentials
		userVo.setFirstName(user.getFirstName());
		userVo.setLastName(user.getLastName());
		userVo.setMiddleName(user.getMiddleName());
		userVo.setFullName(user.getFullName());
		userVo.setDob(user.getDateOfBirth());
		userVo.setPrefix(user.getPrefix());
		userVo.setSuffix(user.getSuffix());
		userVo.setTitle(user.getTitle());
		
		// User Phone and Address
		userVo.setPhone(user.getPhone());
		userVo.setAddress(convertModelToVO(user.getAddress()));
		
		// Audit Data
		userVo.setCreatedBy(user.getCreatedBy());
		userVo.setCreatedDate(user.getCreatedDate());
		userVo.setUpdatedBy(user.getUpdatedBy());
		userVo.setUpdatedDate(user.getUpdatedDate());
		userVo.setComments(user.getComments());
		
		// User Login Data	
		userVo.setUsername(user.getUserLogin().getUsername());
		userVo.setPassword(user.getUserLogin().getPassword());
		userVo.setForgotPwd(user.getUserLogin().isForgotPassword());
		userVo.setFirstLogin(user.getUserLogin().isFirstLogin());
		
		return userVo;
	}
	
	public Users convertVOToModel(UserVO userVo) {
		Users user = null;
		Integer userId = userVo.getUserId();
		if (userId == null) {
			user = new Users();
			user.setCreatedBy(userVo.getCreatedBy());
			user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			user.setUserLogin(new UserLogin());
			user.getUserLogin().setUser(user);
		}
		else {
			user = getUserDao().getById(userId);
		}
		
		user.setActive(userVo.isActive());
		user.setFirstName(userVo.getFirstName());
		user.getUserLogin().setForgotPassword(userVo.isForgotPwd());
		user.setDateOfBirth(userVo.getDob());
		user.setLastName(userVo.getLastName());
		user.setMiddleName(userVo.getMiddleName());
		user.setFullName(userVo.getFullName());
		user.getUserLogin().setPassword(userVo.getPassword());
		user.setPhone(userVo.getPhone());
		user.setPrefix(userVo.getPrefix());
		user.setAdmin(userVo.isAdminUser());
		user.setSuffix(userVo.getSuffix());
		user.setTitle(userVo.getTitle());
		user.getUserLogin().setUsername(userVo.getUsername());
		user.setUpdatedBy(userVo.getUpdatedBy());
		user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		user.setComments(userVo.getComments());
		user.getUserLogin().setFirstLogin(userVo.isFirstLogin());
		if (userVo.getAddress() != null) {
			user.setAddress(convertVOToModel(userVo.getAddress()));
		}
		
		return user;
	}
	
	public List<TabsVO> getAllTabs() {
		List<TabsVO> tabs = new ArrayList<TabsVO>();
		TabsVO tab1 = new TabsVO();
		
		tab1.setTabName("Home");
		tab1.setTabDesc("Home");
		tab1.setTabOrder(1);
		tab1.setTabPermission("HOME");
		tabs.add(tab1);
		
		TabsVO tab2 = new TabsVO();
		tab2.setTabName("Materials");
		tab2.setTabDesc("Materials");
		tab2.setTabOrder(2);
		tab2.setTabPermission("VIEW_MATERIAL_ENTRY");
		tabs.add(tab2);
		
		TabsVO tab3 = new TabsVO();
		tab3.setTabName("Reports");
		tab3.setTabDesc("Reports");
		tab3.setTabOrder(3);
		tab3.setTabPermission("VIEW_REPORTS");
		tabs.add(tab3);
		
		TabsVO tab4 = new TabsVO();
		tab4.setTabName("Setup");
		tab4.setTabDesc("Setup");
		tab4.setTabPermission("EDIT_USER");
		tab4.setTabOrder(4);
		tabs.add(tab4);
		
		TabsVO tab5 = new TabsVO();
		tab5.setTabName("Log");
		tab5.setTabDesc("Log");
		tab5.setTabPermission("HOME");
		tab5.setTabOrder(5);
		tabs.add(tab5);
		
		return tabs;
	}
	
	public LogVO getLogFromSms(String phone, String sms) {
		LogVO logVo = new LogVO();
		logVo.setMsg(sms);
		logVo.setPhone(phone);
        int len = phone.length();
        if (len > 10) {
            logVo.setPhone(phone.substring(len - 10, len));
        } else {
            logVo.setPhone(phone);
        }
		try {
			String[] arr = sms.split("\\$");
			boolean valid = true;
			List<LogMaterialVO> logMaterialVOs = new ArrayList<LogMaterialVO>();
			
			if (arr == null || arr.length < 5 ||
					arr[0] == null || arr[0].trim().isEmpty() ||
					arr[1] == null || arr[1].trim().isEmpty() ||
					arr[2] == null || arr[2].trim().isEmpty() ||
					arr[3] == null || arr[3].trim().isEmpty() ||
					arr[4] == null || arr[4].trim().isEmpty()) {
				valid = false;
				logVo.setLogMaterials(logMaterialVOs);
				logVo.getLogMaterials().add(new LogMaterialVO());
			}
			else {
				if (arr[0].equalsIgnoreCase("N")) {
					logVo.setNightShift(true);
				}
				else {
					logVo.setNightShift(false);
				}
				logVo.setVehicleNumber(arr[1]);
				logVo.setTransportName(arr[2]);
				
				String[] stocks = arr[3].split(",");
				
				if (stocks.length == 0) {
					valid = false;
				}
				else {
					for (int i = 0; i < stocks.length; i++) {
						LogMaterialVO logMaterialVo = new LogMaterialVO();
						logMaterialVo.setValid(true);
						String[] split = stocks[i].split("@");
						if (split == null || split.length < 3) {
							valid = false;
							logMaterialVo.setValid(false);
						}
						
						if (split[0] == null || split[0].trim().isEmpty()) {
							valid = false;
							logMaterialVo.setValid(false);
						}
						else {
							logMaterialVo.setStockItemName(split[0]);
						}
						
						if (split[1] == null || split[1].trim().isEmpty()) {
							valid = false;
							logMaterialVo.setValid(false);
						}
						else {
							logMaterialVo.setQuantity(split[1]);
						}
						
						if (split[2] == null || split[2].trim().isEmpty()) {
							valid = false;
							logMaterialVo.setValid(false);
						}
						else {
							logMaterialVo.setUnit(split[2]);
						}
						
						logMaterialVo.setComplete(false);
						logMaterialVOs.add(logMaterialVo);
					}
				}
				
				logVo.setSiteName(arr[4]);
				logVo.setLogMaterials(logMaterialVOs);
			}
			
			logVo.setValid(valid);
			logVo.setGateInTime(new Timestamp(System.currentTimeMillis()));
		} catch (Exception e) {
			log.error("Exception while converting raw SMS into object: " + e.getMessage(), e);
		}
		return logVo;
	}
	
	public boolean isAuthMachine() {
		HttpClient client = new HttpClient();
		GetMethod method = null;
	    try {
			// Create a method instance.
			method = new GetMethod(PropertyUtil.getInstance().getProperty("mbUrl"));

			// Provide custom retry handler is necessary
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
			  log.debug("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			String mb = (new String(responseBody));
			return getUserDao().checkMachineValidity(mb);
	    
	    } catch (HttpException e) {
	    	log.error("Fatal protocol violation: " + e.getMessage());
	    } catch (IOException e) {
	    	log.error("Fatal transport error: " + e.getMessage());
	    } catch (Exception e) {
	    	log.error("Execption: " + e.getMessage());
	    }
	    finally {
	      // Release the connection.
	      client = null;
	      method.releaseConnection();
	    }
	    return false;
	}
	
	
}
