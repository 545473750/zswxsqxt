package com.zswxsqxt.wf.action;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
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
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfActivityParticipan;
import com.zswxsqxt.wf.query.WfActivityParticipanQuery;
import com.zswxsqxt.wf.service.WfActivityManager;
import com.zswxsqxt.wf.service.WfActivityParticipanManager;

/**
 * describe:流程节点参与者管理
 * 
 *
 */
@Namespace("/wf")
@Results({
@Result(name="list",location="/WEB-INF/pages/wf/wfActivityParticipan/list.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/wf/wfActivityParticipan/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/wf/wfActivityParticipan/view.jsp", type = "dispatcher"),

@Result(name="user_list",location="/WEB-INF/pages/wf/wfActivityParticipan/user_list.jsp", type = "dispatcher"),
@Result(name="org_list",location="/WEB-INF/pages/wf/wfActivityParticipan/org_list.jsp", type = "dispatcher"),
@Result(name="role_list",location="/WEB-INF/pages/wf/wfActivityParticipan/role_list.jsp", type = "dispatcher"),
@Result(name="zjz_list",location="/WEB-INF/pages/wf/wfActivityParticipan/zjz_list.jsp", type = "dispatcher"),
@Result(name="listAction",location="../wf/WfActivityParticipan!list.do?${params}", type = "redirectAction")
})
public class WfActivityParticipanAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	
	private static final String USER_JSP = "user_list";
	private static final String ORG_JSP = "org_list";
	private static final String ROLE_JSP = "role_list";
	private static final String ZJZ_JSP = "zjz_list";
	
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private WfActivityParticipanManager wfActivityParticipanManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private WfActivityParticipan wfActivityParticipan;
	private WfActivityParticipanQuery query=new WfActivityParticipanQuery();
	
	private UserManager userManager;//用户管理
	private OrganizationManager organizationManager;//单位管理
	private OrganizationUserRelationManager organizationUserRelationManager;//单位用户关系管理
	private RoleManager roleManager;//角色管理
	private UserQuery uquery = new UserQuery();//用户
	private OrganizationQuery orgquery = new OrganizationQuery();//部门
	private OrganizationUserRelationQuery ouRelationQuery = new OrganizationUserRelationQuery();//部门用户关系
	private RoleQuery rolequery = new RoleQuery();//角色
	private String activityId;//节点ID
	private WfActivityManager wfActivityManager;//节点管理
	private WfActivity wfActivity = new WfActivity();//节点对象
	private String proId;//流程表ID，list页面返回时使用
	private String orgId;//单位ID，根据单位流程关系表得来，用于过滤参与者
	private String groupFlag;//所在组
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.wfActivityParticipan= new WfActivityParticipan();
		} else {
			this.wfActivityParticipan = (WfActivityParticipan)this.wfActivityParticipanManager.getById(id);
		}
		/*User u = (User) getRequest().getSession().getAttribute("user");
		u.getOuRelations();*/
	}
		
	public WfActivityParticipanManager getWfActivityParticipanManager() {
		return this.wfActivityParticipanManager;
	}

	public void setWfActivityParticipanManager(WfActivityParticipanManager wfActivityParticipanManager) {
		this.wfActivityParticipanManager = wfActivityParticipanManager;
	}
	
	public Object getModel() {
		return this.wfActivityParticipan;
	}
	
	public WfActivityParticipanQuery getQuery() {
		return query;
	}

	public void setQuery(WfActivityParticipanQuery query) {
		this.query = query;
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

	public WfActivityParticipan getWfActivityParticipan() {
		return wfActivityParticipan;
	}

	public void setWfActivityParticipan(WfActivityParticipan wfActivityParticipan) {
		this.wfActivityParticipan = wfActivityParticipan;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public WfActivity getWfActivity() {
		return wfActivity;
	}

	public void setWfActivity(WfActivity wfActivity) {
		this.wfActivity = wfActivity;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
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
		if(query.getWfActivity()==null){
			this.wfActivity.setId(activityId);
			query.setWfActivity(wfActivity);
		}
		activityId = query.getWfActivity().getId();//节点id
		proId = query.getProId();//流程Id,用于返回时传值
		Page page = this.wfActivityParticipanManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
	 * 查询用户列表
	 * @return
	 */
	public String userlist(){
		List<Organization> orgs = this.getOrganizationManager().findAll();
		//将query中的分页数据放入ouRelationQuery中
		String username = this.getRequest().getParameter("username");//用户名，查询条件
		String orgName = this.getRequest().getParameter("orgName");//单位名称,查询条件
		String userNum = this.getRequest().getParameter("userNum");//继教号,查询条件
		ouRelationQuery.setUsername(username);
		ouRelationQuery.setPageNumber(query.getPageNumber());
		ouRelationQuery.setPageSize(query.getPageSize());
		ouRelationQuery.setOrgname(orgName);
		ouRelationQuery.setUserNum(userNum);
		Page page = this.organizationUserRelationManager.findPage(ouRelationQuery);
		super.saveCurrentPage(page, query);
		getRequest().setAttribute("orgs", orgs);
		this.getRequest().setAttribute("username", username);//用户名，查询条件
		this.getRequest().setAttribute("orgName", orgName);//单位id,查询条件
		this.getRequest().setAttribute("userNum", userNum);
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
		orgquery.setId(orgId);
		Page page = this.organizationManager.findPage(orgquery);
		super.saveCurrentPage(page, query);
		getRequest().setAttribute("orgname", orgname);
		getRequest().setAttribute("actType", query.getActType());//节点类型1、一般审核，2、专家审核
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
		super.saveCurrentPage(page, query);
		getRequest().setAttribute("rolname", rolname);
		getRequest().setAttribute("actType", query.getActType());//节点类型1、一般审核，2、专家审核
		return ROLE_JSP;
	}
	
	/**
	 * 添加参与者 (1、用户；2、部门；3、角色；4、专家组)
	 */
	public String addparticipant(){
		int refType = Integer.parseInt(getRequest().getParameter("reftype"));
		//添加用户
		for(int i = 0; i < items.length; i++){
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");//用户id
			String username = (this.userManager.getById(id)).getUsername();//得到关联名称，即用户名
			WfActivityParticipan activityParticipan = new WfActivityParticipan();
			activityParticipan.setRefId(id);
			activityParticipan.setRefName(username);
			activityParticipan.setRefType(refType);
			activityParticipan.setActivityId(query.getWfActivity().getId());
			activityParticipan.setTs(new Date());
			this.wfActivityParticipanManager.save(activityParticipan);
		}
		return LIST_ACTION;
	}
	
	/**	
		保存新增
	*/
	public String saveAdd()
	{
		this.wfActivityParticipan.setTs(new Date());
		this.wfActivityParticipanManager.save(this.wfActivityParticipan);
		Flash.current().success("参与者已保存！");
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
		this.wfActivityParticipanManager.update(this.wfActivityParticipan);
		Flash.current().success("参与者已更新！");
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
			this.wfActivityParticipanManager.removeById(id);
			Flash.current().success("参与者已删除！");
		}
		return LIST_ACTION;
	}
	
	/**	
		查看
	*/
	public String view()
	{
		if(this.wfActivityParticipan.getRefType()==3){
			String str = null;
			Role role = this.roleManager.getById(wfActivityParticipan.getRefId());//角色
			Set<User> users = role.getUsers();//获得角色下的用户
			for (User user : users) {
				if(str == null){
					str = user.getUsername();
				}else{
					str += user.getUsername();
				}
			}
			getRequest().setAttribute("username", str);
		}
		return VIEW_JSP;
	}
	
}
