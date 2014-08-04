package com.zswxsqxt.wf.action;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.service.WfInstanceNodeManager;
import com.zswxsqxt.wf.query.WfInstanceNodeQuery;

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
 * describe:流程实例节点表管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfInstanceNode/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfInstanceNode/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfInstanceNode/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfInstanceNode/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfInstanceNode!list.do?${params}", type = "redirectAction")
})
public class WfInstanceNodeAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfInstanceNodeManager wfInstanceNodeManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfInstanceNode wfInstanceNode;
	private WfInstanceNodeQuery query=new WfInstanceNodeQuery();
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfInstanceNode= new WfInstanceNode();
		} else {
			this.wfInstanceNode = (WfInstanceNode)this.wfInstanceNodeManager.getById(id);
		}
	}
		
	public WfInstanceNodeManager getWfInstanceNodeManager() {
		return this.wfInstanceNodeManager;
	}

	public void setWfInstanceNodeManager(WfInstanceNodeManager wfInstanceNodeManager) {
		this.wfInstanceNodeManager = wfInstanceNodeManager;
	}
	
	public Object getModel() {
		return this.wfInstanceNode;
	}
	
	public WfInstanceNodeQuery getQuery() {
		return query;
	}

	public void setQuery(WfInstanceNodeQuery query) {
		this.query = query;
	}
	
	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.wfInstanceNodeManager.findPage(query);
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
		this.wfInstanceNode.setTs(new Date());
		this.wfInstanceNodeManager.save(this.wfInstanceNode);
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
		this.wfInstanceNodeManager.update(this.wfInstanceNode);
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
			this.wfInstanceNodeManager.removeById(id);
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
