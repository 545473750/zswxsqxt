/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

import com.opendata.common.util.Common;
import com.opendata.application.model.Application;
import com.opendata.common.base.*;

import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;


import com.opendata.organiz.model.*;
import com.opendata.organiz.service.*;
import com.opendata.organiz.vo.query.*;
import com.opendata.sys.model.Partition;
import com.opendata.sys.model.Resources;
import com.opendata.sys.service.PartitionManager;
import com.opendata.sys.service.ResourcesManager;

/**
 * 角色管理Action类 负责和表现层的交互以及调用相关业务逻辑层完成功能
 * @author 付威
 */
@Namespace("/organiz")
@Results({
@Result(name="closeDialog", location="/commons/dialogclose.jsp", type="dispatcher"),
@Result(name="list",location="/WEB-INF/pages/organiz/Role/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/organiz/Role/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/organiz/Role/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/organiz/Role/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/organiz/Role/show.jsp", type = "dispatcher"),
@Result(name="grant_permission",location="/WEB-INF/pages/organiz/Role/grant_permission.jsp", type = "dispatcher"),
@Result(name="grant_user",location="/WEB-INF/pages/organiz/Role/grant_user_main.jsp", type = "dispatcher"),
@Result(name="listAction",location="../organiz/Role!list.do?partitionId=${partitionId}", type = "redirectAction"),
@Result(name="grantUserAction",location="../organiz/Role!grantUserPage.do?id=${id}&partitionId=${partitionId}", type = "redirectAction"),
})
public class RoleAction extends BaseStruts2Action implements Preparable, ModelDriven {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String SHOW_JSP = "show";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	protected static final String GRANT_PERMISSION_PAGE = "grant_permission";
	
	protected static final String GRANT_USER_PAGE="grant_user";
	
	protected static final String GRANT_USER_ACTION="grantUserAction";
	
	private RoleManager roleManager;
	private ResourcesManager resourcesManager;
	private UserManager userManager;
	private PartitionManager partitionManager;
	
	private Role role;
	private String id = null;
	private String[] items;
	private String checkIds;
	String partitionId;
	
