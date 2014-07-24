package net.corp.core.service.helper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.corp.core.dao.AddressDAO;
import net.corp.core.dao.MaterialDAO;
import net.corp.core.dao.PrimaryGroupDAO;
import net.corp.core.dao.PrivilegeDAO;
import net.corp.core.dao.RoleDAO;
import net.corp.core.dao.RolePrivilegeDAO;
import net.corp.core.dao.StockItemDAO;
import net.corp.core.dao.UserAuthorizationDAO;
import net.corp.core.dao.UserDAO;
import net.corp.core.dao.VehicleDAO;
import net.corp.core.dao.VibhagDAO;
import net.corp.core.model.Address;
import net.corp.core.model.Materials;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.Privileges;
import net.corp.core.model.Roles;
import net.corp.core.model.StockItems;
import net.corp.core.model.UserLogin;
import net.corp.core.model.Users;
import net.corp.core.model.Vehicles;
import net.corp.core.model.Vibhag;
import net.corp.core.vo.AddressVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.PrivilegeVO;
import net.corp.core.vo.RoleVO;
import net.corp.core.vo.TabsVO;
import net.corp.core.vo.UserVO;

import org.springframework.beans.BeanUtils;

public class CoreServiceHelper {
	private UserDAO userDao;
	private AddressDAO addressDao;
	private PrivilegeDAO privilegeDao;
	private RoleDAO roleDao;
	private RolePrivilegeDAO rolePrivilegeDao;
	private UserAuthorizationDAO userAuthorizationDao;
	private MaterialDAO materialDao;
	private VehicleDAO vehicleDao;
	private StockItemDAO stockItemDao;
	private VibhagDAO vibhagDao;
	private PrimaryGroupDAO primaryGroupDao;
	
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
	public RolePrivilegeDAO getRolePrivilegeDao() {
		return rolePrivilegeDao;
	}
	public void setRolePrivilegeDao(RolePrivilegeDAO rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
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
	
	/*--------------------Utility Methods-----------------------*/
	
	public MaterialsVO convertModelToVO(Materials materials) {
		MaterialsVO materialsVo = new MaterialsVO();
		BeanUtils.copyProperties(materials, materialsVo);
		
		if (materials.getVehicleId() != null) {
			Vehicles vehicle = getVehicleDao().getById(materials.getVehicleId());
			if (vehicle != null) {
				materialsVo.setVehicleNumber(vehicle.getVehicleNumber());
			}
		}
		
		if (materials.getVibhagId() != null) {
			Vibhag vibhag = getVibhagDao().getById(materials.getVibhagId());
			if (vibhag != null) {
				materialsVo.setVibhagName(vibhag.getVibhagName());
			}
		}
		
		if (materials.getVendorId() != null) {
			Vibhag vibhag = getVibhagDao().getById(materials.getVendorId());
			if (vibhag != null) {
				materialsVo.setVendorName(vibhag.getVibhagName());
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
			Vehicles vehicle = getVehicleDao().findVehicleByNumber(materialVo.getVehicleNumber());
			if (vehicle != null && ((materialVo.getVehicleId() == null) || (vehicle.getVehicleId() != materialVo.getVehicleId()))) {
				materials.setVehicleId(vehicle.getVehicleId());
			}
		}
		
		if (materialVo.getVibhagName() != null) {
			Vibhag vibhag = getVibhagDao().findVibhagByName(materialVo.getVibhagName());
			if (vibhag != null && ((materialVo.getVehicleId() == null) || (vibhag.getVibhagId() != materialVo.getVibhagId()))) {
				materials.setVibhagId(vibhag.getVibhagId());
			}
		}
		
		if (materialVo.getVendorName() != null) {
			Vibhag vibhag = getVibhagDao().findVibhagByName(materialVo.getVendorName());
			if (vibhag != null && ((materialVo.getVehicleId() == null) || (vibhag.getVibhagId() != materialVo.getVendorId()))) {
				materials.setVendorId(vibhag.getVibhagId());
			}
		}
		
		if (materialVo.getTransporterName() != null) {
			PrimaryGroup pg = getPrimaryGroupDao().findPrimaryGroupByName(materialVo.getTransporterName());
			if (pg != null && ((materialVo.getVehicleId() == null) || (pg.getVendorId() != materialVo.getTransportId()))) {
				materials.setTransportId(pg.getVendorId());
			}
		}
		
		if (materialVo.getStockName() != null) {
			StockItems stockItems = getStockItemDao().findStockByName(materialVo.getStockName());
			if (stockItems != null && ((materialVo.getVehicleId() == null) || (stockItems.getStockId() != materialVo.getStockId()))) {
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
		userVo.setGuestUser(user.isGuest());
		userVo.setRootUser(user.isRoot());
				
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
		userVo.setAddlPhone(user.getAddlPhone());
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
		userVo.setSessionTimeout(user.getUserLogin().getSessionTimeout());
		userVo.setFirstLogin(user.getUserLogin().isFirstLogin());
		userVo.setKeepAlive(user.getUserLogin().isKeepAlive());
		
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
		user.setAddlPhone(userVo.getAddlPhone());
		user.setFirstName(userVo.getFirstName());
		user.getUserLogin().setForgotPassword(userVo.isForgotPwd());
		user.setDateOfBirth(userVo.getDob());
		user.setLastName(userVo.getLastName());
		user.setMiddleName(userVo.getMiddleName());
		user.setFullName(userVo.getFullName());
		user.setGuest(userVo.isGuestUser());
		user.getUserLogin().setPassword(userVo.getPassword());
		user.setPhone(userVo.getPhone());
		user.setPrefix(userVo.getPrefix());
		user.setRoot(userVo.isRootUser());
		user.getUserLogin().setSessionTimeout(userVo.getSessionTimeout());
		user.setSuffix(userVo.getSuffix());
		user.setTitle(userVo.getTitle());
		user.getUserLogin().setUsername(userVo.getUsername());
		user.setUpdatedBy(userVo.getUpdatedBy());
		user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		user.setComments(userVo.getComments());
		user.getUserLogin().setKeepAlive(userVo.isKeepAlive());
		user.getUserLogin().setFirstLogin(userVo.isFirstLogin());
		user.setAddress(convertVOToModel(userVo.getAddress()));
		
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
		
		return tabs;
	}
	
}
