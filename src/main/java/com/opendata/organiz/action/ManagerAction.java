package com.opendata.organiz.action;
import com.opendata.organiz.model.Manager;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.ManagerManager;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.query.ManagerQuery;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.sys.model.Partition;
import com.opendata.sys.model.Resources;
import com.opendata.sys.service.PartitionManager;
import com.opendata.sys.service.ResourcesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.opendata.application.model.Application;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Common;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * describe:系统分级管理员管理
 * 
 *
 */
@Namespace("/organiz")
@Results({
@Result(name="list",location="/WEB-INF/pages/organiz/manager/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/organiz/manager/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/organiz/manager/edit.jsp", type = "dispatcher"),
@Result(name="choiceOrganization", location="/WEB-INF/pages/organiz/manager/organizationTree.jsp", type="dispatcher"),
@Result(name="view",location="/WEB-INF/pages/organiz/manager/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../organiz/Manager!list.do?${params}", type = "redirectAction"),
@Result(name="grant_permission",location="/WEB-INF/pages/organiz/manager/grant_permission.jsp", type = "dispatcher"),
@Result(name="grant_user",location="/WEB-INF/pages/organiz/manager/select_user.jsp", type = "dispatcher")
})
public class ManagerAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	protected static final String GRANT_USER_PAGE="grant_user";
	protected static final String GRANT_PERMISSION_PAGE = "grant_permission";
	
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private ManagerManager managerManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private Manager manager;
	private ManagerQuery query=new ManagerQuery();
	private DicUtil dicUtil;
	
	private OrganizationManager organizationManager;
	private UserManager userManager;
	private ResourcesManager resourcesManager;
	
	private PartitionManager partitionManager;
	private String partitionId;//分区ID
	public PartitionManager getPartitionManager() {
		return partitionManager;
	}

	public void setPartitionManager(PartitionManager partitionManager) {
		this.partitionManager = partitionManager;
	}

	public ResourcesManager getResourcesManager() {
		return resourcesManager;
	}

	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
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

	public DicUtil getDicUtil() {
		return dicUtil;
	}

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.manager= new Manager();
		} else {
			this.manager = (Manager)this.managerManager.getById(id);
		}
	}
		
	public ManagerManager getManagerManager() {
		return this.managerManager;
	}

	public void setManagerManager(ManagerManager managerManager) {
		this.managerManager = managerManager;
	}
	
	public Object getModel() {
		return this.manager;
	}
	
	public ManagerQuery getQuery() {
		return query;
	}

	public void setQuery(ManagerQuery query) {
		this.query = query;
	}
	
	
	private String selectedIds;

	
	/**
	 * 权限授予
	 * @return
	 */
	public String grantPermission(){
		String permissionIds = getRequest().getParameter("permissionIds");
		if("".equals(partitionId)){
			manager.setMenuIds(null);
		}
		if(!"".equals(permissionIds)){
			String[] perIds  = permissionIds.split(",");
			Set<Resources> resources = new HashSet<Resources>(0);
			for(String permID : perIds){
				Resources res = resourcesManager.getById(permID);
				if(res!=null){
					resources.add(res);
				}
			}
//			manager.setScopeValue(resources);
			manager.setMenuIds(permissionIds);//设置菜单权限
			managerManager.update(manager);
		}
		getRequest().setAttribute("dialogId", getRequest().getParameter("dialogId"));
		//Flash.current().success(UPDATE_SUCCESS);
		return CLOSE_DIALOG_NOREFRESH;
	}
	
	/**
	 * 进入设置用户页面
	 * @return
	 */
	public String grantUserPage(){
		if(this.manager.getScopeValue() != null && this.manager.getScopeValue().equals("")) {
			this.manager.setScopeValue(null);
		}
		if(this.manager != null) {
			
			String userid = getRequest().getParameter("userId");
			String selectedUsers = this.manager.getScopeValue();
			StringBuffer sbUserId = new StringBuffer();
			StringBuffer sbUserName = new StringBuffer();
			StringBuffer html = new StringBuffer();
			if(selectedUsers != null){
			String[] userSelect = selectedUsers.split(",");
			List<User> userSe = new ArrayList<User>();
			for(int i=0;i<userSelect.length;i++){
				
				String u = userSelect[i];
				
				User us = new User();
				us.setId(u);
				User uuu = this.userManager.getById(us.getId());
//				User uuu = managerManager.findUserById(us.getId());
				userSe.add(uuu);
			}
			for(User user : userSe) {
				sbUserId.append(user.getId() + ",");
				sbUserName.append(user.getUsername() + ",");
				html.append("<div class='iframe_name_box' id='" + user.getId() + "'>");
				html.append("<p class='iframe_name_text'>" + user.getUsername() + "</p>");
				html.append("<p class='iframe_name_close'>");
				html.append("<a onclick=\"delItem('" + user.getId() + "', '" + user.getUsername() + "')\">");
				html.append("<img src='" + ServletActionContext.getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' />");
				html.append("</a>");
				html.append("</p>");
				html.append("</div>");
			}
			}
			getRequest().setAttribute("selectedUserHTML", html.toString());
			
			if(sbUserId.toString() != null && !sbUserId.toString().equals("")) {
				String _sbUserId = sbUserId.toString().substring(0, sbUserId.toString().length() - 1);
				getRequest().setAttribute("selectedIds", _sbUserId);
			}
			if(sbUserName.toString() != null && !sbUserName.toString().equals("")) {
				String _sbUserName = sbUserName.toString().substring(0, sbUserName.toString().length() - 1);
				getRequest().setAttribute("selectedNames", _sbUserName);
			}
		}
		return GRANT_USER_PAGE;
	}
	
	// 选择用户ID 如有多个用逗号隔开
	private String selectedUserIds;
	/**
	 * 设置角色用户
	 * @return
	 */
	public String grantUser(){
		if(this.manager != null) {
			String id = getRequest().getParameter("id");
			selectedUserIds = getRequest().getParameter("selectedUserIds");
			if("".equals(selectedUserIds)){
				manager.setScopeValue(null);
			}
			Set<User> users = new HashSet<User>();
			if(this.selectedUserIds != null && !this.selectedUserIds.equals("")) {
				String[] selectUserIds = this.selectedUserIds.split(",");
				for(String selectUserId : selectUserIds) {
					User user = this.userManager.getById(selectUserId);
					users.add(user);
				}
			}
//			manager.setUsers(users);   //存入用户设置
			manager.setScopeValue(selectedUserIds);
			this.manager.setId(id);
			this.managerManager.update(manager);
			getRequest().setAttribute("dialogId", getRequest().getParameter("dialogId"));
			return CLOSE_DIALOG_NOREFRESH;
		}
		
		return null;
		
	}
	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.managerManager.findPage(query);
		super.saveCurrentPage(page,query);
		this.findDateScope(); //查询姓名和数据范围
		String State = "userState";
		HashMap<String, String> userState = this.dicUtil.getDicList(State);
		getRequest().setAttribute("userState", userState);
		
		return LIST_JSP;
	}
	
	/**
		新增
	*/
	public String add()
	{
		
		this.findDateScope(); //查询姓名和数据范围
		return ADD_JSP;
	}
	
	/**
	 * 查询姓名和数据字典值范围
	 */
	public void findDateScope(){
		List<User> userName = managerManager.findAllName();
		String data = "dataScope";
		
		HashMap<String, String> dicList = this.dicUtil.getDicList(data);
		
		getRequest().setAttribute("userName", userName);
		getRequest().setAttribute("dicList", dicList);
		
	}
	
	
	/**
	 * 进入权限授予页面
	 * @return
	 */
	public String grantPage(){
		return GRANT_PERMISSION_PAGE;
	}
	
	/**
	 * 进入权限授予页面
	 * @return
	 */
	public String toGrantPage(){
		
			try {
				List<Resources> resourcesList = new ArrayList<Resources>();
				if (partitionId == null || "".equals(partitionId)) {
					
					resourcesList = resourcesManager.findAllByDf();
				} else {
					Partition partition = partitionManager.getById(partitionId);
					/*for (Application app : partition.getApplications()) {
						resourcesList.addAll(resourcesManager.findByAppLication(app.getId()));
					}*/
				}
				String permsEd = manager.getMenuIds();
				List<Resources> resources = new ArrayList<Resources>();
				if(permsEd != null && !permsEd.equals("")) {
					String[] selectUserIds = permsEd.split(",");
					for(String selectUserId : selectUserIds) {
						
						resources.add(resourcesManager.getById(selectUserId));
					}
				}
//				List<Manager> resourcesListByRole = resourcesManager.getById(permsEd);
				//组装成带多选框的树
				for (Resources res : resources) {
					for (Resources r : resourcesList) {
						if (r.getId().equals(res.getId())) {
							r.setChecked("1");
							break;
						}
					}
				}
				List<Resources> result = Common.getTopMenuListByRole(resourcesList);
				//查找有checked属性的框
				String menuXmlString = Common.getTreeXML(result);
				HttpServletResponse response = getResponse();
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-control", "no-cache");
				response.getWriter().print(menuXmlString);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	
	/**	
		保存新增
	*/
	public String saveAdd()
	{
		//获取被添加用户的id
		String user = this.manager.getUserId();
		User userName = userManager.getById(user);
		this.manager.setUserId(userName.getLoginname());
		this.manager.setName(userName.getUsername());
		
		
		//获取Session中的用户和id
		String userid = this.getCurrUser().getUserName();
		String username = this.getCurrUser().getLoginName();
		this.manager.setAddName(userid);
		this.manager.setAddUserId(username);
		
		this.manager.setTs(new Date());
		this.manager.setAddTime(new Date());
		this.manager.setUserState(1);		//设置状态
		this.managerManager.save(this.manager);
		return LIST_ACTION;
	}
	
	/**	
		修改
	*/
	public String edit()
	{
		
		
			String id = getRequest().getParameter("items");
			
			Object manager = this.managerManager.getEntityDao().getById(id);
			List<User> userName = managerManager.findAllName();
			
			
			
			String data = "dataScope";
			
			HashMap<String, String> dicList = this.dicUtil.getDicList(data);
			
			getRequest().setAttribute("userName", userName);
			getRequest().setAttribute("dicList", dicList);
			getRequest().setAttribute("model", manager);
			
		return EDIT_JSP;
	}
	
	
	/**	
		保存修改
	*/
	public String saveEdit()
	{
		String id =getRequest().getParameter("id");
		//获取被添加用户的id
		String user = this.manager.getUserId();
		User userName = userManager.getById(user);
		this.manager.setUserId(userName.getLoginname());
		this.manager.setName(userName.getUsername());
		this.manager.setId(id);
		//获取修改的数值范围和菜单
		if(this.manager.getDataScope()!='3'){
			
			this.manager.setScopeValue(null);
		}
		
		
		this.managerManager.update(this.manager);
		return LIST_ACTION;
	}
		
	/**	
		删除
	*/
	public String remove()
	{
		for(int i = 0; i < items.length; i++)
		 {
			String id = items[i];
			this.managerManager.removeById(id);
		}
		return LIST_ACTION;
	}
	
	/**	
		查看
	*/
	public String view()
	{
		//获得菜单授权
		String menuids = manager.getMenuIds();
		List<Resources> resources = new ArrayList<Resources>();
		List<Resources> resou = new ArrayList<Resources>();
		if(menuids != null && !menuids.equals("")) {
			String[] menuid = menuids.split(",");
			for(String menu : menuid) {
				
				resources.add(resourcesManager.getById(menu));
				
			}
			for(int i=0;i<menuid.length;i++){
				
				String men = menuid[i];
				
				Resources us = new Resources();
				us.setId(men);
				resou.add(us);
				
			}
		}
		
		getRequest().setAttribute("resources", resources);
		getRequest().setAttribute("resou", resou);
		//获得用户授权
		if(manager.getScopeValue() != null){
			String selectedUsers = manager.getScopeValue();
			String[] userSelect = selectedUsers.split(",");
			List<User> userSe = new ArrayList<User>();
			List<User> userSee = new ArrayList<User>();
			for(int i=0;i<userSelect.length;i++){
				
				String u = userSelect[i];
				
				User us = new User();
				us.setId(u);
				userSee.add(us);
				User uuu = this.userManager.getById(us.getId());
	//			User uuu = managerManager.findUserById(us.getId());
				userSe.add(uuu);
			}
			getRequest().setAttribute("userSe", userSe);
		}
		
		
		return VIEW_JSP;
	}
	
}
