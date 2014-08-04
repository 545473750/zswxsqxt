/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.common.base.BaseEntity;

/**
 * 数据字典值
 * 
 * @author顾保臣
 */
@Entity
@Table(name = "t_dictvalue")
public class Dictvalue extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "数据字典值";
	public static final String ALIAS_ID = "字典值ID";
	public static final String ALIAS_CODE = "字典值编号";
	public static final String ALIAS_VALUE = "字典值";
	public static final String ALIAS_PARENT_ID = "父ID";
	public static final String ALIAS_DICTITEM_ID = "字典项";
	public static final String ALIAS_SEQUENCE = "排序号";
	public static final String ALIAS_DISCRIPTION = "描述";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 字典值ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 字典值编号       db_column: code 
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String code;
    /**
     * 字典值       db_column: value 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String value;
    /**
     * 父ID       db_column: parent_id 
     */ 	
	@Length(max=32)
	private java.lang.String parentId;
    /**
     * 字典项ID       db_column: dictitem_id 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String dictitemId;
    /**
     * 排序号       db_column: sequence 
     */ 	
	
	private java.lang.Long sequence;
    /**
     * 描述       db_column: discription 
     */ 	
	@Length(max=300)
	private java.lang.String discription;
    /**
     * 创建时间       db_column: ts 
     */ 	
	@NotBlank @Length(max=19)
	private java.lang.String ts;
    /**
     * 删除标识       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df;
	//columns END

	public void setId(java.lang.String value) {
		this.id = value;
	}
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid.hex")
	@Column(name = "id",  nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	@Column(name = "value", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getValue() {
		return this.value;
	}
	
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	//----------------------------------------------------------外键维护
	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getParentId() {
		return this.parentId;
	}
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	
	@Column(name = "dictitem_id", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getDictitemId() {
		return this.dictitemId;
	}
	public void setDictitemId(java.lang.String value) {
		this.dictitemId = value;
	}
	//----------------------------------------------------------外键维护
	
	@Column(name = "sequence", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	
	public void setSequence(java.lang.Long value) {
		this.sequence = value;
	}
	
	@Column(name = "discription", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public java.lang.String getDiscription() {
		return this.discription;
	}
	
	public void setDiscription(java.lang.String value) {
		this.discription = value;
	}
	
	@Column(name = "ts", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	@Column(name = "df", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	
	/**
	 * 字典值的子值
	 */
	private Set<Dictvalue> dictvalues = new HashSet<Dictvalue>(0);
	public void setDictvalues(Set<Dictvalue> dictvalue){
		this.dictvalues = dictvalue;
	}
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "dictvalue")
	public Set<Dictvalue> getDictvalues() {
		return dictvalues;
	}
	
	/**
	 * 字典值的父值
	 */
	private Dictvalue dictvalue;
	public void setDictvalue(Dictvalue dictvalue){
		this.dictvalue = dictvalue;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "parent_id",nullable = false, insertable = false, updatable = false) 
	})
	public Dictvalue getDictvalue() {
		return dictvalue;
	}
	
	/**
	 * 字典值所属字典项
	 */
	private Dictitem dictitem;
	public void setDictitem(Dictitem dictitem){
		this.dictitem = dictitem;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name = "dictitem_id",nullable = false, insertable = false, updatable = false) 
	})
	public Dictitem getDictitem() {
		return dictitem;
	}

	// 直接指向某一字典项
	private String relationItem;
	@Column(name = "relationItem", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getRelationItem() {
		return relationItem;
	}
	public void setRelationItem(String relationItem) {
		this.relationItem = relationItem;
	}
	
	public Dictvalue(){
	}
	public Dictvalue(
		java.lang.String id
	){
		this.id = id;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Code",getCode())
			.append("Value",getValue())
			.append("ParentId",getParentId())
			.append("DictitemId",getDictitemId())
			.append("Sequence",getSequence())
			.append("Discription",getDiscription())
			.append("Ts",getTs())
			.append("Df",getDf())
			.toString();
	}
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	public boolean equals(Object obj) {
		if(obj instanceof Dictvalue == false) return false;
		if(this == obj) return true;
		Dictvalue other = (Dictvalue)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

