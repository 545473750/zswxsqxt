/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;

/**
 * @author 王海龙
 */
public class RsResourcesQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** id */
	private java.lang.String id;
	/** logo */
	private java.lang.String logo;
	/** title */
	private java.lang.String title;
	/** keyword */
	private java.lang.String keyword;
	/** description */
	private java.lang.String description;
	/** author */
	private java.lang.String author;
	/** attaPath */
	private java.lang.String attaPath;
	/** uploadType */
	private java.lang.String uploadType;
	/** uploadTime */
	private java.lang.String uploadTime;
	/** uploadUserId */
	private java.lang.String uploadUserId;
	/** dataType */
	private java.lang.String dataType;
	/** resourcesTypeId */
	private java.lang.String resourcesTypeId;
	/** thumbnail */
	private java.lang.String thumbnail;
	/** auditStatus */
	private java.lang.String auditStatus;
	/** browseNumber */
	private java.lang.Long browseNumber;
	/** downloadNumber */
	private java.lang.Long downloadNumber;
	/** ts */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	
	private java.lang.String keywords;  //显示的关键字
	
	public java.lang.String getKeywords() {
		return keywords;
	}

	public void setKeywords(java.lang.String keywords) {
		this.keywords = keywords;
	}

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getLogo() {
		return this.logo;
	}
	
	public void setLogo(java.lang.String value) {
		this.logo = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	
	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(java.lang.String value) {
		this.author = value;
	}
	
	public java.lang.String getAttaPath() {
		return this.attaPath;
	}
	
	public void setAttaPath(java.lang.String value) {
		this.attaPath = value;
	}
	
	public java.lang.String getUploadType() {
		return this.uploadType;
	}
	
	public void setUploadType(java.lang.String value) {
		this.uploadType = value;
	}
	
	public java.lang.String getUploadTime() {
		return this.uploadTime;
	}
	
	public void setUploadTime(java.lang.String value) {
		this.uploadTime = value;
	}
	
	public java.lang.String getUploadUserId() {
		return this.uploadUserId;
	}
	
	public void setUploadUserId(java.lang.String value) {
		this.uploadUserId = value;
	}
	
	public java.lang.String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(java.lang.String value) {
		this.dataType = value;
	}
	
	public java.lang.String getResourcesTypeId() {
		return this.resourcesTypeId;
	}
	
	public void setResourcesTypeId(java.lang.String value) {
		this.resourcesTypeId = value;
	}
	
	public java.lang.String getThumbnail() {
		return this.thumbnail;
	}
	
	public void setThumbnail(java.lang.String value) {
		this.thumbnail = value;
	}
	
	public java.lang.String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(java.lang.String value) {
		this.auditStatus = value;
	}
	
	public java.lang.Long getBrowseNumber() {
		return this.browseNumber;
	}
	
	public void setBrowseNumber(java.lang.Long value) {
		this.browseNumber = value;
	}
	
	public java.lang.Long getDownloadNumber() {
		return this.downloadNumber;
	}
	
	public void setDownloadNumber(java.lang.Long value) {
		this.downloadNumber = value;
	}
	
	public java.lang.String getTsBegin() {
		return this.tsBegin;
	}
	
	public void setTsBegin(java.lang.String value) {
		this.tsBegin = value;
	}	
	
	public java.lang.String getTsEnd() {
		return this.tsEnd;
	}
	
	public void setTsEnd(java.lang.String value) {
		this.tsEnd = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

