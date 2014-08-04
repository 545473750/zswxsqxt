package com.opendata.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.login.model.LoginInfo;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.RoleManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.sys.service.ResourcesManager;

/**
 * 用户IDS、session信息应用类
 *
 */
public class SessionUserUtil
{
    private LoginInfo user;
    private boolean isNull = false;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private OrganizationManager organizationManager;
    private UserManager userManager;
    private RoleManager roleManager;
    private ResourcesManager resourcesManager;
    private OrganizationUserRelationManager organizationUserRelationManager;
    
    /**
     * 直接从当前容器session中查找用户
     * @param request
     */
    public SessionUserUtil(HttpServletRequest request)
    {
        this.request = request;
        initSessionUser();
    }

    protected SessionUserUtil()
    {

    }

    protected void initSessionUser()
    {
        try
        {
            this.user = (LoginInfo) request.getSession(true).getAttribute(BaseStruts2Action.LOGIN_INFO);
        }
        catch (Exception e)
        {
        }
        if (this.user == null || StringUtils.isBlank(user.getUserID())){
        	this.isNull = true;
        }
    }

    /**
     * 从当前容器session查找用户，如果没有则去ids查找
     * @param request
     * @param response
     */
    public SessionUserUtil(HttpServletRequest request, HttpServletResponse response)
    {
        this.request = request;
        this.response = response;
        initSessionUser();
    }

    public LoginInfo getUser()
    {
        return this.user;
    }

    /**
     * 如果用户为空 返回true 否则返回 false
     * @return
     */
    public boolean isNull()
    {
        return this.isNull;
    }

    /**
     * 注销用户
     */
    public void logout() throws Exception
    {
        request.getSession(true).invalidate();
    }

    /**
     * 获取用户名
     * @return
     */
    public String getUserName()
    {
        return checkEmpty(user.getLoginName());
    }

    /**
     * 获取用户ID
     * @return
     */
    public String getUserId()
    {
        
        return user.getUserID();
    }
    /**
     * 获取继教号
     * @return
     */
    public String getUserNum(){
    	if(user==null){
    		return null;
    	}
    	return user.getUserNum();
    }
    /**
     * 获取真实姓名
     * @return
     */
    public String getName()
    {
        return checkEmpty(user.getUserName());
    }

    protected String checkEmpty(String value)
    {
        if (this.isNull)
            return "";
        if (value == null)
            return "";
        return value;
    }

    public OrganizationManager getOrganizationManager() {
		if (this.organizationManager == null) {
	        this.organizationManager = ((OrganizationManager)Platform.getBean("organizationManager"));
	    }
	     return this.organizationManager;
	}

	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}

	public UserManager getUserManager() {
		if (this.userManager == null) {
	        this.userManager = ((UserManager)Platform.getBean("userManager"));
	    }
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public RoleManager getRoleManager(){
		if (this.roleManager == null) {
	        this.roleManager = ((RoleManager)Platform.getBean("roleManager"));
	    }
	     return this.roleManager;
	}
	
	public ResourcesManager getResourcesManager(){
		if (this.resourcesManager == null) {
	        this.resourcesManager = ((ResourcesManager)Platform.getBean("resourcesManager"));
	    }
	     return this.resourcesManager;
	}
	
	public OrganizationUserRelationManager getOrganizationUserRelationManager(){
		if (this.organizationUserRelationManager == null) {
	        this.organizationUserRelationManager = ((OrganizationUserRelationManager)Platform.getBean("organizationUserRelationManager"));
	    }
	     return this.organizationUserRelationManager;
	}

}
