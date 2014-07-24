package net.corp.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class UserVO implements Serializable {
	private Integer userId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String fullName;
	private String prefix;
	private String suffix;
	private AddressVO address;
	private Long phone;
	private Long addlPhone;
	private boolean active;
	private boolean keepAlive;
	private boolean firstLogin;
	private Integer sessionTimeout;
	private String title;
	private Date dob;
	private String username;
	private String password;
	private boolean forgotPwd;
	private List<String> privileges; 
	private List<RoleVO> roles;
	private boolean guestUser;
	private boolean rootUser;
	private int createdBy;
	private Timestamp createdDate;
    private int updatedBy;
    private Timestamp updatedDate;
	private String comments;
	private List<TabsVO> tabs;
		
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public AddressVO getAddress() {
		return address;
	}
	public void setAddress(AddressVO address) {
		this.address = address;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public Long getAddlPhone() {
		return addlPhone;
	}
	public void setAddlPhone(Long addlPhone) {
		this.addlPhone = addlPhone;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isForgotPwd() {
		return forgotPwd;
	}
	public void setForgotPwd(boolean forgotPwd) {
		this.forgotPwd = forgotPwd;
	}
	public List<String> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	public List<RoleVO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}
	public boolean isGuestUser() {
		return guestUser;
	}
	public void setGuestUser(boolean guestUser) {
		this.guestUser = guestUser;
	}
	public boolean isRootUser() {
		return rootUser;
	}
	public void setRootUser(boolean rootUser) {
		this.rootUser = rootUser;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public boolean isKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	public boolean isFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	public List<TabsVO> getTabs() {
		return tabs;
	}
	public void setTabs(List<TabsVO> tabs) {
		this.tabs = tabs;
	}
		
}
