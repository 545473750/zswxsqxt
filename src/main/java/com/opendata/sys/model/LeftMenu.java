package com.opendata.sys.model;

import java.util.List;

public class LeftMenu {
	private String id;
	private String code;
	private String name;
	private String parentId;
	private String permissionId;
	private String menuType;
	private String url;
	public List<LeftMenu> child;
	
	
	
	
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<LeftMenu> getChild() {
		return child;
	}
	public void setChild(List<LeftMenu> child) {
		this.child = child;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
}
