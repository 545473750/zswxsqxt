package com.opendata.webservice.sys.model;

public class Permission {

	/**
	 * 权限ID
	 */
	private java.lang.String id;
	
	/**
	 * 权限编号
	 */
	private java.lang.String code;
	
	/**
	 * 权限名称
	 */
	private java.lang.String name;
	
	/**
	 * 访问地址
	 */
	private java.lang.String url;
	
	/**
	 * 上级权限ID
	 */
	private java.lang.String parentId;
	
	/**
	 * 所属应用名称
	 */
	private java.lang.String applicationName;
	
	/**
	 * 所属应用编码
	 */
	private java.lang.String applicationCode;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(java.lang.String applicationName) {
		this.applicationName = applicationName;
	}

	public java.lang.String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(java.lang.String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
}
