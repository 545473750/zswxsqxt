package com.opendata.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.organiz.model.Dept;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.Station;
import com.opendata.sys.model.Dictitem;
import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.model.Resources;

/**
 * 构造树型结构XML的工具类
 * @author 付威
 *
 */
public class Common {
	
	/**
	 * 顶层权限菜单
	 * @param list
	 * @return
	 */
	public static List<Resources> getTopMenuList(List<Resources> list) {
		
		List<Resources> menuList = new ArrayList<Resources>();
		String parentId = null;
		for (int i = 0 ;i< list.size();i++) {
			Resources res = list.get(i);
			if ((res.getParentId() == null || res.getParentId().trim().equals("0"))) {
				//list.remove(res);
				//如果菜单对应的访问入口的应用被停用则不显示此菜单
				if(!(res.getPermission()!=null&&"APP_STATE002".equals(res.getPermission().getApplication().getState()))){
					parentId = res.getId();
					res.setResourcess(getChildMenuList(list, parentId));
					menuList.add(res);	
				}
			}
		}
		return menuList;
	}
	/**
	 * 获取基础应用下的菜单
	 * @param list
	 * @return
	 */
	public static List<Resources> getBaseMenuList(List<Resources> list) {
		
		List<Resources> menuList = new ArrayList<Resources>();
		String parentId = null;
		for (int i = 0 ;i< list.size();i++) {
			Resources res = list.get(i);
			if ((res.getParentId() == null || res.getParentId().trim().equals("0"))) {
				if("基础应用".equals(res.getName())){
					//list.remove(res);
					//如果菜单对应的访问入口的应用被停用则不显示此菜单
					if(!(res.getPermission()!=null&&"APP_STATE002".equals(res.getPermission().getApplication().getState()))){
						parentId = res.getId();
						res.setResourcess(getChildMenuList(list, parentId));
						menuList.add(res);	
					}
				}
				
			}
		}
		return menuList;
	}
	
	/**
	 * 非顶层权限菜单
	 * @param list
	 * @param parentId
	 * @return
	 */
	public static List<Resources> getChildMenuList(List<Resources> list, String parentId) {
		List<Resources> childList = new ArrayList<Resources>();
		for (int i = 0 ;i< list.size();i++) {
			Resources res = list.get(i);
			if (parentId.equals(res.getParentId())) {
				
				//如果菜单对应的访问入口的应用被停用则不显示此菜单
				if(!(res.getPermission()!=null&&"APP_STATE002".equals(res.getPermission().getApplication().getState()))){
					if(!Resources.TYPE_FUNCTION.equals(res.getType())){
						res.setResourcess(getChildMenuList(list, res.getId()));
						childList.add(res);
						//list.remove(res);
					}
				}
				
			}
		}
		return childList;
	}
	
	
	
	
	/**
	 * 顶层权限菜单 用来给角色授权
	 * @param list
	 * @return
	 */
	public static List<Resources> getTopMenuListByRole(List<Resources> list) {
		List<Resources> menuList = new ArrayList<Resources>();
		String parentId = null;
		for (Resources res : list) {
			if ((res.getParentId() == null || res.getParentId().trim().equals("0"))) {	
					parentId = res.getId();
					res.setResourcess(getChildMenuListByRole(list, parentId));
					menuList.add(res);
			}
		}
		return menuList;
	}
	
	/**
	 * 非顶层权限菜单  用来给角色授权
	 * @param list
	 * @param parentId
	 * @return
	 */
	public static List<Resources> getChildMenuListByRole(List<Resources> list, String parentId) {
		List<Resources> childList = new ArrayList<Resources>();
		for (Resources res : list) {
			if (parentId.equals(res.getParentId())) {
				
					res.setResourcess(getChildMenuListByRole(list, res.getId()));
					childList.add(res);
			}
		}
		
		return childList;
	}
	
	
	/**
	 * 顶层访问入口
	 * @param list
	 * @return
	 */
	public static Set<Permission> getTopPermissionList(List<Permission> list) {
		Set<Permission> menuList = new HashSet<Permission>();
		String parentId = null;
		for (Permission per : list) {
			if ((per.getParentId() == null || per.getParentId().trim().equals("0"))) {	
					parentId = per.getId();
					per.setPermissions(getChildPermissionList(list, parentId));
					menuList.add(per);
			}
		}
		return menuList;
	}
	
