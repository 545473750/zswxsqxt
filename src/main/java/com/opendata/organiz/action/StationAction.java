/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.opendata.application.model.Application;
import com.opendata.common.base.*;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Common;

import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.*;
import com.opendata.organiz.service.*;
import com.opendata.organiz.vo.query.*;
import com.opendata.sys.model.Resources;
import com.opendata.sys.service.PartitionManager;
import com.opendata.sys.service.ResourcesManager;

/**
 * 负责处理岗位管理的请求
 * 
 * @author 顾保臣
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Namespace("/organiz")
@Results({
@Result(name="main",location="/WEB-INF/pages/organiz/Station/main.jsp", type = "dispatcher"),
@Result(name="leftTree",location="/WEB-INF/pages/organiz/Station/leftTree.jsp", type = "dispatcher"),
@Result(name="list",location="/WEB-INF/pages/organiz/Station/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/organiz/Station/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/organiz/Station/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/organiz/Station/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/organiz/Station/show.jsp", type = "dispatcher"),
@Result(name="configUserForStation", location="/commons/dialogclose.jsp", type="dispatcher"),
@Result(name="grant_permission", location="/WEB-INF/pages/organiz/Station/grant_permission.jsp"),
@Result(name="listAction",location="..//organiz/Station!list.do", type = "redirectAction")
})
public class StationAction extends BaseStruts2Action implements Preparable,ModelDriven{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String SHOW_JSP = "show";
	protected static final String GRANT_PERMISSION_PAGE = "grant_permission";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	private UserManager userManager;
	
	private StationManager stationManager;

	private ResourcesManager resourcesManager;
	private OrganizationManager organizationManager; // 组织机构业务处理
	private List<Organization> organizations; // 组织机构
	private Map<String, String> stationLevelMap; // 组织机构级别
	
	private DicUtil dicUtil;
	
	private Station station;
	java.lang.String id = null;
	private String[] items;
	StationQuery query = new StationQuery();
	String partitionId;
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			station = new Station();
		} else {
			station = (Station)stationManager.getById(id);
		}
	}
	
	/**
	 * 跳转到新的页面
	 * @return
	 */
	public String main() {
		getCurrentUnitId();
		return "main";
	}
	/**
	 *  跳转到左侧组织结构
	 * @return
	 */
	public String leftTree() {
		getCurrentUnitId();
		return "leftTree";
	} 
	
	

	/**
	 * 加载组织结构XML
	 * @return
	 */
	public String deptXML() {
		try {
			String menuXmlString = "";
			String orgIds = this.getCurrUser().getDeptIDs();
			List<Organization> orgs = new ArrayList<Organization>();
			if(StringUtils.isNotBlank(orgIds)){
				if(orgIds.indexOf(",")!=-1){
					String[] orgIdss= orgIds.split(",");
					for(String orgId:orgIdss){
						Organization org = organizationManager.getById(orgId);
						orgs.add(org);
					}
				}else{
					Organization org = organizationManager.getById(orgIds);
					orgs.add(org);
				}
			}
			menuXmlString = Common.getDeptTree(orgs);
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
	 * 执行搜索
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		// 所属组织机构
//		this.organizations = this.organizationManager.findAllByDf();
		// 岗位级别
//		this.stationLevelMap = dicUtil.getDicList("ORG_STAlEVEL");
		
		query.setDf("0");
		Page page = this.stationManager.findPage(query);
		
		List<Station> result = page.getResult();
		for(Station station : result) {
			station.setOrgString(station.getOrganization().getName());
			station.setLevelString(dicUtil.getDicList("ORG_STAlEVEL").get(station.getLevel()));
		}
		
		this.saveCurrentPage(page, query);
		return LIST_JSP;
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		
		this.station.setOrgString(this.station.getOrganization().getName());
		this.station.setLevelString(dicUtil.getDicList("ORG_STAlEVEL").get(this.station.getLevel()));
		return SHOW_JSP;
	}
	
	/**
	 * 进入新增页面
	 * @return
	 */
	public String create() {
		// 组织机构列表
//		this.organizations = this.organizationManager.findAllByDf();
		// 岗位级别
//		this.stationLevelMap = dicUtil.getDicList("ORG_STAlEVEL");
		Organization organization = this.organizationManager.getById(query.getOrganizationId());
		this.getRequest().setAttribute("organization",organization);
		return CREATE_JSP;
	}
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		// 校验编号是否唯一
		List<Station> result = this.stationManager.findAllByDf("code", station.getCode());
		if(result != null && result.size() > 0) {
			Flash.current().success("岗位编号已经存在,请重新填写！");
			
			// 组织机构列表
			this.organizations = this.organizationManager.findAllByDf();
			// 岗位级别
			this.stationLevelMap = dicUtil.getDicList("ORG_STAlEVEL");
			return CREATE_JSP;
		}
		
		station.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		station.setDf("0");
		stationManager.save(station);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
//		// 组织机构列表
//		this.organizations = this.organizationManager.findAllByDf();
//		// 岗位级别
//		this.stationLevelMap = dicUtil.getDicList("ORG_STAlEVEL");
		Organization organization = this.organizationManager.getById(query.getOrganizationId());
		this.getRequest().setAttribute("organization",organization);
		
		return EDIT_JSP;
	}
	
	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		stationManager.update(this.station);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			station = (Station)stationManager.getById(id);
			station.setDf("1");
			stationManager.update(this.station);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 进入权限设置页面
	 * @return
	 */
	public String grantPage(){
		return GRANT_PERMISSION_PAGE;
	}
	
	/**
	 * 进入权限设置页面
	 * @return
	 */
	public String toGrantPage(){
		try{
			List<Resources> resourcesList = new ArrayList<Resources>();
			resourcesList = resourcesManager.findAllByDf();		//取得所有未被删除的菜单
			List<Resources> resourecsListByStation = resourcesManager.findByStation(station.getId());	//取得该岗位所对的菜单
			//组装成带多选框的树
			for(Resources res : resourecsListByStation){
				for(Resources re : resourcesList){
					if(re.getId().equals(res.getId())){
						re.setChecked("1");
					}
				}
			}
			List<Resources> result = Common.getTopMenuList(resourcesList);
			//查找有checked属性的框
			String menuXmlString = Common.getTreeXML(result);
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 权限设置
	 * @return
	 */
	public String grantPermission(){
		String permissionIds = getRequest().getParameter("permissionIds");
		if(!"".equals(permissionIds)){
			String[] perIds = permissionIds.split(",");
			Set<Resources> resources = new HashSet<Resources>(0);
			for(String perID : perIds){
				Resources res = resourcesManager.getById(perID);
				if(res!=null){
					resources.add(res);
				}
			}
			station.setResources(resources);
			stationManager.update(station);
		}else{
			station.setResources(null);
			stationManager.update(station);
		}
		getRequest().setAttribute("dialogId", getRequest().getParameter("dialogId"));
		return CLOSE_DIALOG_NOREFRESH;
	}
	
	private String stationId;

	/**
	 * 用户设置
	 * @return
	 */
	public String configUserForStation() {
		Station station = stationManager.getById(this.stationId);
		
		Set<User> users = new HashSet<User>();
		if(items != null) {
			for(int i = 0; i < items.length; i++) {
				Hashtable params = HttpUtils.parseQueryString(items[i]);
				java.lang.String id = new java.lang.String((String)params.get("id"));
				
				users.add(userManager.getById(id));
			}
		}
		station.setUsers(users);
		stationManager.update(station);
		
		Flash.current().success("人员设置操作完成！");
		return "configUserForStation";
	}
	
	private void getCurrentUnitId(){
		String orgIds = this.getCurrUser().getDeptIDs();
		String orgId="";
		if(StringUtils.isNotBlank(orgIds)){
			if(orgIds.indexOf(",")!=-1){
				String[] idss = orgIds.split(",");
				orgId = idss[0];
			}else{
				orgId=orgIds;
			}
		}
		getRequest().setAttribute("orgId",orgId);
	}
	
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setStationManager(StationManager manager) {
		this.stationManager = manager;
	}	
	
	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}
	
	public Object getModel() {
		return station;
	}
	
	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public Map<String, String> getStationLevelMap() {
		return stationLevelMap;
	}

	public void setStationLevelMap(Map<String, String> stationLevelMap) {
		this.stationLevelMap = stationLevelMap;
	}
	
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public StationQuery getQuery() {
		return query;
	}

	public void setQuery(StationQuery query) {
		this.query = query;
	}

	public StationManager getStationManager() {
		return stationManager;
	}
	
	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
}
