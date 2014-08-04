package com.opendata.component.init;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.springframework.beans.BeanUtils;

import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.application.service.ApplicationManager;
import com.opendata.application.service.PermissionManager;
import com.opendata.common.util.Platform;
import com.opendata.common.util.PropertyUtil;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.RoleManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.sys.model.Dictitem;
import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.model.Resources;
import com.opendata.sys.model.Sysvariable;
import com.opendata.sys.service.DictitemManager;
import com.opendata.sys.service.DictvalueManager;
import com.opendata.sys.service.ResourcesManager;
import com.opendata.sys.service.SysvariableManager;

public class DBIniter {
	
	private static ApplicationManager applicationManager;
	private static PermissionManager permissionManager;
	private static RoleManager roleManager;
	private static ResourcesManager resourcesManager;
	private static UserManager userManager;
	private static DictitemManager dictitemManager;
	private static DictvalueManager dictvalueManager;
	private static SysvariableManager sysvariableManager;
	
	private static OrganizationManager organizationManager;
	private static OrganizationUserRelationManager organizationUserRelationManager;
	
	private static Set<User> users = new HashSet<User>(0);//用来记录系统管理员角色的用户
	private static Map<String,String> perResMap = new HashMap<String,String>();//记录应用及访问入口ID与新生成的菜单ID关系,用来保持以前的父子关系
	private static Set<Resources> resources = new HashSet<Resources>(0);//用来记录所有新导入的菜单 用来给系统管理员分配权限
	