	/**
	 * 非顶层访问
	 * @param list
	 * @param parentId
	 * @return
	 */
	public static Set<Permission> getChildPermissionList(List<Permission> list, String parentId) {
		Set<Permission> childList = new HashSet<Permission>();
		for (Permission per : list) {
			if (parentId.equals(per.getParentId())) {
					per.setPermissions(getChildPermissionList(list, per.getId()));
					childList.add(per);
			}
		}
		
		return childList;
	}

	/**
	 * 获取树型菜单xml 可得到菜单url用于访问
	 * @param list 菜单对象集合
	 * @return
	 */
	public static String getTreeXML(List<Resources> list) {
		Document document = DocumentHelper.createDocument();  
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
		for(Resources res : list){
			Element item = root.addElement("item");  
		    item.addAttribute("text", res.getName());  
		    item.addAttribute("id", res.getId());  
			if(res.getResourcess() != null && res.getResourcess().size()!=0)
			{
				
			    if (res.getIcon() != null) {
			    	String icon = res.getIcon().trim();
			    	if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } else {
			    	item.addAttribute("im0", "folderClosed.gif");  
				    item.addAttribute("im1", "folderOpen.gif");  
				    item.addAttribute("im2", "folderClosed.gif");
			    }
			    
			    getTreeXML(item, res.getResourcess());
			}
			else
			{
			    if (res.getIcon() != null) {
			    	String icon = res.getIcon().trim();
			    	if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } else {
			    	item.addAttribute("im0", "");  
					item.addAttribute("im1", "");  
					item.addAttribute("im2", "");
			    }
					
			    
			    if(res.getPermission()!=null){
			    	Element userdata = item.addElement("userdata");
			    	//记录URL访问地址
				    userdata.addAttribute("name", "url");
			    	userdata.setText(res.getPermission().getUrl());
			    	//记录是否弹出窗口标识
			    	Element userdata_kind = item.addElement("userdata");
			    	userdata_kind.addAttribute("name", "kind");
			    }
			}
			if (res.getChecked().equals("1")) {
		    	item.addAttribute("checked", "0");
		    }
		}
		return document.asXML();
	}
	
	/**
	 * 获取树型菜单xml 递归取得所有子菜单
	 * @param itemparent
	 * @param list
	 */
	public static void getTreeXML(Element itemparent, List<Resources> list) {
		for(Resources res : list){
			if(res.getResourcess() !=null && res.getResourcess().size()!=0) {
				Element item = itemparent.addElement("item");  
			    item.addAttribute("text", res.getName());  
			    item.addAttribute("id", res.getId());
			    if (res.getIcon() != null) {
			    	String icon = res.getIcon().trim();
			    	if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } else {
			    	item.addAttribute("im0", "folderClosed.gif");  
				    item.addAttribute("im1", "folderOpen.gif");  
				    item.addAttribute("im2", "folderClosed.gif");
			    }
			    if (res.getChecked().equals("1")) {
					item.addAttribute("checked", res.getChecked());
				}
			    getTreeXML(item, res.getResourcess());
			}
			else
			{
				Element item = itemparent.addElement("item");  
			    item.addAttribute("text", res.getName());  
			    item.addAttribute("id", res.getId());
			    if (res.getIcon() != null) {
			    	String icon = res.getIcon().trim();
			    	if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0",icon );  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } else {
			    	 item.addAttribute("im0", "");  
					 item.addAttribute("im1", "");  
					 item.addAttribute("im2", "");
			    }
			    if (res.getChecked().equals("1")) {
					item.addAttribute("checked", res.getChecked());
				}
			    
			    
			    if(res.getPermission()!=null){
			    	Element userdata = item.addElement("userdata");
			    	//记录URL访问地址
				    userdata.addAttribute("name", "url");
			    	userdata.setText(res.getPermission().getUrl());
			    	//记录是否弹出窗口标识
			    	Element userdata_kind = item.addElement("userdata");
			    	userdata_kind.addAttribute("name", "kind");
			    	userdata_kind.setText(res.getKind());
			    	
			    }
			    
			} 
		}
	}
	
	/**
	 * 生成菜单管理的树型XML
	 */
	public static String getResourcesTreeXML(List<Resources> resList) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
	    Element root_item = root.addElement("item");
	    root_item.addAttribute("text", "菜单管理");
	    root_item.addAttribute("id", "root");
	    
