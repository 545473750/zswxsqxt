/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.action;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opendata.common.base.BaseStruts2Action;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.rs.action.Commom.ConcatStr;
import com.opendata.rs.action.Commom.ConvertTreeToXML;
import com.opendata.rs.model.RsStructure;
import com.opendata.rs.service.RsStructureManager;
import com.opendata.rs.vo.query.RsStructureQuery;
import com.opendata.common.util.Util;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 资源体系结构
 * 
 * @author 王海龙
 * 
 */
@Namespace("/rs")
@Results({
	@Result(name = "QUERY_JSP", location = "/WEB-INF/pages/rs/RsStructure/query.jsp", type = "dispatcher"),
	@Result(name = "LIST_JSP", location = "/WEB-INF/pages/rs/RsStructure/list.jsp", type = "dispatcher"),
	@Result(name = "CREATE_JSP", location = "/WEB-INF/pages/rs/RsStructure/create.jsp", type = "dispatcher"),
	@Result(name = "EDIT_JSP", location = "/WEB-INF/pages/rs/RsStructure/edit.jsp", type = "dispatcher"),
	@Result(name = "SHOW_JSP", location = "/WEB-INF/pages/rs/RsStructure/show.jsp", type = "dispatcher"),
	@Result(name = "LIST_ACTION", location = "../rs/RsStructure!list.do", type = "redirectAction"),
	@Result(name = "LEFT_ACTION", location = "/WEB-INF/pages/rs/RsStructure/left.jsp", type = "dispatcher"),
	@Result(name = "INDEX_ACTION", location = "/WEB-INF/pages/rs/RsStructure/index.jsp", type = "dispatcher") 
})
public class RsStructureAction extends BaseStruts2Action implements Preparable, ModelDriven {
	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = " sort asc";

	// forward paths
	protected static final String LEFT_ACTION = "LEFT_ACTION";
	protected static final String INDEX_ACTION = "INDEX_ACTION";
	// redirect paths,startWith: !
	private RsStructureManager rsStructureManager;
	private RsStructure rsStructure;
	java.lang.String id = null;
	private String[] items;

	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			rsStructure = new RsStructure();
		} else {
			rsStructure = (RsStructure) rsStructureManager.getById(id);
		}
	}

	public Object getModel() {
		return rsStructure;
	}

	/**
	 * 执行搜索
	 * @throws Exception
	 */
	public String list() throws Exception {
		RsStructureQuery query = newQuery(RsStructureQuery.class,
				DEFAULT_SORT_COLUMNS);
		if (query.getName() != null && !query.getName().equals(""))
			query.setName("%" + query.getName() + "%");
		Page page = rsStructureManager.findPage(query);
		getTree();
		if (query.getName() != null)
			query.setName(query.getName().substring(1,
					query.getName().length() - 1));
		savePage(page, query);
		return LIST_JSP;
	}

	private void getTree() throws Exception {
		List<RsStructure> list = rsStructureManager.findAllRoot();
		list = new ConcatStr().concatStr(list);
		getRequest().setAttribute("structures", list);
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
	 * @throws Exception
	 */
	public String create() throws Exception {
		getTree();
		return CREATE_JSP;
	}

	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		rsStructure.setTs(Util.getTimeStampString());
		rsStructureManager.save(rsStructure);
		Flash.current().success(CREATED_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}

	/**
	 * 进入更新页面
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		getTree();
		return EDIT_JSP;
	}

	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		rsStructure.setTs(Util.getTimeStampString());
		rsStructureManager.update(this.rsStructure);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}

	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String(
					(String) params.get("id"));
			rsStructure = (RsStructure) rsStructureManager.getById(id);
			rsStructure.setTs(Util.getTimeStampString());
			rsStructureManager.removeById(this.rsStructure.getId());
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	public String findRootChildren() {
		return LIST_ACTION;
	}

	public void showTree() throws Exception {
		String ctx = getRequest().getContextPath();
		List<RsStructure> list = rsStructureManager.findAllRoot();
		Collections.sort(list);
		String xml = ConvertTreeToXML.convertTreeToXML(list, ctx);
		HttpServletResponse response = getResponse();
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-control", "no-cache");
		response.getWriter().print(xml);
	}

	public String showLeft() {
		return LEFT_ACTION;
	}

	public String forwardIndex() {
		return INDEX_ACTION;
	}
	
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsStructureManager(RsStructureManager manager) {
		this.rsStructureManager = manager;
	}
	
	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
}
