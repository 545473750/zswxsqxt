/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

import com.opendata.application.model.Permission;
import com.opendata.common.base.BaseEntity;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.Station;

/**
 * 菜单的model类
 * @author 付威
 */
@Entity
@Table(name = "t_resources")
public class Resources extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "菜单";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_CODE = "编码";
	public static final String ALIAS_SEQUENCE = "排序号";
	public static final String ALIAS_PARENT_ID = "父菜单";
	public static final String ALIAS_PERMISSION_ID = "应用功能入口";
	public static final String ALIAS_ICON = "小图标";
	public static final String ALIAS_BIG_ICON = "大图标";
	public static final String ALIAS_TYPE = "显示类型";
	public static final String ALIAS_KIND ="是否弹出窗口";//0表示不是弹出窗口 1表示是弹出窗口
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	
	
	//三种类别 目录 菜单访问入口 功能访问入口
	public static final String TYPE_DIC ="0";//目录
	
	public static final String TYPE_MENU = "1";//菜单访问入口
	
	public static final String TYPE_FUNCTION = "2";//功能访问入口
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 菜单名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
    /**
     * 菜单编码       db_column: code 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String code;
    /**
     * 排序号       db_column: sequence 
     */ 	
	@NotNull 
	private int sequence;
    /**
     * 父ID       db_column: parent_id 
     */ 	
	@Length(max=32)
	private java.lang.String parentId;
    /**
     * 应用功能入口       db_column: permission_id 
     */ 	
	@Length(max=32)
	private java.lang.String permissionId;
    /**
     * 小图标       db_column: icon 
     */ 	
	@Length(max=100)
	private java.lang.String icon;
    /**
     * 大图标       db_column: big_icon 
     */ 	
	@Length(max=100)
	private java.lang.String bigIcon;
    /**
     * 显示类型       db_column: type 
     */ 	
	@Length(max=100)
	private java.lang.String type = TYPE_DIC;//0表示菜单目录 1表示主菜单（带URL） 2 表示功能菜单 父菜单为主菜单
	
	/**
     * 弹出窗口       db_column: kind 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String kind="0";//0表示不是弹出窗口 1表示是弹出窗口
    /**
     * 创建时间       db_column: ts 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String ts;
    /**
     * 删除标识       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df ="0";
	//columns END


	public Resources(){
	}

	public Resources(
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
	
	@Column(name = "sequence", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getSequence() {
		return this.sequence;
	}
	
	public void setSequence(int value) {
		this.sequence = value;
	}
	
	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	
	@Column(name = "permission_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getPermissionId() {
		return this.permissionId;
	}
	
	public void setPermissionId(java.lang.String value) {
		this.permissionId = value;
	}
	
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setIcon(java.lang.String value) {
		this.icon = value;
	}
	
	@Column(name = "big_icon", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getBigIcon() {
		return this.bigIcon;
	}
	
	public void setBigIcon(java.lang.String value) {
		this.bigIcon = value;
	}
	
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	@Column(name = "kind", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getKind() {
		return kind;
	}

	public void setKind(java.lang.String kind) {
		this.kind = kind;
	}

	@Column(name = "ts", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
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
	
	
	private List<Resources> resourcess = new ArrayList<Resources>();
	public void setResourcess(List<Resources> resources){
		this.resourcess = resources;
	}
	
	@Transient
	public List<Resources> getResourcess() {
		return resourcess;
	}
	
	private Resources resources;
	public void setResources(Resources resources){
		this.resources = resources;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "parent_id",nullable = true, insertable = false, updatable = false) 
	})
	public Resources getResources() {
		return resources;
	}
	
	private Permission permission;
	public void setPermission(Permission permission){
		this.permission = permission;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name = "permission_id",nullable = true, insertable = false, updatable = false) 
	})
	public Permission getPermission() {
		return permission;
	}
	
	
	private Set<Role> roles = new HashSet<Role>(0);
	
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_role_resources", joinColumns={@JoinColumn(name="resources_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	private Set<Station> stations = new HashSet<Station>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_station_resources", joinColumns={@JoinColumn(name="resources_id")},inverseJoinColumns={@JoinColumn(name="station_id")})
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}


	private String checked="0";
	@Transient
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	
	private String url;
	
	@Transient
	public String getUrl() {
		if(getPermission()!=null){
			return getPermission().getUrl();
		}
		return "";
		
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Code",getCode())
			.append("Sequence",getSequence())
			.append("ParentId",getParentId())
			.append("PermissionId",getPermissionId())
			.append("Icon",getIcon())
			.append("BigIcon",getBigIcon())
			.append("Type",getType())
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
		if(obj instanceof Resources == false) return false;
		if(this == obj) return true;
		Resources other = (Resources)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

