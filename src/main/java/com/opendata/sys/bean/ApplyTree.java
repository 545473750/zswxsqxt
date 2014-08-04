package com.opendata.sys.bean;

import java.util.List;


public class ApplyTree {
	private String id;
	private String name;
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private List<ApplyTree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ApplyTree> getChildren() {
		return children;
	}
	public void setChildren(List<ApplyTree> children) {
		this.children = children;
	}
	
	
}
