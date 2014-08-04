package com.opendata.login.model;

import java.util.List;

public class LeftMenu implements
java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4001365143383815878L;
	private String menuName;
	private String menuUrl;
	private LeftMenu parent;
	private List<LeftMenu> children;
	private String imgUrl;
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public LeftMenu getParent() {
		return parent;
	}
	public void setParent(LeftMenu parent) {
		this.parent = parent;
	}
	public List<LeftMenu> getChildren() {
		return children;
	}
	public void setChildren(List<LeftMenu> children) {
		this.children = children;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
}
