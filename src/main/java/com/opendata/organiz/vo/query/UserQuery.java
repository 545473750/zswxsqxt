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
 * 用户查询对象
 * 
 * @author 顾保臣
 */
public class UserQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	/** id */
	private java.lang.String id;
	/** loginname */
	private java.lang.String loginname;
	/** password */
	private java.lang.String password;
	/** 姓名 */
	private java.lang.String username;
	/** emaile */
	private java.lang.String emaile;
	/** phone */
	private java.lang.String phone;
	/** 手机 */
	private java.lang.String mobile;
	/** 部门ID */
	private java.lang.String deptId;
	private String orgids;
	private String orgId;
	private java.lang.String isLeave;	//是否离职   0 、是 1、否
	private String organizIds;
	
	/** 部门名称 */
	private String deptName;
	private String source;//0在编教师、1非在编教师、2外请教师、3外请专家、4外籍专家 、5外籍教师;
	/** 创建时间-开始 */
	private java.util.Date createtimeBegin;
	/** 创建时间-结束*/
	private java.util.Date createtimeEnd;
	private java.lang.String df;
	/** */
	private String abledFlag;
	
	/**  方法*/
	public java.lang.String getId() {
		return this.id;
	}
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getLoginname() {
		return this.loginname;
	}
	public void setLoginname(java.lang.String value) {
		this.loginname = value;
	}
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	public java.lang.String getEmaile() {
		return this.emaile;
	}
	public void setEmaile(java.lang.String value) {
		this.emaile = value;
	}
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	public java.lang.String getDeptId() {
		return this.deptId;
	}
	public void setDeptId(java.lang.String value) {
		this.deptId = value;
	}
	public java.util.Date getCreatetimeBegin() {
		return this.createtimeBegin;
	}
	public void setCreatetimeBegin(java.util.Date value) {
		this.createtimeBegin = value;
	}	
	public java.util.Date getCreatetimeEnd() {
		return this.createtimeEnd;
	}
	public void setCreatetimeEnd(java.util.Date value) {
		this.createtimeEnd = value;
	}
	public java.lang.String getDf() {
		return df;
	}
	public void setDf(java.lang.String df) {
		this.df = df;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getAbledFlag() {
		return abledFlag;
	}
	public void setAbledFlag(String abledFlag) {
		this.abledFlag = abledFlag;
	}
	public String getOrganizIds() {
		return organizIds;
	}
	public void setOrganizIds(String organizIds) {
		this.organizIds = organizIds;
	}
	public java.lang.String getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(java.lang.String isLeave) {
		this.isLeave = isLeave;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getOrgids() {
		return orgids;
	}
	public void setOrgids(String orgids) {
		this.orgids = orgids;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	

}

