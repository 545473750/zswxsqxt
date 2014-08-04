package com.opendata.login.model;

/**
 * 登录信息类、封装用户登录的信息
 * @author 付威
 */
public class LoginInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 解密密码？
	 */
	private String plainUserPwd;
	
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
	 * 角色ID组
	 */
	private String roleIDs;
	/**
	 * 角色名称组
	 */
	private String roleNames;
	/**
	 * 权限编号组
	 */
	private String permissionCodes;
	
	/**
	 * 岗位ID组
	 */
	private String stationIds;
	
	/**
	 * 岗位名称组
	 */
	private String stationNames;
	
	/**
	 * 登录时间
	 */
	private String loginTime;
	
	/**
	 * 登录IP
	 */
	private String ip;
	
	/**
	 * 操作访问入口
	 */
	private String permission;//操作访问入口
	
	private String userNum;//继教号

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

	public String getRoleIDs() {
		return roleIDs;
	}

	public void setRoleIDs(String roleIDs) {
		this.roleIDs = roleIDs;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getPermissionCodes() {
		return permissionCodes;
	}

	public void setPermissionCodes(String permissionCodes) {
		this.permissionCodes = permissionCodes;
	}
	
	public String getStationIds() {
		return stationIds;
	}

	public void setStationIds(String stationIds) {
		this.stationIds = stationIds;
	}

	public String getStationNames() {
		return stationNames;
	}

	public void setStationNames(String stationNames) {
		this.stationNames = stationNames;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPlainUserPwd() {
		return plainUserPwd;
	}

	public void setPlainUserPwd(String plainUserPwd) {
		this.plainUserPwd = plainUserPwd;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

}
