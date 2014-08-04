package com.opendata.login.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.application.service.ApplicationManager;
import com.opendata.application.service.PermissionManager;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.util.Common;
import com.opendata.common.util.IpUtil;
import com.opendata.login.model.LoginInfo;
import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.Station;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.RoleManager;
import com.opendata.organiz.service.StationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.sys.model.LeftMenu;
import com.opendata.sys.model.Log;
import com.opendata.sys.model.Partition;
import com.opendata.sys.model.Resources;
import com.opendata.sys.model.Sysvariable;
import com.opendata.sys.service.LogManager;
import com.opendata.sys.service.PartitionManager;
import com.opendata.sys.service.ResourcesManager;
import com.opendata.sys.service.SysvariableManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 登录action类
 * @author 付威
 */
@Namespace("/login")
@Results({
@Result(name="index",location="/index.jsp", type = "dispatcher"),
@Result(name="left",location="/left.jsp", type = "dispatcher"),
@Result(name="indexAction",location="../index/Index!index.do", type = "redirectAction")
})
public class LoginAction extends BaseStruts2Action implements Preparable, ModelDriven {
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	protected static final String LOGIN_SUCCESS = "index";
	protected static final String LEFT_SUCCESS = "left";
	
	private String loginname;
	private String password;
	
	private UserManager userManager;
	private RoleManager roleManager;
	private StationManager stationManager;
	
	private ResourcesManager resourcesManager;
	private PartitionManager partitionManager;
	private LogManager logManager;
	private Application application;
	private ApplicationManager applicationManager;
	private Permission permission;
	private PermissionManager permissionManager;
	private SysvariableManager sysvariableManager;

	
	
	
	public Object getModel() {return null;}
	public void prepare() throws Exception {}
	
	/**
	 * 用户登录
	 * @return
	 */
	public String login(){
		User ui = (User) getRequest().getSession().getAttribute("user");
		if (ui == null) {
			return LOGIN;
		}
		
//		List<Role> list = roleManager.findByUser(ui.getId());
		LoginInfo li = new LoginInfo();
		li.setUserID(ui.getId());
		li.setLoginName(ui.getLoginname());
		li.setPassword(ui.getPassword());
		li.setUserName(ui.getUsername());
		li.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		li.setIp(IpUtil.getClientAddress(getRequest()));
		li.setIp(IpUtil.getIpAddr(getRequest()));
		li.setDeptIDs("");
		li.setDeptNames("");
			
		ui = this.userManager.getById(ui.getId()); // 不这样，会有懒加载问题
		Set<OrganizationUserRelation> ours = ui.getOuRelations();
		StringBuffer sbId = new StringBuffer();
		StringBuffer sbName = new StringBuffer();
		for (OrganizationUserRelation our : ours) {
			sbId.append(our.getOrganization() == null ? "" : our.getOrganization().getId() + ",");
			sbName.append(our.getOrganization() == null ? "" : our.getOrganization().getName() + ",");
		}
		if (sbId != null && !sbId.toString().equals("")) {
			String _sbId = sbId.toString().substring(0, sbId.toString().length() - 1);
			li.setDeptIDs(_sbId);
		}
		if (sbName != null && !sbName.toString().equals("")) {
			String _sbName = sbName.toString().substring(0, sbName.toString().length() - 1);
			li.setDeptNames(_sbName);
		}
			
		// 组装角色、权限
		li.setRoleIDs("");
		li.setRoleNames("");
		li.setPermissionCodes("");
		for (Role role : roleManager.findAllByDf("users", "id", ui.getId())) {
			if (li.getRoleIDs().equals("")) {
				li.setRoleIDs(role.getId());
				li.setRoleNames(role.getName());
			} else {
				li.setRoleIDs(li.getRoleIDs() + "," + role.getId());
				li.setRoleNames(li.getRoleNames() + "," + role.getName());
			}

			for (Resources res : resourcesManager.findAllByDf("roles", "id", role.getId())) {
				if ("".equals(li.getPermissionCodes())) {
					li.setPermissionCodes(res.getCode());
				} else {
					li.setPermissionCodes(li.getPermissionCodes() + "," + res.getCode());
				}
			}
		}
		//组装岗位权限
		li.setStationIds("");
		li.setStationNames("");
		for (Station station : stationManager.findAllByDf("users", "id", ui.getId())) {
			if (li.getStationIds().equals("")) {
				li.setStationIds(station.getId());
				li.setStationNames(station.getName());
			} else {
				li.setStationIds(li.getStationIds() + "," + station.getId());
				li.setStationNames(li.getStationNames() + "," + station.getName());
			}

			for (Resources res : resourcesManager.findAllByDf("stations", "id", station.getId())) {
				if ("".equals(li.getPermissionCodes())) {
					li.setPermissionCodes(res.getCode());
				} else {
					li.setPermissionCodes(li.getPermissionCodes() + "," + res.getCode());
				}
			}
		}
		getSession().setAttribute(LOGIN_INFO, li);
		/*List<Partition> partitions = partitionManager.findAllByDf("users", "id", ui.getId());
		if (partitions.size() > 0) {
			getSession().setAttribute("partitions", partitions);
		}
		Sysvariable sysvariable = sysvariableManager.findByProperty("name", "leftFrameFlag");
		if(sysvariable!=null){
			getSession().setAttribute("leftFrameFlag", sysvariable.getCode());
		}*/
		// 登录记入日志
		saveLog(li);
		return "indexAction";
	}
	/*//查询栏目
	public void findColumn(){
		String subjectid="402880c5432debb901432dfdbbdd0002";
		List<Column> list = this.columnManager.findAllBy("subjectId", subjectid);	//根据专题得到专题下的栏目
//		排序
		Collections.sort(list,new Comparator<Column>(){  
            @Override  
            public int compare(Column b1, Column b2) {  
                return b1.getSeq().compareTo(b2.getSeq());  
            }  
              
        });  
		getRequest().getSession().setAttribute("columns", list);
		getRequest().getSession().setAttribute("subjectid", subjectid);
	}*/
  /**
   * 保存用户登录日志
   * @param loginUser
   */	
  private void saveLog(LoginInfo loginUser){
		Log log = new Log();
		log.setDf("0");
		log.setIp(loginUser.getIp());
		log.setPermission("用户登录");
		log.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		log.setType("APP_LOG001");
		log.setUsername(loginUser.getUserName());
		logManager.save(log);
  }
	
