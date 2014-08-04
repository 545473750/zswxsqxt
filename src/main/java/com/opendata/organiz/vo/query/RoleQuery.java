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
 * 角色的查询对象
 * @author 付威
 */
public class RoleQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	/** id */
	private java.lang.String id;
	/** 角色名称 */
	private java.lang.String name;
	/** 描述 */
	private java.lang.String description;
	/**
	 * 编号
	 */
	private String code;
	 
	/** createtime */
	private java.util.Date createtimeBegin;
	private java.util.Date createtimeEnd;
	private java.lang.String df;

	private String partitionId;
	
	public String getPartitionId() {
		return partitionId;
	}
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	public java.lang.String getId() {
		return this.id;
	}
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

