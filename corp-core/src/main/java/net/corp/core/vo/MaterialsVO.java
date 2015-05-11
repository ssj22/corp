package net.corp.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class MaterialsVO implements Serializable {
	private Integer materialId;
	
	private String challanNo;
	
	private Integer vendorId;
	
	private String vendorName;
	
	private Integer stockId;
	
	private String stockName;
	
	private Integer transportId;
	
	private String transporterName;
	
	private Integer vehicleId;
	
	private String vehicleNumber;
	
	private Integer truckStatus;
	
	private String yourChallan;
	
	private Timestamp vehicleInTime;
	
	private Timestamp vehicleOutTime;
	
	private Double grossWt;
	
	private Double netWt;
	
	private Double tareWt;
	
	private Integer vibhagId;
	
	private String vibhagName;
	
	private Integer entryType;
	
	private String entryTypeText;
	
	private Double rate;
	
	private Double amount;
	
	private String siteName;
	
	private Double heightCorrection;
	
	private Integer inCreatedBy; 
	
	private Integer outCreatedBy;
	
	private Timestamp inCreatedDate; 
	
	private Timestamp outCreatedDate;
	
	private Timestamp invoiceDate;
	
	private String invoiceNo;
	
	private Integer orderId;
	
	private String status;
	
	private Integer userId;
	
	private boolean inAddlInd;
	
	private boolean outAddlInd;
	
	private boolean htCorrectionInd;
	
	private boolean qtyInd;
	
	private boolean klInd;
	
	private boolean invoiceInd;
	
	private Integer category;
	
	private Integer parentMaterialId;
	
	private Integer logMaterialId;
	
	private Double quantity;

    private Double quantityKl;

    private Double vehicleQuantity;

    private Timestamp challanDate;

    public Timestamp getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(Timestamp challanDate) {
        this.challanDate = challanDate;
    }

    public Double getVehicleQuantity() {
        return vehicleQuantity;
    }

    public Double getQuantityKl() {
        return quantityKl;
    }

    public void setQuantityKl(Double quantityKl) {
        this.quantityKl = quantityKl;
    }

    public void setVehicleQuantity(Double vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getLogMaterialId() {
		return logMaterialId;
	}

	public void setLogMaterialId(Integer logMaterialId) {
		this.logMaterialId = logMaterialId;
	}

	public Integer getParentMaterialId() {
		return parentMaterialId;
	}

	public void setParentMaterialId(Integer parentMaterialId) {
		this.parentMaterialId = parentMaterialId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getVibhagName() {
		return vibhagName;
	}

	public void setVibhagName(String vibhagName) {
		this.vibhagName = vibhagName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
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

	public Integer getTruckStatus() {
		return truckStatus;
	}

	public void setTruckStatus(Integer truckStatus) {
		this.truckStatus = truckStatus;
	}

	public String getYourChallan() {
		return yourChallan;
	}

	public void setYourChallan(String yourChallan) {
		this.yourChallan = yourChallan;
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
		if (entryType != null && entryType == 1) {
			setEntryTypeText("IN");
		}
		else if (entryType != null && entryType == 2) {
			setEntryTypeText("OUT");
		}
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

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getEntryTypeText() {
		return entryTypeText;
	}

	public void setEntryTypeText(String entryTypeText) {
		this.entryTypeText = entryTypeText;
	}

	public boolean isInAddlInd() {
		return inAddlInd;
	}

	public void setInAddlInd(boolean inAddlInd) {
		this.inAddlInd = inAddlInd;
	}

	public boolean isOutAddlInd() {
		return outAddlInd;
	}

	public void setOutAddlInd(boolean outAddlInd) {
		this.outAddlInd = outAddlInd;
	}

	public boolean isHtCorrectionInd() {
		return htCorrectionInd;
	}

	public void setHtCorrectionInd(boolean htCorrectionInd) {
		this.htCorrectionInd = htCorrectionInd;
	}

	public boolean isQtyInd() {
		return qtyInd;
	}

	public void setQtyInd(boolean qtyInd) {
		this.qtyInd = qtyInd;
	}

	public boolean isKlInd() {
		return klInd;
	}

	public void setKlInd(boolean klInd) {
		this.klInd = klInd;
	}

	public boolean isInvoiceInd() {
		return invoiceInd;
	}

	public void setInvoiceInd(boolean invoiceInd) {
		this.invoiceInd = invoiceInd;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

		
}
