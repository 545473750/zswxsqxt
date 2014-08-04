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
public class RsFavoritesQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** resourcesId */
	private java.lang.String resourcesId;
	/** userId */
	private java.lang.String userId;
	/** id */
	private java.lang.String id;

	public java.lang.String getResourcesId() {
		return this.resourcesId;
	}
	
	public void setResourcesId(java.lang.String value) {
		this.resourcesId = value;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

