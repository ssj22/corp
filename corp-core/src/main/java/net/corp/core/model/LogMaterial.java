package net.corp.core.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="d_log_material")
public class LogMaterial implements Serializable {
	
	@Id
	@Column(name = "LOG_MATERIAL_ID")
	@GeneratedValue
	private Integer logMaterialId;
	
	@Column(name = "QUANTITY")
	private String quantity;
	
	@Column(name = "UNIT")
	private String unit;
	
	@JoinColumn(name = "MATERIAL_ID")
	@ManyToOne
	private Materials material;
	
	@JoinColumn(name = "STOCK_ITEM_ID")
	@ManyToOne
	private StockItems item;
	
	@JoinColumn(name = "LOG_ID")
	@ManyToOne
	private LogBook log;
	
	@Column(name = "COMPLETE")
	private boolean complete;
	
	@Column(name = "VALID")
	private boolean valid;
	
	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;
	
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

	public Materials getMaterial() {
		return material;
	}

	public void setMaterial(Materials material) {
		this.material = material;
	}

	public StockItems getItem() {
		return item;
	}

	public void setItem(StockItems item) {
		this.item = item;
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

	public LogBook getLog() {
		return log;
	}

	public void setLog(LogBook log) {
		this.log = log;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
