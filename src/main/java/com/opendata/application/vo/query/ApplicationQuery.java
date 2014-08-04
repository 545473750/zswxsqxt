/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;


/**
 * 应用的查询对象 可通过此对象的属性值来查询相关数据
 * @author 付威
 */
public class ApplicationQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 应用ID */
	private java.lang.String id;
	/** 应用编号 */
	private java.lang.String code;
	/** 应用名称 */
	private java.lang.String name;
	/** 应用描述 */
	private java.lang.String description;
	/** 应用状态 */
	private java.lang.String state;
	/** 创建时间 */
	private java.util.Date createtimeBegin;
	private java.util.Date createtimeEnd;
	/** 删除标示 */
	private java.lang.String df;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
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
	
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
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
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

