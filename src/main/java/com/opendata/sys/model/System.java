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

import com.opendata.common.base.BaseEntity;
import com.opendata.organiz.model.User;
/**
 * 系统同步对象
 * 
 * @author顾保臣
 */
@Entity
@Table(name = "t_system")
public class System extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "系统同步";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_TS = "创建时间";
	public static final String ALIAS_DF = "df";
	public static final String ALIAS_CODE = "编号";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_USERNAME = "用户名";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_MANAGER = "系统负责人";
	public static final String ALIAS_PHONE = "联系方式";
	public static final String ALIAS_REMARK = "描述";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * id       db_column: id 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * ts       db_column: ts 
     */ 	
	@NotBlank @Length(max=19)
	private java.lang.String ts;
    /**
     * df       db_column: df 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String df;
    /**
     * 编号       db_column: code 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String code;
    /**
     * 名称       db_column: name 
     */ 	
	@NotBlank @Length(max=100)
	private java.lang.String name;
    /**
     * username       db_column: username 
     */ 	
	@Length(max=100)
	private java.lang.String username;
    /**
     * password       db_column: password 
     */ 	
	@Length(max=100)
	private java.lang.String password;
    /**
     * 系统负责人       db_column: manager 
     */ 	
	@Length(max=100)
	private java.lang.String manager;
    /**
     * 联系方式       db_column: phone 
     */ 	
	@Length(max=20)
	private java.lang.String phone;
    /**
     * 描述       db_column: remark 
     */ 	
	@Length(max=200)
	private java.lang.String remark;
	//columns END


	public System(){
	}

	public System(
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
	
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "username", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	@Column(name = "password", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	@Column(name = "manager", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getManager() {
		return this.manager;
	}
	
	public void setManager(java.lang.String value) {
		this.manager = value;
	}
	
	@Column(name = "phone", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}

	// 用户
	private Set<User> users = new HashSet<User>(0);
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="t_user_system", joinColumns={@JoinColumn(name="system_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Ts",getTs())
			.append("Df",getDf())
			.append("Code",getCode())
			.append("Name",getName())
			.append("Username",getUsername())
			.append("Password",getPassword())
			.append("Manager",getManager())
			.append("Phone",getPhone())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof System == false) return false;
		if(this == obj) return true;
		System other = (System)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

