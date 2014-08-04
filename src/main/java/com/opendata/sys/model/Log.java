/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * 日志的model类
 * @author 付威
 */
@Entity
@Table(name = "t_log")
public class Log extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "日志";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_USERNAME = "操作人姓名";
	public static final String ALIAS_TYPE = "类型";
	public static final String ALIAS_IP = "IP地址";
	public static final String ALIAS_PERMISSION = "功能入口";
	public static final String ALIAS_RESOURCES = "菜单名称";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_DF = "删除标识";
	public static final String ALIAS_TS = "操作时间";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 操作人姓名       db_column: username 
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String username;
    /**
     * 类型       db_column: type 
     */ 	
	@NotBlank @Length(max=50)
	private java.lang.String type;
    /**
     * IP地址       db_column: ip 
     */ 	
	@Length(max=200)
	private java.lang.String ip;
    /**
     * 功能入口       db_column: permission 
     */ 	
	@Length(max=200)
	private java.lang.String permission;
    /**
     * 菜单名称       db_column: resources 
     */ 	
	@Length(max=200)
	private java.lang.String resources;
    /**
     * 内容       db_column: content 
     */ 	
	@Length(max=500)
	private java.lang.String content;
    /**
     * 删除标识       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df;
    /**
     * 操作时间       db_column: ts 
     */ 	
	@NotBlank @Length(max=19)
	private java.lang.String ts;
	//columns END


	public Log(){
	}

	public Log(
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
	
	@Column(name = "username", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	@Column(name = "type", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	@Column(name = "ip", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getIp() {
		return this.ip;
	}
	
	public void setIp(java.lang.String value) {
		this.ip = value;
	}
	
	@Column(name = "permission", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getPermission() {
		return this.permission;
	}
	
	public void setPermission(java.lang.String value) {
		this.permission = value;
	}
	
	@Column(name = "resources", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getResources() {
		return this.resources;
	}
	
	public void setResources(java.lang.String value) {
		this.resources = value;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String value) {
		this.content = value;
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
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Username",getUsername())
			.append("Type",getType())
			.append("Ip",getIp())
			.append("Permission",getPermission())
			.append("Resources",getResources())
			.append("Content",getContent())
			.append("Df",getDf())
			.append("Ts",getTs())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Log == false) return false;
		if(this == obj) return true;
		Log other = (Log)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

