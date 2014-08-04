/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.model;

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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.application.model.Application;
import com.opendata.common.base.BaseEntity;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.User;

/**
 * 分区的model类
 * @author 付威
 */
@Entity
@Table(name = "t_partition")
public class Partition extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "分区";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_NAME = "分区名称";
	public static final String ALIAS_DESCRIPTION = "分区描述";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 分区名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
	/**
	 * 分区类别
	 */
	@NotBlank @Length(max=1)
	private String type;
    /**
     * 分区描述       db_column: description 
     */ 	
	@Length(max=500)
	private java.lang.String description;
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
	
	@Length(max=2)
	private String relationType;
	
	private java.lang.Long sortNumber;//排序
	
	private String orgIds;//分区对应的单位id集合，字符串，逗号分隔

	public Partition(){
	}

	public Partition(
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
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "relationType", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	@Column(name = "sortNumber", unique = false, nullable = true, insertable = true, updatable = true)
	public java.lang.Long getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(java.lang.Long sortNumber) {
		this.sortNumber = sortNumber;
	}
	
	@Column(name = "orgIds", unique = false, nullable = true, insertable = true, updatable = true, columnDefinition="text" )
	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
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
	
	
	/*private Set<User> users = new HashSet<User>(0);
	public void setUsers(Set<User> user){
		this.users = user;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="t_partition_user", joinColumns={@JoinColumn(name="partition_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	public Set<User> getUsers() {
		return users;
	}
	
	private Set<Application> applications = new HashSet<Application>(0);
	public void setApplications(Set<Application> application){
		this.applications = application;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="t_partition_application", joinColumns={@JoinColumn(name="partition_id")},inverseJoinColumns={@JoinColumn(name="application_id")})
	public Set<Application> getApplications() {
		return applications;
	}*/
	
	private Set<Organization> organizations = new HashSet<Organization>(0);
	
	
	
	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="t_organiz_partition", joinColumns={@JoinColumn(name="partition_id")},inverseJoinColumns={@JoinColumn(name="organiz_id")})
	public Set<Organization> getOrganizations() {
		return organizations;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Description",getDescription())
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
		if(obj instanceof Partition == false) return false;
		if(this == obj) return true;
		Partition other = (Partition)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	
	
	

	
}