	/*
	 * 系统初始化数据库
	 */
	public static void init(){
		fill();
		try {
			String init=PropertyUtil.getProperty("system.db.init");
			if(init!=null&&init.equals("true")){
				//得到配置文件
				File initFile=new File(getRootPath()+"\\WEB-INF\\classes\\"+"initdata.xml");
				if(!initFile.exists()){
					//判断文件是否存在
					return;
				}
				//得到根节点
				Element rootElement=new SAXBuilder().build(initFile).getRootElement();
				//得到应用节点
				List appList=XPath.selectNodes(rootElement, "/init/applications");
				boolean flag = true;
				for(int a=0;a<appList.size();a++){
					Element appElement=(Element) appList.get(a);
					// 为解决重复数据，如果有code一样的数据则更新，code不一致的数据则新增
					initApplication(appElement);
					createPerRes();
				}
			/*	if(flag){
					return ;
				}*/
				
				//得到数据字典节点
				List dicList=XPath.selectNodes(rootElement, "/init/dic");
				for(int d=0;d<dicList.size();d++){
					Element roleElement=(Element) dicList.get(d);
					initDic(roleElement);
				}
				//得到系统信息节点
				List sysList=XPath.selectNodes(rootElement, "/init/systemArgements");
				for(int e=0;e<sysList.size();e++){
					Element sysElement=(Element) sysList.get(e);
					initSystemArgements(sysElement);
				}
				
				// 初始化组织机构
				List<Element> organizationsList = XPath.selectNodes(rootElement, "/init/organizations");
				String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				for (int f = 0; f < organizationsList.size(); f++) {
					Element organizationElement = organizationsList.get(f);
					
					List<Element> eleList = organizationElement.getChildren();
					for (int ff = 0; ff < eleList.size(); ff++) {
						Element ele = eleList.get(ff);
						Organization organization = new Organization();
						organization.setDf("0");
						organization.setTs(ts);
						organization.setCode(ele.getAttributeValue("code"));
						organization.setName(ele.getAttributeValue("name"));
						organization.setDescription(ele.getAttributeValue("description"));
						organization.setSequence(Long.valueOf(ele.getAttributeValue("sequence")));
						organization.setType("type_dept"); // 默认是部门类型
						Organization organization1 = organizationManager.findByProperty("name", ele.getAttributeValue("name"));
						if(organization1==null){
							organizationManager.save(organization);
						}else{
							BeanUtils.copyProperties(organization, organization1, new String[]{"id"});
							organization=organization1;
						}
						organizationManager.saveOrUpdate(organization);
					}
				}
				
				//得到用户节点
				List userList=XPath.selectNodes(rootElement, "/init/users");
				for(int b=0;b<userList.size();b++){
					Element userElement=(Element) userList.get(b);
					initUser(userElement);
				}
				
				//得到角色节点
				List roleList=XPath.selectNodes(rootElement, "/init/roles");
				for(int c=0;c<roleList.size();c++){
					Element roleElement=(Element) roleList.get(c);
					initRoles(roleElement);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化基础应用
	 * */
	public static void initApplication(Element element){
		String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		List<Element> appType=element.getChildren();
		    // application节点是平行节点所以是循环
			for(int i=0;i<appType.size();i++){
				//创建基础应用 一级菜单
				Element appElement=(Element) appType.get(i);
				Application application = new Application();
				application.setCode(appElement.getAttributeValue("code"));
				application.setName(appElement.getAttributeValue("name"));
				application.setType(appElement.getAttributeValue("type"));
				application.setProperty(appElement.getAttributeValue("property"));
				application.setVersion(appElement.getAttributeValue("version"));
				application.setInitializePage(appElement.getAttributeValue("initialize_page"));
				application.setConfigurationPage(appElement.getAttributeValue("configuration_page"));
				application.setVersion(appElement.getAttributeValue("description"));
				application.setDf("0");
				application.setTs(ts);
				application.setState("APP_STATE001");
				// 根据code查询该数据是否在数据库中存在，如果存在则更新
				Application applica = applicationManager.findByCode(appElement.getAttributeValue("code"));
				if(applica !=null){
					// 赋值
					BeanUtils.copyProperties(application,applica, new String[]{"id"});
					// 改变引用
					application=applica;
				}
				applicationManager.saveOrUpdate(application);
				
				List<Element> list=appElement.getChildren();
				//递归Permission
				initPermission(list,application,"",ts);
			}
	}
	
	private static void initPermission(List<Element> list,Application applica,String parent_code,String ts){
		for(int j=0;j<list.size();j++){
			if(list.get(j).getName().equals("action")){
				Element ele=list.get(j);
				Permission per = new Permission();
				per.setApplication(applica);
				per.setApplicationId(applica.getId());
				per.setCode(ele.getAttributeValue("code"));
				per.setName(ele.getAttributeValue("name"));
				per.setType("2");// 表示是功能
				per.setUrl(ele.getAttributeValue("url"));
				per.setDf("0");
				per.setTs(ts);
				
				Permission result = permissionManager.findByCode(parent_code);
				if(result != null){
					per.setParentId(result.getId());
				}
				if(!"".equals(ele.getAttributeValue("sequence"))){
					per.setSequence(Integer.parseInt(ele.getAttributeValue("sequence")));
				}
				
				Permission currPer = permissionManager.findByCode(per.getCode());
				if(currPer!=null){
					// 赋值
					BeanUtils.copyProperties(per,currPer, new String[]{"id"});
					// 改变引用
					per=currPer;
				}
				permissionManager.saveOrUpdate(per);
			}else if(list.get(j).getName().equals("permission")){
				Element ele=list.get(j);
				Permission per = new Permission();
				per.setApplication(applica);
				per.setApplicationId(applica.getId());
				per.setCode(ele.getAttributeValue("code"));
				per.setName(ele.getAttributeValue("name"));
				per.setType(ele.getAttributeValue("type"));
				per.setUrl(ele.getAttributeValue("url"));
				per.setBigIcon(ele.getAttributeValue("bigIcon"));
				per.setIcon(ele.getAttributeValue("icon"));
				per.setDescription(ele.getAttributeValue("description"));
				per.setDf("0");
				per.setTs(ts);
				
				Permission result = permissionManager.findByCode(parent_code);
				if(result != null){
					per.setParentId(result.getId());
				}
				if(!"".equals(ele.getAttributeValue("sequence"))){
					per.setSequence(Integer.parseInt(ele.getAttributeValue("sequence")));
				}
				
				Permission currPer = permissionManager.findByCode(per.getCode());
				if(currPer!=null){
					// 赋值
					BeanUtils.copyProperties(per,currPer, new String[]{"id"});
					// 改变引用
					per=currPer;
				}
				permissionManager.saveOrUpdate(per);
				
				List<Element> childrenList=ele.getChildren();
				if(childrenList!=null){
					initPermission(childrenList,applica,per.getCode(),ts);
				}
			}
			
			}
	}
	
	/**
	 * 初始化系统参数
	 * */
	public static void initSystemArgements(Element element){
		String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		List<Element> saList=element.getChildren();
		for(int i=0;i<saList.size();i++){
			Element ele=saList.get(i);
			Sysvariable sysvariable = new Sysvariable();
			sysvariable.setDf("0");
			sysvariable.setTs(ts);
			sysvariable.setName(ele.getAttributeValue("name"));
			sysvariable.setCode(ele.getAttributeValue("code"));
			sysvariable.setDiscription(ele.getAttributeValue("description"));
			Sysvariable currSys =  sysvariableManager.findByName(sysvariable.getName());
			if(currSys!=null){
				// 赋值
				BeanUtils.copyProperties(sysvariable,currSys, new String[]{"id"});
				// 改变引用
				sysvariable=currSys;
			}
			sysvariableManager.saveOrUpdate(sysvariable);
		}
	}
	
	/**
	 * 初始化系统用户信息
	 * */
	public static void initUser(Element element){
		String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			List<Element> userList=element.getChildren();
			for(int i=0;i<userList.size();i++){
				Element userElement=userList.get(i);
				User user = new User();
				user.setLoginname(userElement.getAttributeValue("loginname"));
				user.setPassword(userElement.getAttributeValue("password"));
//				user.setPhone(userElement.getAttributeValue("phone"));
				user.setUsername(userElement.getAttributeValue("username"));
				// 设置删除标记
				user.setDf("0");
				// 默认启用
				user.setAbledFlag("0");
				user.setTs(ts);
				User curr = userManager.findAllUserByName(user.getLoginname());
				if(curr!=null){
					// 赋值
					BeanUtils.copyProperties(user,curr, new String[]{"id"});
					users.remove(curr);
					// 改变引用
					user=curr;
				}
				
				userManager.saveOrUpdate(user);
				Organization organization = organizationManager.findByProperty("name", user.getUsername());
				if(organization!=null){
					OrganizationUserRelation our = new OrganizationUserRelation();
					our.setUserId(user.getId());
					our.setOrganizationId(organization.getId());
					organizationUserRelationManager.save(our);
				}
				
				
				
				users.add(user);
			}
	}
	/**
	 * 初始化角色管理
	 * */
	public static void initRoles(Element element){
		String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			List<Element> roles=element.getChildren();
			for(int i=0;i<roles.size();i++){
				Element rol=roles.get(i);
					Role role = new Role();
					String code = rol.getAttributeValue("code");
					//如果是系统管理员 则给添加所有的菜单权限
					if("admin".equals(code)){
							if(role.getResources()!=null&&role.getResources().size()>0){
								continue;
							}
							role.setResources(resources);
							Set<User> us = new HashSet<User>(0);
							User u = userManager.findByProperty("loginname", "admin");
							us.add(u);
							role.setUsers(us);
						
					//如果是分区管理员则添加分区管理员的三个菜单权限：用户管理 组织机构管理 角色管理	
					}else if("partition_admin".equals(code)){
						if(role.getResources()!=null&&role.getResources().size()>0){
							continue;
						}
						Set<Resources> parResources = new HashSet<Resources>(0);
						Resources tempRes = resourcesManager.findByCode("base_ORG_USER");
						if(tempRes!=null){
							parResources.add(tempRes);
						}
						tempRes = resourcesManager.findByCode("base_ORG_ROLE");
						if(tempRes!=null){
							parResources.add(tempRes);
						}
						tempRes = resourcesManager.findByCode("base_ORG_DEPT");
						if(tempRes!=null){
							parResources.add(tempRes);
						}
						role.setResources(parResources);
					}else{
						if(role.getResources()!=null&&role.getResources().size()>0){
							continue;
						}
						String strusers = rol.getAttributeValue("users");
						if("all".equals(strusers)){
							role.setUsers(users);
						}else{
							String[] str=strusers.split("、");
							Set<User> us = new HashSet<User>();
							for(int j=0;j<str.length;j++){
								User user = userManager.findByProperty("username",str[j]);
								if(null!=user){
									us.add(user);
								}else{
									System.out.println(str[i]);
								}
							}
							role.setUsers(us);
						}
					}
					role.setCode(code);
					role.setDescription(rol.getAttributeValue("description"));
					role.setName(rol.getAttributeValue("name"));
					role.setDf("0");
					role.setTs(ts);
					Role currRole = roleManager.findByCode(role.getCode());
					if(currRole!=null){
						BeanUtils.copyProperties(role,currRole, new String[]{"id"});
						// 改变引用
						role=currRole;
					}
					roleManager.saveOrUpdate(role);
			}
	}
	
	/**
	 * 根据导入的应用
	 * */
	public static Resources saveResources(Application application){
		Resources resources=new Resources();
		resources.setBigIcon(application.getBigIcon());
			resources.setCode(application.getCode());
		resources.setDf("0");
		resources.setIcon(application.getIcon());
		resources.setKind("0");
		resources.setName(application.getName());
		resources.setParentId(null);
		resources.setSequence(0);
		resources.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		resources.setType(Resources.TYPE_DIC);//显示菜单
		Resources curr = resourcesManager.findByCode(resources.getCode());
		if(curr!=null){
			BeanUtils.copyProperties(resources,curr, new String[]{"id"});
			// 改变引用
			resources=curr;
		}
		resourcesManager.saveOrUpdate(resources);
		return resources;
	}
	/**
	 * 根据选择的应用访问入口作为对应的菜单添加到相应父菜单下
	 * */
	private static Resources saveResources(String parentId,
			Permission per){
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
		Resources curr = resourcesManager.findByCode(res.getCode());
		if(curr!=null){
			BeanUtils.copyProperties(res,curr, new String[]{"id"});
			// 改变引用
			res=curr;
		}
		resourcesManager.saveOrUpdate(res);
		return res;
	}
	
	/**
	 * 初始化数据字典
	 * */
	public static void initDic(Element element){
		String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		List<Element> dicList=element.getChildren();
		for(int i=0;i<dicList.size();i++){
			Element dic=dicList.get(i);
			//插入数据字典项
			Dictitem dictItem = new Dictitem();
			dictItem.setDf("0");
			dictItem.setTs(ts);
			dictItem.setName(dic.getAttributeValue("name"));
			dictItem.setCode(dic.getAttributeValue("code"));
			dictItem.setEditf(dic.getAttributeValue("editf"));
			dictItem.setDescription(dic.getAttributeValue("description"));
			Dictitem currdi= dictitemManager.findByCode(dictItem.getCode());
			if(currdi!=null){
				BeanUtils.copyProperties(dictItem,currdi, new String[]{"id"});
				// 改变引用
				dictItem=currdi;
			}
			dictitemManager.saveOrUpdate(dictItem);
			//插入数据字典值
			List<Element> dicEle=dic.getChildren();
			for(int j=0;j<dicEle.size();j++){
				Element ele=dicEle.get(j);
				Dictvalue dictValue = new Dictvalue();
				dictValue.setDf("0");
				dictValue.setTs(ts);
				dictValue.setValue(ele.getAttributeValue("name"));
				dictValue.setCode(ele.getAttributeValue("code"));
				dictValue.setDictitemId(dictItem.getId());
				
				// 直接指向字典项
				if(ele.getAttributeValue("relationItem") != null && !ele.getAttributeValue("relationItem").equals("")) {
					dictValue.setRelationItem(ele.getAttributeValue("relationItem"));
				}
				
				Dictvalue currdv=dictvalueManager.findByCodeAndValue(dictValue.getCode(),dictItem.getCode());
				if(currdv!=null){
					BeanUtils.copyProperties(dictValue,currdv, new String[]{"id"});
					// 改变引用
					dictValue=currdv;
				}
				
				dictvalueManager.saveOrUpdate(dictValue);
			}
		}
	}
	
	/**
	 * 把所有应用入口导入成菜单
	 * */
	private static void createPerRes(){
		Resources res = null;
		List<Application> appList = applicationManager.findAll();
		for(Application app : appList){
			res = saveResources(app);
			if(res==null){
				continue; 
			}
			resources.add(res);
			perResMap.put(app.getId(), res.getId());
			List<Permission> perList = permissionManager.findByApp(app.getId());
			for(Permission per : perList){
				//先判断此访问入口是不是有父访问入口 如果有则判断是不是被选中已生成
				String parentId = null;
				if(perResMap.get(per.getParentId())!=null){
					parentId = perResMap.get(per.getParentId());
				//判断此访问入口所属应用是不是被选中已生成	
				}else if(perResMap.get(per.getApplicationId())!=null){
					parentId = perResMap.get(per.getApplicationId());
				}else{
					parentId = null;
				}
				res = saveResources(parentId,per);
				resources.add(res);
				perResMap.put(per.getId(), res.getId());
				//如果此访问入口为主访问入口，则此主访问入口的所有子访问入口也加入菜单中
				if(Permission.TYPE_MENU.equals(per.getType())){
					saveChindResources(res.getId(),per.getId(),per.getApplicationId(),resources);
				}
				
			}
		}
	}
	/**
	 *  如果选择导入的此访问入口为主访问入口，则此主访问入口的所有子访问入口也加入菜单中
	 * */
	public static void saveChindResources(String parentId,String parentPermissionId,String appId,Set<Resources> resources){
		List<Permission> perList = permissionManager.findByParentId(parentPermissionId,appId);
		for(Permission per :perList){
			resources.add(saveResources(parentId, per));
		}	
	}
	/**
	  * 取得项目部署目录
	  * @return
	  */
	public static String getRootPath() {
		  String classpath = PropertyUtil.class.getResource("").getPath();
			String path = classpath.substring(0,classpath.indexOf("WEB-INF/classes"));
			path = path.replace("%20", " ");
			return path;
		}
	public static void fill(){
		applicationManager = (ApplicationManager)Platform.getBean("applicationManager");
		permissionManager = (PermissionManager)Platform.getBean("permissionManager");
		roleManager = (RoleManager)Platform.getBean("roleManager");
		resourcesManager = (ResourcesManager)Platform.getBean("resourcesManager");
		userManager = (UserManager)Platform.getBean("userManager");
		dictitemManager = Platform.getBean("dictitemManager");
		dictvalueManager = Platform.getBean("dictvalueManager");
		sysvariableManager = Platform.getBean("sysvariableManager");
		
		organizationManager = Platform.getBean("organizationManager");
		organizationUserRelationManager = Platform.getBean("organizationUserRelationManager"); 
	}

}
