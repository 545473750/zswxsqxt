/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.model;

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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.opendata.common.base.BaseEntity;

/**
 * 资源类型
 * 
 * @author 王海龙
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "rs_type")
public class RsType extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "资源类型";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_TYPE_CODE = "类型编码";
	public static final String ALIAS_TYPE_NAME = "类型名称";
	public static final String ALIAS_TYPE_DESCRIPTION = "类型描述";
	public static final String ALIAS_AVAILABLE = "是否可用";
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
     * typeCode       db_column: type_code 
     */ 	
	@Length(max=10)
	private java.lang.String typeCode;
    /**
     * typeName       db_column: type_name 
     */ 	
	@Length(max=100)
	private java.lang.String typeName;
    /**
     * typeDescription       db_column: type_description 
     */ 	
	@Length(max=1000)
	private java.lang.String typeDescription;
    /**
     * available       db_column: available 
     */ 	
	@Length(max=4)
	private java.lang.String available;//可用：0，   不可用：1
    /**
     * ts       db_column: ts 
     */ 	
	@Length(max=19)
	private java.lang.String ts;
	
	//暂存字段
	private String resoucesNum;//类型所有所有资源总数
	private String resouBrowseNum;//类型所属所有资源的浏览量总和
	private String resouDownloadNum;//类型所属所有资源的下载量总和
	//columns END

	public RsType(){
	}

	public RsType(
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
	
	@Column(name = "type_code", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getTypeCode() {
		return this.typeCode;
	}
	
	public void setTypeCode(java.lang.String value) {
		this.typeCode = value;
	}
	
	@Column(name = "type_name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(java.lang.String value) {
		this.typeName = value;
	}
	
	@Column(name = "type_description", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
	public java.lang.String getTypeDescription() {
		return this.typeDescription;
	}
	
	public void setTypeDescription(java.lang.String value) {
		this.typeDescription = value;
	}
	
	@Column(name = "available", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getAvailable() {
		return this.available;
	}
	
	public void setAvailable(java.lang.String value) {
		this.available = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	private Set<RsResources> rsResourcess = new HashSet<RsResources>(0);
	public void setRsResourcess(Set<RsResources> rsResources){
		this.rsResourcess = rsResources;
	}
	
	@OneToMany(cascade = { CascadeType.MERGE,CascadeType.REMOVE  }, fetch = FetchType.LAZY, mappedBy = "rsType")
	public Set<RsResources> getRsResourcess() {
		return rsResourcess;
	}
	
	private Set<RsProperty> rsPropertys;
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsProperty.class)
	@JoinTable(name="rs_type_property",joinColumns={@JoinColumn(name="resources_type_id")},inverseJoinColumns={@JoinColumn(name="property_id")})
	public Set<RsProperty> getRsPropertys() {
		return rsPropertys;
	}

	public void setRsPropertys(Set<RsProperty> rsPropertys) {
		this.rsPropertys = rsPropertys;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TypeCode",getTypeCode())
			.append("TypeName",getTypeName())
			.append("TypeDescription",getTypeDescription())
			.append("Available",getAvailable())
			.append("Ts",getTs())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RsType == false) return false;
		if(this == obj) return true;
		RsType other = (RsType)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	//-----------------------------------暂存字段SET/GET----------------------------------------------------
	@Transient//HIBERNATE注释：不可持久化
	public String getResoucesNum() {
		return resoucesNum;
	}

	public void setResoucesNum(String resoucesNum) {
		this.resoucesNum = resoucesNum;
	}
	@Transient
	public String getResouBrowseNum() {
		return resouBrowseNum;
	}

	public void setResouBrowseNum(String resouBrowseNum) {
		this.resouBrowseNum = resouBrowseNum;
	}
	@Transient
	public String getResouDownloadNum() {
		return resouDownloadNum;
	}

	public void setResouDownloadNum(String resouDownloadNum) {
		this.resouDownloadNum = resouDownloadNum;
	}
}

