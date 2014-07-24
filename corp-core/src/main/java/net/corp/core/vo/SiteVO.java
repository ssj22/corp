package net.corp.core.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SiteVO implements Serializable {
	private String siteName;
	private Long count;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}
