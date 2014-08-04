/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.Dispatcher;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.application.service.ApplicationManager;
import com.opendata.application.service.PermissionManager;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.util.Common;
import com.opendata.sys.model.Resources;
import com.opendata.sys.service.ResourcesManager;
import com.opendata.sys.vo.query.ResourcesQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.RuntimeConfiguration;
import com.opensymphony.xwork2.config.entities.ActionConfig;

/**
 * 菜单Action类 负责和表现层的交互以及调用相关业务逻辑层完成功能
 * @author付威
 */
@Namespace("/sys")
@Results({
@Result(name="list",location="/WEB-INF/pages/sys/Resources/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Resources/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/Resources/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/Resources/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Resources/show.jsp", type = "dispatcher"),
@Result(name="main",location="/WEB-INF/pages/sys/Resources/main.jsp", type = "dispatcher"),
@Result(name="move",location="/WEB-INF/pages/sys/Resources/move.jsp", type = "dispatcher"),
@Result(name="leftTree",location="/WEB-INF/pages/sys/Resources/leftTree.jsp", type = "dispatcher"),
@Result(name="importApp",location="/WEB-INF/pages/sys/Resources/import_app.jsp", type = "dispatcher"),
@Result(name="close_dialog",location="/WEB-INF/pages/sys/Resources/dialogclose.jsp", type = "dispatcher"),
@Result(name="menu_action_list",location="/WEB-INF/pages/sys/Resources/menuactionlist.jsp", type = "dispatcher"),
@Result(name="listAction",location="../sys/Resources!list.do?flag=refresh&queryParentId=${queryParentId}", type = "redirectAction")
})
public class ResourcesAction extends BaseStruts2Action implements Preparable,ModelDriven{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "sequence"; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String SHOW_JSP = "show";
	
	protected static final String MAIN = "main";
	protected static final String LEFTTREE = "leftTree";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	
	protected static final String IMPORT_APP = "importApp";
	
	protected static final String MOVE_JSP = "move";
	
	protected static final String CLOSE_DIALOG = "close_dialog";
	
	protected static final String MENU_ACTION_LIST="menu_action_list";
	
	private ResourcesManager resourcesManager;
	
	private ApplicationManager applicationManager;
	
	private PermissionManager permissionManager;
	
	
	private Resources resources;
	java.lang.String id = null;
	private String[] items;
	private String queryParentId;
	
	private List<String> menuActionList;
	
