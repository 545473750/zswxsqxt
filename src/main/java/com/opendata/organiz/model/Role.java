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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.common.base.BaseEntity;
import com.opendata.sys.model.Partition;
import com.opendata.sys.model.Resources;

/**
 * 角色的model类
 * @author 付威
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "角色";
	
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_CODE="编号";
	public static final String ALIAS_ROLENAME = "角色名称";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_CREATETIME = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	public static final String ALIAS_PARTITIONID = "分区";
	
	//date formats
	public static final String FORMAT_CREATETIME = DATE_TIME_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	/**
     * id       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 角色名称       db_column: name 
     */ 	
	@NotBlank @Length(max=40)
	private java.lang.String name;
	
	/**
     * 编号       db_column: code 
     */ 	
	@NotBlank @Length(max=40)
	private java.lang.String code;
    /**
     * 描述       db_column: description 
     */ 	
	@Length(max=500)
	private java.lang.String description;
	   /**
     * 创建时间       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
	//columns END

	/**
	 * 逻辑删除标识 0表示未删除 1表示已删除
	 */
	@Length(max=1)
	private java.lang.String df="0";
	
	@Length(max=32)
	private String partitionId;
	
	public Role(){
	}

	public Role(
			String id
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
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
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
	
	@Column(name = "partition_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getPartitionId() {
		return partitionId;
	}
	
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	
	private Partition partition ;
	
	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name = "partition_id",nullable = true, insertable = false, updatable = false) 
	})
	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}
	
	private Set<Resources> resources = new HashSet<Resources>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_role_resources", joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="resources_id")})
	public Set<Resources> getResources() {
		return resources;
	}

	public void setResources(Set<Resources> resources) {
		this.resources = resources;
	}
	
	private Set<User> users = new HashSet<User>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_user_role", joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Rolename",getName())
			.append("Description",getDescription())
			.append("ts",getTs())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Role == false) return false;
		if(this == obj) return true;
		Role other = (Role)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
}