package com.zswxsqxt.wf.action;
import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.model.WfInstanceAudit;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.model.WfInstanceResult;
import com.zswxsqxt.wf.service.WfInstanceAuditManager;
import com.zswxsqxt.wf.service.WfInstanceManager;
import com.zswxsqxt.wf.service.WfInstanceNodeManager;
import com.zswxsqxt.wf.service.WfInstanceResultManager;
import com.zswxsqxt.wf.query.WfInstanceAuditQuery;

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
 * describe:流程审核表管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfInstanceAudit/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfInstanceAudit/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfInstanceAudit/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfInstanceAudit/view.jsp", type = "dispatcher"),
@Result(name="auditxd_List",location="../course/Detail!findAuditPage.do?${params}", type = "redirectAction"),
@Result(name="listAction",location="../wf/WfInstanceAudit!list.do?${params}", type = "redirectAction"),
@Result(name="audit_list",location="../bj/ClassBigclass!findAuditList.do?${params}", type = "redirectAction")
})
@Result(name="auditlist",location="../course/Apply!findAuditList.do?${params}", type = "redirectAction")

public class WfInstanceAuditAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private static final String AUDIA_LIST = "audit_list";
	private static final String AUDIAAPPLY_LIST = "auditlist";
	
	private WfInstanceAuditManager wfInstanceAuditManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfInstanceAudit wfInstanceAudit;
	private WfInstanceAuditQuery query=new WfInstanceAuditQuery();
	private WfInstanceNodeManager wfInstanceNodeManager;//流程实例节点管理类
	private WfInstanceResultManager wfInstanceResultManager;//流程实例结果管理类
	private WfInstanceManager wfInstanceManager;//流程实例管理
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfInstanceAudit= new WfInstanceAudit();
		} else {
			this.wfInstanceAudit = (WfInstanceAudit)this.wfInstanceAuditManager.getById(id);
		}
	}
		
	public WfInstanceAuditManager getWfInstanceAuditManager() {
		return this.wfInstanceAuditManager;
	}

	public void setWfInstanceAuditManager(WfInstanceAuditManager wfInstanceAuditManager) {
		this.wfInstanceAuditManager = wfInstanceAuditManager;
	}
	
	public Object getModel() {
		return this.wfInstanceAudit;
	}
	
	public WfInstanceAuditQuery getQuery() {
		return query;
	}

	public void setQuery(WfInstanceAuditQuery query) {
		this.query = query;
	}
	
	public WfInstanceNodeManager getWfInstanceNodeManager() {
		return wfInstanceNodeManager;
	}

	public void setWfInstanceNodeManager(WfInstanceNodeManager wfInstanceNodeManager) {
		this.wfInstanceNodeManager = wfInstanceNodeManager;
	}

	public WfInstanceResultManager getWfInstanceResultManager() {
		return wfInstanceResultManager;
	}

	public void setWfInstanceResultManager(
			WfInstanceResultManager wfInstanceResultManager) {
		this.wfInstanceResultManager = wfInstanceResultManager;
	}

	public WfInstanceManager getWfInstanceManager() {
		return wfInstanceManager;
	}

	public void setWfInstanceManager(WfInstanceManager wfInstanceManager) {
		this.wfInstanceManager = wfInstanceManager;
	}

	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.wfInstanceAuditManager.findPage(query);
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
		保存新增审核信息到审核表和结果表中
	*/
//	public String saveAdd()
//	{
//		if(query.getInsId()!=null){
//			WfInstanceNode wfInstanceNode = this.wfInstanceNodeManager.getNode(query.getInsId(), query.getState());
//			if(wfInstanceNode!=null){
//				query.setWfInstanceNode(wfInstanceNode);
//			}
//		}
//		this.wfInstanceAudit.setAuditUserId(this.getCurrUser().getUserID());
//		this.wfInstanceAudit.setAuditUserName(this.getCurrUser().getUserName());
//		this.wfInstanceAudit.setTs(new Date());
//		this.wfInstanceAudit.setWfInstanceNode(query.getWfInstanceNode());
//		this.wfInstanceAuditManager.save(this.wfInstanceAudit);
//		
//		//添加审核信息到结果表中
//		WfInstance wfInstance = this.wfInstanceManager.getById(query.getInsId());
//		WfInstanceResult wfInstanceResult = new WfInstanceResult();
//		wfInstanceResult.setAuditUserId(this.getCurrUser().getUserID());//审核人
//		wfInstanceResult.setRefFlag(query.getWfInstanceNode().getGroupFlag());//所在组，关联类别
//		wfInstanceResult.setRefId(query.getInsId());//关联id
//		wfInstanceResult.setResult(Integer.parseInt(wfInstanceAudit.getResult()));//审核结果
//		wfInstanceResult.setState(wfInstance.getState());//节点
//		wfInstanceResult.setTs(new Date());//创建时间
//		wfInstanceResultManager.save(wfInstanceResult);//保存结果表
//		
//		wfInstanceAuditManager.updateInsState(query.getInsId(), query.getState(),this.getCurrUser().getUserID());//满足条件修改流程实例状态，进入下个节点
//		
//		//流程判断，如果所在组是1、2、3则属于课程流程类，则需要跳转到课程页面
//		String groupFlag = query.getWfInstanceNode().getGroupFlag();
//		if(groupFlag.equals("1")||groupFlag.equals("2")||groupFlag.equals("3")){
//			return AUDIAAPPLY_LIST;
//		}
//		if(groupFlag.equals("1")){
//			return "fenyuan_list";
//		}
//		//流程判断，如果所在组是6则属于实验课流程类，则需要跳转到实验课页面
//		if(groupFlag.equals("6")){
//			return "auditxd_List";
//		}
//		return AUDIA_LIST;
//	}
	
	/**
	 * 保存新增审核信息到审核表和结果表中
	 * @return
	 */
	public String saveAdd(){
		if(query.getInsId()!=null){
			WfInstanceNode wfInstanceNode = this.wfInstanceNodeManager.getNode(query.getInsId(), query.getState());
			if(wfInstanceNode!=null){
				query.setWfInstanceNode(wfInstanceNode);
			}
		}
		this.wfInstanceAudit.setAuditUserId(this.getCurrUser().getUserID());
		this.wfInstanceAudit.setAuditUserName(this.getCurrUser().getUserName());
		this.wfInstanceAudit.setTs(new Date());
		this.wfInstanceAudit.setWfInstanceNode(query.getWfInstanceNode());
		this.wfInstanceAuditManager.save(this.wfInstanceAudit,query.getInsId(),query.getState());//保存，（包括保存审核结果表）
		
		//流程判断，如果所在组是1、2、3则属于课程流程类，则需要跳转到课程页面
		String groupFlag = query.getWfInstanceNode().getGroupFlag();
		if(groupFlag.equals("1")||groupFlag.equals("2")||groupFlag.equals("3")){
			return AUDIAAPPLY_LIST;
		}
		if(groupFlag.equals("1")){
			return "fenyuan_list";
		}
		//流程判断，如果所在组是6则属于实验课流程类，则需要跳转到实验课页面
		if(groupFlag.equals("6")){
			return "auditxd_List";
		}
		return AUDIA_LIST;
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
		this.wfInstanceAuditManager.update(this.wfInstanceAudit);
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
			this.wfInstanceAuditManager.removeById(id);
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
