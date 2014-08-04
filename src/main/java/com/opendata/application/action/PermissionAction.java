/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.application.service.ApplicationManager;
import com.opendata.application.service.PermissionManager;
import com.opendata.application.vo.query.PermissionQuery;
import com.opendata.common.base.*;
import com.opendata.common.util.*;

import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;

/**
 * 应用访问入口Action类 负责和表现层的交互以及调用相关业务逻辑层完成功能
 * @author 付威
 */


@Namespace("/application")
@Results({
@Result(name="list",location="/WEB-INF/pages/application/Permission/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/application/Permission/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/application/Permission/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/application/Permission/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/application/Permission/show.jsp", type = "dispatcher"),
@Result(name="main",location="/WEB-INF/pages/application/Permission/main.jsp", type = "dispatcher"),
@Result(name="move",location="/WEB-INF/pages/application/Permission/move.jsp", type = "dispatcher"),
@Result(name="close_dialog",location="/WEB-INF/pages/application/Permission/dialogclose.jsp", type = "dispatcher"),
@Result(name="leftTree",location="/WEB-INF/pages/application/Permission/leftTree.jsp", type = "dispatcher"),
@Result(name="listAction",location="../application/Permission!list.do?${params}", type = "redirectAction")

})
public class PermissionAction extends BaseStruts2Action implements Preparable,ModelDriven{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "sequence asc"; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String SHOW_JSP = "show";
	protected static final String MOVE_JSP = "move";
	protected static final String CLOSE_DIALOG = "close_dialog";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	
	protected static final String MAIN = "main";
	protected static final String LEFTTREE = "leftTree";
	
	private PermissionManager permissionManager;
	
	private ApplicationManager applicationManager;
	
	private Permission permission;
	java.lang.String id = null;
	java.lang.String applicationId = null;
	private String[] items;
	private String queryParentId;
	
