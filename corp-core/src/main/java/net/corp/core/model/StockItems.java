package net.corp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

// Generated 21 May, 2014 12:30:42 PM by Hibernate Tools 4.0.0

/**
 * MdStockItems generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="md_stock_items")
public class StockItems implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "stock_id")
	private Integer stockId;
	@Column(name = "stock_itemname")
	private String stockItemname;
	@Column(name = "company_id")
	private Integer companyId;
	@JoinColumn(name = "main_itemid")
	@ManyToOne
	private Items item;
	@Column(name = "stock_wt")
	private Double stockWt;
	@Column(name = "stock_rate")
	private Double stockRate;
	@Column(name = "stock_rate_inword")
	private Double stockRateInword;
	@Column(name = "Unit")
	private Integer unit;
	@Column(name = "CONV_FACT")
	private Double convFact;
	@Column(name = "usrid")
	private Integer userId;
	@Column(name = "INVOICE_IND")
	private Integer invoiceInd;

	public StockItems() {
	}

	public StockItems(String stockItemname, Integer companyId,
			Items item, Double stockWt, Double stockRate,
			Double stockRateInword, Integer unit, Double convFact,
			Integer usrid, Integer invoiceInd) {
		this.stockItemname = stockItemname;
		this.companyId = companyId;
		this.item = item;
		this.stockWt = stockWt;
		this.stockRate = stockRate;
		this.stockRateInword = stockRateInword;
		this.unit = unit;
		this.convFact = convFact;
		this.userId = usrid;
		this.invoiceInd = invoiceInd;
	}

	public Integer getStockId() {
		return this.stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getStockItemname() {
		return this.stockItemname;
	}

	public void setStockItemname(String stockItemname) {
		this.stockItemname = stockItemname;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

	public Double getStockWt() {
		return this.stockWt;
	}

	public void setStockWt(Double stockWt) {
		this.stockWt = stockWt;
	}

	public Double getStockRate() {
		return this.stockRate;
	}

	public void setStockRate(Double stockRate) {
		this.stockRate = stockRate;
	}

	public Double getStockRateInword() {
		return this.stockRateInword;
	}

	public void setStockRateInword(Double stockRateInword) {
		this.stockRateInword = stockRateInword;
	}

	public Integer getUnit() {
		return this.unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Double getConvFact() {
		return this.convFact;
	}

	public void setConvFact(Double convFact) {
		this.convFact = convFact;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer usrid) {
		this.userId = usrid;
	}

	public Integer getInvoiceInd() {
		return invoiceInd;
	}

	public void setInvoiceInd(Integer invoiceInd) {
		this.invoiceInd = invoiceInd;
	}
	
}
