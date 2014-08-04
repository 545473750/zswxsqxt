/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.model;

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
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.application.model.Application;
import com.opendata.common.base.BaseEntity;

/**
 * 系统变量对象
 * 
 * @author顾保臣
 */
@Entity
@Table(name = "t_sysvariable")
public class Sysvariable extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "系统变量";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_NAME = "变量名称";
	public static final String ALIAS_CODE = "变量值";
	public static final String ALIAS_DISCRIPTION = "描述";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标记";
	public static final String ALIAS_APPLICATION_ID = "所属应用";
	
	//date formats

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 变量名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
    /**
     * 变量值       db_column: code 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String code;
    /**
     * 描述       db_column: discription 
     */ 	
	@Length(max=500)
	private java.lang.String discription;
    /**
     * 创建时间       db_column: ts 
     */ 	
	@NotBlank @Length(max=19)
	private java.lang.String ts;
    /**
     * df       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df="0";
	
	/**
     * 所属应用       db_column: applicationId 
     */ 	
	@Length(max=32)
	private java.lang.String applicationId;
	//columns END

	public Sysvariable(){
	}
	public Sysvariable(
		java.lang.String id
	){
		this.id = id;
	}
	
	@Column(name = "applicationId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getApplicationId() {
		return this.applicationId;
	}
	public void setApplicationId(java.lang.String value) {
		this.applicationId = value;
	}
	/** 所属应用 **/
	private Application application;
	public void setApplication(Application application){
		this.application = application;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "applicationId", nullable = false, insertable = false, updatable = false) })
	public Application getApplication() {
		return application;
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
	
	@Column(name = "discription", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Code",getCode())
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
		if(obj instanceof Sysvariable == false) return false;
		if(this == obj) return true;
		Sysvariable other = (Sysvariable)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