	/**
	 * 用户登出
	 * @return
	 */
	public String logout(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		SecurityContextHolder.clearContext();
		getSession().setAttribute(LOGIN_INFO, null);
		getSession().setAttribute("user", null);
		return LOGIN;

	}
	
	/**
	 * 根据权限输出菜单
	 * @return
	 */
	public String mainMenu(){
		try {
			String userId = this.getCurrUser().getUserID();
			User user = userManager.getById(getCurrUser().getUserID());			
			List<Resources> resourcesList1 = resourcesManager.findByUserNoPartition(user.getId());
//			List<Resources> resourcesList2 = resourcesManager.findByUser(user);
			String menuXmlString = Common.getTreeXML(Common.getBaseMenuList(resourcesList1));
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询菜单
	 * @return
	 */
	public String findAllMenu(){
		//根据用户ID 得到所有的一级菜单
		List<LeftMenu> li=resourcesManager.findRootByUserNoPartition(getCurrUser().getUserID());
		for(int i=0;i<li.size();i++){
			LeftMenu lm=li.get(i);
			if(lm.getPermissionId()!=null){
				lm.setUrl(permissionManager.getById(lm.getPermissionId()).getUrl());
			}
			List<LeftMenu> list=resourcesManager.findChildByUserNoPartition(getCurrUser().getUserID(), lm.getId());
			lm.setChild(findChildren(list,getCurrUser().getUserID()));
		}
		getRequest().setAttribute("li", li);
		return LEFT_SUCCESS;
	}
	
	public List<LeftMenu> findChildren(List<LeftMenu> list,String userId){
		for(int i=0; i<list.size();i++){
			LeftMenu lm=list.get(i);
			if(lm.getPermissionId()!=null){
				lm.setUrl(permissionManager.getById(lm.getPermissionId()).getUrl());
			}
			List<LeftMenu> lms=resourcesManager.findChildByUserNoPartition(getCurrUser().getUserID(), lm.getId());
			lm.setChild(findChildren(lms,getCurrUser().getUserID()));
		}
		return list;
	}
	
	
	
	
	/**
	 * 取得分区管理员菜单
	 */
	@SuppressWarnings("unchecked")
	public String partitionMenu(){
		try{
			List<Partition> partitions = (List<Partition>)getRequest().getSession().getAttribute("partitions");//取得此用户管理的所有分区
			String menuXmlString = getPartitionMenuXml(partitions);
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 组装分区管理的树型结构xml
	 * @param partitions
	 * @return
	 */
	private String getPartitionMenuXml(List<Partition> partitions){
		Document document = DocumentHelper.createDocument();  
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    for(Partition partition : partitions){
	    	String partitionId = partition.getId();
	    	Element item = root.addElement("item");  
		    item.addAttribute("text", partition.getName());  
		    item.addAttribute("id", partitionId); 
		    Element userItem = item.addElement("item");
		    userItem.addAttribute("text", "用户管理");
		    userItem.addAttribute("id", partitionId+"1");
		    Element userUrl = userItem.addElement("userdata");
	    	//记录URL访问地址
		    userUrl.addAttribute("name", "url");
		    userUrl.setText("/organiz/User!list.do?partitionId="+partitionId);
		    Element roleItem = item.addElement("item");
		    roleItem.addAttribute("text", "角色管理");
		    roleItem.addAttribute("id", partitionId+"2");
		    Element roleUrl = roleItem.addElement("userdata");
	    	//记录URL访问地址
		    roleUrl.addAttribute("name", "url");
		    roleUrl.setText("/organiz/Role!list.do?partitionId="+partitionId);
		    Element orgItem = item.addElement("item");
		    orgItem.addAttribute("text", "组织机构管理");
		    orgItem.addAttribute("id", partitionId+"3");
		    Element orgUrl = orgItem.addElement("userdata");
	    	//记录URL访问地址
		    orgUrl.addAttribute("name", "url");
		    orgUrl.setText("/organiz/Organization!main.do?partitionId="+partitionId);
	    }
	    return document.asXML();
	}
	
	
	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	public void setStationManager(StationManager stationManager) {
		this.stationManager = stationManager;
	}

	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}

	public void setPartitionManager(PartitionManager partitionManager) {
		this.partitionManager = partitionManager;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public RoleManager getRoleManager() {
		return roleManager;
	}
	public ResourcesManager getResourcesManager() {
		return resourcesManager;
	}
	public PartitionManager getPartitionManager() {
		return partitionManager;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public PermissionManager getPermissionManager() {
		return permissionManager;
	}
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
	public void setSysvariableManager(SysvariableManager sysvariableManager) {
		this.sysvariableManager = sysvariableManager;
	}
}
