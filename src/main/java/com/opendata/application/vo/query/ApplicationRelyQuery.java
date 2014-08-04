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
 * 应用依赖关系的查询对象 可通过此对象的属性值来查询相关数据
 * @author 付威
 */
public class ApplicationRelyQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 主键ID */
	private java.lang.String id;
	/** 应用ID */
	private java.lang.String applicationId;
	/** 被依赖应用ID */
	private java.lang.String relyApplicationId;
	/** 删除标识 */
	private java.lang.String df;
	/** 创建时间 */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	/** 依赖类型 */
	private java.lang.String type;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getApplicationId() {
		return this.applicationId;
	}
	
	public void setApplicationId(java.lang.String value) {
		this.applicationId = value;
	}
	
	public java.lang.String getRelyApplicationId() {
		return this.relyApplicationId;
	}
	
	public void setRelyApplicationId(java.lang.String value) {
		this.relyApplicationId = value;
	}
	
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
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
	
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