	    for(Resources res : resList) {
	    	// 处理顶层的应用访问入口
	    	if(res.getResources() == null) {
	    		Element item = root_item.addElement("item");
	    		item.addAttribute("text", res.getName());
	    		item.addAttribute("id", res.getId());
	    		if (res.getIcon() != null) {
	    			String icon = res.getIcon().trim();
	    			if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } 
	    		
	    		// 处理子应用访问入口
	    		getResourcesTreeXML(res.getResourcess(), item);
	    	}
	    }

		return document.asXML();
	}
	/**
	 * 生成菜单管理的树型xml 递归取得所有的子菜单组成树型
	 * @param ress 
	 * @param e
	 */
	public static void getResourcesTreeXML(List<Resources> ress, Element e) {
		for(Resources res : ress) {
			//显示未被删除的应用访问入口
			if("0".equals(res.getDf())){
				Element item = e.addElement("item");
				item.addAttribute("text", res.getName());
				item.addAttribute("id", res.getId());
				if (res.getIcon() != null) {
					String icon = res.getIcon().trim();
					if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } 
				
				if(res.getResourcess() != null && res.getResourcess().size() > 0) {
					getResourcesTreeXML(res.getResourcess(), item);
				}
			}
		}
	}
	
	/**
	 * 生成分区管理的组织机构XML树型 传过来的是此分区有权限的父组织机构集合
	 */
	public static String getOrganizByPartitionTreeXML(Set<Organization> organizations){
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
	  
	    for(Organization dept : organizations) {
	    		Element item = root.addElement("item");
	    		item.addAttribute("text", dept.getName());
	    		item.addAttribute("id", dept.getId());
	    		
	    		// 处理子组织机构
	    		getDeptTreeXML(dept.getOrganizations(), item);
	    }

		return document.asXML();
	}
	
	public static String getDeptTreeXML(List<Organization> depts) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
	    Element root_item = root.addElement("item");
	    root_item.addAttribute("text", "单位管理");
	    root_item.addAttribute("id", "root");
	    
	    for(Organization dept : depts) {
	    	// 处理顶层的组织机构
	    	if(dept.getOrganization() == null) {
	    		Element item = root_item.addElement("item");
	    		item.addAttribute("text", dept.getName());
	    		item.addAttribute("id", dept.getId());
	    		
	    		// 处理子组织机构
	    		getDeptTreeXML(dept.getOrganizations(), item);
	    	}
	    }

		return document.asXML();
	}
	
	/**
	 * 通过递归取得所有的子组织机构 组成树型结构
	 * @param depts
	 * @param e
	 */
	public static void getDeptTreeXML(Set<Organization> depts, Element e) {
		for(Organization dept : depts) {
			// 过滤掉删除的数据
			if(dept.getDf().equals("0")) {
				Element item = e.addElement("item");
				item.addAttribute("text", dept.getName());
				item.addAttribute("id", dept.getId());
				
				if(dept.getOrganizations() != null && dept.getOrganizations().size() > 0) {
					getDeptTreeXML(dept.getOrganizations(), item);
				}
			}
		}
	}

	/**
	 * 生成组织机构管理的树型xml
	 *//*
	public static String getDeptTreeXML(List<Organization> depts) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
//	    Element root_item = root.addElement("item");
//	    root_item.addAttribute("text", "组织机构");
//	    root_item.addAttribute("id", "root");
	    
	    for(Organization dept : depts) {
	    	// 处理顶层的组织机构
	    	if(dept.getOrganization() != null) {
	    		Element item = root.addElement("item");
	    		item.addAttribute("text", dept.getName());
	    		item.addAttribute("id", dept.getId());
	    		
	    		// 处理子组织机构
	    		getDeptTreeXML(dept.getOrganizations(), item);
	    	}else{
	    		Element item = root.addElement("item");
	    		item.addAttribute("text", dept.getName());
	    		item.addAttribute("id", dept.getId());
	    	}
	    }

		return document.asXML();
	}
	
	*//**
	 * 通过递归取得所有的子部门 组成树型结构
	 * @param depts
	 * @param e
	 *//*
	public static void getDeptTreeXML(Set<Organization> depts, Element e) {
		for(Organization dept : depts) {
			// 过滤掉删除的数据
			if(dept.getDf().equals("0")) {
				Element item = e.addElement("item");
				item.addAttribute("text", dept.getName());
				item.addAttribute("id", dept.getId());
				
				if(dept.getOrganizations() != null && dept.getOrganizations().size() > 0) {
					getDeptTreeXML(dept.getOrganizations(), item);
				}
			}
		}
	}
	*/
	
	
	/**
	 * 生成部门树
	 */
	public static String getDeptTree(List<Organization> orgs ) {
		Document document = DocumentHelper.createDocument(); 
		
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    root.addAttribute("text","单位部门");
	    if(orgs.size()>0){
	    	Element root_item = root.addElement("item");
	    	for(Organization org:orgs){
    			root_item.addAttribute("text", org.getName());
	    	    root_item.addAttribute("id", org.getId());
	    	    Set<Dept> depts = org.getDepts();
	    	    for(Dept dept : depts) {
	    	    	// 处理顶层的组织机构
	    	    	if(dept.getDept() == null) {
	    	    		Element item = root_item.addElement("item");
	    	    		item.addAttribute("text", dept.getName());
	    	    		item.addAttribute("id", dept.getId());
	    	    		// 处理子组织机构
	    	    		getDeptTree(dept.getDepts(), item);
	    	    	}
	    	    }
		    	    
	    	}
	    		
	    }
	  
	    
	    
	    
	    
	    
	    

		return document.asXML();
	}
	
	/**
	 * 通过递归取得所有的子部门 组成树型结构
	 * @param depts
	 * @param e
	 */
	public static void getDeptTree(Set<Dept> depts, Element e) {
		for(Dept dept : depts) {
				// 过滤掉删除的数据
				Element item = e.addElement("item");
				item.addAttribute("text", dept.getName());
				item.addAttribute("id", dept.getId());
				if(dept.getDepts() != null && dept.getDepts().size() > 0) {
					getDeptTree(dept.getDepts(), item);
				}
		}
	}
	
	
	
	/**
	 * 获取生成应用访问入口的树型xml
	 * @param pers 某应用下的应用访问入口对象集合
	 * @param appName 应用名称
	 */
	public static String getPermissionTreeXML(Set<Permission> pers,String appName) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
	    Element root_item = root.addElement("item");
	    root_item.addAttribute("text", appName);
	    root_item.addAttribute("id", "root");
	    
	    for(Permission per : pers) {
	    	// 处理顶层的应用访问入口
	    	if(per.getParentPermission() == null) {
	    		Element item = root_item.addElement("item");
	    		//如果是主访问入口且有子访问入口时 主访问入口名称后加子访问入口数量
				if(Permission.TYPE_MENU.equals(per.getType())&&per.getPermissions().size()>0){
					item.addAttribute("text", per.getName()+"("+per.getPermissions().size()+")");
				}else{
					item.addAttribute("text", per.getName());
				}
	    		item.addAttribute("id", per.getId());
	    		if (per.getIcon() != null) {
	    			String icon = per.getIcon().trim();
	    			if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } 
	    		
	    		// 处理子应用访问入口
	    		getPermissionTreeXML(per.getPermissions(), item);
	    	}
	    }

		return document.asXML();
	}
	
	
	/**
	 * 获取生成所有应用的访问入口树型xml
	 * @param apps 应用集合对象
	 */
	public static String getApplicationTreeXML(List<Application> apps) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    for(Application app : apps){
		    Element root_item = root.addElement("item");
		    root_item.addAttribute("text", app.getName());
		    root_item.addAttribute("id", app.getId());
		    
		    for(Permission per : app.getPermissions()) {
		    	// 处理顶层的应用访问入口
		    	if(per.getParentPermission() == null) {
		    		Element item = root_item.addElement("item");
		    		item.addAttribute("text", per.getName());
		    		item.addAttribute("id", per.getId());
		    		if (per.getIcon() != null) {
		    			String icon = per.getIcon().trim();
		    			if(icon.indexOf("images/tree/")!=-1){
							icon = icon.replace("images/tree/", "");
						}
					    item.addAttribute("im0", icon);  
					    item.addAttribute("im1", icon);  
					    item.addAttribute("im2", icon);
				    } 
		    		
		    		// 处理子应用访问入口
		    		getPermissionByAppTreeXML(per.getPermissions(), item);
		    	}
		    }
	    }

		return document.asXML();
	}
	
	/**
	 * 通过递归取得所有子访问入口 组建成树型结构
	 * @param pers 子访问入口集合
	 * @param e 父访问入口Element
	 */
	public static void getPermissionByAppTreeXML(Set<Permission> pers, Element e) {
		for(Permission per : pers) {
			//显示未被删除的应用访问入口 如果是子访问入口则不显示
			if(!Permission.TYPE_FUNCTION.equals(per.getType())){
				Element item = e.addElement("item");
				item.addAttribute("text", per.getName());
				item.addAttribute("id", per.getId());
				if (per.getIcon() != null) {
					String icon = per.getIcon().trim();
					if(icon.indexOf("images/tree/")!=-1){
						icon = icon.replace("images/tree/", "");
					}
			    	
				    item.addAttribute("im0", icon);  
				    item.addAttribute("im1", icon);  
				    item.addAttribute("im2", icon);
			    } 
				
				if(per.getPermissions() != null && per.getPermissions().size() > 0) {
					getPermissionByAppTreeXML(per.getPermissions(), item);
				}
			}
		}
	}
	
	/**
	 * 通过递归取得所有子访问入口 组建成树型结构
	 * @param pers  子访问入口集合
	 * @param e 父访问入口Element
	 */
	public static void getPermissionTreeXML(Set<Permission> pers, Element e) {
		for(Permission per : pers) {
			//子访问入口不显示在树型结构上
			if(!Permission.TYPE_FUNCTION.equals(per.getType())){
				Element item = e.addElement("item");
					//如果是主访问入口且有子访问入口时 主访问入口名称后加子访问入口数量
					if(Permission.TYPE_MENU.equals(per.getType())&&per.getPermissions().size()>0){
						item.addAttribute("text", per.getName()+"("+per.getPermissions().size()+")");
					}else{
						item.addAttribute("text", per.getName());
					}
					
					item.addAttribute("id", per.getId());
					if (per.getIcon() != null) {
						String icon = per.getIcon().trim();
						if(icon.indexOf("images/tree/")!=-1){
							icon = icon.replace("images/tree/", "");
						}
				    	
					    item.addAttribute("im0", icon);  
					    item.addAttribute("im1", icon);  
					    item.addAttribute("im2", icon);
				    } 
					
					if(per.getPermissions() != null && per.getPermissions().size() > 0) {
						getPermissionTreeXML(per.getPermissions(), item);
					}
			}
		}
	}
	
	/**
	 * 获取字典值树型xml
	 */
	public static String getDictValueXML(List<Dictvalue> dictValues, Dictitem dictItem) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
	    Element rootDictItem = root.addElement("item");
	    rootDictItem.addAttribute("id", dictItem.getId());
	    rootDictItem.addAttribute("text", dictItem.getName());
	    // 字典项
	    Element userdata = rootDictItem.addElement("userdata");
	    userdata.addAttribute("name", "type");
	    userdata.setText("dictitem");
	    // 是否可编辑
	    Element userdata_edit = rootDictItem.addElement("userdata");
	    userdata_edit.addAttribute("name", "edit");
	    userdata_edit.setText(dictItem.getEditf());
	    
		for(Dictvalue dv : dictValues) {
			// 顶层的字典值
//			if(dv.getDictitem() == null) {
			if(dv.getParentId() == null || dv.getParentId().equals("")) {
				Element item = rootDictItem.addElement("item");
				item.addAttribute("text", dv.getValue());
				item.addAttribute("id", dv.getId());
				// 字典值
				Element userdata2 = item.addElement("userdata");
				userdata2.addAttribute("name", "type");
				userdata2.setText("dictvalue");
				// 是否可编辑
				Element userdata2_edit = item.addElement("userdata");
				userdata2_edit.addAttribute("name", "edit");
				userdata2_edit.setText(dictItem.getEditf());
				
				// 处理子值
				getDictValueXML(dv.getDictvalues(), item, dictItem);
			}
		}
		return document.asXML();
	}
	/**
	 * 递归取得所有子字典值 组建成树型结构
	 * @param dictValues
	 * @param e
	 * @param dictItem
	 */
	public static void getDictValueXML(Set<Dictvalue> dictValues, Element e, Dictitem dictItem) {
		for(Dictvalue dv : dictValues) {
			if(dv.getDf().equals("0")) {
				Element item = e.addElement("item");
				item.addAttribute("text", dv.getValue());
				item.addAttribute("id", dv.getId());
				// 字典值
				Element userdata = item.addElement("userdata");
				userdata.addAttribute("name", "type");
				userdata.setText("dictvalue");
				// 是否可编辑
				Element userdata_edit = item.addElement("userdata");
				userdata_edit.addAttribute("name", "edit");
				userdata_edit.setText(dictItem.getEditf());
				
				// 递归调用自己
				if(dv.getDictvalues() != null && dv.getDictvalues().size() > 0) {
					getDictValueXML(dv.getDictvalues(), item, dictItem);
				}
			}
		}
	}

	/**
	 * 获取组织机构树的XML格式
	 * @param organizations
	 * @return
	 */
	public static String getOrganizationTreeXml(List<Organization> organizations, String selectedIds, String filterNodeId) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    
	    Element toproot = document.addElement("tree");  
	    toproot.addAttribute("id", "0");
	    
	    
	    Element root = toproot.addElement("item");
	    root.addAttribute("id", "item");
	    root.addAttribute("text", "根单位");

	    for(Organization organization : organizations) {
	    	// 处理顶层组织机构
	    	//if(organization.getOrganization() == null) {
	    	if(organization.getDf().equals("0")) {
	    		// 过滤掉部分组织机构
	    		if(filterNodeId != null) {
	    			if(!organization.getId().equals(filterNodeId)) {
	    				Element topE = root.addElement("item");
	    	    		topE.addAttribute("id", organization.getId());
	    	    		topE.addAttribute("text", replaceNullString(organization.getName()));
	    	    		
	    	    		Element nodeType = topE.addElement("userdata");
	    	    		nodeType.addAttribute("name", "type");
	    	    		nodeType.setText(replaceNullString(organization.getType()));
	    	    		
	    	    		// 判断是否已经被选中
	    	    		if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
	    	    			topE.addAttribute("checked", "1");
	    	    		}
	    	    		// 处理子组织机构
	    	    		getOrganizationTreeXml(organization.getOrganizations(), topE, selectedIds, filterNodeId);
	    			}
	    		} else {
	    			Element topE = root.addElement("item");
    	    		topE.addAttribute("id", organization.getId());
    	    		topE.addAttribute("text", replaceNullString(organization.getName()));
    	    		
    	    		Element nodeType = topE.addElement("userdata");
    	    		nodeType.addAttribute("name", "type");
    	    		nodeType.setText(replaceNullString(organization.getType()));
    	    		
    	    		// 判断是否已经被选中
    	    		if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
    	    			topE.addAttribute("checked", "1");
    	    		}
    	    		// 处理子组织机构
    	    		getOrganizationTreeXml(organization.getOrganizations(), topE, selectedIds, filterNodeId);
	    		}
	    	}
	    	//}
	    }
		return document.asXML();
	}
	
	/**
	 * 递归取得所有子的组织机构 组建成树型结构
	 * @param organizations
	 * @param e
	 * @param selectedIds
	 * @param filterNodeId
	 */
	public static void getOrganizationTreeXml(Set<Organization> organizations, Element e, String selectedIds, String filterNodeId) {
		for(Organization organization : organizations) {
			if(organization.getDf().equals("0")) {
				// 过滤掉部分组织机构
	    		if(filterNodeId != null) {
	    			if(!organization.getId().equals(filterNodeId)) {
	    				Element childE = e.addElement("item");
	    				childE.addAttribute("id", organization.getId());
	    				childE.addAttribute("text", replaceNullString(organization.getName()));
	    				
//	    				Element nodeType = childE.addElement("userdata");
//	    	    		nodeType.addAttribute("name", "type");
//	    	    		nodeType.setText(organization.getType());
	    				
	    				if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
	    					childE.addAttribute("checked", "1");
	    				}
	    				
	    				if(organization.getOrganizations() != null) {
	    					// 递归调用自己
	    					getOrganizationTreeXml(organization.getOrganizations(), childE, selectedIds, filterNodeId);
	    				}
	    			}
	    		} else {
	    			Element childE = e.addElement("item");
					childE.addAttribute("id", organization.getId());
					childE.addAttribute("text", organization.getName());
					
					/*Element nodeType = childE.addElement("userdata");
    	    		nodeType.addAttribute("name", "type");
    	    		nodeType.setText(organization.getType());*/
					
					if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
						childE.addAttribute("checked", "1");
					}
					
					if(organization.getOrganizations() != null) {
						// 递归调用自己
						getOrganizationTreeXml(organization.getOrganizations(), childE, selectedIds, filterNodeId);
					}
	    		}
			}
		}
	}
	
	
	
	/**
	 * 获取分区
	 * @param organizations
	 * @return
	 */
	public static String getOrganizationForFq(List<Organization> organizations, String selectedIds, String filterNodeId) {
		Document document = DocumentHelper.createDocument(); 
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    
	    Element e = root.addElement("item");
	    e.addAttribute("id", "0");
	    e.addAttribute("text", "所有单位");

	    for(Organization organization : organizations) {
	    	// 处理顶层组织机构
	    	if(organization.getOrganization() == null) {
	    	if(organization.getDf().equals("0")) {
	    		// 过滤掉部分组织机构
	    		if(filterNodeId != null) {
	    			if(!organization.getId().equals(filterNodeId)) {
	    				Element topE = e.addElement("item");
	    	    		topE.addAttribute("id", organization.getId());
	    	    		topE.addAttribute("text", organization.getName());
	    	    		
	    	    		/*Element nodeType = topE.addElement("userdata");
	    	    		nodeType.addAttribute("name", "type");
	    	    		nodeType.setText(organization.getType());*/
	    	    		
	    	    		// 判断是否已经被选中
	    	    		if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
	    	    			topE.addAttribute("checked", "1");
	    	    		}
	    	    		// 处理子组织机构
//	    	    		getOrganizationForFq(organization.getOrganizations(), topE, selectedIds, filterNodeId);
	    			}
	    		} else {
	    			Element topE = e.addElement("item");
    	    		topE.addAttribute("id", organization.getId());
    	    		topE.addAttribute("text", organization.getName());
    	    		
    	    		/*Element nodeType = topE.addElement("userdata");
    	    		nodeType.addAttribute("name", "type");
    	    		nodeType.setText(organization.getType());*/
    	    		
    	    		// 判断是否已经被选中
    	    		if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
    	    			topE.addAttribute("checked", "1");
    	    		}
    	    		// 处理子组织机构
//    	    		getOrganizationForFq(organization.getOrganizations(), topE, selectedIds, filterNodeId);
	    		}
	    	}
	    	}
	    }
		return document.asXML();
	}
	/**
	 * 递归取得所有子的组织机构 分区
	 * @param organizations
	 * @param e
	 * @param selectedIds
	 * @param filterNodeId
	 */
	public static void getOrganizationForFq(Set<Organization> organizations, Element e, String selectedIds, String filterNodeId) {
		for(Organization organization : organizations) {
			if(organization.getDf().equals("0")) {
				// 过滤掉部分组织机构
	    		if(filterNodeId != null) {
	    			if(!organization.getId().equals(filterNodeId)) {
	    				Element childE = e.addElement("item");
	    				childE.addAttribute("id", organization.getId());
	    				childE.addAttribute("text", organization.getName());
	    				
//	    				Element nodeType = childE.addElement("userdata");
//	    	    		nodeType.addAttribute("name", "type");
//	    	    		nodeType.setText(organization.getType());
	    				
	    				if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
	    					childE.addAttribute("checked", "1");
	    				}
	    				
	    				if(organization.getOrganizations() != null) {
	    					// 递归调用自己
	    					getOrganizationTreeXml(organization.getOrganizations(), childE, selectedIds, filterNodeId);
	    				}
	    			}
	    		} else {
	    			Element childE = e.addElement("item");
					childE.addAttribute("id", organization.getId());
					childE.addAttribute("text", organization.getName());
					
					/*Element nodeType = childE.addElement("userdata");
    	    		nodeType.addAttribute("name", "type");
    	    		nodeType.setText(organization.getType());*/
					
					if(!"".equals(selectedIds) && selectedIds.indexOf(organization.getId()) != -1) {
						childE.addAttribute("checked", "1");
					}
					
					if(organization.getOrganizations() != null) {
						// 递归调用自己
						getOrganizationTreeXml(organization.getOrganizations(), childE, selectedIds, filterNodeId);
					}
	    		}
			}
		}
	}
	
	public static String replaceNullString(String str){
		  if(str == null ) return "";
		           else return str;
	}
}
