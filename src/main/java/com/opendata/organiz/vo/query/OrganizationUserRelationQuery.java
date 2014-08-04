/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;

/**
 * 组织机构和用户关系查询对象
 * @author 顾保臣
 */
public class OrganizationUserRelationQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** ID */
	private java.lang.String id;
	/** 组织机构ID */
	private java.lang.String organizationId;
	/** 用户ID */
	private java.lang.String userId;
	/** 排序号 */
	private java.lang.Long sortNumber;
	
	private String username;
	private String userNum;
	private String orgname;
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getOrganizationId() {
		return this.organizationId;
	}
	
	public void setOrganizationId(java.lang.String value) {
		this.organizationId = value;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	public java.lang.Long getSortNumber() {
		return this.sortNumber;
	}
	
	public void setSortNumber(java.lang.Long value) {
		this.sortNumber = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	
}

