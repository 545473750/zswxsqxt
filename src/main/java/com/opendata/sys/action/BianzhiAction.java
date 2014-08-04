package com.opendata.sys.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Common;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.service.DeptManager;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.RoleManager;
import com.opendata.sys.model.Bianzhi;
import com.opendata.sys.service.BianzhiManager;
import com.opendata.sys.vo.query.BianzhiQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * describe:系统编制管理
 * 
 *
 */
@Namespace("/sys")
@Results({
@Result(name="list",location="/WEB-INF/pages/sys/bianzhi/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/sys/bianzhi/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/bianzhi/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/sys/bianzhi/view.jsp", type = "dispatcher"),
@Result(name="main",location="/WEB-INF/pages/sys/bianzhi/main.jsp", type = "dispatcher"),
@Result(name="leftTree",location="/WEB-INF/pages/sys/bianzhi/leftTree.jsp", type = "dispatcher"),
@Result(name="listAction",location="../sys/Bianzhi!list.do?${params}", type = "redirectAction")
})
public class BianzhiAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	@Autowired
	private BianzhiManager bianzhiManager;
	@Autowired
	private DeptManager deptManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private Bianzhi bianzhi;
	private BianzhiQuery query=new BianzhiQuery();
	private RoleManager roleManager;//角色处理
	private DicUtil dicUtil;//字典表
	private List<Organization> orgs;//部门集合
	private OrganizationManager organizationManager;//部门管理
	private List<Role> roles;
	
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.bianzhi= new Bianzhi();
		} else {
			this.bianzhi = (Bianzhi)this.bianzhiManager.getById(id);
		}
	}
		
	public BianzhiManager getBianzhiManager() {
		return this.bianzhiManager;
	}

	public void setBianzhiManager(BianzhiManager bianzhiManager) {
		this.bianzhiManager = bianzhiManager;
	}
	
	public Object getModel() {
		return this.bianzhi;
	}
	
	public BianzhiQuery getQuery() {
		return query;
	}

	public void setQuery(BianzhiQuery query) {
		this.query = query;
	}
	
	public void init(){
		Map<String,String> map = dicUtil.getLinkedMap("ywfw");//得到业务范围集合
		this.getRequest().setAttribute("ywfw", map);
//		orgs = this.organizationManager.findAllBy("parent_id", this.getCurrentUnitId());  //部门列表
		roles = this.roleManager.findAllByDf();    //角色列表
		Map<String, String> xkMap = dicUtil.getLinkedMap("xk");//得到学科集合
		this.getRequest().setAttribute("xk", xkMap);
		Map<String, String> xdMap = dicUtil.getLinkedMap("xd");//得到学段集合
		this.getRequest().setAttribute("xd", xdMap);
//		Map<String, String> zjsjMap = dicUtil.getLinkedMap("zjsj");//得到直接上级集合
//		this.getRequest().setAttribute("zjsj",zjsjMap);
		Organization organization = this.organizationManager.getById(query.getOrgId());
		this.getRequest().setAttribute("organization",organization);
		//获取直接上级。
		String orgId = this.query.getOrgId();
		if(StringUtils.isNotBlank(orgId)){
			String orgParentId = organization.getParentId();
			List<Bianzhi> list = new ArrayList<Bianzhi>();
			List<Bianzhi> l1 = this.bianzhiManager.findAllBy("orgId", orgId);
			list.addAll(l1);
			if(StringUtils.isNotBlank(orgParentId)){
				List<Bianzhi> l2 = this.bianzhiManager.findAllBy("orgId", orgParentId);
				list.addAll(l2);
			}
			this.getRequest().setAttribute("parentBz", list);
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
	 *  根据左侧组织机构的id跳转到右侧页面
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public String toRightPage() {
		getCurrentUnitId();
		Page page = this.deptManager.findPage(query);
		
		this.saveCurrentPage(page, query);
		return RIGHTPAGE;
	}
	*/
	private String getCurrentUnitId(){
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
		return orgId;
	}
	
	
	/**
		查询列表
	*/
	public String list()
	{
//		this.organizationManager.getById(query.getOrgId());
		Page page = this.bianzhiManager.findPage(query);
		super.saveCurrentPage(page,query);		
		
		return LIST_JSP;
	}
	
	/**
		新增
	*/
	public String add()
	{
		init();
		return ADD_JSP;
	}
	
	
	
	/**	
		保存新增
	*/
	public String saveAdd()
	{
	
		this.bianzhi.setTs(new Date());
		this.bianzhi.setAddTime(new Date());
		this.bianzhi.setAddUserId(this.getCurrUser().getUserID());
		this.bianzhi.setAddUserName(this.getCurrUser().getUserName());
		this.bianzhiManager.save(this.bianzhi);
		return LIST_ACTION;
	}
	
	/**	
		修改
	*/
	public String edit()
	{
		init();
		return EDIT_JSP;
	}
	
	
	/**	
		保存修改
	*/
	public String saveEdit()
	{
		this.bianzhiManager.update(this.bianzhi);
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
			this.bianzhiManager.removeById(id);
		}
		return LIST_ACTION;
	}
	
	/**	
		查看
	*/
	public String view()
	{
		init();
		return VIEW_JSP;
	}

	
	/**
	 * 加载组织结构XML
	 * @return
	 */
	public String deptXML() {
		try {
			
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
			String menuXmlString = Common.getDeptTree(orgs);
//			String menuXmlString = Common.getDeptTreeXML(orgs);
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}


	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}


	public DicUtil getDicUtil() {
		return dicUtil;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Bianzhi getBianzhi() {
		return bianzhi;
	}

	public void setBianzhi(Bianzhi bianzhi) {
		this.bianzhi = bianzhi;
	}

	public List<Organization> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Organization> orgs) {
		this.orgs = orgs;
	}

	public OrganizationManager getOrganizationManager() {
		return organizationManager;
	}

	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
