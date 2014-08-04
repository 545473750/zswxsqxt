/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.opendata.common.base.BaseEntity;

/**
 * 资源扩展属性
 * 
 * @author 王海龙
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "rs_expand_property")
public class RsExpandProperty extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "资源扩展属性";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ATTR_CODE = "属性编码";
	public static final String ALIAS_ATTR_NAME = "属性名称";
	public static final String ALIAS_ATTR_VALUE = "属性值";
	public static final String ALIAS_INPUT_TYPE = "属性类型";
	public static final String ALIAS_OPTIONAL_VALUE = "属性扩展值";
	public static final String ALIAS_ATTR_SOURCE = "属性来源";
	public static final String ALIAS_RESOURCES_ID = "关联资源";
	public static final String ALIAS_TS = "时间";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * id       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * attrCode       db_column: attr_code 
     */ 	
	@Length(max=10)
	private java.lang.String attrCode;
    /**
     * attrName       db_column: attr_name 
     */ 	
	@Length(max=100)
	private java.lang.String attrName;
    /**
     * attrValue       db_column: attr_value 
     */ 	
	@Length(max=5000)
	private java.lang.String attrValue;
    /**
     * inputType       db_column: input_type 
     */ 	
	@Length(max=4)
	private java.lang.String inputType;//文本框:1,  文本域:2,  单选组:3,  下拉菜单:4,  多选组:5
	/**
     * optionalValue       db_column: optional_value 
     */ 	
	@Length(max=2000)
	private java.lang.String optionalValue;
    /**
     * attrSource       db_column: attr_source 
     */ 	
	@Length(max=200)
	private java.lang.String attrSource;
    /**
     * resourcesId       db_column: resources_id 
     */ 	
	@Length(max=32)
	private java.lang.String resourcesId;
    /**
     * ts       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
	//columns END


	public RsExpandProperty(){
	}
	
	public RsExpandProperty(String id, String attrCode, String attrName,
			String attrValue, String inputType, String optionalValue,
			String attrSource, String resourcesId, String ts,
			RsResources rsResources) {
		super();
		this.id = id;
		this.attrCode = attrCode;
		this.attrName = attrName;
		this.attrValue = attrValue;
		this.inputType = inputType;
		this.optionalValue = optionalValue;
		this.attrSource = attrSource;
		this.resourcesId = resourcesId;
		this.ts = ts;
		this.rsResources = rsResources;
	}


	public RsExpandProperty(java.lang.String id){
		this.id = id;
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid.hex")
	@Column(name = "id",  nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "attr_code", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getAttrCode() {
		return this.attrCode;
	}
	
	public void setAttrCode(java.lang.String value) {
		this.attrCode = value;
	}
	
	@Column(name = "attr_name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getAttrName() {
		return this.attrName;
	}
	
	public void setAttrName(java.lang.String value) {
		this.attrName = value;
	}
	
	@Column(name = "attr_value", unique = false, nullable = true, insertable = true, updatable = true, length = 5000)
	public java.lang.String getAttrValue() {
		return this.attrValue;
	}
	
	public void setAttrValue(java.lang.String value) {
		this.attrValue = value;
	}
	
	@Column(name = "input_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getInputType() {
		return this.inputType;
	}
	
	public void setInputType(java.lang.String value) {
		this.inputType = value;
	}
	
	@Column(name = "optional_value", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public java.lang.String getOptionalValue() {
		return this.optionalValue;
	}
	
	public void setOptionalValue(java.lang.String value) {
		this.optionalValue = value;
	}
	
	@Column(name = "attr_source", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getAttrSource() {
		return this.attrSource;
	}
	
	public void setAttrSource(java.lang.String value) {
		this.attrSource = value;
	}
	
	@Column(name = "resources_id", unique = false, nullable = true, insertable = false, updatable = false, length = 32)
	public java.lang.String getResourcesId() {
		return this.resourcesId;
	}
	
	public void setResourcesId(java.lang.String value) {
		this.resourcesId = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	private RsResources rsResources;
	public void setRsResources(RsResources rsResources){
		this.rsResources = rsResources;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "resources_id",nullable = true, insertable = true, updatable = true) 
	})
	public RsResources getRsResources() {
		return rsResources;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AttrCode",getAttrCode())
			.append("AttrName",getAttrName())
			.append("AttrValue",getAttrValue())
			.append("InputType",getInputType())
			.append("AttrSource",getAttrSource())
			.append("ResourcesId",getResourcesId())
			.append("Ts",getTs())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RsExpandProperty == false) return false;
		if(this == obj) return true;
		RsExpandProperty other = (RsExpandProperty)obj;
		return new EqualsBuilder()
			.append(getAttrCode(),other.getAttrCode())
			.append(getId(),other.getId())
			.isEquals();
	}
}

