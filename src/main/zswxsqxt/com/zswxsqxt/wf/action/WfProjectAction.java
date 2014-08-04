package com.zswxsqxt.wf.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.RoleManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfActivityParticipan;
import com.zswxsqxt.wf.model.WfProject;
import com.zswxsqxt.wf.query.WfProjectQuery;
import com.zswxsqxt.wf.service.WfActivityManager;
import com.zswxsqxt.wf.service.WfActivityParticipanManager;
import com.zswxsqxt.wf.service.WfProjectManager;

/**
 * describe:流程表管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfProject/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfProject/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfProject/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfProject/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfProject!list.do?${params}", type = "redirectAction"),
@Result(name="addNode",location="/WEB-INF/pages/wf/wfProject/addNode.jsp",type = "dispatcher")
})
public class WfProjectAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String ADDNODE_JSP = "addNode";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfProjectManager wfProjectManager;//流程管理
	private WfActivityManager wfActivityManager;//流程节点管理
	private WfActivityParticipanManager wfActivityParticipanManager;//流程节点参与者管理
	private RoleManager roleManager;//角色管理
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfProject wfProject;
	private WfProjectQuery query=new WfProjectQuery();
	@Autowired
	private OrganizationUserRelationManager ouRelationsManager;
	private DicUtil dicUtil;
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfProject= new WfProject();
		} else {
			this.wfProject = (WfProject)this.wfProjectManager.getById(id);
		}
		getDic();
	}
		
	public WfProjectManager getWfProjectManager() {
		return this.wfProjectManager;
	}

	public void setWfProjectManager(WfProjectManager wfProjectManager) {
		this.wfProjectManager = wfProjectManager;
	}
	
	public Object getModel() {
		return this.wfProject;
	}
	
	public WfProjectQuery getQuery() {
		return query;
	}

	public void setQuery(WfProjectQuery query) {
		this.query = query;
	}
	
	public OrganizationUserRelationManager getOuRelationsManager() {
		return ouRelationsManager;
	}

	public void setOuRelationsManager(
			OrganizationUserRelationManager ouRelationsManager) {
		this.ouRelationsManager = ouRelationsManager;
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

	public WfActivityParticipanManager getWfActivityParticipanManager() {
		return wfActivityParticipanManager;
	}

	public void setWfActivityParticipanManager(
			WfActivityParticipanManager wfActivityParticipanManager) {
		this.wfActivityParticipanManager = wfActivityParticipanManager;
	}

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.wfProjectManager.findPage(query);
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
		this.wfProject.setAddUserId(this.getCurrUser().getUserID());				//添加人
		this.wfProject.setAddUserName(this.getCurrUser().getUserName());		//添加人姓名
		//this.wfProject.setGroupFlag(query.getGroupFlag());	//所在组（ 1表课程，2表协作组，3表示组班；4表示公文）
		this.wfProject.setTs(new Date());					//创建时间
		this.wfProject.setUseState(0);						//启用状态，0未启用 1启用2停止
		this.wfProjectManager.save(this.wfProject);
		Flash.current().success("流程已保存！");
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
		this.wfProjectManager.update(this.wfProject);
		Flash.current().success("流程已更新！");
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
			this.wfProjectManager.removeById(id);
			Flash.current().success("流程已删除！");
		}
		return LIST_ACTION;
	}
	
	/**
	 * 启用流程 ;选中的流程改为启用，其它均为停止状态
	 * @return
	 */
	public String start(){
//		List<WfProject> projects = this.wfProjectManager.findAllBy("groupFlag", wfProject.getGroupFlag());
//		for(WfProject project : projects ){
//			if(project.getId().equals(this.wfProject.getId())){
//				project.setUseState(1);	//如果流程等于页面选择的流程则将状态改为启用
//			}else{
//				project.setUseState(2);//将除却页面选中的流程，其余全部状态改为停止
//			}
//			this.wfProjectManager.update(project);
//		}
		this.wfProject.setUseState(1);//将状态改为停止
		this.wfProjectManager.update(wfProject);
		Flash.current().success("流程已启用！");
		return LIST_ACTION;
	}
	
	/**
	 * 修改状态停止
	 * @return
	 */
	public String stop(){
		this.wfProject.setUseState(2);//将状态改为停止
		this.wfProjectManager.update(wfProject);
		Flash.current().success("流程已停用！");
		return LIST_ACTION;
	}
	
	/**
	 * 检查流程是否可以启用
	 */
	public void checkUser(){
		String result = "0";//0表示可以启用流程
		Set<WfActivity> activitys = this.wfProject.getWfActivitys();//得到流程下所有节点
		if(activitys != null && activitys.size()>0){
			for (WfActivity wfActivity : activitys) {
				Set<WfActivityParticipan> participans = wfActivity.getWfActivityParticipans();//得到节点下所有参与者
				if(participans == null || participans.size() <=0){
					result = "2";//2表示流程节点下没有设置参与者，不能启用流程
				}
			}
		}else{
			result = "1";//1表示流程下没有节点，不能够启用流程
		}
		try {
			PrintWriter out = getResponse().getWriter();
			out.print(result);
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
//		getDic();
//		List<WfActivity> activitys = this.wfActivityManager.getWfActivitys(this.wfProject.getId());
//		for (WfActivity wfActivity : activitys) {
//			String str = null;
//			List<WfActivityParticipan> participan = this.wfActivityParticipanManager.findAllBy("activityId", wfActivity.getId());//得到参与者集合
//			for (WfActivityParticipan wfActivityParticipan : participan) {
//				if(wfActivityParticipan.getRefType()==4){
//					List<ExpertsUser> expertsUser = this.expertsUserManager.findAllBy("experts.id", wfActivityParticipan.getRefId());//专家组成员
//					for (ExpertsUser eu : expertsUser) {
//						if(str == null){
//							str = eu.getUserName();//得到参与者名称
//						}else{
//							str += ","+eu.getUserName();//得到参与者名称
//						}
//					}
//				}else if(wfActivityParticipan.getRefType() ==1){
//					if(str == null){
//						str = wfActivityParticipan.getRefName();//得到参与者名称
//					}else{
//						str += ","+wfActivityParticipan.getRefName();//得到参与者名称
//					}
//				}
//				if(wfActivityParticipan.getRefType()==3){
//					Role role = this.roleManager.getById(wfActivityParticipan.getRefId());//角色
//					Set<User> users = role.getUsers();//获得角色下的用户
//					for (User user : users) {
//						if(str == null){
//							str = user.getUsername();
//						}else{
//							str += user.getUsername();
//						}
//					}
//				}
//			}
//			wfActivity.setParticipans(str);//给节点表中的参与者字符串赋值
//		}
//		getRequest().setAttribute("activitys", activitys);
		return VIEW_JSP;
	}
	
	public String addNode(){
		return ADDNODE_JSP;
	}
	
	//得到所在组的数据字典
	public void getDic(){
		HashMap<String, String> gzzMap = this.dicUtil.getDicList("szz");
		getRequest().setAttribute("szz", gzzMap);
	}
}
