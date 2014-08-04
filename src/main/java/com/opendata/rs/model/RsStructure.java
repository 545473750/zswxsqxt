/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.opendata.common.base.BaseEntity;

/**
 * 体系结构
 * 
 * @author 王海龙
 * @version 1.0
 * 
 */
@Entity
@Table(name = "rs_structure")
public class RsStructure extends BaseEntity implements java.io.Serializable,Comparable<RsStructure>{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "体系结构";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_PARENT_ID = "父级节点";
	public static final String ALIAS_SORT = "顺序";
	public static final String ALIAS_TS = "创建时间";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * id       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * name       db_column: name 
     */ 	
	@Length(max=100)
	private java.lang.String name;
    /**
     * description       db_column: description 
     */ 	
	@Length(max=500)
	private java.lang.String description;
    /**
     * parentId       db_column: parent_id 
     */ 	
	@Length(max=32)
	private java.lang.String parentId;
    /**
     * ts       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
	
	private Integer sort;
	//columns END


	public RsStructure(){
	}

	public RsStructure(
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
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
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
	
	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	@Column(name = "sort", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	private Set<RsStructure> rsStructures;
	@OneToMany(cascade={},fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsStructure.class)
	@JoinColumn(name="parent_id")
	public Set<RsStructure> getRsStructures() {
		return rsStructures;
	}

	public void setRsStructures(Set<RsStructure> rsStructures) {
		this.rsStructures = rsStructures;
	}

//	private Set<RsResources> rsResourcesSet;
	private Set<RsResources> rsResourcess;
	
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsResources.class)
	@JoinTable(name="rs_resources_structure",joinColumns={@JoinColumn(name="structure_id")},inverseJoinColumns={@JoinColumn(name="resources_id")})
	public Set<RsResources> getRsResourcess() {
		return rsResourcess;
	}
	
	public void setRsResourcess(Set<RsResources> rsResourcess) {
		this.rsResourcess = rsResourcess;
	}
	
	private RsStructure rsStructure;
	
	@ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=RsStructure.class)
	@JoinColumn(name="parent_id",insertable=false,updatable=false)
	public RsStructure getRsStructure() {
		return rsStructure;
	}

	public void setRsStructure(RsStructure rsStructure) {
		this.rsStructure = rsStructure;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Description",getDescription())
			.append("ParentId",getParentId())
			.append("Ts",getTs())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RsStructure == false) return false;
		if(this == obj) return true;
		RsStructure other = (RsStructure)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Override
	public int compareTo(RsStructure o) {
		return this.getSort()-o.getSort();
	}
}

