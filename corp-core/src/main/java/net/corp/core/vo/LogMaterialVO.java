package net.corp.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class LogMaterialVO implements Serializable {
	private Integer logMaterialId;
	private String quantity;
	private String unit;
	private Integer materialId;
	private String stockItemName;
	private Integer logId;
	private boolean complete;
	private boolean valid;
	private Timestamp updateDate;
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Integer getLogMaterialId() {
		return logMaterialId;
	}
	public void setLogMaterialId(Integer logMaterialId) {
		this.logMaterialId = logMaterialId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getStockItemName() {
		return stockItemName;
	}
	public void setStockItemName(String stockItemName) {
		this.stockItemName = stockItemName;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
}
