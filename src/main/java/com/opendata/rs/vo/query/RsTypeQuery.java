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
public class RsTypeQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** id */
	private java.lang.String id;
	/** typeCode */
	private java.lang.String typeCode;
	/** typeName */
	private java.lang.String typeName;
	/** typeDescription */
	private java.lang.String typeDescription;
	/** available */
	private java.lang.String available;
	/** ts */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getTypeCode() {
		return this.typeCode;
	}
	
	public void setTypeCode(java.lang.String value) {
		this.typeCode = value;
	}
	
	public java.lang.String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(java.lang.String value) {
		this.typeName = value;
	}
	
	public java.lang.String getTypeDescription() {
		return this.typeDescription;
	}
	
	public void setTypeDescription(java.lang.String value) {
		this.typeDescription = value;
	}
	
	public java.lang.String getAvailable() {
		return this.available;
	}
	
	public void setAvailable(java.lang.String value) {
		this.available = value;
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

