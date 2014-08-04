package com.zswxsqxt.core.model;

/**
 * 客户状态
 */
public class Sustatus {
	
	private String id;
	/**
	 * 状态代码
	 */
	private String status;
	/**
	 * 状态描述
	 */
	private String statusdescription;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusdescription() {
		return statusdescription;
	}
	public void setStatusdescription(String statusdescription) {
		this.statusdescription = statusdescription;
	}
	public String getSubsstatuslongdesc() {
		return subsstatuslongdesc;
	}
	public void setSubsstatuslongdesc(String subsstatuslongdesc) {
		this.subsstatuslongdesc = subsstatuslongdesc;
	}
	public String getScreencolour1234() {
		return screencolour1234;
	}
	public void setScreencolour1234(String screencolour1234) {
		this.screencolour1234 = screencolour1234;
	}
	/**
	 * 状态长描述
	 */
	private String subsstatuslongdesc;
	private String screencolour1234;

}
