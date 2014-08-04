/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

import com.opendata.common.base.*;

import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.opendata.sys.service.SystemManager;
import com.opendata.sys.vo.query.SystemQuery;
import com.opendata.sys.model.System;

/**
 * 系统同步action, 用于处理系统同步模块的请求转发等操作
 * @author顾保臣
 */
@SuppressWarnings({ "serial", "rawtypes" })
@Namespace("/sys")
@Results({
	@Result(name="closeDialog", location="/commons/dialogclose.jsp", type="dispatcher"),
	@Result(name = "toConfigSynchronousPage", location="/WEB-INF/pages/sys/System/system_user_main.jsp", type="dispatcher"),
@Result(name="list",location="/WEB-INF/pages/sys/System/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/System/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/System/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/System/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/System/show.jsp", type = "dispatcher"),

@Result(name="configSynchronous", location="/WEB-INF/pages/sys/System/dialogclose.jsp", type="dispatcher"),

@Result(name="listAction",location="..//sys/System!list.do", type = "redirectAction")
})
public class SystemAction extends BaseStruts2Action implements Preparable, ModelDriven {
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
	
	private SystemManager systemManager;
	private System system;
	java.lang.String id = null;
	private String[] items;
	
	private SystemQuery query = new SystemQuery();
	
	
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			system = new System();
		} else {
			system = (System)systemManager.getById(id);
		}
	}
	
	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {
		query.setDf("0");
		Page page = this.systemManager.findPage(query);
		this.saveCurrentPage(page,query);
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
	
	private String code;
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		// 编号唯一性校验
		List<System> result = this.systemManager.checkCode(this.code);
		if(result != null && result.size() > 0) {
			Flash.current().success("当前编号重复,请重新填写!");
			return CREATE_JSP;
		}
		
		system.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		system.setDf("0");
		systemManager.save(system);
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
		systemManager.update(this.system);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			system = (System)systemManager.getById(id);
			system.setDf("1");
			
			// 清除与用户的关联关系
			system.setUsers(null);
			systemManager.update(this.system);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	private String systemId;
	private String selectedUserIds;
	private UserManager userManager;
	
	/**
	 * 同步设置
	 * @return
	 */
	public String configSynchronous() {
		
		System system = this.systemManager.getSystemById(this.systemId);
		if(this.selectedUserIds != null && !this.selectedUserIds.equals("")) {
			Set<User> users = new HashSet<User>(0);
			String[] selectedUserids = this.selectedUserIds.split(",");
			for(String userId : selectedUserids) {
				User user = this.userManager.getById(userId);
				users.add(user);
			}
			
			system.setUsers(users);
		}
		
		this.systemManager.saveOrUpdate(system);
		
		Flash.current().success("同步设置完成！");
		return "closeDialog";
	}
	
	/**
	 * 跳转到系统同步设置页面
	 * @return
	 */
	public String toConfigSynchronousPage() {
		// 准备数据
		// selectedIds
		// selectedNames
		// selectedUserHTML
		System system = this.systemManager.getSystemById(this.systemId);
		StringBuffer sbIds = new StringBuffer();
		StringBuffer sbNames = new StringBuffer();
		StringBuffer html = new StringBuffer();
		for(User user : system.getUsers()) {
			sbIds.append(user.getId() + ",");
			sbNames.append(user.getUsername() + ",");
			
			html.append("<div class='iframe_name_box' id='" + user.getId() + "'>");
			html.append("<p class='iframe_name_text'>" + user.getUsername() + "</p>");
			html.append("<p class='iframe_name_close'>");
			html.append("<a onclick=\"delItem('" + user.getId() + "', '" + user.getUsername() + "')\">");
			html.append("<img src='" + ServletActionContext.getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' />");
			html.append("</a>");
			html.append("</p>");
			html.append("</div>");
		}
		getRequest().setAttribute("selectedUserHTML", html);
		if(sbIds != null && !sbIds.toString().equals("")) {
			String _sbIds = sbIds.toString().substring(0, sbIds.toString().length() - 1);
			getRequest().setAttribute("selectedIds", _sbIds);
		}
		if(sbNames != null && !sbNames.toString().equals("")) {
			String _sbNames = sbNames.toString().substring(0, sbNames.toString().length() - 1);
			getRequest().setAttribute("selectedNames", _sbNames);
		}
		
		return "toConfigSynchronousPage";
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setSystemManager(SystemManager manager) {
		this.systemManager = manager;
	}	
	public Object getModel() {
		return system;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getSelectedUserIds() {
		return selectedUserIds;
	}

	public void setSelectedUserIds(String selectedUserIds) {
		this.selectedUserIds = selectedUserIds;
	}
	public SystemQuery getQuery() {
		return query;
	}

	public void setQuery(SystemQuery query) {
		this.query = query;
	}

	
}
