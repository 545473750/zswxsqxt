/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;

/**
 * 系统同步查询对象
 * 
 * @author顾保臣
 */
public class SystemQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** id */
	private java.lang.String id;
	/** ts */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	/** df */
	private java.lang.String df;
	/** 编号 */
	private java.lang.String code;
	/** 名称 */
	private java.lang.String name;
	/** username */
	private java.lang.String username;
	/** password */
	private java.lang.String password;
	/** 系统负责人 */
	private java.lang.String manager;
	/** 联系方式 */
	private java.lang.String phone;
	/** 描述 */
	private java.lang.String remark;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getTsBegin() {
		return this.tsBegin;
	}
	
	public void setTsBegin(java.lang.String value) {
		this.tsBegin = value;
	}	
	
	public java.lang.String getTsEnd() {
		return this.tsEnd;
	}
	
	public void setTsEnd(java.lang.String value) {
		this.tsEnd = value;
	}
	
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getManager() {
		return this.manager;
	}
	
	public void setManager(java.lang.String value) {
		this.manager = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

