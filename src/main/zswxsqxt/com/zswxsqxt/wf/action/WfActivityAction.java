package com.zswxsqxt.wf.action;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.service.WfActivityManager;
import com.zswxsqxt.wf.query.WfActivityQuery;

import java.util.HashMap;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * describe:流程节点表管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfActivity/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfActivity/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfActivity/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfActivity/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfActivity!list.do?${params}&proId=${proId}", type = "redirectAction")
})
public class WfActivityAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfActivityManager wfActivityManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfActivity wfActivity;
	private WfActivityQuery query=new WfActivityQuery();
	private DicUtil dicUtil;//数据字典工具类
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfActivity= new WfActivity();
		} else {
			this.wfActivity = (WfActivity)this.wfActivityManager.getById(id);
		}
	}
		
	public WfActivityManager getWfActivityManager() {
		return this.wfActivityManager;
	}

	public void setWfActivityManager(WfActivityManager wfActivityManager) {
		this.wfActivityManager = wfActivityManager;
	}
	
	public Object getModel() {
		return this.wfActivity;
	}
	
	public WfActivityQuery getQuery() {
		return query;
	}

	public void setQuery(WfActivityQuery query) {
		this.query = query;
	}

	public DicUtil getDicUtil() {
		return dicUtil;
	}

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	/**
		查询列表
	*/
	public String list()
	{
		//query.setWfProject(wfProject);//查询节点是只查询指定工作流的节点
		Page page = this.wfActivityManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
		新增
	*/
	public String add()
	{
		int num = 0;
		WfActivity activity = this.wfActivityManager.getOrderNum(query.getWfProject());
		if(activity != null&&activity.getOrderNum()!=null){
			num = activity.getOrderNum();
		}
		this.wfActivity.setOrderNum(num+1);	//提前获得节点顺序
		return ADD_JSP;
	}
	
	/**	
		保存新增
	*/
	public String saveAdd()
	{
		this.wfActivity.setGroupFlag(query.getGroupFlag());//所在组
		this.wfActivity.setTs(new Date());
		this.wfActivity.setWfProject(query.getWfProject());//设置流程Id
		this.wfActivityManager.save(this.wfActivity);
		this.wfActivity.setUrl(getDic(this.wfActivity.getGroupFlag())+"?query.state="+this.wfActivity.getOrderNum());//拼接节点的url，从数据字典中获取，并加上节点顺序（即状态）
		this.wfActivityManager.update(wfActivity);
		Flash.current().success("流程节点已保存！");
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
		this.wfActivityManager.update(this.wfActivity);
		Flash.current().success("流程节点已修改！");
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
			this.wfActivityManager.removeById(id);
		}
		//得到指定流程下的所有节点，将节点顺序按照原顺序从小到大重新排序
		List<WfActivity> list = wfActivityManager.getWfActivitys(query.getWfProject().getId());
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				WfActivity wfActivity = list.get(i);
				wfActivity.setOrderNum(i+1);
				wfActivityManager.update(wfActivity);
			}
		}
		Flash.current().success("流程节点已删除！");
		return LIST_ACTION;
	}
	
	/**	
		查看
	*/
	public String view()
	{
		return VIEW_JSP;
	}
	
	//得到所在组的数据字典
	public String getDic(String key){
		String nodeUrl = "nodeUrl";
		HashMap<String, String> nodeUrlMap = this.dicUtil.getDicList(nodeUrl);
		return nodeUrlMap.get(key);
	}
}
