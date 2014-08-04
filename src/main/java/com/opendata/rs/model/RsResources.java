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
import javax.persistence.JoinColumns;
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
import com.opendata.organiz.model.User;

/**
 * 资源属性
 * 
 * @author 王海龙
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "rs_resources")
public class RsResources extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "资源属性";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_LOGO = "标识";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_KEYWORD = "关键字";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_AUTHOR = "作者";
	public static final String ALIAS_ATTA_PATH = "附件地址";
	public static final String ALIAS_UPLOAD_TYPE = "上传类型";
	public static final String ALIAS_UPLOAD_TIME = "上传时间";
	public static final String ALIAS_UPLOAD_USER_ID = "上传人";
	public static final String ALIAS_DATA_TYPE = "数据类型";
	public static final String ALIAS_RESOURCES_TYPE_ID = "资源类型";
	public static final String ALIAS_THUMBNAIL = "缩略图";
	public static final String ALIAS_AUDIT_STATUS = "审核状态";
	public static final String ALIAS_BROWSE_NUMBER = "浏览次数";
	public static final String ALIAS_DOWNLOAD_NUMBER = "下载次数";
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
     * logo       db_column: logo 
     */ 	
	@Length(max=100)
	private java.lang.String logo;
    /**
     * title       db_column: title 
     */ 	
	@Length(max=500)
	private java.lang.String title;
    /**
     * keyword       db_column: keyword 
     */ 	
	@Length(max=200)
	private java.lang.String keyword;
    /**
     * description       db_column: description 
     */ 	
	@Length(max=2000)
	private java.lang.String description;
    /**
     * author       db_column: author 
     */ 	
	@Length(max=100)
	private java.lang.String author;
    /**
     * attaPath       db_column: atta_path 
     */ 	
	@Length(max=500)
	private java.lang.String attaPath;
    /**
     * uploadType       db_column: upload_type 
     */ 	
	@Length(max=4)
	private java.lang.String uploadType;//web资源上传 ：1 ，  URL上传：2，  服务器上传：3
    /**
     * uploadTime       db_column: upload_time 
     */ 	
	@Length(max=19)
	private java.lang.String uploadTime;
    /**
     * uploadUserId       db_column: upload_user_id 
     */ 	
	@Length(max=32)
	private java.lang.String uploadUserId;
    /**
     * dataType       db_column: data_type 
     */ 	
	@Length(max=4)
	private java.lang.String dataType;
    /**
     * resourcesTypeId       db_column: resources_type_id 
     */ 	
	@Length(max=32)
	private java.lang.String resourcesTypeId;
    /**
     * thumbnail       db_column: thumbnail 
     */ 	
	@Length(max=500)
	private java.lang.String thumbnail;
    /**
     * auditStatus       db_column: audit_status 
     */ 	
	@Length(max=4)
	private java.lang.String auditStatus;//未审核：0，  已通过：1，   未通过：2
    /**
     * browseNumber       db_column: browse_number 
     */ 	
	
	private java.lang.Long browseNumber;
    /**
     * downloadNumber       db_column: download_number 
     */ 	
	
	private java.lang.Long downloadNumber;
    /**
     * ts       db_column: ts 
     */ 	
	private java.lang.String fileName;
	
	@Length(max=19)
	private java.lang.String ts;
	//columns END

	public RsResources(){
	}

	public RsResources(
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
	
	@Column(name = "logo", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getLogo() {
		return this.logo;
	}
	
	public void setLogo(java.lang.String value) {
		this.logo = value;
	}
	
	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	@Column(name = "keyword", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	
	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	@Column(name = "author", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(java.lang.String value) {
		this.author = value;
	}
	
	@Column(name = "atta_path", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getAttaPath() {
		return this.attaPath;
	}
	
	public void setAttaPath(java.lang.String value) {
		this.attaPath = value;
	}
	
	@Column(name = "upload_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getUploadType() {
		return this.uploadType;
	}
	
	public void setUploadType(java.lang.String value) {
		this.uploadType = value;
	}
	
	@Column(name = "upload_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getUploadTime() {
		return this.uploadTime;
	}
	
	public void setUploadTime(java.lang.String value) {
		this.uploadTime = value;
	}
	
	@Column(name = "upload_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getUploadUserId() {
		return this.uploadUserId;
	}
	
	public void setUploadUserId(java.lang.String value) {
		this.uploadUserId = value;
	}
	
	@Column(name = "file_name", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name = "data_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(java.lang.String value) {
		this.dataType = value;
	}
	
	@Column(name = "resources_type_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getResourcesTypeId() {
		return this.resourcesTypeId;
	}
	
	public void setResourcesTypeId(java.lang.String value) {
		this.resourcesTypeId = value;
	}
	
	@Column(name = "thumbnail", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public java.lang.String getThumbnail() {
		return this.thumbnail;
	}
	
	public void setThumbnail(java.lang.String value) {
		this.thumbnail = value;
	}
	
	@Column(name = "audit_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(java.lang.String value) {
		this.auditStatus = value;
	}
	
	@Column(name = "browse_number", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public java.lang.Long getBrowseNumber() {
		return this.browseNumber;
	}
	
	public void setBrowseNumber(java.lang.Long value) {
		this.browseNumber = value;
	}
	
	@Column(name = "download_number", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public java.lang.Long getDownloadNumber() {
		return this.downloadNumber;
	}
	
	public void setDownloadNumber(java.lang.Long value) {
		this.downloadNumber = value;
	}
	
	@Column(name = "ts", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.String getTs() {
		return this.ts;
	}
	
	public void setTs(java.lang.String value) {
		this.ts = value;
	}
	
	private Set<RsStructure> rsStructures;
	
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsStructure.class)
	@JoinTable(name="rs_resources_structure",joinColumns={@JoinColumn(name="resources_id")},inverseJoinColumns={@JoinColumn(name="structure_id")})
	public Set<RsStructure> getRsStructures() {
		return rsStructures;
	}

	public void setRsStructures(Set<RsStructure> rsStructures) {
		this.rsStructures = rsStructures;
	}

	private Set<RsResources> rsResourcess;
	
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsResources.class)
	@JoinTable(name="rs_resources_resources",joinColumns={@JoinColumn(name="start_resources_id")},inverseJoinColumns={@JoinColumn(name="target_resources_id")})
	public Set<RsResources> getRsResourcess() {
		return rsResourcess;
	}

	public void setRsResourcess(Set<RsResources> rsResourcess) {
		this.rsResourcess = rsResourcess;
	}
	
	private Set<RsResources> targerRsResourcess;
	
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.rs.model.RsResources.class)
	@JoinTable(name="rs_resources_resources",joinColumns={@JoinColumn(name="target_resources_id")},inverseJoinColumns={@JoinColumn(name="start_resources_id")})
	public Set<RsResources> getTargerRsResourcess() {
		return targerRsResourcess;
	}

	public void setTargerRsResourcess(Set<RsResources> rsResourcess) {
		this.targerRsResourcess = rsResourcess;
	}
	
	private Set<User> myFavoritess = new HashSet<User>(0);
	@ManyToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY,targetEntity=com.opendata.organiz.model.User.class)
	@JoinTable(name="rs_favorites",joinColumns={@JoinColumn(name="resources_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	public Set<User> getMyFavoritess() {
		return myFavoritess;
	}

	public void setMyFavoritess(Set<User> myFavoritess) {
		this.myFavoritess = myFavoritess;
	}
	
	private Set<RsExpandProperty> rsExpandPropertys = new HashSet<RsExpandProperty>(0);
	public void setRsExpandPropertys(Set<RsExpandProperty> rsExpandPropertys){
		this.rsExpandPropertys = rsExpandPropertys;
	}
	
	@OneToMany(cascade = { CascadeType.ALL,CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "rsResources")
	public Set<RsExpandProperty> getRsExpandPropertys() {
		return rsExpandPropertys;
	}
	
	private RsType rsType;
	public void setRsType(RsType rsType){
		this.rsType = rsType;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "resources_type_id",nullable = false, insertable = false, updatable = false) 
	})
	public RsType getRsType() {
		return rsType;
	}
	
	private User ogUser;
	public void setOgUser(User ogUser){
		this.ogUser = ogUser;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "upload_user_id",nullable = false, insertable = false, updatable = false) 
	})
	public User getOgUser() {
		return ogUser;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Logo",getLogo())
			.append("Title",getTitle())
			.append("Keyword",getKeyword())
			.append("Description",getDescription())
			.append("Author",getAuthor())
			.append("AttaPath",getAttaPath())
			.append("UploadType",getUploadType())
			.append("UploadTime",getUploadTime())
			.append("UploadUserId",getUploadUserId())
			.append("DataType",getDataType())
			.append("ResourcesTypeId",getResourcesTypeId())
			.append("Thumbnail",getThumbnail())
			.append("AuditStatus",getAuditStatus())
			.append("BrowseNumber",getBrowseNumber())
			.append("DownloadNumber",getDownloadNumber())
			.append("Ts",getTs())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RsResources == false) return false;
		if(this == obj) return true;
		RsResources other = (RsResources)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

