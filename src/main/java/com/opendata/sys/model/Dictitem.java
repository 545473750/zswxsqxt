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

import com.opendata.application.model.Application;
import com.opendata.common.base.BaseEntity;

/**
 * 数据字典项
 * 
 * @author顾保臣
 */
@Entity
@Table(name = "t_dictitem")
public class Dictitem extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "数据字典项";
	public static final String ALIAS_ID = "数据字典ID";
	public static final String ALIAS_NAME = "字典名称";
	public static final String ALIAS_CODE = "字典编号";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_EDITF = "可编辑标识";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	public static final String ALIAS_APPLICATION_ID = "所属应用";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 数据字典ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 字典名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
    /**
     * 字典编号       db_column: code 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String code;
    /**
     * 描述       db_column: description 
     */ 	
	@Length(max=300)
	private java.lang.String description;
    /**
     * 可编辑标识       db_column: editf 
     */ 	
	@Length(max=1)
	private java.lang.String editf;
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
    /**
     * 所属应用       db_column: application_id 
     */ 	
	@Length(max=32)
	private java.lang.String applicationId;
	//columns END
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid.hex")
	@Column(name = "id",  nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getCode() {
		return this.code;
	}
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	@Column(name = "editf", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getEditf() {
		return this.editf;
	}
	public void setEditf(java.lang.String value) {
		this.editf = value;
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
	
	@Column(name = "application_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getApplicationId() {
		return this.applicationId;
	}
	public void setApplicationId(java.lang.String value) {
		this.applicationId = value;
	}
	
	/**
	 * 数据字典值
	 */
	private Set<Dictvalue> dictvalues = new HashSet<Dictvalue>(0);
	public void setDictvalues(Set<Dictvalue> dictvalue){
		this.dictvalues = dictvalue;
	}
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "dictitem")
	public Set<Dictvalue> getDictvalues() {
		return dictvalues;
	}
	
	/**
	 * 所属应用
	 */
	private Application application;
	public void setApplication(Application application){
		this.application = application;
	}
	@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = true)
	@JoinColumns({ @JoinColumn(name = "application_id", insertable = false, updatable = false) })
	public Application getApplication() {
		return application;
	}
	
	public Dictitem(){
	}
	public Dictitem(
		java.lang.String id
	){
		this.id = id;
	}
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Code",getCode())
			.append("Description",getDescription())
			.append("Editf",getEditf())
			.append("Ts",getTs())
			.append("Df",getDf())
			.append("ApplicationId",getApplicationId())
			.toString();
	}
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	public boolean equals(Object obj) {
		if(obj instanceof Dictitem == false) return false;
		if(this == obj) return true;
		Dictitem other = (Dictitem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

