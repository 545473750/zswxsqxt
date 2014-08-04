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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.opendata.common.base.BaseEntity;
import com.opendata.sys.model.Partition;

/**
 * 用户
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "用户";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_LOGINNAME = "登录名";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_USERNAME = "姓名";
	public static final String ALIAS_PHONE = "手机号";
	public static final String ALIAS_DEPTS = "所属部门";
	public static final String ALIAS_CREATETIME = "创建时间";
	public static final String ALIAS_DF = "删除标识";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_BIRTHDAY = "出生年月";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_ABLEDFLAG = "启用/禁用";
	
	/*
	 *  新增的基本信息
	 * 
	 */
	public static final String ALIAS_MAIL="电子邮箱";
	public static final String ALIAS_QQ="QQ";
	public static final String ALIAS_AGE="年龄";
	public static final String ALIAS_MSN="MSN";
	public static final String ALIAS_STATION="岗位";
	public static final String ALIAS_STATIONLEVEL="岗位级别";
	public static final String ALIAS_ISLEAVE="是否离职";
	public static final String ALIAS_STATUS="状态";
	public static final String ALIAS_WBLOG="微博";
	public static final String ALIAS_PHOTO="照片";
	//date formats
	public static final String FORMAT_CREATETIME = DATE_TIME_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START	
	@Length(max=32)
	private java.lang.String id;	
	@NotBlank @Length(max=100)
	private java.lang.String loginname;	
	@NotBlank @Length(max=100)
	private java.lang.String password; 	
	@Length(max=100)
	private java.lang.String username; 	
	
	@Length(max=19)
	private java.lang.String ts;
	@Length(max=1)
	private java.lang.String df="0";
	@Length(max=1)
	private java.lang.String isLeave;	//是否离职   0 、是 1、否
	@Length(max=1)
	// 启用/禁用标记
	@Column(name = "abledFlag", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String abledFlag;
	
	@Length(max=50)
	private String userNum;//继教号
	private String cardType;//证件类型 1：身份证；2护照
	private String passport;//护照号
	private String IDNum;//身份证号码
	private String source;//0在编教师、1非在编教师、2外请教师、3外请专家、4外籍教师 、5外籍专家;
	
	private String module;
	private String personType;//
	
	/**
	 * 单位id，维护跟单位的关系用ouRelations，次字段只用于方便查询
	 */
	@Length(max=32)
	private String unitId;
	/**
	 * 单位名称 维护跟单位的关系用ouRelations，次字段只用于方便查询
	 */
	@Length(max=500)
	private String unitName;
	/**
	 * ids同步的用户id
	 */
	@Length(max=100)
	private String refId;
	
	
	private String checked;
	
	//
	@Transient
    private String userpass;
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public User(){
	}
	public User(String id){
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
	
	@Column(name = "loginname", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getLoginname() {
		return this.loginname;
	}
	public void setLoginname(java.lang.String value) {
		this.loginname = value;
	}
	
	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	@Column(name = "username", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	@Column(name="userNum", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUserNum() {
		return userNum;
	}
	
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	
	@Column(name="isLeave", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(java.lang.String isLeave) {
		this.isLeave = isLeave;
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
	@Column(name = "unitId", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getUnitId() {
		return unitId;
	}
	
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	@Column(name = "unitName", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Column(name = "source", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Column(name = "passport", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	@Column(name = "IDNum", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getIDNum() {
		return IDNum;
	}
	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}
	
	@Column(name = "refId", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	@Column(name = "cardType", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@Column(name = "module", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Column(name = "personType", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	
	
	// 角色
	private Set<Role> roles = new HashSet<Role>(0);
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_user_role", joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	private Set<Partition> partitions = new HashSet<Partition>(0);
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_partition_user", joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="partition_id")})
	public Set<Partition> getPartitions() {
		return partitions;
	}
	public void setPartitions(Set<Partition> partitions) {
		this.partitions = partitions;
	}
	
	// 用户单位
	private Set<OrganizationUserRelation> ouRelations = new HashSet<OrganizationUserRelation>();
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "userId")
	public Set<OrganizationUserRelation> getOuRelations() {
		return ouRelations;
	}
	public void setOuRelations(Set<OrganizationUserRelation> ouRelations) {
		this.ouRelations = ouRelations;
	}
	
	// 岗位
	private Set<Station> stations = new HashSet<Station>(0);
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_user_station", joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="station_id")})
	public Set<Station> getStations() {
		return stations;
	}
	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}
	
	@Transient
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	@Transient
	private java.lang.String deptIDs;
	
	@Transient
	private java.lang.String deptsString;
	
	public java.lang.String getDeptsString() {
		return deptsString;
	}
	public void setDeptsString(java.lang.String deptsString) {
		this.deptsString = deptsString;
	}
	
	public java.lang.String getDeptIDs() {
		return deptIDs;
	}
	
	public void setDeptIDs(java.lang.String deptIDs) {
		this.deptIDs = deptIDs;
	}
	
	
	public String getAbledFlag() {
		return abledFlag;
	}
	public void setAbledFlag(String abledFlag) {
		this.abledFlag = abledFlag;
	}
	
	/*
	 * 新增set get方法  结束
	*/
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	
	
	
	
	
	
}

