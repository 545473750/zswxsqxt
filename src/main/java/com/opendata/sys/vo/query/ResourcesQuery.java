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
 * 菜单的查询对象
 * @author 付威
 */
public class ResourcesQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 主键ID */
	private java.lang.String id;
	/** 菜单名称 */
	private java.lang.String name;
	/** 菜单编码 */
	private java.lang.String code;
	/** 排序号 */
	private java.lang.Long sequence;
	/** 父ID */
	private java.lang.String parentId;
	/** 应用功能入口 */
	private java.lang.String permissionId;
	/** 小图标 */
	private java.lang.String icon;
	/** 大图标 */
	private java.lang.String bigIcon;
	/** 显示类型 */
	private java.lang.String type;
	/** 创建时间 */
	private java.lang.String tsBegin;
	private java.lang.String tsEnd;
	/** 删除标识 */
	private java.lang.String df;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.Long getSequence() {
		return this.sequence;
	}
	
	public void setSequence(java.lang.Long value) {
		this.sequence = value;
	}
	
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	
	public java.lang.String getPermissionId() {
		return this.permissionId;
	}
	
	public void setPermissionId(java.lang.String value) {
		this.permissionId = value;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setIcon(java.lang.String value) {
		this.icon = value;
	}
	
	public java.lang.String getBigIcon() {
		return this.bigIcon;
	}
	
	public void setBigIcon(java.lang.String value) {
		this.bigIcon = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
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
	
	public java.lang.String getDf() {
		return this.df;
	}
	
	public void setDf(java.lang.String value) {
		this.df = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

