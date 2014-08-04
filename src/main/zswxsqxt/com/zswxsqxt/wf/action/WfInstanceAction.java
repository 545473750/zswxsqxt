package com.zswxsqxt.wf.action;
import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.service.WfInstanceManager;
import com.zswxsqxt.wf.query.WfInstanceQuery;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.opendata.common.base.BaseStruts2Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * describe:流程实例管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfInstance/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfInstance/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfInstance/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfInstance/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfInstance!list.do?${params}", type = "redirectAction")
})
public class WfInstanceAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfInstanceManager wfInstanceManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfInstance wfInstance;
	private WfInstanceQuery query=new WfInstanceQuery();
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfInstance= new WfInstance();
		} else {
			this.wfInstance = (WfInstance)this.wfInstanceManager.getById(id);
		}
	}
		
	public WfInstanceManager getWfInstanceManager() {
		return this.wfInstanceManager;
	}

	public void setWfInstanceManager(WfInstanceManager wfInstanceManager) {
		this.wfInstanceManager = wfInstanceManager;
	}
	
	public Object getModel() {
		return this.wfInstance;
	}
	
	public WfInstanceQuery getQuery() {
		return query;
	}

	public void setQuery(WfInstanceQuery query) {
		this.query = query;
	}
	
	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.wfInstanceManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
		新增
	*/
	public String add()
	{
		return ADD_JSP;
	}
	
	/**	
		保存新增
	*/
	public String saveAdd()
	{
		this.wfInstance.setTs(new Date());
		this.wfInstanceManager.save(this.wfInstance);
		return LIST_ACTION;
	}
	
	/**	
		修改
	*/
	public String edit()
	{
		return EDIT_JSP;
	}
	
	
	/**	
		保存修改
	*/
	public String saveEdit()
	{
		this.wfInstanceManager.update(this.wfInstance);
		return LIST_ACTION;
	}
		
	/**	
		删除
	*/
	public String remove()
	{
		for(int i = 0; i < items.length; i++)
		 {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");
			this.wfInstanceManager.removeById(id);
		}
		return LIST_ACTION;
	}
	
	/**	
		查看
	*/
	public String view()
	{
		return VIEW_JSP;
	}
	
}
