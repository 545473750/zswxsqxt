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
 * 系统变量查询对象
 * 
 * @author顾保臣
 */
public class SysvariableQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 主键ID */
	private java.lang.String id;
	/** 变量名称 */
	private java.lang.String name;
	/** 变量值 */
	private java.lang.String code;
	/** 描述 */
	private java.lang.String discription;
	/** 创建时间 */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	/** df */
	private java.lang.String df;
	
	/** 所属应用 **/
	private String applicationId;
	
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
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
	
	public java.lang.String getDiscription() {
		return this.discription;
	}
	
	public void setDiscription(java.lang.String value) {
		this.discription = value;
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
	
}

