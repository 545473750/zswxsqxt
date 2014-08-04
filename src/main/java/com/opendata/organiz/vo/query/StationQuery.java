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
 * 岗位查询对象
 * 
 * @author 顾保臣
 */
public class StationQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.String id;
	/** 单位id */
	private java.lang.String organizationId;
	/**
	 * 部门id
	 */
	private String deptId ;
	/** 岗位名称 */
	private java.lang.String name;
	/** 岗位编号 */
	private java.lang.String code;
	/** 级别 */
	private java.lang.String level;
	/** 排序号 */
	private java.lang.Long sequence;
	/** 备注 */
	private java.lang.String remark;
	/** 创建时间 */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	/** 删除标识 */
	private java.lang.String df;

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
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getLevel() {
		return this.level;
	}
	
	public void setLevel(java.lang.String value) {
		this.level = value;
	}
	
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	
	public void setSequence(java.lang.Long value) {
		this.sequence = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}

