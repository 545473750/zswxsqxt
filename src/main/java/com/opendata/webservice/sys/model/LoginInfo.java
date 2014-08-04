package com.opendata.webservice.sys.model;

import java.util.List;

public class LoginInfo {
	
	/**
	 * 用户ID
	 */
	private String userID;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 真实姓名
	 */
	private String userName;
	
	/**
	 * 部门ID
	 */
	private String deptIDs;
	/**
	 * 部门名称
	 */
	private String deptNames;
	/**
	 * 登录时间
	 */
	private String loginTime;
	/**
	 * 权限
	 */
	private List<Permission> permissionList;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptIDs() {
		return deptIDs;
	}

	public void setDeptIDs(String deptIDs) {
		this.deptIDs = deptIDs;
	}

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	
}
