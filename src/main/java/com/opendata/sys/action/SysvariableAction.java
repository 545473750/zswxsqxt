/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import com.opendata.application.model.Application;
import com.opendata.application.service.ApplicationManager;
import com.opendata.common.base.*;
import com.opendata.common.util.SysVariableUtil;

import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;

import com.opendata.lg.query.LanguageQuery;
import com.opendata.sys.model.*;
import com.opendata.sys.service.*;
import com.opendata.sys.vo.query.*;

/**
 * 系统变量action, 用户系统变量模块的请求转发等操作
 * @author顾保臣
 */
@Namespace("/sys")
@Results({
@Result(name="list",location="/WEB-INF/pages/sys/Sysvariable/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Sysvariable/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/Sysvariable/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/Sysvariable/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Sysvariable/show.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//sys/Sysvariable!list.do", type = "redirectAction"),
})
public class SysvariableAction extends BaseStruts2Action implements Preparable,ModelDriven{
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
	
	private SysvariableManager sysvariableManager;
	private ApplicationManager applicationManager;
	private SysvariableQuery query=new SysvariableQuery();
	
	private SysVariableUtil sysVariableUtil;
	@Autowired
	public void setSysVariableUtil(SysVariableUtil sysVariableUtil) {
		this.sysVariableUtil = sysVariableUtil;
	}

	private Sysvariable sysvariable;
	java.lang.String id = null;
	private String[] items;
	
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			sysvariable = new Sysvariable();
		} else {
			sysvariable = (Sysvariable)sysvariableManager.getById(id);
		}
	}

	public Object getModel() {
		return sysvariable;
	}
	
	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {

		// 应用列表
		this.applications = this.applicationManager.findAllByDf();
		query.setDf("0");
		Page page = this.sysvariableManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		return SHOW_JSP;
	}
	
	private List<Application> applications; // 应用列表
	
	/**
	 * 进入新增页面
	 * @return
	 */
	public String create() {
		// 应用列表
		this.applications = this.applicationManager.findAllByDf();
		return CREATE_JSP;
	}
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		//先判断变量名称是否重复
		if(!sysvariableManager.isUniqueByDf(sysvariable, "name")){
			Flash.current().success("变量名称已存在！");
			// 应用列表
			this.applications = this.applicationManager.findAllByDf();
			return CREATE_JSP;
		}
		
		sysvariable.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		if(this.sysvariable.getApplicationId() != null && this.sysvariable.getApplicationId().equals("")) {
			this.sysvariable.setApplicationId(null);
		}
		
		sysvariableManager.save(sysvariable);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
		// 应用列表
		this.applications = this.applicationManager.findAllByDf();
		return EDIT_JSP;
	}
	
	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		//先判断变量名称是否重复
		if(!sysvariableManager.isUniqueByDf(sysvariable, "name")){
			Flash.current().success("变量名称已存在！");
			// 应用列表
			this.applications = this.applicationManager.findAllByDf();
			return EDIT_JSP;
		}
		
		if(this.sysvariable.getApplicationId() != null && this.sysvariable.getApplicationId().equals("")) {
			this.sysvariable.setApplicationId(null);
		}
		
		sysvariableManager.update(this.sysvariable);
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
			sysvariable = (Sysvariable)sysvariableManager.getById(id);
			sysvariable.setDf("1");
			sysvariableManager.update(this.sysvariable);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setSysvariableManager(SysvariableManager manager) {
		this.sysvariableManager = manager;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public SysvariableQuery getQuery() {
		return query;
	}

	public void setQuery(SysvariableQuery query) {
		this.query = query;
	}
}
