package net.corp.core.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CountVO implements Serializable {
	private Long todayCount;
	private Long todayInCount;
	private Long todayOutCount;
	private Long totalCount;
	private Long totalInCount;
	private Long totalOutCount;
	private Long weekCount;
	private Long weekInCount;
	private Long weekOutCount;
	private Long monthCount;
	private Long monthInCount;
	private Long monthOutCount;
	public Long getTodayCount() {
		return todayCount;
	}
	public void setTodayCount(Long todayCount) {
		this.todayCount = todayCount;
	}
	public Long getTodayInCount() {
		return todayInCount;
	}
	public void setTodayInCount(Long todayInCount) {
		this.todayInCount = todayInCount;
	}
	public Long getTodayOutCount() {
		return todayOutCount;
	}
	public void setTodayOutCount(Long todayOutCount) {
		this.todayOutCount = todayOutCount;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getTotalInCount() {
		return totalInCount;
	}
	public void setTotalInCount(Long totalInCount) {
		this.totalInCount = totalInCount;
	}
	public Long getTotalOutCount() {
		return totalOutCount;
	}
	public void setTotalOutCount(Long totalOutCount) {
		this.totalOutCount = totalOutCount;
	}
	public Long getWeekCount() {
		return weekCount;
	}
	public void setWeekCount(Long weekCount) {
		this.weekCount = weekCount;
	}
	public Long getWeekInCount() {
		return weekInCount;
	}
	public void setWeekInCount(Long weekInCount) {
		this.weekInCount = weekInCount;
	}
	public Long getWeekOutCount() {
		return weekOutCount;
	}
	public void setWeekOutCount(Long weekOutCount) {
		this.weekOutCount = weekOutCount;
	}
	public Long getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Long monthCount) {
		this.monthCount = monthCount;
	}
	public Long getMonthInCount() {
		return monthInCount;
	}
	public void setMonthInCount(Long monthInCount) {
		this.monthInCount = monthInCount;
	}
	public Long getMonthOutCount() {
		return monthOutCount;
	}
	public void setMonthOutCount(Long monthOutCount) {
		this.monthOutCount = monthOutCount;
	}
	
}
