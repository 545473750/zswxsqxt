package com.zswxsqxt.core.query;

import com.opendata.common.base.BaseQuery;

/**
 * 用户信息
 */
public class SubscribQuery extends BaseQuery implements java.io.Serializable {

	private String id;
	
	/**
	 * 许可证号
	 */
	private String licenseNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}
}