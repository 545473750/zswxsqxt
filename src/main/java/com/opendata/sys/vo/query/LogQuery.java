/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.vo.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.opendata.common.base.BaseQuery;

/**
 * 日志的查询对象
 * @author 付威
 */
public class LogQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 主键ID */
	private java.lang.String id;
	/** 操作人姓名 */
	private java.lang.String username;
	/** 类型 */
	private java.lang.String type;
	/** IP地址 */
	private java.lang.String ip;
	/** 功能入口 */
	private java.lang.String permission;
	/** 菜单名称 */
	private java.lang.String resources;
	/** 内容 */
	private java.lang.String content;
	/** 删除标识 */
	private java.lang.String df;
	/** ts */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.String getIp() {
		return this.ip;
	}
	
	public void setIp(java.lang.String value) {
		this.ip = value;
	}
	
	public java.lang.String getPermission() {
		return this.permission;
	}
	
	public void setPermission(java.lang.String value) {
		this.permission = value;
	}
	
	public java.lang.String getResources() {
		return this.resources;
	}
	
	public void setResources(java.lang.String value) {
		this.resources = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
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

