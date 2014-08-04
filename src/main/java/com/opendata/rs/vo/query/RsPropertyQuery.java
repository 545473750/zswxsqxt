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
public class RsPropertyQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	/** id */
	private java.lang.String id;
	/** attrCode */
	private java.lang.String attrCode;
	/** attrName */
	private java.lang.String attrName;
	/** inputType */
	private java.lang.String inputType;
	/** optionalValue */
	private java.lang.String optionalValue;
	/** attrSource */
	private java.lang.String attrSource;
	/** ts */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	/** editable */
	private java.lang.String editable;

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
	
	public java.lang.String getInputType() {
		return this.inputType;
	}
	
	public void setInputType(java.lang.String value) {
		this.inputType = value;
	}
	
	public java.lang.String getOptionalValue() {
		return this.optionalValue;
	}
	
	public void setOptionalValue(java.lang.String value) {
		this.optionalValue = value;
	}
	
	public java.lang.String getAttrSource() {
		return this.attrSource;
	}
	
	public void setAttrSource(java.lang.String value) {
		this.attrSource = value;
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
	
	public java.lang.String getEditable() {
		return this.editable;
	}
	
	public void setEditable(java.lang.String value) {
		this.editable = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

