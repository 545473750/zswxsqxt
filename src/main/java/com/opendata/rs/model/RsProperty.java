/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.opendata.common.base.BaseEntity;

/**
 * 类型属性
 * 
 * @author 王海龙
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "rs_property")
public class RsProperty extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "类型属性";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ATTR_CODE = "属性编码";
	public static final String ALIAS_ATTR_NAME = "属性名称";
	public static final String ALIAS_INPUT_TYPE = "输入类型";
	public static final String ALIAS_OPTIONAL_VALUE = "可选值";
	public static final String ALIAS_ATTR_SOURCE = "属性来源";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_EDITABLE = "可编辑";
	
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
	@Length(max=4)
	private java.lang.String attrSource;//自定义：1，    教育部：2，    LOM：3
    /**
     * ts       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
    /**
     * editable       db_column: editable 
     */ 	
	@Length(max=4)
	private java.lang.String editable;//可编辑：0，   不可编辑：1
	//columns END

	public RsProperty(){
	}

	public RsProperty(
		java.lang.String id
	){
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
	
	@Column(name = "attr_source", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getAttrSource() {
		return this.attrSource;
	}
	
	public void setAttrSource(java.lang.String value) {
		this.attrSource = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	@Column(name = "editable", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getEditable() {
		return this.editable;
	}
	
	public void setEditable(java.lang.String value) {
		this.editable = value;
	}
	
	private Set<RsType> rsTypes;
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsType.class)
	@JoinTable(name="rs_type_property",joinColumns={@JoinColumn(name="property_id")},inverseJoinColumns={@JoinColumn(name="resources_type_id")})
	public Set<RsType> getRsTypes() {
		return rsTypes;
	}

	public void setRsTypes(Set<RsType> rsTypes) {
		this.rsTypes = rsTypes;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AttrCode",getAttrCode())
			.append("AttrName",getAttrName())
			.append("InputType",getInputType())
			.append("OptionalValue",getOptionalValue())
			.append("AttrSource",getAttrSource())
			.append("Ts",getTs())
			.append("Editable",getEditable())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RsProperty == false) return false;
		if(this == obj) return true;
		RsProperty other = (RsProperty)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

