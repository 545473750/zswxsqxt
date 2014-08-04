/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.application.model.Application;
import com.opendata.application.service.ApplicationManager;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.util.Common;
import com.opendata.common.util.StrUtil;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.RoleManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.OrganizationQuery;
import com.opendata.sys.model.Partition;
import com.opendata.sys.service.PartitionManager;
import com.opendata.sys.vo.query.PartitionQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 分区Action类 负责和表现层的交互以及调用相关业务逻辑层完成功能
 * @author付威
 */
@Namespace("/sys")
@Results({
	@Result(name="closeDialog", location="/commons/dialogclose.jsp", type="dispatcher"),
@Result(name="list",location="/WEB-INF/pages/sys/Partition/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Partition/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/Partition/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/Partition/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Partition/show.jsp", type = "dispatcher"),
@Result(name="chonse_jsp",location="/WEB-INF/pages/sys/Partition/chooseSchool.jsp", type = "dispatcher"),
@Result(name="schoollist",location="/WEB-INF/pages/sys/Partition/schoollist.jsp", type = "dispatcher"),
@Result(name="grant_administrator",location="/WEB-INF/pages/sys/Partition/grant_administrator.jsp", type = "dispatcher"),
@Result(name="grant_application",location="/WEB-INF/pages/sys/Partition/grant_application.jsp", type = "dispatcher"),
@Result(name="grant_organiz",location="/WEB-INF/pages/sys/Partition/grant_organiz.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//sys/Partition!list.do", type = "redirectAction"),
@Result(name="grantAdministratorAction",location="..//sys/Partition!grantAdministratorPage.do?id=${id}", type = "redirectAction"),
@Result(name="grantApplicationAction",location="..//sys/Partition!grantApplicationPage.do?id=${id}", type = "redirectAction")
})
public class PartitionAction extends BaseStruts2Action implements Preparable,
		ModelDriven {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String SHOW_JSP = "show";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	protected static final String GRANT_ADMINISTRATOR_PAGE = "grant_administrator";//设置管理员页面
	protected static final String GRANT_ADMINISTRATOR_ACTION ="grantAdministratorAction";//设置管理员方法
	protected static final String GRANT_APPLICATION_PAGE = "grant_application";//设置应用页面
	protected static final String GRANT_APPLICATION_ACTION = "grantApplicationAction";//设置应用的方法
	protected static final String GRANT_ORGANIZ_JSP = "grant_organiz";//分配组织机构页面
	
	private PartitionManager partitionManager;
	private ApplicationManager applicationManager;
	private OrganizationManager organizationManager;
	private RoleManager roleManager;
	
	private Partition partition;
	PartitionQuery query = new PartitionQuery();
	java.lang.String id = null;
	
	private String[] items;
	
	public void prepare() throws Exception {
		int i = 123123;
		if((i % 5) == 0){
			
		}
		if (isNullOrEmptyString(id)) {
			partition = new Partition();
		} else {
			partition = (Partition)partitionManager.getById(id);
		}
	}
	
	/** 执行搜索 */
	public String list() {
		query.setDf("0");
		Page page = this.partitionManager.findPage(query);
		this.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/** 查看对象*/
	public String show() {
		return SHOW_JSP;
	}
	
	/** 进入新增页面*/
	public String create() {
		return CREATE_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		//先判断分区名称是否重复
		if(!partitionManager.isUniqueByDf(partition, "name")){
			Flash.current().success(Partition.ALIAS_NAME+"已存在！");
			return CREATE_JSP;
		}
		partition.setDf("0");
		partition.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		partitionManager.save(partition);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**进入更新页面*/
	public String edit() {
		return EDIT_JSP;
	}
	
	/**保存更新对象*/
	public String update() {
		//先判断分区名称是否重复
		if(!partitionManager.isUniqueByDf(partition, "name")){
			Flash.current().success(Partition.ALIAS_NAME+"已存在！");
			return EDIT_JSP;
		}
		partitionManager.update(this.partition);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**删除对象*/
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			partition = (Partition)partitionManager.getById(id);
			partition.setDf("1");
			//删除分区时，把分区与管理员的关系 分区与角色的关系删除
//			partition.setApplications(new HashSet<Application>(0));
//			partition.setUsers(new HashSet<User>(0));
			partition.setOrganizations(new HashSet<Organization>(0));
			List roles = roleManager.findAllByDf("partitionId", partition.getId());
			if(!roles.isEmpty()&&roles.size()>0){
				Flash.current().error("分区"+partition.getName()+"下已经有角色等数据，不能删除！");
				return LIST_ACTION;
			}
			partitionManager.update(this.partition);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	// 进入设置管理员页面
	public String grantAdministratorPage(){
		// 准备数据
		// selectedIds
		// selectedNames
		// selectedUserHTML
		if(this.partition != null) {
			StringBuffer sbIds = new StringBuffer();
			StringBuffer sbNames = new StringBuffer();
			StringBuffer html = new StringBuffer();
			
			/*Set<User> users = this.partition.getUsers();
			for(User user : users) {
				sbIds.append(user.getId() + ",");
				sbNames.append(user.getUsername() + ",");
				
				html.append("<div class='iframe_name_box' id='" + user.getId() + "'>");
				html.append("<p class='iframe_name_text'>" + user.getUsername() + "</p>");
				html.append("<p class='iframe_name_close'>");
				html.append("<a onclick=\"delItem('" + user.getId() + "', '" + user.getUsername() + "')\">");
				html.append("<img src='" + ServletActionContext.getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' />");
				html.append("</a>");
				html.append("</p>");
				html.append("</div>");
			}*/
			getRequest().setAttribute("selectedUserHTML", html);
			if(!sbNames.toString().equals("")) {
				String _sbNames = sbNames.toString().substring(0, sbNames.toString().length() - 1);
				getRequest().setAttribute("selectedNames", _sbNames);
			}
			if(!sbIds.toString().equals("")) {
				String _sbIds = sbIds.toString().substring(0, sbIds.toString().length() - 1);
				getRequest().setAttribute("selectedIds", _sbIds);
			}
		}
		return GRANT_ADMINISTRATOR_PAGE;
	}
	private String selectedUserIds;
	//设置管理员
	public String grantAdministrator(){
		if(this.selectedUserIds != null && !this.selectedUserIds.equals("")) {
			Set<User> users = new HashSet<User>(0);
			String[] ids = this.selectedUserIds.split(",");
			for(String id : ids) {
				User user = this.userManager.getById(id);
				
				users.add(user);
				
				// 给用户绑定分区管理员角色
				Role role = this.roleManager.findByDf("code", "Partition_admin");
				Set<Role> roles = user.getRoles();
				roles.add(role);
				user.setRoles(roles);
				this.userManager.update(user);
			}
//			this.partition.setUsers(users);
		} else {
//			this.partition.setUsers(null);
		}
		
		this.partitionManager.update(partition);
		return CLOSE_DIALOG;
	}
	
	private String selectedAppIds;
	
	/**
	 * 进入设置应用页面
	 * @return
	 */
	public String grantApplicationPage(){
		if(this.partition != null) {
			StringBuffer sbIds = new StringBuffer();
			StringBuffer sbNames = new StringBuffer();
			StringBuffer html = new StringBuffer();
			
			/*Set<Application> applications = this.partition.getApplications();
			for(Application app : applications) {
				sbIds.append(app.getId() + ",");
				sbNames.append(app.getName() + ",");
				
				html.append("<div class='iframe_name_box' id='" + app.getId() + "'>");
				html.append("<p class='iframe_name_text'>" + app.getName() + "</p>");
				html.append("<p class='iframe_name_close'>");
				html.append("<a onclick=\"delItem('" + app.getId() + "', '" + app.getName() + "')\">");
				html.append("<img src='" + ServletActionContext.getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' />");
				html.append("</a>");
				html.append("</p>");
				html.append("</div>");
			}*/
			getRequest().setAttribute("selectedAppHTML", html);
			if(!sbNames.toString().equals("")) {
				String _sbNames = sbNames.toString().substring(0, sbNames.toString().length() - 1);
				getRequest().setAttribute("selectedNames", _sbNames);
			}
			if(!sbIds.toString().equals("")) {
				String _sbIds = sbIds.toString().substring(0, sbIds.toString().length() - 1);
				getRequest().setAttribute("selectedIds", _sbIds);
			}
		}
		
		
		return GRANT_APPLICATION_PAGE;
	}
	
	/**
	 * 设置应用
	 * @return
	 */
	public String grantApplication(){
		Set<Application> applications = new HashSet<Application>(0);
		if(this.selectedAppIds != null && !this.selectedAppIds.equals("")) {
			String[] ids = this.selectedAppIds.split(",");
			for(String id : ids) {
				Application app = applicationManager.getById(id);
				applications.add(app);
			}
		}
//		partition.setApplications(applications);
		partitionManager.update(partition);
		return CLOSE_DIALOG;
	}
	/**
	 * 显示选择单位列表
	 * @return
	 */
	public String selectUnitList(){
		String selectUnit = this.partition.getOrgIds();
		
		StringBuffer sbOrgId = new StringBuffer();
		StringBuffer sbOrgName = new StringBuffer();
		StringBuffer html = new StringBuffer();
		if(!StringUtils.isEmpty(selectUnit)){
			String[] strs = selectUnit.split(",");
			for (String str : strs) {
				Organization org = organizationManager.getById(str);
				String orgName = org.getName();
				if(orgName.length()>16){
					orgName = orgName.substring(0,15)+"...";
				}
				if(org!=null){
					sbOrgId.append(org.getId()+",");
					sbOrgName.append(org.getName()+",");
					html.append("<div class='iframe_name_box' id='" + org.getId() + "'>");
					html.append("<p class='iframe_name_text'>" + orgName + "</p>");
					html.append("<p class='iframe_name_close'>");
					html.append("<a onclick=\"delItem('" + org.getId() + "', '" + org.getName() + "')\">");
					html.append("<img src='" + getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' />");
					html.append("</a>");
					html.append("</p>");
					html.append("</div>");
				}
			}
		}
		String str = ServletActionContext.getRequest().getContextPath() +  "/images/component/open_textboxclose.gif";
		System.out.println(str);
		getRequest().setAttribute("selectedOrgHTML", html.toString());
		if(!StringUtils.isEmpty(sbOrgId.toString())){
			String _sbOrgId = sbOrgId.toString().substring(0, sbOrgId.toString().length() - 1);
			getRequest().setAttribute("selectedIds", _sbOrgId);
		}
		if(!StringUtils.isEmpty(sbOrgName.toString())){
			String _sbOrgName = sbOrgName.toString().substring(0, sbOrgName.toString().length() - 1);
			getRequest().setAttribute("selectedNames", _sbOrgName);
		}
		return "chonse_jsp";
	}
	public String findSchool(){
		OrganizationQuery orgQuery = new OrganizationQuery();
		orgQuery.setPageNumber(query.getPageNumber());
		orgQuery.setPageSize(query.getPageSize());
		orgQuery.setName(query.getName());
		Page page = this.organizationManager.findPage(orgQuery);
		super.saveCurrentPage(page,query);
		return "schoollist";
	}
	
	/**
	 * 去分配组织机构页面
	 * @return
	 */
	public String toGrantOrganizPage(){
		OrganizationQuery orgQuery = new OrganizationQuery();
		BeanUtils.copyProperties(orgQuery,query);
		orgQuery.setDf("0");
		orgQuery.setPageSize(Integer.MAX_VALUE);
		Page page = organizationManager.findPage(orgQuery);
		List<Organization> l = page.getResult();
		String selectIds = this.partition.getOrgIds();
		for(Organization org : l){
			if(StrUtil.isNotNullOrBlank(selectIds)){
				if(selectIds.indexOf(org.getId())!=-1){
					org.setChecked("checked");
				}
			}
		}
		this.saveCurrentPage(page,query);
		return GRANT_ORGANIZ_JSP;
	}
	
	/**
	 * 组装组织机构数 对已分配的组织机构回填
	 * @return
	 */
	public String grantOrgnizPage(){
		try {
			//取得所有的组织机构
			List<Organization> resultTopList = organizationManager.findTopByDf();
			String selectedIds ="";
			if(partition.getOrganizations()!=null&&partition.getOrganizations().size()>0){
				for(Organization orgByPar : partition.getOrganizations()){
					selectedIds += orgByPar.getId() +"|";
				}
			}
			//查找有checked属性的框
			String menuXmlString = Common.getOrganizationForFq(resultTopList,selectedIds,null);
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
	 * 分配组织机构
	 * @return
	 */
	public String grantOrganiz(){
		String organizIds = this.partition.getOrgIds();
//		String organizIds = this.getRequest().getParameter("orgIds");
//		String oldOrgIds = this.getRequest().getParameter("oldOrgIds");
//		Set<Organization> organizations = partition.getOrganizations();
		Set<Organization> organizations = new HashSet<Organization>();
		if(!"".equals(organizIds)){
			String[] orgIds  = organizIds.split(",");
			for(String orgId : orgIds){
				Organization org=organizationManager.getById(orgId);
				organizations.add(org);
			}
		}
		/*if(StringUtils.isNotBlank(oldOrgIds)){
			String[] orgIds  = oldOrgIds.split(", ");
			
		}*/
		
//		String 
		
		partition.setOrganizations(organizations);
		partitionManager.update(partition);
		return CLOSE_DIALOG;
	}
	/**
	 * 获取分区类别集合
	 * @return
	 */
	public Map<String,String> getTypeMap(){
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("1","行政分区");
		map.put("2","自定义分区");
		return null;
	}
	
	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}
	public String getSelectedUserIds() {
		return selectedUserIds;
	}
	public void setSelectedUserIds(String selectedUserIds) {
		this.selectedUserIds = selectedUserIds;
	}
	public String getSelectedAppIds() {
		return selectedAppIds;
	}
	public void setSelectedAppIds(String selectedAppIds) {
		this.selectedAppIds = selectedAppIds;
	}
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setPartitionManager(PartitionManager manager) {
		this.partitionManager = manager;
	}
	private UserManager userManager;
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public Object getModel() {
		return partition;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}
	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public PartitionQuery getQuery() {
		return query;
	}

	public void setQuery(PartitionQuery query) {
		this.query = query;
	}

	public java.lang.String getId() {
		return id;
	}
	
}
