package net.corp.core.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="d_materials")
public class Materials implements Serializable {
	@Id
	@Column(name = "MATERIAL_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer materialId;
	
	@Column(name = "PARENT_MATERIAL_ID")
	private Integer parentMaterialId;
		
	@Column(name = "CHALLAN_NO")
	private String challanNo;
	
	@Column(name = "VENDOR_ID")
	private Integer vendorId;
	
	@Column(name = "STOCK_ID")
	private Integer stockId;
	
	@Column(name = "TRANSPORT_ID")
	private Integer transportId;
	
	@Column(name="VEHICLE_ID")
	private Integer vehicleId;
	
	@Column(name = "VEHICLE_IN_TIME")
	private Timestamp vehicleInTime;
	
	@Column(name = "VEHICLE_OUT_TIME")
	private Timestamp vehicleOutTime;
	
	@Column(name = "GROSS_WT")
	private Double grossWt;
	
	@Column(name = "NET_WT")
	private Double netWt;
	
	@Column(name = "TARE_WT")
	private Double tareWt;
	
	@Column(name = "VIBHAG_ID")
	private Integer vibhagId;
	
	@Column(name = "ENTRY_TYPE")
	private Integer entryType;
	
	@Column(name = "RATE")
	private Double rate;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "SITE_NAME")
	private String siteName;
	
	@Column(name = "HEIGHT_CORRECTION")
	private Double heightCorrection;
	
	@Column(name = "IN_CREATED_BY")
	private Integer inCreatedBy; 
	
	@Column(name = "OUT_CREATED_BY")
	private Integer outCreatedBy;
	
	@Column(name = "IN_CREATED_DATE")
	private Timestamp inCreatedDate; 
	
	@Column(name = "OUT_CREATED_DATE")
	private Timestamp outCreatedDate;
	
	@Column(name = "INVOICE_DATE")
	private Timestamp invoiceDate;
	
	@Column(name = "INVOICE_NO")
	private String invoiceNo;
	
	@Column(name = "ORDER_ID")
	private String orderId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "LOG_MATERIAL_ID")
	private Integer logMaterialId;
	
	@Column(name = "QTY")
	private Double quantity;

    @Column(name = "VEHICLE_QTY")
    private Double vehicleQuantity;

    @Column(name = "QTYKL")
    private Double quantityKl;

    @Column(name = "your_chanllandt")
    private Timestamp challanDate;

    public Double getQuantityKl() {
        return quantityKl;
    }

    public void setQuantityKl(Double quantityKl) {
        this.quantityKl = quantityKl;
    }

    public Integer getLogMaterialId() {
		return logMaterialId;
	}

	public void setLogMaterialId(Integer logMaterialId) {
		this.logMaterialId = logMaterialId;
	}

	public String getOrderId() {
		return orderId;
	}

    public Double getVehicleQuantity() {
        return vehicleQuantity;
    }

    public void setVehicleQuantity(Double vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getTransportId() {
		return transportId;
	}

	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Timestamp getVehicleInTime() {
		return vehicleInTime;
	}

	public void setVehicleInTime(Timestamp vehicleInTime) {
		this.vehicleInTime = vehicleInTime;
	}

	public Timestamp getVehicleOutTime() {
		return vehicleOutTime;
	}

	public void setVehicleOutTime(Timestamp vehicleOutTime) {
		this.vehicleOutTime = vehicleOutTime;
	}

	public Double getGrossWt() {
		return grossWt;
	}

	public void setGrossWt(Double grossWt) {
		this.grossWt = grossWt;
	}

	public Double getNetWt() {
		return netWt;
	}

	public void setNetWt(Double netWt) {
		this.netWt = netWt;
	}

	public Double getTareWt() {
		return tareWt;
	}

	public void setTareWt(Double tareWt) {
		this.tareWt = tareWt;
	}

	public Integer getVibhagId() {
		return vibhagId;
	}

	public void setVibhagId(Integer vibhagId) {
		this.vibhagId = vibhagId;
	}

	public Integer getEntryType() {
		return entryType;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Double getHeightCorrection() {
		return heightCorrection;
	}

	public void setHeightCorrection(Double heightCorrection) {
		this.heightCorrection = heightCorrection;
	}

	public Integer getInCreatedBy() {
		return inCreatedBy;
	}

	public void setInCreatedBy(Integer inCreatedBy) {
		this.inCreatedBy = inCreatedBy;
	}

    public Integer getOutCreatedBy() {
		return outCreatedBy;
	}

	public void setOutCreatedBy(Integer outCreatedBy) {
		this.outCreatedBy = outCreatedBy;
	}

	public Timestamp getInCreatedDate() {
		return inCreatedDate;
	}

	public void setInCreatedDate(Timestamp inCreatedDate) {
		this.inCreatedDate = inCreatedDate;
	}

	public Timestamp getOutCreatedDate() {
		return outCreatedDate;
	}

	public void setOutCreatedDate(Timestamp outCreatedDate) {
		this.outCreatedDate = outCreatedDate;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Integer getParentMaterialId() {
		return parentMaterialId;
	}

	public void setParentMaterialId(Integer parentMaterialId) {
		this.parentMaterialId = parentMaterialId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

    public Timestamp getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(Timestamp challanDate) {
        this.challanDate = challanDate;
    }
}
