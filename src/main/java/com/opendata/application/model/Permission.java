/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.common.base.BaseEntity;

/**
 * 应用访问入口的model类
 * @author 付威
 */
@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "应用功能入口";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_CODE = "入口编号";
	public static final String ALIAS_NAME = "入口名称";
	public static final String ALIAS_URL = "URL";
	public static final String ALIAS_PARENT_ID = "父级入口";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_CREATETIME = "创建时间";
	public static final String ALIAS_TYPE = "显示类型";
	public static final String ALIAS_DF = "删除标识";
	public static final String ALIAS_ICON="小图标";
	public static final String ALIAS_BIGICON="大图标";
	public static final String ALIAS_SEQUENCE = "排序号";
	public static final String FORMAT_CREATETIME = DATE_TIME_FORMAT;
	
	
	//三种类别 目录 菜单访问入口 功能访问入口
	public static final String TYPE_DIC ="0";//目录
	
	public static final String TYPE_MENU = "1";//菜单访问入口
	
	public static final String TYPE_FUNCTION = "2";//功能访问入口
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	/**
     * id       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 编号       db_column: code 
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String code;
    /**
     * 名称       db_column: name 
     */ 	
	@NotBlank @Length(max=40)
	private java.lang.String name;
    /**
     * url       db_column: url 
     */ 	
	@Length(max=200)
	private java.lang.String url;
    /**
     * 图标       db_column: icon 
     */ 
	@Length(max=50)
    private String icon;
	
	/**
	 * 大图标
	 */
	@Length(max=50)
	
	private String bigIcon;
	/**
     * 父级入口       db_column: parent_id 
     */ 	
	@Length(max=32)
	private java.lang.String parentId;
	
	/**
	 * 应用ID
	 */
	@Length(max=32)			 
	private java.lang.String applicationId;
    /**
     * 描述       db_column: description 
     */ 	
	@Length(max=500)
	private java.lang.String description;
	//columns END
	 /**
     * 排序号       db_column: sequence 
     */ 	
	@NotNull 
	private int sequence;
	
	/**
	 * 删除标识
	 */
	@Length(max=1)
	private java.lang.String df="0";
	
	/**
     * 创建时间       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
	
	
	/**
     * 显示类型       db_column: type 
     */ 	
	@Length(max=100)
	private java.lang.String type=Permission.TYPE_DIC;//0表示应用入口目录 1表示主访问入口（带URL） 2 表示功能访问入口 父菜单为主访问入口


	public Permission(){
	}

	public Permission(java.lang.String id){
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
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "url", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name = "sequence", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getSequence() {
		return this.sequence;
	}
	
	public void setSequence(int value) {
		this.sequence = value;
	}
	@Column(name = "big_icon", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}
	
	
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}

	@Column(name = "application_id", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getApplicationId() {
		return this.applicationId;
	}
	
	public void setApplicationId(java.lang.String value) {
		this.applicationId = value;
	}
	
	private Application application;
	
	
	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name = "application_id",nullable = false, insertable = false, updatable = false) 
	})
	public Application getApplication() {
		return application;
	}
	
	public void setApplication(Application application){
		this.application = application;
	}
	
	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	@Column(name = "DF", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	
	private Set<Permission> permissions = new HashSet<Permission>(0);
	public void setPermissions(Set<Permission> permissions){
		this.permissions = permissions;
	}
	
	@Transient
	public Set<Permission> getPermissions() {
		return permissions;
	}
	
	private Permission parentPermission;
	public void setParentPermission(Permission parentPermission){
		this.parentPermission = parentPermission;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "parent_id",nullable = true, insertable = false, updatable = false) 
	})
	public Permission getParentPermission() {
		return parentPermission;
	}

	
	
	


	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Code",getCode())
			.append("Name",getName())
			.append("Url",getUrl())
			.append("Description",getDescription())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Permission == false) return false;
		if(this == obj) return true;
		Permission other = (Permission)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

