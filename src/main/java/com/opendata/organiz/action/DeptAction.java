/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Common;
import com.opendata.common.util.DateUtil;
import com.opendata.organiz.model.Dept;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.service.DeptManager;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.StationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.DeptQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 组织机构action,用于处理组织机构请求操作
 * 
 * @author 顾保臣
 */
@SuppressWarnings({ "serial", "rawtypes" })
@Namespace("/organiz")
@Results({
@Result(name="moveDialogclose", location="/WEB-INF/pages/organiz/Dept/moveDialogClose.jsp", type="dispatcher"),
@Result(name="list",location="/WEB-INF/pages/organiz/Dept/list.jsp", type = "dispatcher"),

@Result(name="query",location="/WEB-INF/pages/organiz/Dept/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/organiz/Dept/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/organiz/Dept/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/organiz/Dept/show.jsp", type = "dispatcher"),
@Result(name="move", location="/WEB-INF/pages/organiz/Dept/move.jsp", type="dispatcher"),

@Result(name="main",location="/WEB-INF/pages/organiz/Dept/main.jsp", type = "dispatcher"),
@Result(name="list_partition",location="/WEB-INF/pages/organiz/Dept/list_partition.jsp", type = "dispatcher"),
@Result(name="leftTree",location="/WEB-INF/pages/organiz/Dept/leftTree.jsp", type = "dispatcher"),
@Result(name="rightpage",location="/WEB-INF/pages/organiz/Dept/rightPage.jsp", type = "dispatcher"),
@Result(name="loaduser",location="/WEB-INF/pages/organiz/Dept/rightPage_user.jsp", type = "dispatcher"),
@Result(name="loadjob",location="/WEB-INF/pages/organiz/Dept/rightPage_job.jsp", type = "dispatcher"),
@Result(name="loaddept",location="/WEB-INF/pages/organiz/Dept/rightPage.jsp", type = "dispatcher"),

@Result(name="choiceOrganization", location="/WEB-INF/pages/organiz/User/organizationTree.jsp", type="dispatcher"),
@Result(name="rightPage_user_list_choice.jsp", location="/WEB-INF/pages/organiz/organization/rightPage_user_list_choice.jsp", type="dispatcher"),

@Result(name="loadDefaultStation", location="/WEB-INF/pages/organiz/Dept/rightPage_job_list.jsp", type="dispatcher"),
@Result(name="loadUserAction", location="..//organiz/Dept!loadUser.do?parentid=${parentOrganizationId}&partitionId=${partitionId}", type="redirectAction"),
@Result(name="configUserAndOrganization", location="/WEB-INF/pages/organiz/Dept/dialogclose.jsp", type="dispatcher"),
@Result(name="loadDefaultUser", location="/WEB-INF/pages/organiz/Dept/rightPage_user_list.jsp", type="dispatcher"),
@Result(name="listAction",location="..//organiz/Dept!list.do?partitionId=${partitionId}", type = "redirectAction"),
@Result(name="delete_torightpage", location="..//organiz/Dept!toRightPage.do?${params}&flag=refresh", type="redirectAction"),
@Result(name="update_torightpage", location="..//organiz/Dept!toRightPage.do?${params}&flag=refresh", type="redirectAction"),
@Result(name="torightpageAction", location="..//organiz/Dept!toRightPage.do?${params}&flag=refresh", type="redirectAction")
})
public class DeptAction extends BaseStruts2Action implements Preparable,ModelDriven{
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
	protected static final String LIST_PARTITION_JSP = "list_partition";
	protected static final String MAIN = "main";
	protected static final String LEFTTREE = "leftTree";
	protected static final String RIGHTPAGE = "rightpage";
	
	
	private OrganizationManager organizationManager;
	private DeptManager deptManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private StationManager stationManager;
	@Autowired
	private DicUtil dicUtil;
	private Dept dept;
	private String partitionId;//分区ID
	
	DeptQuery query = new DeptQuery();
	java.lang.String id = null;
	private String[] items;
	

	public void prepare() throws Exception {
		
		id=getRequest().getParameter("id");
		this.saveParameters();
		if (isNullOrEmptyString(id)) {
			dept = new Dept();
		} else {
			dept = (Dept)deptManager.getById(id);
		}
	}
	
