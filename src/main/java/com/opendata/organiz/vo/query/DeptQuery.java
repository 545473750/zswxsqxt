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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.common.base.BaseQuery;

/**
 * @author 顾保臣
 */
public class DeptQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

    /**
     * ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
	/**
	 * 上级部门id
	 */
	private String parentId;
	/**
	 * 单位id
	 */
	private String organizationId;
    /**
     * 部门名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
	/**
	 * 部门领导
	 */
    private String leaderId;
	private String range;
	/**
     * 描述       db_column: description 
     */ 	
	@Length(max=1000)
	private java.lang.String description;
	
	
    /**
     * 创建时间       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
    /**
     * 删除标示       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df;
    /**
     * 排序号       db_column: sequence 
     */ 	
	private java.lang.Long sequence;
	
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public java.lang.String getDescription() {
		return description;
	}
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	public java.lang.String getTs() {
		return ts;
	}
	public void setTs(java.lang.String ts) {
		this.ts = ts;
	}
	public java.lang.String getDf() {
		return df;
	}
	public void setDf(java.lang.String df) {
		this.df = df;
	}
	public java.lang.Long getSequence() {
		return sequence;
	}
	public void setSequence(java.lang.Long sequence) {
		this.sequence = sequence;
	}
	
}