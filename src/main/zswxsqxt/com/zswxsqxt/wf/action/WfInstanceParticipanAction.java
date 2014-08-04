package com.zswxsqxt.wf.action;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.RoleManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.OrganizationQuery;
import com.opendata.organiz.vo.query.OrganizationUserRelationQuery;
import com.opendata.organiz.vo.query.RoleQuery;
import com.opendata.organiz.vo.query.UserQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.model.WfInstanceParticipan;
import com.zswxsqxt.wf.query.WfInstanceParticipanQuery;
import com.zswxsqxt.wf.service.WfInstanceNodeManager;
import com.zswxsqxt.wf.service.WfInstanceParticipanManager;

/**
 * describe:流程参与者管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfInstanceParticipan/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/wf/wfInstanceParticipan/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfInstanceParticipan/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfInstanceParticipan/view.jsp", type = "dispatcher"),
@Result(name="user_list",location="/WEB-INF/pages/wf/wfInstanceParticipan/user_list.jsp", type = "dispatcher"),
@Result(name="org_list",location="/WEB-INF/pages/wf/wfInstanceParticipan/org_list.jsp", type = "dispatcher"),
@Result(name="role_list",location="/WEB-INF/pages/wf/wfInstanceParticipan/role_list.jsp", type = "dispatcher"),
@Result(name="zjz_list",location="/WEB-INF/pages/wf/wfInstanceParticipan/zjz_list.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfInstanceParticipan!list.do?${params}", type = "redirectAction"),
@Result(name = "closeDialog", location = "/commons/closeDialogNoRefresh.jsp", type = "dispatcher")
})
public class WfInstanceParticipanAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	
	private static final String USER_JSP = "user_list";
	private static final String ORG_JSP = "org_list";
	private static final String ROLE_JSP = "role_list";
	private static final String ZJZ_JSP = "zjz_list";
	
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfInstanceParticipanManager wfInstanceParticipanManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfInstanceParticipan wfInstanceParticipan;
	private WfInstanceParticipanQuery query=new WfInstanceParticipanQuery();
	private WfInstanceNodeManager wfInstanceNodeManager;//流程实例节点管理类
	
	private UserManager userManager;//用户管理
	private OrganizationManager organizationManager;//部门管理
	private OrganizationUserRelationManager organizationUserRelationManager;//部门用户关系管理
	private RoleManager roleManager;//角色管理
	private UserQuery uquery = new UserQuery();//用户
	private OrganizationQuery orgquery = new OrganizationQuery();//部门
	private OrganizationUserRelationQuery ouRelationQuery = new OrganizationUserRelationQuery();//部门用户关系
	private RoleQuery rolequery = new RoleQuery();//角色
	
	private String insId;
	private Integer actFlag;
	private String objId;
	private Integer groupFlag;//所在组
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfInstanceParticipan= new WfInstanceParticipan();
		} else {
			this.wfInstanceParticipan = (WfInstanceParticipan)this.wfInstanceParticipanManager.getById(id);
		}
	}
		
	public WfInstanceParticipanManager getWfInstanceParticipanManager() {
		return this.wfInstanceParticipanManager;
	}

	public void setWfInstanceParticipanManager(WfInstanceParticipanManager wfInstanceParticipanManager) {
		this.wfInstanceParticipanManager = wfInstanceParticipanManager;
	}
	
	public Object getModel() {
		return this.wfInstanceParticipan;
	}
	
	public WfInstanceParticipanQuery getQuery() {
		return query;
	}

	public void setQuery(WfInstanceParticipanQuery query) {
		this.query = query;
	}
	

	public WfInstanceNodeManager getWfInstanceNodeManager() {
		return wfInstanceNodeManager;
	}

	public void setWfInstanceNodeManager(WfInstanceNodeManager wfInstanceNodeManager) {
		this.wfInstanceNodeManager = wfInstanceNodeManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public OrganizationManager getOrganizationManager() {
		return organizationManager;
	}

	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}

	public OrganizationUserRelationManager getOrganizationUserRelationManager() {
		return organizationUserRelationManager;
	}

	public void setOrganizationUserRelationManager(
			OrganizationUserRelationManager organizationUserRelationManager) {
		this.organizationUserRelationManager = organizationUserRelationManager;
	}

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public Integer getActFlag() {
		return actFlag;
	}

	public void setActFlag(Integer actFlag) {
		this.actFlag = actFlag;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.wfInstanceParticipanManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
	 * 查询用户列表
	 * @return
	 */
	public String userlist(){
		//将query中的分页数据放入ouRelationQuery中
		ouRelationQuery.setPageNumber(query.getPageNumber());
		ouRelationQuery.setPageSize(query.getPageSize());
		String username = getRequest().getParameter("username");
		ouRelationQuery.setUsername(username);
		Page page = this.organizationUserRelationManager.findPage(ouRelationQuery);
		super.saveCurrentPage(page, ouRelationQuery);
		getRequest().setAttribute("objId", query.getObjId());
		getRequest().setAttribute("username", username);
		return USER_JSP;
	}
	
	/**
	 * 查询部门列表
	 * @return
	 */
	public String orglist(){
		//将query中的分页数据放入orgquery中
		orgquery.setPageNumber(query.getPageNumber());
		orgquery.setPageSize(query.getPageSize());
		String orgname = getRequest().getParameter("orgname");
		orgquery.setName(orgname);
		//orgquery.setId(orgId);
		Page page = this.organizationManager.findPage(orgquery);
		super.saveCurrentPage(page, orgquery);
		getRequest().setAttribute("orgname", orgname);
		getRequest().setAttribute("objId", query.getObjId());
		return ORG_JSP;
	}
	
	/**
	 * 查询角色列表
	 * @return
	 */
	public String rolelist(){
		//将query中的分页数据放入rolequery中
		rolequery.setPageNumber(query.getPageNumber());
		rolequery.setPageSize(query.getPageSize());
		String rolname = getRequest().getParameter("rolname");
		rolequery.setName(rolname);
		Page page = this.roleManager.findPage(rolequery);
		super.saveCurrentPage(page, rolequery);
		getRequest().setAttribute("rolname", rolname);
		getRequest().setAttribute("objId", query.getObjId());
		return ROLE_JSP;
	}
	
	/**
	 * 添加参与者 (1、用户；2、部门；3、角色)
	 */
	public String addparticipant(){
		int refType = Integer.parseInt(getRequest().getParameter("reftype"));
		//添加用户
		if(refType == 1){
			for(int i = 0; i < items.length; i++)
			 {
				Hashtable params = HttpUtils.parseQueryString(items[i]);
				String id = (String)params.get("id");
				String username = (this.userManager.getById(id)).getUsername();//得到关联名称，即用户名
				WfInstanceParticipan instanceParticipan = new WfInstanceParticipan();
				instanceParticipan.setRefId(id);//关联id
				instanceParticipan.setRefName(username);//关联名称
				instanceParticipan.setRefType(refType);//关联类型
				
				if(actFlag == 2){
					WfInstanceNode wfInstanceNode = this.wfInstanceNodeManager.getNode(query.getInsId(), query.getWfInstanceNode().getActFlag()+1);
					instanceParticipan.setWfInstanceNode(wfInstanceNode);//节点
				}else{
					instanceParticipan.setWfInstanceNode(query.getWfInstanceNode());//节点
				}
				instanceParticipan.setTs(new Date());//创建时间
				this.wfInstanceParticipanManager.save(instanceParticipan);
			}
		}
		//添加部门
		if(refType == 2){
			for(int i = 0; i < items.length; i++)
			 {
				Hashtable params = HttpUtils.parseQueryString(items[i]);
				String id = (String)params.get("id");
				String name = (this.organizationManager.getById(id)).getName();//得到关联名称，即部门名称
				WfInstanceParticipan instanceParticipan = new WfInstanceParticipan();
				instanceParticipan.setRefId(id);//关联id
				instanceParticipan.setRefName(name);//关联名称
				instanceParticipan.setRefType(refType);//关联类型
				instanceParticipan.setWfInstanceNode(query.getWfInstanceNode());//节点
				instanceParticipan.setTs(new Date());//创建时间
				this.wfInstanceParticipanManager.save(instanceParticipan);
			}
		}
		//添加角色
		if(refType == 3){
			for(int i = 0; i < items.length; i++)
			 {
				Hashtable params = HttpUtils.parseQueryString(items[i]);
				String id = (String)params.get("id");
				String name = (this.roleManager.getById(id)).getName();//得到关联名称，即角色名称
				WfInstanceParticipan instanceParticipan = new WfInstanceParticipan();
				instanceParticipan.setRefId(id);//关联id
				instanceParticipan.setRefName(name);//关联名称
				instanceParticipan.setRefType(refType);//关联类型
				instanceParticipan.setWfInstanceNode(query.getWfInstanceNode());//节点
				instanceParticipan.setTs(new Date());//创建时间
				this.wfInstanceParticipanManager.save(instanceParticipan);
			}
		}
		getRequest().setAttribute("dialogId", getRequest().getParameter("dialogId"));
		return "closeDialog";
	}
	
	/**
	 * 根据所在组和被审核对象id得到节点
	 * @return
	 */
	public WfInstanceNode getNodeObj(){
		WfInstanceNode wfInstanceNode = new WfInstanceNode();
		if(groupFlag == 1){
			
		}
		if(groupFlag == 2){
			
		}
		if(groupFlag == 3){
			
		}
		if(groupFlag == 5){
			
		}
		return wfInstanceNode;
	}
	
	/**
	 * 删除默认的审核人员
	 * @param nodeId	节点id
	 */
	public void deleteAll(String nodeId){
		List<WfInstanceParticipan> list = wfInstanceParticipanManager.findAllBy("wfInstanceNode.id", nodeId);
		for (WfInstanceParticipan wfInstanceParticipan : list) {
			wfInstanceParticipanManager.removeById(wfInstanceParticipan.getId());
		}
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
		this.wfInstanceParticipan.setTs(new Date());
		this.wfInstanceParticipanManager.save(this.wfInstanceParticipan);
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
		this.wfInstanceParticipanManager.update(this.wfInstanceParticipan);
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
			this.wfInstanceParticipanManager.removeById(id);
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
