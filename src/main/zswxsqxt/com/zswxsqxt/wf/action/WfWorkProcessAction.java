package com.zswxsqxt.wf.action;
import com.zswxsqxt.wf.model.WfWorkProcess;
import com.zswxsqxt.wf.service.WfWorkProcessManager;
import com.zswxsqxt.wf.query.WfWorkProcessQuery;

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
 * describe:流程工作任务表管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfWorkProcess/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfWorkProcess/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfWorkProcess/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfWorkProcess/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfWorkProcess!list.do?${params}", type = "redirectAction")
})
public class WfWorkProcessAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfWorkProcessManager wfWorkProcessManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfWorkProcess wfWorkProcess;
	private WfWorkProcessQuery query=new WfWorkProcessQuery();
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfWorkProcess= new WfWorkProcess();
		} else {
			this.wfWorkProcess = (WfWorkProcess)this.wfWorkProcessManager.getById(id);
		}
	}
		
	public WfWorkProcessManager getWfWorkProcessManager() {
		return this.wfWorkProcessManager;
	}

	public void setWfWorkProcessManager(WfWorkProcessManager wfWorkProcessManager) {
		this.wfWorkProcessManager = wfWorkProcessManager;
	}
	
	public Object getModel() {
		return this.wfWorkProcess;
	}
	
	public WfWorkProcessQuery getQuery() {
		return query;
	}

	public void setQuery(WfWorkProcessQuery query) {
		this.query = query;
	}
	
	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.wfWorkProcessManager.findPage(query);
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
		this.wfWorkProcess.setTs(new Date());
		this.wfWorkProcessManager.save(this.wfWorkProcess);
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
		this.wfWorkProcessManager.update(this.wfWorkProcess);
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
			this.wfWorkProcessManager.removeById(id);
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
