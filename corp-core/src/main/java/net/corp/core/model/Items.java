package net.corp.core.model;

// Generated 21 May, 2014 12:30:42 PM by Hibernate Tools 4.0.0

/**
 * MdItemMain generated by hbm2java
 */
@SuppressWarnings("serial")
public class Items implements java.io.Serializable {

	private Integer mainItemid;
	private String mainItemname;
	private Integer usrid;
	private Double factor;
	private Double transRate;

	public Integer getMainItemid() {
		return this.mainItemid;
	}

	public void setMainItemid(Integer mainItemid) {
		this.mainItemid = mainItemid;
	}

	public String getMainItemname() {
		return this.mainItemname;
	}

	public void setMainItemname(String mainItemname) {
		this.mainItemname = mainItemname;
	}

	public Integer getUsrid() {
		return this.usrid;
	}

	public void setUsrid(Integer usrid) {
		this.usrid = usrid;
	}

	public Double getFactor() {
		return this.factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

	public Double getTransRate() {
		return this.transRate;
	}

	public void setTransRate(Double transRate) {
		this.transRate = transRate;
	}

}
