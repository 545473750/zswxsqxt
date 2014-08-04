package com.opendata.login.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opendata.application.service.PermissionManager;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.organiz.service.UserManager;
import com.opendata.sys.model.Resources;
import com.opendata.sys.service.ResourcesManager;
@Namespace("/index")
@Results({
@Result(name="index",location="/index.jsp", type = "dispatcher"),
@Result(name="main",location="/WEB-INF/pages/index/main.jsp", type = "dispatcher"),
@Result(name="shouYe",location="/WEB-INF/pages/index/shouYe.jsp", type = "dispatcher"),
@Result(name="ziYuan",location="/WEB-INF/pages/index/ziYuan.jsp", type = "dispatcher"),
@Result(name="xieZuo",location="/WEB-INF/pages/index/xieZuo.jsp", type = "dispatcher"),
@Result(name="banGong",location="/WEB-INF/pages/index/banGong.jsp", type = "dispatcher"),
@Result(name="tongJi",location="/WEB-INF/pages/index/tongJi.jsp", type = "dispatcher"),
@Result(name="peiXun",location="/WEB-INF/pages/index/peiXun.jsp", type = "dispatcher"),
@Result(name="jiJiao",location="/WEB-INF/pages/index/jiJiao.jsp", type = "dispatcher"),
@Result(name="jiaoWu",location="/WEB-INF/pages/index/jiaoWu.jsp", type = "dispatcher"),
@Result(name="yanXiu",location="/WEB-INF/pages/index/yanXiu.jsp", type = "dispatcher"),
@Result(name="xiTong",location="/WEB-INF/pages/index/xiTong.jsp", type = "dispatcher"),
@Result(name="left",location="/left.jsp", type = "dispatcher")
})
public class IndexAction extends BaseStruts2Action {

	private ResourcesManager resourcesManager;		//权限控制管理类
	private PermissionManager permissionManager;	//应用菜单管理类
//	private WfInstanceParticipanManager wfInstanceParticipanManager;//流程实例节点表服务类
	private UserManager userManager;
	private Resources resources;	//应用菜单实体类
	
	/**
	 * 教师之家首页
	 * @return
	 */
	public String index(){
		getRequest().setAttribute("leftFrameFlag", "2");
		return "index";
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public ResourcesManager getResourcesManager() {
		return resourcesManager;
	}

	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}
}
