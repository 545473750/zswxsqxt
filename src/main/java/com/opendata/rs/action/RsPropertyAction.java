/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.action;

import java.io.IOException;
import java.io.PrintWriter;

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

import com.opendata.common.util.Util;

import com.opendata.rs.model.*;
import com.opendata.rs.service.*;
import com.opendata.rs.vo.query.*;

/**
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="QUERY_JSP", location="/WEB-INF/pages/rs/RsProperty/query.jsp", type="dispatcher"),
@Result(name="LIST_JSP", location="/WEB-INF/pages/rs/RsProperty/list.jsp", type="dispatcher"),
@Result(name="CREATE_JSP", location="/WEB-INF/pages/rs/RsProperty/create.jsp", type="dispatcher"),
@Result(name="EDIT_JSP", location="/WEB-INF/pages/rs/RsProperty/edit.jsp", type="dispatcher"),
@Result(name="SHOW_JSP", location="/WEB-INF/pages/rs/RsProperty/show.jsp", type="dispatcher"),
@Result(name="LIST_ACTION", location="../rs/RsProperty!list.do", type="redirectAction")
})
public class RsPropertyAction extends BaseStruts2Action implements Preparable, ModelDriven {
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	private RsPropertyManager rsPropertyManager;
	
	private RsProperty rsProperty;
	java.lang.String id = null;
	private String[] items;

	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			rsProperty = new RsProperty();
		} else {
			rsProperty = (RsProperty)rsPropertyManager.getById(id);
		}
	}
	
	public Object getModel() {
		return rsProperty;
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
		RsPropertyQuery query = newQuery(RsPropertyQuery.class,DEFAULT_SORT_COLUMNS);
		String name=query.getAttrName();
		if (name != null && !name.equals("")) {
			query.setAttrName("%" + name.trim() + "%");
		}
		Page page = rsPropertyManager.findPage(query);
		savePage(page,query);
		query.setAttrName(name);
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
		rsProperty.setTs(Util.getTimeStampString());
		rsProperty.setEditable("0");//默认为可编辑
		rsPropertyManager.save(rsProperty);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**
	 * 验证属性编号是否已注册
	 */
	public void valiCode(){
		String code=getRequest().getParameter("attrCode");
		RsProperty property=rsPropertyManager.findByCode(code);
		if(property!=null){
			PrintWriter pw;
			try {
				pw = getResponse().getWriter();
				pw.write("属性编号已被占用，请另换编号");//打印的内容，显示在鼠标悬停事件上
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		rsProperty.setTs(Util.getTimeStampString());
		//修改时如果修改为文本或者文本域时，清空类型值
		if(rsProperty.getInputType().equals("1")||rsProperty.getInputType().equals("2")){
			rsProperty.setOptionalValue(null);
		}
		rsPropertyManager.update(this.rsProperty);
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
			rsProperty = (RsProperty)rsPropertyManager.getById(id);
			if(rsProperty!=null){
				rsProperty.setRsTypes(null);//清空关联关系
				rsPropertyManager.removeById(rsProperty.getId());
			}
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsPropertyManager(RsPropertyManager manager) {
		this.rsPropertyManager = manager;
	}
}
