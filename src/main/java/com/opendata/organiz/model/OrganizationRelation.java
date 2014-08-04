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
 * 单位关联表
 * @author 陈永锋
 * 
 */
@Entity
@Table(name = "t_organization_relation")
public class OrganizationRelation extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "单位关联表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_ORGANIZATION_ID = "组织机构ID";
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
	 * 关联类别
	 */
	@Length(max=2)
	private String relationType;
	/**
     * 组织机构ID       db_column: organizationId 
     */ 	
	@Length(max=32)
	private java.lang.String organizationId;
	/**
	 * 上级id
	 */
	@Length(max=32)
	private String parentId;
	
    

	/**
     * 排序号       db_column: sortNumber 
     */ 	
	
	private java.lang.Long sortNumber;
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
	
	@Column(name = "relationType", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	@Column(name = "organizationId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getOrganizationId() {
		return this.organizationId;
	}
	public void setOrganizationId(java.lang.String value) {
		this.organizationId = value;
	}
	
	@Column(name = "parentId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	@Column(name = "sortNumber", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSortNumber() {
		return this.sortNumber;
	}
	public void setSortNumber(java.lang.Long value) {
		this.sortNumber = value;
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
	
	// 上级关系
	private OrganizationRelation organizationRelation;
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "parentId", nullable = false, insertable = false, updatable = false) })
	public OrganizationRelation getOrganizationRelation() {
		return organizationRelation;
	}
	public void setOrganizationRelation(OrganizationRelation organizationRelation) {
		this.organizationRelation = organizationRelation;
	}
	
	public OrganizationRelation(){
	}
	public OrganizationRelation(
		java.lang.String id
	){
		this.id = id;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OrganizationId",getOrganizationId())
			.append("SortNumber",getSortNumber())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OrganizationRelation == false) return false;
		if(this == obj) return true;
		OrganizationRelation other = (OrganizationRelation)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

