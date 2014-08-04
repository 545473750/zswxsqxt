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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
import com.opendata.sys.model.Partition;

/**
 * 应用的model类
 * @author 付威
 */
@Entity
@Table(name = "t_application")
public class Application extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "应用";
	public static final String ALIAS_ID = "应用";
	public static final String ALIAS_CODE = "应用编号";
	public static final String ALIAS_NAME = "应用名称";
	public static final String ALIAS_DESCRIPTION = "应用描述";
	public static final String ALIAS_STATE = "应用状态";
	public static final String ALIAS_CREATETIME = "创建时间";
	public static final String ALIAS_DF = "删除标示";
	public static final String ALIAS_TYPE="应用类型";
	public static final String ALIAS_PROPERTY="应用性质";//(注册|安装|基础组件)(自动判定)
	public static final String ALIAS_ICON="小图标";
	public static final String ALIAS_BIG_ICON="大图标";
	public static final String ALIAS_INITIALIZE_PAGE="初始化页面";
	public static final String ALIAS_CONFIGURATION_PAGE="配置管理页面";
	public static final String ALIAS_SEQUENCE = "排序号";
	public static final String ALIAS_VERSION = "版本";
	
	//date formats
	public static final String FORMAT_CREATETIME = DATE_TIME_FORMAT;
	
	
	public static final String PROPERTY_BASE="0";//表示应用性质为基础组件 
	public static final String PROPERTY_REGISTER="1";//表示应用性质为注册
	public static final String PROPERTY_INSTALL="2";//表示应用性质为安装
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 应用ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 应用编号       db_column: code 
     */ 	
	@NotBlank @Length(max=100,message="应用编号长度不能大于100")
	private java.lang.String code;
    /**
     * 应用名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100,message="应用名称长度不能大于100")
	private java.lang.String name;
    /**
     * 应用描述       db_column: description 
     */ 	
	@Length(max=100)
	private java.lang.String description;
	
	
    /**
     * 应用状态       db_column: state 对应数据字典
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String state="APP_STATE002";
    /**
     * ts       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
    /**
     * 删除标示       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df="0";
	//columns END
   
	/**
	 * 应用类型
	 */
	@Length(max=50)
	private String type;//应用类型
	
	/**
	 * 应用性质(注册|安装|基础组件)(自动判定)
	 */
	@NotBlank @Length(max=1)
	private String property=Application.PROPERTY_REGISTER;//应用性质(注册|安装|基础组件)(自动判定)
	
	/**
	 * 图标
	 */
	@Length(max=50,message="图标长度不能大于50")
	private String icon;//图标
	
	/**
	 * 大图标
	 */
	@Length(max=50,message="大图标长度不能大于50")
	private String bigIcon;//大图标
	
	/**
	 * 初始化页面
	 */
	@Length(max=50,message="初始化页面长度不能大于50")
	private String initializePage;//初始化页面
	
	/**
	 * 配置管理页面
	 */
	@Length(max=50,message="配置管理页面长度不能大于50")
	private String configurationPage;//配置管理页面
	
	/**
	 * 版本
	 */
	private String version ;//版本
	
	/**
     * 排序号       db_column: sequence 
     */ 	
	@NotNull 
	private int sequence;

	public Application(){
	}

	public Application(
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
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	

	
	
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	@Column(name = "version", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "property", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "big_icon", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}
	
	@Column(name = "sequence", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getSequence() {
		return this.sequence;
	}
	
	public void setSequence(int value) {
		this.sequence = value;
	}

	@Column(name = "initialize_page", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getInitializePage() {
		return initializePage;
	}

	public void setInitializePage(String initializePage) {
		this.initializePage = initializePage;
	}

	@Column(name = "configuration_page", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getConfigurationPage() {
		return configurationPage;
	}

	public void setConfigurationPage(String configurationPage) {
		this.configurationPage = configurationPage;
	}

	@Column(name = "state", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	

	

	
	@Column(name = "df", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
    private Set<Partition> partitions = new HashSet<Partition>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_partition_application", joinColumns={@JoinColumn(name="application_id")},inverseJoinColumns={@JoinColumn(name="partition_id")})
	public Set<Partition> getPartitions() {
		return partitions;
	}

	public void setPartitions(Set<Partition> partitions) {
		this.partitions = partitions;
	}
	@Transient
	Set<Permission> permissions = new HashSet<Permission>();
	
	@Transient
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Code",getCode())
			.append("Name",getName())
			.append("Description",getDescription())
			.append("State",getState())
			.append("ts",getTs())
			.append("Df",getDf())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Application == false) return false;
		if(this == obj) return true;
		Application other = (Application)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