	RoleQuery query = new RoleQuery();
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			role = new Role();
		} else {
			role = (Role) roleManager.getById(id);
		}
	}
	
	/**
	 * 执行搜索 
	 * @return
	 */
	public String list() {
		query.setDf("0");
		// 分区
		if (partitionId != null && !"".equals(partitionId)) {
			query.setPartitionId(partitionId);
		}
		
		Page page = this.roleManager.findPage(query);
		this.saveCurrentPage(page, query);
		
		return LIST_JSP;
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		return SHOW_JSP;
	}
	
	/**
	 * 进入新增页面
	 * @return
	 */
	public String create() {
		return CREATE_JSP;
	}
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		if("".equals(partitionId)){
			role.setPartitionId(null);
		}
		//新增时先判断编号是否存在 编号必须唯一
		if(!roleManager.isUniqueByDf(role, "code")){
			Flash.current().error(Role.ALIAS_CODE+"已经存在");
			return CREATE_JSP;
		}
		
		role.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		roleManager.save(role);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
		return EDIT_JSP;
	}
	
	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		if ("".equals(partitionId)) {
			role.setPartitionId(null);
		}
		//更新是判断编号是否存在
		if (!roleManager.isUniqueByDf(role, "code")) {
			Flash.current().error(Role.ALIAS_CODE + "已经存在");
			return EDIT_JSP;
		}
		
		roleManager.update(this.role);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 删除对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String delete() {
		String deleteMessage = "";
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");
			Role role = roleManager.getById(id);
			
			// 超级管理员角色不允许删除
			if(role.getCode() != null && role.getCode().equals("admin")) {
				deleteMessage = "超级管理员[角色]禁止删除";
			} else if(role.getCode() != null && role.getCode().equals("Partition_admin")) {
				deleteMessage = "分区管理员[角色]禁止删除";
			} else {
				role.setDf("1");
				//删除角色时 需把角色与人员的关系 以及角色与权限的关系删除
				role.setResources(new HashSet<Resources>(0));
				role.setUsers(new HashSet<User>(0));
				
				roleManager.delete(role);
			}
		}
		
		if (deleteMessage != null && !deleteMessage.equals("")) {
			Flash.current().success(deleteMessage);
		} else {
			Flash.current().success(DELETE_SUCCESS);
		}
		return LIST_ACTION;
	}
	
	/**
	 * 进入权限授予页面
	 * @return
	 */
	public String grantPage(){
		return GRANT_PERMISSION_PAGE;
	}
	
	/**
	 * 进入权限授予页面
	 * @return
	 */
	public String toGrantPage(){
		try {
			List<Resources> resourcesList = new ArrayList<Resources>();
			if (partitionId == null || "".equals(partitionId)) {
				role.setPartitionId(null);
				resourcesList = resourcesManager.findAllByDf();
			} else {
				/*Partition partition = partitionManager.getById(partitionId);
				for (Application app : partition.getApplications()) {
					resourcesList.addAll(resourcesManager.findByAppLication(app.getId()));
				}*/
			}
//			String permsEd = "";
			List<Resources> resourcesListByRole = resourcesManager.findByRole(role.getId());
			//组装成带多选框的树
			for (Resources res : resourcesListByRole) {
				for (Resources r : resourcesList) {
					if (r.getId().equals(res.getId())) {
						r.setChecked("1");
						break;
					}
				}
			}
			List<Resources> result = Common.getTopMenuListByRole(resourcesList);
			//查找有checked属性的框
			String menuXmlString = Common.getTreeXML(result);
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 权限授予
	 * @return
	 */
	public String grantPermission(){
		String permissionIds = getRequest().getParameter("permissionIds");
		if("".equals(partitionId)){
			role.setPartitionId(null);
		}
		if(!"".equals(permissionIds)){
			String[] perIds  = permissionIds.split(",");
			Set<Resources> resources = new HashSet<Resources>(0);
			for(String permID : perIds){
				Resources res = resourcesManager.getById(permID);
				if(res!=null){
					resources.add(res);
				}
			}
			role.setResources(resources);
			roleManager.update(role);
		}else{
			role.setResources(null);
			roleManager.update(role);
		}
		getRequest().setAttribute("dialogId", getRequest().getParameter("dialogId"));
		//Flash.current().success(UPDATE_SUCCESS);
		return CLOSE_DIALOG_NOREFRESH;
	}
	
	/**
	 * 进入设置用户页面
	 * @return
	 */
	public String grantUserPage(){
		// 准备数据,当前角色已经关联的用户列表
		// selectedIds
		// selectedNames
		// selectedUserHTML
		
		if(this.role.getPartitionId() != null && this.role.getPartitionId().equals("")) {
			this.role.setPartitionId(null);
		}
		
		if(this.role != null) {
			Set<User> selectedUsers = this.role.getUsers();
			StringBuffer sbUserId = new StringBuffer();
			StringBuffer sbUserName = new StringBuffer();
			StringBuffer html = new StringBuffer();
			for(User user : selectedUsers) {
				sbUserId.append(user.getId() + ",");
				sbUserName.append(user.getUsername() + ",");
				html.append("<div class='iframe_name_box' id='" + user.getId() + "'>");
				html.append("<p class='iframe_name_text'>" + user.getUsername() + "</p>");
				html.append("<p class='iframe_name_close'>");
				html.append("<a onclick=\"delItem('" + user.getId() + "', '" + user.getUsername() + "')\">");
				html.append("<img src='" + ServletActionContext.getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' />");
				html.append("</a>");
				html.append("</p>");
				html.append("</div>");
			}
			getRequest().setAttribute("selectedUserHTML", html.toString());
			
			if(sbUserId.toString() != null && !sbUserId.toString().equals("")) {
				String _sbUserId = sbUserId.toString().substring(0, sbUserId.toString().length() - 1);
				getRequest().setAttribute("selectedIds", _sbUserId);
			}
			if(sbUserName.toString() != null && !sbUserName.toString().equals("")) {
				String _sbUserName = sbUserName.toString().substring(0, sbUserName.toString().length() - 1);
				getRequest().setAttribute("selectedNames", _sbUserName);
			}
		}
		return GRANT_USER_PAGE;
	}

	// 选择用户ID 如有多个用逗号隔开
	private String selectedUserIds;
	
	/**
	 * 设置角色用户
	 * @return
	 */
	public String grantUser(){
		if(this.role != null) {
			Set<User> users = new HashSet<User>();
			if(this.selectedUserIds != null && !this.selectedUserIds.equals("")) {
				String[] selectUserIds = this.selectedUserIds.split(",");
				for(String selectUserId : selectUserIds) {
					User user = this.userManager.getById(selectUserId);
					users.add(user);
				}
			}
			role.setUsers(users);
			this.roleManager.update(role);
			getRequest().setAttribute("dialogId", getRequest().getParameter("dialogId"));
			return CLOSE_DIALOG_NOREFRESH;
		}
		
		return null;
	}

	public String getCheckIds() {
		return checkIds;
	}
	
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}
	
	public String getSelectedUserIds() {
		return selectedUserIds;
	}
	
	public void setSelectedUserIds(String selectedUserIds) {
		this.selectedUserIds = selectedUserIds;
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRoleManager(RoleManager manager) {
		this.roleManager = manager;
	}
    
	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}

	public void setPartitionManager(PartitionManager partitionManager) {
		this.partitionManager = partitionManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public Object getModel() {
		return role;
	}
	
	public void setId(String val) {
		this.id = val;
	}

	public String getId() {
		return id;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	
	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public RoleQuery getQuery() {
		return query;
	}

	public void setQuery(RoleQuery query) {
		this.query = query;
	}
}