	public Object getModel() {
		return dept;
	}
	

	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {
		query.setSortColumns(" ts ");
		query.setDf("0");
		Page page = deptManager.findPage(query);
		savePage(page,query);
		getRequest().setAttribute("flag", "refresh");
		return RIGHTPAGE;
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		return SHOW_JSP;
	}
	
	
	/**
	 * 进入新增页面
	 * @return
	 */
	public String create() {
		return CREATE_JSP;
	}
	/**
	 * 获取父级部门
	 * @return
	 */
	public Dept getParent(){
		String parentId = query.getParentId();
		if(StringUtils.isNotBlank(parentId)){
			Dept parent = this.deptManager.getById(parentId);
			return parent;
		}
		return null;
	}
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
//		dept.setDf("0");
//		dept.setSequence(1l);
//		dept.setScope("1");
//		dept.setCode("001");
		dept.setTs(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		if("".equals(dept.getParentId())){
			dept.setParentId(null);
		}
		deptManager.save(dept);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		
		this.flag="refresh";
		// 回到列表页面
		return "torightpageAction";
	}
	
	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
		
		return EDIT_JSP;
	}
	

	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		if("".equals(dept.getParentId())){
			dept.setParentId(null);
		}
		deptManager.update(dept);
		Flash.current().success(this.UPDATE_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		
		return "torightpageAction";
	}
	
	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		int flag = 0;
		for(int i = 0; i < items.length; i++)
		 {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");
			this.deptManager.removeById(id);
		}
//		this.deptManager.delete(dept);
		Flash.current().success(DELETE_SUCCESS);
		if(flag != 0) {
			Flash.current().success("当前删除数据包含子组织机构，请先删除子组织机构！");
		} 
		
		this.flag="refresh";
		return "delete_torightpage";
	}

	
	
	/**
	 * 跳转到新的页面
	 * @return
	 */
	public String main() {
		getCurrentUnitId();
		return MAIN;
	}
	
	/**
	 *  跳转到左侧组织结构
	 * @return
	 */
	public String leftTree() {
		getCurrentUnitId();
		return LEFTTREE;
	} 
	
	/**
	 *  根据左侧组织机构的id跳转到右侧页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toRightPage() {
		getCurrentUnitId();
		Page page = this.deptManager.findPage(query);
		
		this.saveCurrentPage(page, query);
		return RIGHTPAGE;
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
	
	
	private String argArray;
	
	
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
	
	private String selectedIds;

	
	
	/**
	 * 获取组织机构树的XML格式
	 * @return
	 */
	public String getOrganizationTreeXml() {
		try {
			List<Organization> organizations = null;
			
			organizations = this.organizationManager.findTopByDf(); 
			
			String xmlString = Common.getOrganizationTreeXml(organizations, this.selectedIds, null);
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(xmlString);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public String getOrganizationTreeXmlWithRoot() {
		try {
//			List<Organization> organizations = this.organizationManager.findAllByDf();
			List<Organization> organizations = null;
			if (partitionId != null && !"".equals(partitionId)) {
				organizations = organizationManager.findAllByDf("partitions", "id", partitionId);
			}else{
				organizations = this.organizationManager.findTopByDf();
			}	
			
			String xmlString = Common.getOrganizationTreeXml(organizations, this.selectedIds, this.id);
			
			String tmp = xmlString.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");
			StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			tmp = tmp.replaceAll("<tree id=\"0\">", "");
			sb.append("<tree id=\"0\">");
			tmp = tmp.replaceAll("</tree>", "");
			if(this.selectedIds != null && this.selectedIds.equals("root")) {
				sb.append("<item id=\"root\" text=\"组织机构\" checked=\"1\">");
			} else {
				sb.append("<item id=\"root\" text=\"组织机构\">");
			}
			sb.append(tmp);
			sb.append("</item></tree>");
			
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(sb.toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String getDept(){
		try{
			String userId = this.getRequest().getParameter("tId");
			List<Organization> Organizations = this.organizationManager.findByUserId(userId);
			
			JsonConfig cfg = new JsonConfig();
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					if (name.equals("id") || name.equals("name")) {
						return false;
					} else {
						return true;
					}
				}
			});
			String ss = JSONArray.fromObject(Organizations, cfg).toString();
			StringBuffer returnstr = new StringBuffer("");
			returnstr.append(ss);
			super.renderHtmlUTF(returnstr.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return null ;
	}
	/**
	 * 业务范围
	 * @return
	 */
	public Map<String,String> getYwfwMap(){
		Map<String,String> map = this.dicUtil.getLinkedMap("ywfw");
		return map;
	}
	
	private String flag;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setOrganizationManager(OrganizationManager manager) {
		this.organizationManager = manager;
	}	
	
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public DeptQuery getQuery() {
		return query;
	}

	public void setQuery(DeptQuery query) {
		this.query = query;
	}

	public java.lang.String getId() {
		return id;
	}

	public String[] getItems() {
		return items;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public DeptManager getDeptManager() {
		return deptManager;
	}

	public void setDeptManager(DeptManager deptManager) {
		this.deptManager = deptManager;
	}

	
}
