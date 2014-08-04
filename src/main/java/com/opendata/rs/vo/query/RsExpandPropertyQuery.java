/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;

/**
 * @author 王海龙
 */
public class RsExpandPropertyQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	/** id */
	private java.lang.String id;
	/** attrCode */
	private java.lang.String attrCode;
	/** attrName */
	private java.lang.String attrName;
	/** attrValue */
	private java.lang.String attrValue;
	/** inputType */
	private java.lang.String inputType;
	/** attrSource */
	private java.lang.String attrSource;
	/** resourcesId */
	private java.lang.String resourcesId;
	/** ts */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getAttrCode() {
		return this.attrCode;
	}
	
	public void setAttrCode(java.lang.String value) {
		this.attrCode = value;
	}
	
	public java.lang.String getAttrName() {
		return this.attrName;
	}
	
	public void setAttrName(java.lang.String value) {
		this.attrName = value;
	}
	
	public java.lang.String getAttrValue() {
		return this.attrValue;
	}
	
	public void setAttrValue(java.lang.String value) {
		this.attrValue = value;
	}
	
	public java.lang.String getInputType() {
		return this.inputType;
	}
	
	public void setInputType(java.lang.String value) {
		this.inputType = value;
	}
	
	public java.lang.String getAttrSource() {
		return this.attrSource;
	}
	
	public void setAttrSource(java.lang.String value) {
		this.attrSource = value;
	}
	
	public java.lang.String getResourcesId() {
		return this.resourcesId;
	}
	
	public void setResourcesId(java.lang.String value) {
		this.resourcesId = value;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

