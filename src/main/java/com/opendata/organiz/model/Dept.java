/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
 * 部门
 * @since 2014-04-25
 * @author 陈永锋
 * 
 */
@Entity
@Table(name = "t_dept")
public class Dept extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	//columns START
    /**
     * ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
	/**
	 * 上级部门id
	 */
	private String parentId;
	/**
	 * 单位id
	 */
	private String organizationId;
    /**
     * 部门名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
	/**
	 * 部门领导
	 */
    private String leaderId;
    /**
     * 范围
     */
	private String scope;
	/**
     * 描述       db_column: description 
     */ 	
	@Length(max=1000)
	private java.lang.String description;
	
	
    /**
     * 创建时间       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
    /**
     * 删除标示       db_column: df 
     */ 	
	@Length(max=1)
	private java.lang.String df;
    /**
     * 排序号       db_column: sequence 
     */ 	
	private java.lang.Long sequence;
	
	private String code;//部门编码
	
	public Dept(){
	}

	public Dept(
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
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
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
	
	@Column(name = "sequence", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	
	public void setSequence(java.lang.Long value) {
		this.sequence = value;
	}
	
	@Column(name = "organizationId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	@Column(name = "parentId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name = "leaderId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	
	@Column(name = "scope", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	@Column(name = "code", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	
	//下级部门
	private Set<Dept> depts = new HashSet<Dept>(0);
	public void setDepts(Set<Dept> dept){
		this.depts = dept;
	}
	
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "dept")
	@OrderBy(" sequence ASC ")
	public Set<Dept> getDepts() {
		return depts;
	}
	//上级部门
	private Dept dept;
	public void setDept(Dept dept){
		this.dept = dept;
	}
	
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "parentId",nullable = true, insertable = false, updatable = false) 
	})
	public Dept getDept() {
		return dept;
	}
	
	/**
	 * 部门领导
	 */
	private User leader;
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "leaderId",nullable = true, insertable = false, updatable = false) 
	})
	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}
	
	/**
	 * 部门所属单位
	 */
	private Organization organization;
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "organizationId",nullable = true, insertable = false, updatable = false) 
	})
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	

	/*// 岗位
	private Set<Station> stations = new HashSet<Station>(0);
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}*/
	


	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Description",getDescription())
			.append("Ts",getTs())
			.append("Sequence",getSequence())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Dept == false) return false;
		if(this == obj) return true;
		Dept other = (Dept)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public java.lang.String getDf() {
		return df;
	}

	public void setDf(java.lang.String df) {
		this.df = df;
	}

	

	

	

	

	

	

	
}

