/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.action;

import java.util.Hashtable;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.util.Util;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.rs.model.RsExpandProperty;
import com.opendata.rs.service.RsExpandPropertyManager;
import com.opendata.rs.vo.query.RsExpandPropertyQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="QUERY_JSP", location="/WEB-INF/pages/rs/RsExpandProperty/query.jsp", type="dispatcher"),
@Result(name="LIST_JSP", location="/WEB-INF/pages/rs/RsExpandProperty/list.jsp", type="dispatcher"),
@Result(name="CREATE_JSP", location="/WEB-INF/pages/rs/RsExpandProperty/create.jsp", type="dispatcher"),
@Result(name="EDIT_JSP", location="/WEB-INF/pages/rs/RsExpandProperty/edit.jsp", type="dispatcher"),
@Result(name="SHOW_JSP", location="/WEB-INF/pages/rs/RsExpandProperty/show.jsp", type="dispatcher"),
@Result(name="LIST_ACTION", location="../rs/RsExpandProperty!list.do", type="redirectAction")
})
public class RsExpandPropertyAction extends BaseStruts2Action implements Preparable, ModelDriven {
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	private RsExpandPropertyManager rsExpandPropertyManager;
	
	private RsExpandProperty rsExpandProperty;
	java.lang.String id = null;
	private String[] items;
	
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			rsExpandProperty = new RsExpandProperty();
		} else {
			rsExpandProperty = (RsExpandProperty)rsExpandPropertyManager.getById(id);
		}
	}
	
	public Object getModel() {
		return rsExpandProperty;
	}
	
	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	
	
	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {
		RsExpandPropertyQuery query = newQuery(RsExpandPropertyQuery.class,DEFAULT_SORT_COLUMNS);
		Page page = rsExpandPropertyManager.findPage(query);
		savePage(page,query);
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
		rsExpandProperty.setTs(Util.getTimeStampString());
		rsExpandPropertyManager.save(rsExpandProperty);
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
		rsExpandProperty.setTs(Util.getTimeStampString());
		rsExpandPropertyManager.update(this.rsExpandProperty);
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
			rsExpandProperty = (RsExpandProperty)rsExpandPropertyManager.getById(id);
			rsExpandProperty.setTs(Util.getTimeStampString());
			rsExpandPropertyManager.delete(this.rsExpandProperty);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsExpandPropertyManager(RsExpandPropertyManager manager) {
		this.rsExpandPropertyManager = manager;
	}
}
