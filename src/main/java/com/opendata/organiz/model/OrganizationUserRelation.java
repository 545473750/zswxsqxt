/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.model;

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

import com.opendata.common.base.BaseEntity;

/**
 * 组织机构关联用户关系对象
 * 
 * @author 顾保臣
 * 
 */
@Entity
@Table(name = "t_organization_user_relation")
public class OrganizationUserRelation extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "组织机构关联用户关系";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_ORGANIZATION_ID = "组织机构ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_SORT_NUMBER = "排序号";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 组织机构ID       db_column: organizationId 
     */ 	
	@Length(max=32)
	private java.lang.String organizationId;
    /**
     * 用户ID       db_column: userId 
     */ 	
	@Length(max=32)
	private java.lang.String userId;
    /**
     * 排序号       db_column: sortNumber 
     */ 	
	
	private java.lang.Long sortNumber;
	/*
	 * 机构类别：1：单位（人员）；2部门(人员)
	 */
	private String type;
	
	//columns END

	public void setId(java.lang.String value) {
		this.id = value;
	}
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid.hex")
	@Column(name = "id",  nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "organizationId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getOrganizationId() {
		return this.organizationId;
	}
	public void setOrganizationId(java.lang.String value) {
		this.organizationId = value;
	}
	
	@Column(name = "userId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "sortNumber", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSortNumber() {
		return this.sortNumber;
	}
	public void setSortNumber(java.lang.Long value) {
		this.sortNumber = value;
	}
	
	// 用户
	private User user;
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false) })
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	// 组织机构
	private Organization organization;
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "organizationId", nullable = false, insertable = false, updatable = false) })
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	
	public OrganizationUserRelation(){
	}
	public OrganizationUserRelation(
		java.lang.String id
	){
		this.id = id;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OrganizationId",getOrganizationId())
			.append("UserId",getUserId())
			.append("SortNumber",getSortNumber())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OrganizationUserRelation == false) return false;
		if(this == obj) return true;
		OrganizationUserRelation other = (OrganizationUserRelation)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

}

