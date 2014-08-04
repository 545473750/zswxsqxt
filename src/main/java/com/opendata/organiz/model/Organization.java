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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
import com.opendata.sys.model.Partition;

/**
 * 组织机构
 * 
 * @author 顾保臣
 * 
 */
@Entity
@Table(name = "t_organization")
public class Organization extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "组织机构";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_NAME = "组织机构名称";
	public static final String ALIAS_TYPE = "组织机构类别";
	public static final String ALIAS_CODE = "组织机构编号";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "删除标示";
	public static final String ALIAS_SEQUENCE = "排序号";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * ID       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 组织机构名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
    /**
     * 组织机构类别       db_column: type 
     */ 	
	@Length(max=100)
	private java.lang.String type;
    /**
     * 组织机构编码       db_column: code 
     */ 	
	@Length(max=100)
	private java.lang.String code;
    /**
     * 描述       db_column: description 
     */ 	
	@Length(max=1000)
	private java.lang.String description;
	/**
	 * 范围
	 */
	@Length(max=100)
	private String scope;
	
    /**
     * 创建时间       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
    /**
     * 删除标示       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df;
    /**
     * 排序号       db_column: sequence 
     */ 	
	private java.lang.Long sequence;
	/**
	 * 单位级别
	 */
	@Length(max=50)
	private String unitlevel;
	/**
	 * 单位性质
	 */
	@Length(max=50)
	private String nature;
	/**
	 * 单位管理员id
	 */
	@Length(max=32)
	private String managerId;
	/**
	 * 单位表现类型
	 */
	private String dataType;//Catalog(目录),UnitDept(单位)
	/**
	 * 单位分类
	 */
	private String sysType;//PrimarySchool(小学);Kindergarten(幼儿园);HighSchool(中学)Department(科室);
	/**
	 * 单位类型 10：学校；20：机关；30：事业单位；40：企业；50：其它
	 */
	private String orgType;
	/**
	 * ids同步下来的单位id
	 */
	@Length(max=100)
	private String refId;
	/**
	 * ids同步下来的单位的父id
	 */
	private String parentRefId;
	
	private String groupFullPath;
	/**
	 * 单位拼音
	 */
	private String pinyin;
	
	
	@Length(max=32)
	private String parentId;
	
	@Transient
	private String checked;
	@Transient
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
	@Transient
	private String typeString;
	//columns END

	@Transient
	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
	@Transient
	private java.lang.String deptsString;
	@Transient
	public java.lang.String getDeptsString() {
		return deptsString;
	}
	public void setDeptsString(java.lang.String deptsString) {
		this.deptsString = deptsString;
	}
	public Organization(){
	}

	public Organization(
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
	
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	@Column(name = "code", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
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
	
	@Column(name = "df", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	
	@Column(name = "sequence", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	
	public void setSequence(java.lang.Long value) {
		this.sequence = value;
	}
	/**
	 * 部门
	 */
	private Set<Dept> depts = new HashSet<Dept>(0);
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "organization")
	@OrderBy(" sequence ASC ")
	public Set<Dept> getDepts() {
		return depts;
	}

	public void setDepts(Set<Dept> depts) {
		this.depts = depts;
	}
	
	
	private Set<Organization> organizations = new HashSet<Organization>(0);
	public void setOrganizations(Set<Organization> organization){
		this.organizations = organization;
	}
	
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Organization> getOrganizations() {
		return organizations;
	}
	
	private Organization organization;
	public void setOrganization(Organization organization){
		this.organization = organization;
	}
	
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "parentId",nullable = true, insertable = false, updatable = false) 
	})
	public Organization getOrganization() {
		return organization;
	}
	/**
	 * 管理员
	 */
	private User manager;
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "managerId",nullable = true, insertable = false, updatable = false) 
	})
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}
	
	/*// 用户
	private Set<User> users = new HashSet<User>(0);
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_user_org", joinColumns={@JoinColumn(name="org_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}*/
	// 组织机构用户关联关系对象
	private Set<OrganizationUserRelation> ouRelations = new HashSet<OrganizationUserRelation>();
	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "organizationId")
	public Set<OrganizationUserRelation> getOuRelations() {
		return ouRelations;
	}
	public void setOuRelations(Set<OrganizationUserRelation> ouRelations) {
		this.ouRelations = ouRelations;
	}

	// 岗位
	private Set<Station> stations = new HashSet<Station>(0);
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}
	
	//分区
	private Set<Partition> partitions = new HashSet<Partition>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="t_organiz_partition", joinColumns={@JoinColumn(name="organiz_id")},inverseJoinColumns={@JoinColumn(name="partition_id")})
	public Set<Partition> getPartitions() {
		return partitions;
	}

	public void setPartitions(Set<Partition> partitions) {
		this.partitions = partitions;
	}

	@Column(name = "parentId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getParentId() {
		return parentId;
	}

	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "sysType", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	@Column(name = "dataType", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name = "orgType", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	
	@Column(name = "unitlevel", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUnitlevel() {
		return unitlevel;
	}

	public void setUnitlevel(String unitlevel) {
		this.unitlevel = unitlevel;
	}
	
	@Column(name = "nature", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
	@Column(name = "managerId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	@Column(name = "refId", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	@Column(name = "parentRefId", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getParentRefId() {
		return parentRefId;
	}

	public void setParentRefId(String parentRefId) {
		this.parentRefId = parentRefId;
	}
	@Column(name = "pinyin", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@Column(name = "scope", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Column(name = "groupFullPath", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
	public String getGroupFullPath() {
		return groupFullPath;
	}

	public void setGroupFullPath(String groupFullPath) {
		this.groupFullPath = groupFullPath;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Type",getType())
			.append("Code",getCode())
			.append("Description",getDescription())
			.append("Ts",getTs())
			.append("Df",getDf())
			.append("Sequence",getSequence())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Organization == false) return false;
		if(this == obj) return true;
		Organization other = (Organization)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	

	
	
	

	

	

	

	

	

	

	

	
}