	PermissionQuery query = new PermissionQuery();
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			permission = new Permission();
		} else {
			permission = (Permission)permissionManager.getById(id);
		}
		/*//初始化上级访问入口
		if(!isNullOrEmptyString(query.getParentId())&&!"root".equals(query.getParentId())&&!"-1".equals(query.getParentId())){
			permission.setParentPermission(permissionManager.getById(query.getParentId()));
		}*/
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setPermissionManager(PermissionManager manager) {
		this.permissionManager = manager;
	}
	
	
	
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public Object getModel() {
		return permission;
	}


	public String getQueryParentId() {
		return queryParentId;
	}

	public void setQueryParentId(String queryParentId) {
		this.queryParentId = queryParentId;
	}

	public void setApplicationId(java.lang.String applicationId) {
		this.applicationId = applicationId;
	}

	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	
	/** 执行搜索 */
	public String list() {
		//先判断查询的父菜单是不是功能菜单，如果是功能菜单 则直接到查看页面
		if(permission.getParentPermission()!=null&&Permission.TYPE_FUNCTION.equals(permission.getParentPermission().getType())){
			permission = permission.getParentPermission();
			return SHOW_JSP;
		}
		query.setDf("0");
		query.setSortColumns(" sequence asc  ");
		/*// 如果是root,则显示根应用访问入口
		if(queryParentId != null && queryParentId.equals("root")) {
			query.setParentId("-1");//取得所有根应用访问入口
		} else if(queryParentId==null||"".equals(queryParentId)){
			query.setParentId("-1");
		}else{
		
			query.setParentId(queryParentId);
		}*/
		Page page = this.permissionManager.findPage(query);
		
		this.saveCurrentPage(page, query);
		return LIST_JSP;
	}
	
	/** 查看对象*/
	public String show() {
		return SHOW_JSP;
	}
	
	/** 进入新增页面*/
	public String create() {
		// 排序号
		permission.setSequence(permissionManager.findSequence(queryParentId,applicationId));
		return CREATE_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		
		if("".equals(query.getParentId())||"-1".equals(query.getParentId())||"root".equals(query.getParentId())){
			permission.setParentId(null);
		}/*else{
			permission.setParentId(query.getParentId());
		}*/
		
		String oldUrl = getRequest().getParameter("oldUrl");
		String url = permission.getUrl();
		if(StringUtils.isNotBlank(url)){
			//判断URL是否存在StringUtils.isBlank(oldUrl)证明是新增，!url.equals(oldUrl)证明是修改的时候修改菜单地址
			long l = permissionManager.countUrl(url);
			if((StringUtils.isBlank(oldUrl)&&l>0)||(!url.equals(oldUrl)&&l>0)){
				Flash.current().success("本系统此"+Permission.ALIAS_URL+"已经存在");
				getPermissionList();
				return CREATE_JSP;
			}
		}
		/*//判断URL是否存在
		if (permission.getUrl()!=null && !permission.getUrl().trim().equals("") &&permissionManager.countUrl(permission.getUrl())>0) {
			Flash.current().success("本系统此"+Permission.ALIAS_URL+"已经存在");
			return CREATE_JSP;
		}*/
		//同一应用下的目录和主功能入口编码不能重复
		//同一主功能入口下的子功能入口编码不能重复
		//添加前判断code是否唯一
		if(!Permission.TYPE_FUNCTION.equals(permission.getType())){
			if(!permissionManager.isUniqueByDf(permission, "applicationId,code")){
				Flash.current().success("同一应用下的"+Permission.ALIAS_CODE+"已经存在");
				return CREATE_JSP;
			}
		}else{
			if(!permissionManager.isUniqueByDf(permission, "parentId,code")){
				Flash.current().success("同一主功能访问入口下的"+Permission.ALIAS_CODE+"已经存在");
				return CREATE_JSP;
			}
		}
		//添加前判断同一父菜单下的名称是否唯一
		if(!permissionManager.isUniqueByDf(permission, "name,parentId")){
			Flash.current().success("同一父访问入口下访问入口名称已经存在");
			return CREATE_JSP;
		}
	
		
		applicationId = permission.getApplicationId();
		permission.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(StringUtils.isBlank(id)){
			permission.setId(null);
		}
		
		permissionManager.saveOrUpdate(permission);
		if(StringUtils.isBlank(id)){
			Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		}else{
			Flash.current().success(UPDATE_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		}
		
		return LIST_ACTION;
	}
	
	/**进入更新页面*/
	public String edit() {
		
//		permission = (Permission)permissionManager.getById(id);
		getPermissionList();
		return EDIT_JSP;
	}

	/**
	 * 取得移动时的访问入口列表 组合成树型显示 移动某访问入口 此访问入口及其所有子访问入口都不显示 因为不能往自己的下级访问入口移动
	 */
	private void getPermissionList() {
		List<Permission> permissionList = permissionManager.findByParentId(null, permission.getApplicationId());//取得所有最上级的菜单 也就是parentId为NULL
		String level ="";//用来表示级数的，没多一级 level加--
		List<Permission> resultResList = new ArrayList<Permission>();
		for(Permission per : permissionList){
			//如果不是目录就不显示，只能移动到某目录下
			if(per.getType().equals(Permission.TYPE_DIC)){
				//如果不是修改的菜单就显示 主要是为了移动菜单不出错
				if(!per.getId().equals(permission.getId())){
					resultResList.add(per);
					getChildPermission(resultResList,per,level);
				}
			}
		}
		getRequest().setAttribute(PERMISSIONS, resultResList);
	}
	/**
	 * 通过递归取得移动所有的子的访问入口 组合成树型显示
	 * @param resultResList 结果集合对象
	 * @param parentPer 父访问入口
	 * @param level 级别 用来下拉框显示树型结构
	 */
	private void getChildPermission(List<Permission> resultResList,Permission parentPer,String level){
		List<Permission> perList = permissionManager.findByParentId(parentPer.getId(), permission.getApplicationId());
		level+="——";
		if(perList!=null){
			for(Permission per : perList){
				//如果不是目录就不显示，只能移动到某目录下
				if(per.getType().equals(Permission.TYPE_DIC)){
					//如果不是修改的菜单就显示 主要是为了移动菜单不出错
					if(!per.getId().equals(permission.getId())){
						resultResList.add(per);
						per.setName(level+per.getName());
						getChildPermission(resultResList,per,level);
					}
				}
			}
		}
	}
	/**保存更新对象*/
	public String update() {
		//判断URL是否存在
		if (permissionManager.countUrl(permission.getUrl())>1) {
			Flash.current().success("系统此"+Permission.ALIAS_URL+"已经存在");
			return EDIT_JSP;
		}
		//同一应用下的目录和主功能入口编码不能重复
		//同一主功能入口下的子功能入口编码不能重复
		//添加前判断code是否唯一
		if(!Permission.TYPE_FUNCTION.equals(permission.getType())){
			if(!permissionManager.isUniqueByDf(permission, "applicationId,code")){
				Flash.current().success("同一应用下的"+Permission.ALIAS_CODE+"已经存在");
				return EDIT_JSP;
			}
		}else{
			if(!permissionManager.isUniqueByDf(permission, "parentId,code")){
				Flash.current().success("同一主功能访问入口下的"+Permission.ALIAS_CODE+"已经存在");
				return EDIT_JSP;
			}
		}
		//更新前判断同一父菜单下的名称是否唯一
		if(!permissionManager.isUniqueByDf(permission, "name,parentId")){
			Flash.current().success("同一父访问入口下"+Permission.ALIAS_NAME+"已经存在");
			getPermissionList();
			return EDIT_JSP;
		}
		
		applicationId = permission.getApplicationId();
		permissionManager.update(this.permission);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/** 
	 * 到移动页面
	 * @return
	 */
	public String toMove(){
		getPermissionList();
		return MOVE_JSP;
	}
	
	/**
	 * 访问入口移动方法 移动某访问入口时 此访问入口的所有子访问入口随之移动
	 * @return
	 */
	public String move(){
		if("".equals(permission.getParentId())){
			permission.setParentId(null);
		}
		permissionManager.update(this.permission);
		Flash.current().success(MOVE_SUCCESS);
		getRequest().setAttribute("flag", "refresh");
		return CLOSE_DIALOG;
	}
	
	
	/**删除对象*/
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");
			Permission permission = permissionManager.getById(id);
			
			permissionManager.deleteByAllChild(permission);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	
	/**
	 * 到应用访问入口管理页面 左侧为树型应用访问入口 右侧为子访问入口列表及操作
	 */
	public String main() {
		return MAIN;
	}
	
	/**
	 * 跳转到左侧应用访问入口
	 */
	public String leftTree() {
		return LEFTTREE;
	} 
	
	
	/**
	 * 加载应用访问入口左侧树型XML
	 */
	public String permissionXML() {

		try {
			Application app = applicationManager.getById(applicationId);
			Set<Permission> pers = Common.getTopPermissionList(this.permissionManager.findByApp(applicationId));//查询某应用下的所有访问入口 并组成树状结构
			
			String menuXmlString = Common.getPermissionTreeXML(pers,app.getName());
			
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PermissionQuery getQuery() {
		return query;
	}

	public void setQuery(PermissionQuery query) {
		this.query = query;
	}
	

}
