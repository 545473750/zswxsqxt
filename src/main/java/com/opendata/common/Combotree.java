package com.opendata.common;

import java.util.List;
/**
 * 下拉树列表
 * @author 陈永锋
 *
 */
public class Combotree {
	private String id;
	private String text;
	private String iconCls;
	private String state;
	private Boolean checked;
	private List<Combotree> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public List<Combotree> getChildren() {
		return children;
	}
	public void setChildren(List<Combotree> children) {
		this.children = children;
	}
	
	
}
