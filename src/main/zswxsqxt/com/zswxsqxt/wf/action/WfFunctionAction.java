package com.zswxsqxt.wf.action;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfFunction;
import com.zswxsqxt.wf.service.WfActivityManager;
import com.zswxsqxt.wf.service.WfFunctionManager;
import com.zswxsqxt.wf.query.WfFunctionQuery;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * describe:流程功能点表管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfFunction/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfFunction/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfFunction/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfFunction/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfFunction!list.do?${params}", type = "redirectAction")
})
public class WfFunctionAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfFunctionManager wfFunctionManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfFunction wfFunction;
	private WfFunctionQuery query=new WfFunctionQuery();
	private WfActivityManager wfActivityManager;
	private DicUtil dicUtil;//数据字典管理
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfFunction= new WfFunction();
		} else {
			this.wfFunction = (WfFunction)this.wfFunctionManager.getById(id);
		}
	}
		
	public WfFunctionManager getWfFunctionManager() {
		return this.wfFunctionManager;
	}

	public void setWfFunctionManager(WfFunctionManager wfFunctionManager) {
		this.wfFunctionManager = wfFunctionManager;
	}
	
	public Object getModel() {
		return this.wfFunction;
	}
	
	public WfFunctionQuery getQuery() {
		return query;
	}

	public void setQuery(WfFunctionQuery query) {
		this.query = query;
	}
	
	public DicUtil getDicUtil() {
		return dicUtil;
	}

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}


	public WfActivityManager getWfActivityManager() {
		return wfActivityManager;
	}

	public void setWfActivityManager(WfActivityManager wfActivityManager) {
		this.wfActivityManager = wfActivityManager;
	}

	/**
		查询列表
	*/
	public String list()
	{
		getDic();
		Page page = this.wfFunctionManager.findPage(query);
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
		this.wfFunction.setInsType(2);	//节点类型，为普通节点
		this.wfFunction.setTs(new Date());
		String parStr = wfFunction.getParName();
		if(!StringUtils.isEmpty(parStr)){
			parStr+=",";
		}
		parStr+="wfid,actid";
		this.wfFunction.setParName(parStr);
		this.wfFunction.setGroupFlag(query.getGroupFlag());	//功能点所在组，为菜单中课程功能点或组班功能点传的值
		this.wfFunctionManager.save(this.wfFunction);
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
		this.wfFunctionManager.update(this.wfFunction);
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
			this.wfFunctionManager.removeById(id);
		}
		return LIST_ACTION;
	}
	
	public void checkIsUse(){
		List<WfActivity> list = this.wfActivityManager.findAllBy("wfInstance", this.wfFunction);
		String flag = "0";
		try {
			PrintWriter out = getResponse().getWriter();
			getResponse().setCharacterEncoding("utf-8");
			if(list.size()>0){	//list长度大于0，表示该功能点已被使用
				flag = "1";
				out.write(flag);
			}else{	//未被使用
				out.write(flag);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**	
		查看
	*/
	public String view()
	{
		getDic();
		return VIEW_JSP;
	}
	
	public void getDic(){
		String szz = "szz";
		HashMap<String, String> gzzMap = this.dicUtil.getDicList(szz);
		getRequest().setAttribute("szz", gzzMap);
	}
	
}
