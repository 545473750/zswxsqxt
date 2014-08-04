package com.opendata.webservice.sys.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import cn.org.rapid_framework.page.Page;

import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.UserQuery;
import com.opendata.webservice.sys.LoginWebService;
import com.opendata.webservice.sys.model.LoginInfo;
import com.opendata.webservice.sys.model.Permission;
import com.thoughtworks.xstream.XStream;

@WebService(endpointInterface="com.opendata.webservice.sys.LoginWebService")
public class LoginWebServiceImpl implements LoginWebService{
    private UserManager userManager;
    
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public String login(String loginName, String password) {
		if(loginName==null||"".equals(loginName)||password==null||"".equals(password)){
			return null;
		}
		LoginInfo loginInfo = null;
		String xml = "";
		try{
			UserQuery query = new UserQuery();
			query.setLoginname(loginName);
			query.setPassword(password);
			Page page = userManager.findPage(query);//取得登录用户
			if(page.getResult() != null && page.getResult().size() > 0){
				User user = (User)page.getResult().get(0);
				loginInfo = new LoginInfo();
				loginInfo.setLoginName(loginName);
				loginInfo.setPassword(password);
				loginInfo.setUserID(user.getId());
				loginInfo.setUserName(user.getUsername());
				loginInfo.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				loginInfo.setDeptIDs("");
				loginInfo.setDeptNames("");
				Map<String,Permission> perMap = new HashMap();
				//组装部门
				/*
				for (Organization dept : user.getDepts()) {
					if (loginInfo.getDeptIDs().equals("")) {
						loginInfo.setDeptIDs(dept.getId());
						loginInfo.setDeptNames(dept.getName());
					} else {
						loginInfo.setDeptIDs(loginInfo.getDeptIDs() + ","
								+ dept.getId());
						loginInfo.setDeptNames(loginInfo.getDeptNames() + ","
								+ dept.getName());
					}

					// 权限
					for (Role role : user.getRoles()) {

						for (com.opendata.sys.model.Resources perm : role
								.getResources()) {
							Permission permission = new Permission();
							// permission.setApplicationCode(perm.getApplication().getCode());
							// permission.setApplicationName(perm.getApplication().getName());
							// permission.setCode(perm.getCode());
							// permission.setId(perm.getId());
							// permission.setName(perm.getName());
							// permission.setParentId(perm.getParentId());
							// permission.setUrl(perm.getUrl());
							perMap.put(perm.getId(), permission);
						}
					}
					List perList = new ArrayList();
					perList.addAll(perMap.values());
					loginInfo.setPermissionList(perList);
					XStream xstream = new XStream();
					xstream.alias("loginInfo", LoginInfo.class);
					xstream.alias("permission", Permission.class);
					xml = xstream.toXML(loginInfo);
				}*/
				for(OrganizationUserRelation our : user.getOuRelations()){
					if (loginInfo.getDeptIDs().equals("")) {
						loginInfo.setDeptIDs(our.getOrganization() == null ? "" : our.getOrganization().getId());
						loginInfo.setDeptNames(our.getOrganization() == null ? "" : our.getOrganization().getName());
					} else {
						loginInfo.setDeptIDs(loginInfo.getDeptIDs() + "," + our.getOrganization() == null ? "" : our.getOrganization().getId());
						loginInfo.setDeptNames(loginInfo.getDeptNames() + "," + our.getOrganization() == null ? "" : our.getOrganization().getName());
					}
					// 权限
					for (Role role : user.getRoles()) {
						for (com.opendata.sys.model.Resources perm : role.getResources()) {
							Permission permission = new Permission();
							perMap.put(perm.getId(), permission);
						}
					}
					List perList = new ArrayList();
					perList.addAll(perMap.values());
					loginInfo.setPermissionList(perList);
					XStream xstream = new XStream();
					xstream.alias("loginInfo", LoginInfo.class);
					xstream.alias("permission", Permission.class);
					xml = xstream.toXML(loginInfo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return xml;
	}
}
