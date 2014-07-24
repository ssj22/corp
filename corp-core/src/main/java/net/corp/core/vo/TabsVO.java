package net.corp.core.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TabsVO implements Serializable {
	private String tabName;
	private Integer tabOrder;
	private String tabDesc;
	private boolean visible;
	private boolean disabled;
	private boolean nested;
	private String parentTabName;
	private String tabPermission;
	
	public boolean isNested() {
		return nested;
	}
	public void setNested(boolean nested) {
		this.nested = nested;
	}
	public String getParentTabName() {
		return parentTabName;
	}
	public void setParentTabName(String parentTabName) {
		this.parentTabName = parentTabName;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public Integer getTabOrder() {
		return tabOrder;
	}
	public void setTabOrder(Integer tabOrder) {
		this.tabOrder = tabOrder;
	}
	public String getTabDesc() {
		return tabDesc;
	}
	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getTabPermission() {
		return tabPermission;
	}
	public void setTabPermission(String tabPermission) {
		this.tabPermission = tabPermission;
	}
	
}
