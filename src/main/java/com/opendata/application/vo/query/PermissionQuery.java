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
 * 应用访问入口的查询对象 可通过此对象的属性值来查询相关数据
 * @author 付威
 */
public class PermissionQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.String id;
	/** 编号 */
	private java.lang.String code;
	/** 名称 */
	private java.lang.String name;
	/** url */
	private java.lang.String url;
	/** 小图标 */
	private java.lang.String iconS;
	/** 大图标 */
	private java.lang.String iconB;
	/** 父ID */
	private java.lang.String parentId;
	/** 描述 */
	private java.lang.String description;
	
	private String applicationId;
	
	private java.util.Date createtimeBegin;
	private java.util.Date createtimeEnd;
	private String seq;//排序
	
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
	
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getIconS() {
		return this.iconS;
	}
	
	public void setIconS(java.lang.String value) {
		this.iconS = value;
	}
	
	public java.lang.String getIconB() {
		return this.iconB;
	}
	
	public void setIconB(java.lang.String value) {
		this.iconB = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	

	public java.lang.String getDf() {
		return df;
	}

	public void setDf(java.lang.String df) {
		this.df = df;
	}

	public java.util.Date getCreatetimeBegin() {
		return createtimeBegin;
	}

	public void setCreatetimeBegin(java.util.Date createtimeBegin) {
		this.createtimeBegin = createtimeBegin;
	}

	public java.util.Date getCreatetimeEnd() {
		return createtimeEnd;
	}

	public void setCreatetimeEnd(java.util.Date createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}
	
	

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}

