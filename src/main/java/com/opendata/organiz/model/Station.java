/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.model;

import java.util.HashSet;
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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.common.base.BaseEntity;
import com.opendata.sys.model.Resources;

/**
 * 岗位
 * 
 * @author 顾保臣
 * 
 */
@Entity
@Table(name = "t_station")
public class Station extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "岗位";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ORGANIZATION_ID = "所属组织机构";
	public static final String ALIAS_NAME = "岗位名称";
	public static final String ALIAS_CODE = "岗位编号";
	public static final String ALIAS_LEVEL = "级别";
	public static final String ALIAS_SEQUENCE = "排序号";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 所属单位      db_column: organization_id 
     */ 	
	@Length(max=32)
	private java.lang.String organizationId;
	
	private String deptId;
    /**
     * 岗位名称       db_column: name 
     */ 	
	@NotBlank @Length(max=20)
	private java.lang.String name;
    /**
     * 岗位编号       db_column: code 
     */ 	
	@Length(max=30)
	private java.lang.String code;
    /**
     * 级别       db_column: level 
     */ 	
	@Length(max=30)
	private java.lang.String level;
    /**
     * 排序号       db_column: sequence 
     */ 	
	private java.lang.Long sequence;
	
	private String parentId;//直接上级
	
    /**
     * 备注       db_column: remark 
     */ 	
	@Length(max=200)
	private java.lang.String remark;
    /**
     * 创建时间       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
    /**
     * 删除标识       db_column: df 
     */ 	
	@Length(max=1)
	private java.lang.String df;
	//columns END
	
	@Transient
	private java.lang.String orgString;
	@Transient
	private java.lang.String levelString;
	@Transient
	public java.lang.String getOrgString() {
		return orgString;
	}
	@Transient
	public java.lang.String getLevelString() {
		return levelString;
	}
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid.hex")
	@Column(name = "id",  nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	@Column(name = "organization_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getOrganizationId() {
		return this.organizationId;
	}
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getName() {
		return this.name;
	}
	@Column(name = "code", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getCode() {
		return this.code;
	}
	@Column(name = "level", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getLevel() {
		return this.level;
	}
	@Column(name = "sequence", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getRemark() {
		return this.remark;
	}
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	@Column(name = "df", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getDf() {
		return this.df;
	}
	@Column(name = "deptId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "parentId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	// 组织机构
	private Organization organization;
	public void setOrganization(Organization organization){
		this.organization = organization;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "organization_id",nullable = true, insertable = false, updatable = false) })
	public Organization getOrganization() {
		return organization;
	}
	
	private Dept dept;
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "deptId",nullable = true, insertable = false, updatable = false) })
	public Dept getDept() {
		return dept;
	}
	
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	private Station station;
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "parentId",nullable = true, insertable = false, updatable = false) })
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	
	private Set<Resources> resources = new HashSet<Resources>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_station_resources", joinColumns={@JoinColumn(name="station_id")},inverseJoinColumns={@JoinColumn(name="resources_id")})
	public Set<Resources> getResources() {
		return resources;
	}

	public void setResources(Set<Resources> resources) {
		this.resources = resources;
	}
	
	// 用户
	private Set<User> users = new HashSet<User>(0);
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_user_station", joinColumns={@JoinColumn(name="station_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	public Set<User> getUsers() {
		return users;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OrganizationId",getOrganizationId())
			.append("Name",getName())
			.append("Code",getCode())
			.append("Level",getLevel())
			.append("Sequence",getSequence())
			.append("Remark",getRemark())
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
		if(obj instanceof Station == false) return false;
		if(this == obj) return true;
		Station other = (Station)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	public void setLevelString(java.lang.String levelString) {
		this.levelString = levelString;
	}
	public void setOrgString(java.lang.String orgString) {
		this.orgString = orgString;
	}
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public void setOrganizationId(java.lang.String value) {
		this.organizationId = value;
	}
	public void setSequence(java.lang.Long value) {
		this.sequence = value;
	}
	public void setLevel(java.lang.String value) {
		this.level = value;
	}
	public Station(){
	}
	public Station(
		java.lang.String id
	){
		this.id = id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	
	
	
	
	
}

