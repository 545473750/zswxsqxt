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
 * 数据字典值查询对象
 * 
 * @author顾保臣
 */
public class DictvalueQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 字典值ID */
	private java.lang.String id;
	/** 字典编码 */
	private java.lang.String code;
	/** 字典值 */
	private java.lang.String value;
	
	/** 字典值父值 **/
	private String parentValue;
	/** 字典项ID */
	private java.lang.String dictitemId;
	/** 描述 */
	private java.lang.String discription;
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
	
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getDictitemId() {
		return this.dictitemId;
	}
	
	public void setDictitemId(java.lang.String value) {
		this.dictitemId = value;
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

	public String getParentValue() {
		return parentValue;
	}

	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