	ResourcesQuery query = new ResourcesQuery();
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			resources = new Resources();
		} else {
			resources = (Resources)resourcesManager.getById(id);
		}
		//初始化上级访问入口
		if(!isNullOrEmptyString(queryParentId)&&!"root".equals(queryParentId)&&!"-1".equals(queryParentId)){
			resources.setResources(resourcesManager.getById(queryParentId));
		}
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setResourcesManager(ResourcesManager manager) {
		this.resourcesManager = manager;
	}	
	
	
	
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public Object getModel() {
		return resources;
	}
	
	public void setId(java.lang.String val) {
		this.id = val;
	}
	
	



	public String getQueryParentId() {
		return queryParentId;
	}

	public void setQueryParentId(String queryParentId) {
		this.queryParentId = queryParentId;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	
	/** 执行搜索 */
	public String list() {
		//先判断查询的父菜单是不是功能菜单，如果是功能菜单 则直接到查看页面
		if(resources.getResources()!=null&&Resources.TYPE_FUNCTION.equals(resources.getResources().getType())){
			resources = resources.getResources();
			return SHOW_JSP;
		}
		query.setDf("0");
		query.setSortColumns(" sequence asc ");
		// 如果是root,则显示根应用访问入口
		if(queryParentId != null && queryParentId.equals("root")) {
			query.setParentId("-1");//取得所有根应用访问入口
		} else if(queryParentId==null||"".equals(queryParentId)){
			query.setParentId("-1");
		}else{
			query.setParentId(queryParentId);
		}
		Page page = this.resourcesManager.findPage(query);
		//如果查询的父菜单是菜单入口，而且此菜单入口下面没有功能入口 则直接到查看页面
		if(resources.getResources()!=null&&Resources.TYPE_MENU.equals(resources.getResources().getType())&&page.getTotalCount()==0){
			resources = resources.getResources();
			return SHOW_JSP;
		}
		this.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/** 查看对象*/
	public String show() {
		return SHOW_JSP;
	}
	
	/** 进入新增页面*/
	public String create() {
		// 排序号
		resources.setSequence(resourcesManager.findSequence(queryParentId));
		return CREATE_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		
		
		
		if("".equals(queryParentId)||"-1".equals(queryParentId)||"root".equals(queryParentId)){
			resources.setParentId(null);
		}else{
			resources.setParentId(queryParentId);
		}
		//添加前判断code是否唯一
		if(!resourcesManager.isUniqueByDf(resources, "code")){
			Flash.current().success("菜单code已经存在");
			return CREATE_JSP;
		}
		//添加前判断同一父菜单下的名称是否唯一
		if(!resourcesManager.isUniqueByDf(resources, "name,parentId")){
			Flash.current().success("同一父菜单下菜单名称已经存在");
			return CREATE_JSP;
		}
		resources.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		resourcesManager.save(resources);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**进入更新页面*/
	public String edit() {
		
		
		return EDIT_JSP;
	}

	/**
	 * 取得移动时菜单的集合 组合成树型显示
	 */
	private void getResourcesList() {
		List<Resources> resourcesList = resourcesManager.findByTop();//取得所有最上级的菜单 也就是parentId为NULL
		String level ="";//用来表示级数的，没多一级 level加--
		List<Resources> resultResList = new ArrayList<Resources>();
		for(Resources res : resourcesList){
			//如果不是目录就不显示，只能移动到某目录下
			if(res.getType().equals(Resources.TYPE_DIC)){
				//如果不是修改的菜单就显示 主要是为了移动菜单不出错
				if(!res.getId().equals(resources.getId())){
					resultResList.add(res);
					getChildResources(resultResList,res,level);
				}
			}
		}
		getRequest().setAttribute(RESOURCES, resultResList);
	}
	
	/**
	 * 通过递归调用取得所有移动时菜单的所有子菜单 组合成树型显示
	 * @param resultResList 结果集合对象
	 * @param parentRes 父菜单
	 * @param level 级别 用来下拉框显示树型结构
	 */
	private void getChildResources(List<Resources> resultResList,Resources parentRes,String level){
		List<Resources> resList = resourcesManager.findAllByDf("parentId", parentRes.getId());
		level+="——";
		if(resList!=null){
			for(Resources res : resList){
				//如果不是目录就不显示，只能移动到某目录下
				if(res.getType().equals(Resources.TYPE_DIC)){
					//如果不是修改的菜单就显示 主要是为了移动菜单不出错
					if(!res.getId().equals(resources.getId())){
						resultResList.add(res);
						res.setName(level+res.getName());
						getChildResources(resultResList,res,level);
					}
				}
			}
		}
	}
	
	/**保存更新对象*/
	public String update() {
		if("".equals(resources.getParentId())){
			resources.setParentId(null);
		}
		//更新前判断code是否唯一
		if(!resourcesManager.isUniqueByDf(resources, "code")){
			
			Flash.current().success("菜单code已经存在");
			return EDIT_JSP;
		}
		//更新前判断同一父菜单下的名称是否唯一
		if(!resourcesManager.isUniqueByDf(resources, "name,parentId")){
			Flash.current().success("同一父菜单下菜单名称已经存在");
			return EDIT_JSP;
		}
		resourcesManager.update(this.resources);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**删除对象*/
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			resources = (Resources)resourcesManager.getById(id);
			//删除菜单时  菜单与角色的关系都删除 此菜单下的子菜单也一并删除
			resourcesManager.deleteByAllChild(this.resources);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	

	/**
	 * 到移动页面
	 */
	public String toMove(){
		getResourcesList();
		return MOVE_JSP;
	}
	
	/**
	 * 菜单移动 移动时所有子菜单都随之移动
	 */
	public String move(){
		if("".equals(resources.getParentId())){
			resources.setParentId(null);
		}
		resourcesManager.update(this.resources);
		Flash.current().success(MOVE_SUCCESS);
		return CLOSE_DIALOG;
		
	}
	
	
	/**
	 * 到菜单的管理页面 左侧为树型菜单 右侧是其子菜单的列表及操作
	 * @return
	 */
	public String main() {
		return MAIN;
	}
	
	/**action链接列表**/
	public String menuActionList(){
		menuActionList = new ArrayList<String>();
		Dispatcher dispatcher = Dispatcher.getInstance();
        ConfigurationManager configurationManager = dispatcher.getConfigurationManager();
        Configuration config = configurationManager.getConfiguration();
        RuntimeConfiguration runtimeConfiguration = config.getRuntimeConfiguration();
        //所有的namespace
        Map<String, Map<String, ActionConfig>> namespaces = runtimeConfiguration.getActionConfigs();
        Iterator nskeys = namespaces.keySet().iterator();
        Iterator acKeys;
        //遍历namespace
        while (nskeys.hasNext()) {
        	String namespaceKey=nskeys.next().toString();
            Map<String, ActionConfig> actionConfigs = namespaces.get(namespaceKey);
            //获得所有的action配置信息
            acKeys = actionConfigs.keySet().iterator();
            while (acKeys.hasNext()) {
            	String actionConfigKey=acKeys.next().toString();
                ActionConfig ac = actionConfigs.get(actionConfigKey);
                Class c=null;
				try {
					c = Class.forName(ac.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				menuActionList.add(namespaceKey+"/"+actionConfigKey+">>>>>>>>>>>>>>>>");
                for(Method m : c.getDeclaredMethods()){
                	if(m.getModifiers()==Modifier.PUBLIC){
                		boolean showFlag=true;
                		if(m.getName().indexOf("set")!=-1||m.getName().indexOf("get")!=-1)
                		{
                			String tempName = m.getName().replace("set", "").replace("get", "");
                			tempName = String.valueOf(tempName.charAt(0)).concat(tempName.substring(1));
                			Field field = null;
                			try {
                				field = c.getDeclaredField(tempName);
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								//e.printStackTrace();
							}
							if(field==null){
            					showFlag=false;
            				}
                		}
                		
                		if(showFlag){
                			menuActionList.add(namespaceKey+"/"+actionConfigKey+"!"+m.getName()+".do");
                		}
                	}
                }
                menuActionList.add("<br/>");	
            }
        }
        return MENU_ACTION_LIST;
	}
	
	/**
	 * 到左侧树型菜单页面
	 * @return
	 */
	public String leftTree() {
		return LEFTTREE;
	} 
	
	
	/**
	 * 加载左侧菜单树型XML
	 * @return
	 */
	public String resourcesXML() {

		try {
			
			List<Resources> resList = this.resourcesManager.findAllByNoFunction();//查询所有的根菜单
			String menuXmlString = Common.getResourcesTreeXML(Common.getTopMenuList(resList));
			
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
	 * 去导入应用页面 列出的为所有应用及应用下的访问入口 用树型显示
	 */
	public String toImportAppPage(){
		
		return IMPORT_APP;
	}
	
	/**
	 * 加载所有应用及应用下访问入口的树型XML
	 * @return
	 */
	public String toImportApp(){
		try{	
			List<Application> applications = applicationManager.findAllByDf();
			for(Application app : applications){
				app.setPermissions(Common.getTopPermissionList(permissionManager.findByApp(app.getId())));
			}
			String menuXmlString = Common.getApplicationTreeXML(applications);
			
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
	 * 根据所选的应用或访问入口及父菜单导入此父菜单下
	 */
	@Transactional
	public String importApp(){
		String permissionIds = getRequest().getParameter("permissionIds");
		String codeTs =  new SimpleDateFormat("HHmmss").format(new Date());
		Map<String,String> perResMap = new HashMap<String,String>();//记录应用及访问入口ID与新生成的菜单ID关系,用来保持以前的父子关系
		if("".equals(queryParentId)||"root".equals(queryParentId)||"-1".equals(queryParentId)){
			queryParentId = null;
		}
		Resources res = null;
		if(!"".equals(permissionIds)){
			String[] perIds  = permissionIds.split(",");
			for(String perId : perIds){
				Application app = applicationManager.getById(perId);//判断是不是应用ID 如果是应用则通过应用属性保存菜单目录
				if(app!=null){
					res = saveResources(codeTs, app);
				}else{
					//不是应用ID则说明是访问入口ID
					Permission per = permissionManager.getById(perId);
					//先判断此访问入口是不是有父访问入口 如果有则判断是不是被选中已生成
					String parentId = null;
					if(perResMap.get(per.getParentId())!=null){
						parentId = perResMap.get(per.getParentId());
					//判断此访问入口所属应用是不是被选中已生成	
					}else if(perResMap.get(per.getApplicationId())!=null){
						parentId = perResMap.get(per.getApplicationId());
					}else{
						parentId = queryParentId;
					}
					res = saveResources(parentId,per);
					//如果此访问入口为主访问入口，则此主访问入口的所有子访问入口也加入菜单中
					if(Permission.TYPE_MENU.equals(per.getType())){
						saveChindResources(res.getId(),perId,per.getApplicationId());
					}
				}
				perResMap.put(perId, res.getId());
			}
		}	
		Flash.current().success("导入成功！");
		return CLOSE_DIALOG;
	}

	/**
	 * 根据选择导入的应用作为菜单目录保存到此父菜单下
	 * @param codeTs 当前时间 一般用应用编号作为菜单的编号 如果此编号已经存在 则在编号后加上这个时间标识用来区分
	 * @param app 选择导入的应用
	 * @return 保存后菜单目录
	 */
	private Resources saveResources(String codeTs, Application app) {
		Resources res = new Resources();
		res.setBigIcon(app.getBigIcon());
		if(resourcesManager.findByCode(app.getCode())!=null){
			res.setCode(app.getCode()+codeTs);
		}else{
			res.setCode(app.getCode());
		}
		res.setDf("0");
		res.setIcon(app.getIcon());
		res.setKind("0");
		res.setName(app.getName());

		res.setParentId(queryParentId);

		res.setSequence(0);
		res.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		res.setType(Resources.TYPE_DIC);//显示菜单
		
		resourcesManager.save(res);
		return res;
	}

	/**
	 * 根据选择的应用访问入口作为对应的菜单添加到相应父菜单下
	 * @param parentId 父菜单ID
	 * @param per 应用访问入口
	 * @return 保存后的菜单
	 */
	private Resources saveResources(String parentId,
			Permission per) {
		//当前时间 一般用访问入口编号作为菜单的编号 如果此编号已经存在 则在编号后加上这个时间标识用来区分
		String codeTs =  new SimpleDateFormat("HHmmss").format(new Date());
		Resources res = new Resources();
		res.setBigIcon(per.getBigIcon());
		String code = per.getApplication().getCode()+"_"+per.getCode();
		if(resourcesManager.findByCode(code)!=null){
			res.setCode(code+codeTs);
		}else{
			res.setCode(code);
		}
		res.setDf("0");
		res.setIcon(per.getIcon());
		res.setKind("0");//是否弹出,默认为不弹出显示
		res.setName(per.getName());
		res.setParentId(parentId);
		//如果是主菜单或功能菜单就对应到应用功能入口
		if(!Permission.TYPE_DIC.equals(per.getType())){
			res.setPermissionId(per.getId());
		}
		res.setSequence(per.getSequence());
		res.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		res.setType(per.getType());//显示菜单
		resourcesManager.save(res);
		return res;
	}
	
	/**
	 * 如果选择导入的此访问入口为主访问入口，则此主访问入口的所有子访问入口也加入菜单中
	 * @param parentId 保存到的父菜单ID
	 * @param parentPermissionId 选择导入的主访问入口ID
	 * @param appId 应用ID
	 */
	private void saveChindResources(String parentId,String parentPermissionId,String appId){
		List<Permission> perList = permissionManager.findByParentId(parentPermissionId,appId);
		for(Permission per :perList){
				saveResources(parentId, per);
		}	
	}

	/**
	 * 
	 * @param applicationManager
	 */
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public List<String> getMenuActionList() {
		return menuActionList;
	}

	public void setMenuActionList(List<String> menuActionList) {
		this.menuActionList = menuActionList;
	}

	public ResourcesQuery getQuery() {
		return query;
	}

	public void setQuery(ResourcesQuery query) {
		this.query = query;
	}

}
