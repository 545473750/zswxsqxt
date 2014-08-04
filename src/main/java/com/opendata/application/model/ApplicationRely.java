/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.model;

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

import com.opendata.common.base.BaseEntity;

/**
 * 应用依赖关系的model类
 * @author 付威
 */
@Entity
@Table(name = "t_application_rely")
public class ApplicationRely extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ApplicationRely";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_APPLICATION_ID = "应用ID";
	public static final String ALIAS_RELY_APPLICATION_ID = "被依赖应用ID";
	public static final String ALIAS_DF = "删除标识";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_TYPE = "依赖类型";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 应用ID       db_column: application_id 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String applicationId;
    /**
     * 被依赖应用ID       db_column: rely_application_id 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String relyApplicationId;
    /**
     * 删除标识       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df;
    /**
     * 创建时间       db_column: ts 
     */ 	
	@NotBlank @Length(max=19)
	private java.lang.String ts;
    /**
     * 依赖类型       db_column: type 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String type;
	//columns END


	public ApplicationRely(){
	}

	public ApplicationRely(
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
	
	@Column(name = "application_id", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getApplicationId() {
		return this.applicationId;
	}
	
	public void setApplicationId(java.lang.String value) {
		this.applicationId = value;
	}
	
	@Column(name = "rely_application_id", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getRelyApplicationId() {
		return this.relyApplicationId;
	}
	
	public void setRelyApplicationId(java.lang.String value) {
		this.relyApplicationId = value;
	}
	
	@Column(name = "df", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	
	@Column(name = "ts", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	@Column(name = "type", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	
	private Application application;
	public void setApplication(Application application){
		this.application = application;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "application_id",nullable = false, insertable = false, updatable = false) 
	})
	public Application getApplication() {
		return application;
	}
	
	
	private Application relyApplication;
	
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "rely_application_id",nullable = false, insertable = false, updatable = false) 
	})
	public Application getRelyApplication() {
		return relyApplication;
	}

	public void setRelyApplication(Application relyApplication) {
		this.relyApplication = relyApplication;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ApplicationId",getApplicationId())
			.append("RelyApplicationId",getRelyApplicationId())
			.append("Df",getDf())
			.append("Ts",getTs())
			.append("Type",getType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ApplicationRely == false) return false;
		if(this == obj) return true;
		ApplicationRely other = (ApplicationRely)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

