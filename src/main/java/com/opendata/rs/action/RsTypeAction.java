/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.action;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.opendata.common.util.HTMLUtil;
import com.opendata.common.util.Util;

import com.opendata.rs.model.*;
import com.opendata.rs.service.*;
import com.opendata.rs.vo.query.*;

/**
 * 资源类型action,用于对资源类型模块的请求转发处理
 * 
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="QUERY_JSP", location="/WEB-INF/pages/rs/RsType/query.jsp", type="dispatcher"),
@Result(name="LIST_JSP", location="/WEB-INF/pages/rs/RsType/list.jsp", type="dispatcher"),
@Result(name="CREATE_JSP", location="/WEB-INF/pages/rs/RsType/create.jsp", type="dispatcher"),
@Result(name="EDIT_JSP", location="/WEB-INF/pages/rs/RsType/edit.jsp", type="dispatcher"),
@Result(name="SHOW_JSP", location="/WEB-INF/pages/rs/RsType/show.jsp", type="dispatcher"),
@Result(name="LIST_ACTION", location="../rs/RsType!list.do", type="redirectAction"),
@Result(name="PROPERTY_HTML", location="/WEB-INF/pages/rs/RsType/propertyHTML.jsp", type="dispatcher"),
@Result(name="TYPEPROPER_LIST_JSP", location="/WEB-INF/pages/rs/RsType/typeProperList.jsp", type="dispatcher")
})
public class RsTypeAction extends BaseStruts2Action implements Preparable, ModelDriven {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	protected static final String PROPERTY_HTML = "PROPERTY_HTML";
	protected static final String TYPEPROPER_LIST_JSP = "TYPEPROPER_LIST_JSP";
	
	private RsTypeManager rsTypeManager;
	private RsPropertyManager rsPropertyManager;
	
	private RsType rsType;
	java.lang.String id = null;
	private String[] items;
	private String html;

	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			rsType = new RsType();
		} else {
			rsType = (RsType)rsTypeManager.getById(id);
		}
	}

	/** 
	 * 执行搜索
	 * @return
	 */
	public String list() {
		RsTypeQuery query = newQuery(RsTypeQuery.class,DEFAULT_SORT_COLUMNS);
		String name = query.getTypeName();
		if (name != null && !name.equals("")) {
			query.setTypeName("%" + name.trim() + "%");
		}
		Page page = rsTypeManager.findPage(query);
		savePage(page,query);
		query.setTypeName(name);
		return LIST_JSP;
	}
	
	/**
	 * 关联属性列表
	 * @return
	 */
	public String typeProperList(){
		List<RsProperty> properList=rsPropertyManager.findAll();
		getRequest().setAttribute("properList", properList);
		return TYPEPROPER_LIST_JSP;
	}
	
	/**
	 * 保存关联关系
	 * @return
	 */
	public void saveTypeProper(){
		String[] propertyIds=getRequest().getParameterValues("propertyids");
		if (propertyIds != null) {
			Set<RsProperty> rsPropertys = new HashSet<RsProperty>(0);
			for (int i = 0; i < propertyIds.length; i++) {
				String str = propertyIds[i].trim();
				rsPropertys.add(rsPropertyManager.getById(str));
			}
			rsType.setRsPropertys(rsPropertys);
			rsTypeManager.save(rsType);
		}
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
		rsType.setTs(Util.getTimeStampString());
		rsTypeManager.save(rsType);
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
		rsType.setTs(Util.getTimeStampString());
		
		rsTypeManager.update(this.rsType);
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
			rsType = (RsType)rsTypeManager.getById(id);
			if(rsType!=null){
				rsType.setRsPropertys(null);//清空关联关系
				rsTypeManager.removeById(rsType.getId());
			}
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 根据当前类型ID获取对应HTML
	 * @return
	 */
	public void getPropertyHTML()throws Exception{
		RsType rsType = rsTypeManager.getById(id);
		if(rsType != null){
			html = HTMLUtil.generateHTML(rsType.getRsPropertys());
		}
		PrintWriter pw = getResponse().getWriter();
		pw.write(html);
		pw.close();
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsTypeManager(RsTypeManager manager) {
		this.rsTypeManager = manager;
	}	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsPropertyManager(RsPropertyManager manager) {
		this.rsPropertyManager = manager;
	}
	
	public Object getModel() {
		return rsType;
	}
	
	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
}
