/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Common;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.Station;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.StationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.OrganizationQuery;
import com.opendata.organiz.vo.query.OrganizationUserRelationQuery;
import com.opendata.organiz.vo.query.StationQuery;
import com.opendata.organiz.vo.query.UserQuery;
import com.opendata.sys.model.Partition;
import com.opendata.sys.service.PartitionManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 单位action
 * @since 2014-04-16
 * @author 陈永锋
 */
@SuppressWarnings({ "serial", "rawtypes" })
@Namespace("/organiz")
@Results({
@Result(name="moveDialogclose", location="/WEB-INF/pages/organiz/unit/moveDialogClose.jsp", type="dispatcher"),
@Result(name="list",location="/WEB-INF/pages/organiz/unit/list.jsp", type = "dispatcher"),

@Result(name="query",location="/WEB-INF/pages/organiz/unit/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/organiz/unit/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/organiz/unit/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/organiz/unit/show.jsp", type = "dispatcher"),
@Result(name="move", location="/WEB-INF/pages/organiz/unit/move.jsp", type="dispatcher"),

@Result(name="loaduser",location="/WEB-INF/pages/organiz/unit/unitUser.jsp", type = "dispatcher"),
@Result(name="loadjob",location="/WEB-INF/pages/organiz/unit/rightPage_job.jsp", type = "dispatcher"),
@Result(name="loaddept",location="/WEB-INF/pages/organiz/unit/rightPage.jsp", type = "dispatcher"),

@Result(name="choiceOrganization", location="/WEB-INF/pages/organiz/User/organizationTree.jsp", type="dispatcher"),
@Result(name="rightPage_user_list_choice.jsp", location="/WEB-INF/pages/organiz/unit/rightPage_user_list_choice.jsp", type="dispatcher"),

@Result(name="loadDefaultStation", location="/WEB-INF/pages/organiz/unit/rightPage_job_list.jsp", type="dispatcher"),
@Result(name="loadUserAction", location="..//organiz/unit!loadUser.do?parentid=${parentOrganizationId}&partitionId=${partitionId}", type="redirectAction"),
@Result(name="configUserAndOrganization", location="/WEB-INF/pages/organiz/unit/dialogclose.jsp", type="dispatcher"),
@Result(name="loadDefaultUser", location="/WEB-INF/pages/organiz/unit/rightPage_user_list.jsp", type="dispatcher"),
@Result(name="delete_torightpage", location="..//organiz/unit!toRightPage.do?id=${id}&org_type=${org_type}&flag=${flag}&partitionId=${partitionId}", type="redirectAction"),
@Result(name="update_torightpage", location="..//organiz/unit!toRightPage.do?id=${parentOrganizationId}&org_type=${parentOrganizationType}&flag=refresh&partitionId=${partitionId}", type="redirectAction"),
@Result(name="listAction", location="../organiz/Unit!list.do?${params}", type="redirectAction"),
@Result(name="torightpageAction", location="..//organiz/unit!toRightPage.do?id=${parentid}&org_type=${org_type}&flag=${flag}&partitionId=${partitionId}", type="redirectAction")
})
public class UnitAction extends BaseStruts2Action implements Preparable,ModelDriven{
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
	
	private OrganizationUserRelationManager organizationUserRelationManager;
	
	private OrganizationManager organizationManager;
	
	private UserManager userManager;
	private StationManager stationManager;
	
	private Organization organization;
	
	private String partitionId;//分区ID
	
	OrganizationQuery query = new OrganizationQuery();
	

	OrganizationUserRelationQuery urQuery = new OrganizationUserRelationQuery();
	

	java.lang.String id = null;
	private String[] items;
	private DicUtil dicUtil;
	private Map<String, String> organizationTypeMap;
	private List<Organization> organizationList;

	private String org_type;
	
	
	private String name;
	private String sequence;
	private String description;
	
	private String parentOrganizationId;
	private String parentOrganizationType;
	
	private Map<String, String> sexMap;
	
	private String type;
	
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			organization = new Organization();
		} else {
			organization = (Organization)organizationManager.getById(id);
		}
	}
	
	/**
	 * 数据字典：获取单位级别
	 * @return
	 */
	public Map<String,String> getDwjbMap(){
		Map<String,String> map = this.dicUtil.getLinkedMap("dwjb");
		return map;
	}
	/**
	 * 数据字典：获取单位性质
	 * @return
	 */
	public Map<String,String> getDwxzMap(){
		Map<String,String> map = this.dicUtil.getLinkedMap("dwxz");
		return map;
	}
	/**
	 * 单位类别
	 * @return
	 */
	public Map<String,String > getDataTypeMap(){
		Map<String,String> map = this.dicUtil.getLinkedMap("dwlb");
		return map;
	}
	/**
	 * 单位分类
	 * @return
	 */
	public Map<String,String > getSysTypeMap(){
		Map<String,String> map = this.dicUtil.getLinkedMap("dwfl");
		return map;
	}

	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {
//		OrganizationQuery query = newQuery(OrganizationQuery.class,DEFAULT_SORT_COLUMNS);
		query.setDf("0");
		query.setSortColumns(" ts desc ");
		Page page = organizationManager.findPage(query);
		this.saveCurrentPage(page,query);
		return "list";
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		this.organization = this.organizationManager.getById(this.id);
		this.organization.setTypeString(dicUtil.getDicList("ORG_ORGTYPE").get(this.organization.getType()));
		getRequest().setAttribute("org", this.organization);
		return SHOW_JSP;
	}
	
	
	
	
	/**
	 * 进入新增页面
	 * @return
	 */
	public String create() {
//		getRequest().setAttribute("pid", this.id);
//		getRequest().setAttribute("org_type", this.org_type == null ? "" : this.org_type);
		
		// 组织机构类别
//		this.organizationTypeMap = dicUtil.getDicList("ORG_ORGTYPE");
		// 编号
//		this.getRequest().setAttribute("sequence", this.organizationManager.findSequence(this.id));
		// 部门列表
//		this.organizationList = this.organizationManager.findAllByDf();
		
		return CREATE_JSP;
	}
	
	private String parentid;
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		// 组织机构编号不可重复
		List<Organization> result = this.organizationManager.findAllByDf("code", this.organization.getCode());
		if(result != null && result.size() > 0) {
			Flash.current().success("单位号已经存在！");
			return CREATE_JSP;
		}
		
		organization.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		organization.setDf("0");
		organizationManager.save(organization);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		// 回到列表页面
		return "listAction";
	}
	
	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
		getRequest().setAttribute("pid", this.parentid);
		
		//准备数据
		this.organization = this.organizationManager.getById(this.id);
		this.getRequest().setAttribute("organization", this.organization);
		
		// 组织机构类别
		this.organizationTypeMap = dicUtil.getDicList("ORG_ORGTYPE");
		// 部门列表
		this.organizationList = this.organizationManager.findAllByDf();
		
		/*// 如果当前组织机构是部门,则过滤掉业务组
		if(this.organization.getType().equals("type_dept")) {
			this.organizationList = this.organizationManager.findAllByDf("type", "type_dept");
		}*/
		
		return EDIT_JSP;
	}
	
	/**
	 *  跳转到移动操作页面
	 * @return
	 */
	public String move() {
		Organization organization = this.organizationManager.getById(this.id);
		getRequest().setAttribute("organizationType", organization.getType());
		return "move";
	}
	/**
	 *  移动操作
	 * @return
	 */
	public String moveOperation() {
		this.organization = this.organizationManager.getById(this.id);
		this.organization.setParentId(this.parentOrganizationId);
		if(this.parentOrganizationId != null && this.parentOrganizationId.equals("root")) {
			this.organization.setParentId(null);
		}
		this.organizationManager.save(this.organization);
		
		return "moveDialogclose";
	}
	
	
	

	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		this.organizationManager.update(organization);
		Flash.current().success(UPDATE_SUCCESS);
		return "listAction";
	}
	/**
	 * 更新上级id（ids同步数据） 
	 * @return
	 */
	public String updateParentId() {
		this.organizationManager.updateParentId();
		Flash.current().success(UPDATE_SUCCESS);
		return "listAction";
	}
	
	
	
	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		int flag = 0;
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			organization = (Organization)organizationManager.getById(id);
			
			if(organization.getOrganizations() != null && organization.getOrganizations().size() > 0) {
				for(Organization org : organization.getOrganizations()) {
					if(org.getDf().equals("0")) {
						flag++;
						break;
					}
				}
				// 都是删除状态的
				if(flag == 0) {
					organization.setDf("1");
					organization.setPartitions(new HashSet<Partition>());
				}
			} else {
				organization.setDf("1");
				organization.setPartitions(new HashSet<Partition>());
			}
			
			if(flag == 0) {
				organizationManager.delete(organization);
				// 删除当前组织机构和用户的关联关系
				this.organizationUserRelationManager.removeOURByOrganizationId(organization.getId());
			}
		}
		
		Flash.current().success(DELETE_SUCCESS);
		if(flag != 0) {
			Flash.current().success("当前删除数据包含子组织机构，请先删除子组织机构！");
		} 
		
		this.flag="refresh";
		return "delete_torightpage";
	}

	
	/**
	 * 加载组织机构下的人员
	 * @return
	 */
	public String loadUser() {
		init();
		BeanUtils.copyProperties(query,urQuery);
//		urQuery.setOrganizationId(query.getParentId());
		
		Page page = this.organizationUserRelationManager.findPage(urQuery);
		
		List<OrganizationUserRelation> ours = page.getResult();
		// 处理部门
		for(OrganizationUserRelation our : ours) {
			//用户的所有部门
			User user = our.getUser();
			StringBuffer sb = new StringBuffer();
			Set<OrganizationUserRelation> tmp = user.getOuRelations();
			for(OrganizationUserRelation o : tmp) {
				if(o.getOrganization() != null) {
					sb.append(o.getOrganization().getName() + ",");
				}
			}
			our.getUser().setDeptsString(sb.toString());
		}
		
		this.saveCurrentPage(page,query);
		return "loaduser";
	}
	
	/**
	 * 加载用户列表
	 * @return
	 */
	public String loadDefaultUser() {
		getRequest().setAttribute("parentOrganizationId", this.parentOrganizationId);
		// 当前组织机构下已经关联的用户列表
		StringBuffer sb = new StringBuffer();
		StringBuffer sbUserIds = new StringBuffer();
//		List<OrganizationUserRelation> ours = this.organizationUserRelationManager.findAllBy("organizationId", this.parentOrganizationId);
//		if(this.parentOrganizationId != null && this.parentOrganizationId.equals("root")) {
//			ours = this.organizationUserRelationManager.findAllBy("organizationId", null);
//		}
		List<OrganizationUserRelation> ours = this.organizationUserRelationManager.findAllByOrgId(this.parentOrganizationId);
		
		for(OrganizationUserRelation our : ours) {
			//sb.append("<span id='" + our.getUser().getId() + "'>" + our.getUser().getUsername() + "&nbsp;<input type='text' value='" + our.getSortNumber() + "' name='sortNum' style='width:30px;height:13px;'/>&nbsp;<a onclick=\"delItem('" + our.getUser().getId() + "')\" href='#'>X</a>&nbsp;&nbsp;</span>" );
			//"<div class='iframe_name_box' id='" + id + "'><p class='iframe_name_text'>" + name + "&nbsp;<input type='text' name='sortNum' style='width:30px;height:18px;'/></p><p class='iframe_name_close'><a onclick=\"delItem('" + id + "')\"><img src='${ctx}/images/component/open_textboxclose.gif' width='11' height='11' /></a></p></div>"
			sb.append("<div class='iframe_name_box' id='" + our.getUser().getId() + "'><p class='iframe_name_text'>" + our.getUser().getUsername() + "&nbsp;<input type='text' name='sortNum' value='" + (our.getSortNumber() == null ? "" : our.getSortNumber()) + "' style='width:30px;height:18px;'/></p><p class='iframe_name_close'><a onclick=\"delItem('" + our.getUser().getId() + "')\"><img src='" + ServletActionContext.getRequest().getContextPath() + "/images/component/open_textboxclose.gif' width='11' height='11' /></a></p></div>");
			sbUserIds.append(our.getUser().getId() + ",");
		}
		// 回显选择的用户
		getRequest().setAttribute("selectedUserHTML", sb.toString());
		// 回显选择的用户的IDs
		String ids = sbUserIds.toString();
		if(ids == null || ids.equals("")) {
			getRequest().setAttribute("selectedUserIds", "");
		} else {
			getRequest().setAttribute("selectedUserIds", ids.substring(0, ids.length() - 1));
		}
		
		return "loadDefaultUser";
	}
	public String choiceUserList() {
		UserQuery query = newQuery(UserQuery.class, DEFAULT_SORT_COLUMNS);
		// 删除标记的不显示
		query.setDf("0");
		query.setPageSize(5);
		Page page = userManager.findPage(query);
		List<User> users = page.getResult();
		// 处理部门列表
		for(User user : users) {
			StringBuffer sb = new StringBuffer();
			Set<OrganizationUserRelation> ours = user.getOuRelations();
			for(OrganizationUserRelation our : ours) {
				if(our.getOrganization() != null) {
					sb.append(our.getOrganization().getName() + ",");
				}
			}
			user.setDeptsString(sb.toString());
		}
		// 性别
		for(User user : users) {
			/*if(user.getSex() != null && !user.getSex().equals("")) {
				this.sexMap = dicUtil.getDicList("DICT_SEX");
				user.setSexString(this.sexMap.get(user.getSex()));
			}*/
		}
		savePage(page,query);
		return "rightPage_user_list_choice.jsp";
	}
	private String argArray;
	
	/**
	 * 配置组织机构和用户的关系
	 * @return
	 */
	public String configUserAndOrganization() {
		// 清空所有的和当前部门关联的用户记录
		this.organizationUserRelationManager.removeOURByOrganizationId(this.parentOrganizationId);
		
		String[] arg = this.argArray.split(",");
		for(String s : arg) {
			String[] userIdAndSortNum = s.split("-");
			
			// 用户ID
			String userId = userIdAndSortNum[0];
			// 如果用户ID为空，则清空所有关联信息
			if(userId != null && !userId.equals("")) {
				
				String sortNum = null;
				if(userIdAndSortNum.length == 2) {
					sortNum = userIdAndSortNum[1];
				}
				
				OrganizationUserRelation our = new OrganizationUserRelation();

				our.setOrganizationId(this.parentOrganizationId);
				if(this.parentOrganizationId != null && this.parentOrganizationId.equals("root")) {
					our.setOrganizationId(null);
				}
				
				our.setUserId(userId);
				if(sortNum == null) {
					our.setSortNumber(null);
				} else {
					our.setSortNumber(Long.valueOf(sortNum));
				}
				this.organizationUserRelationManager.save(our);
			} 
			
		}
		
		Flash.current().success("设置人员成功!");
		return "configUserAndOrganization";
	}
	
	/**
	 * 删除用户和组织机构的关联
	 * @return
	 */
	public String deleteUser() {
		
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");
			this.organizationUserRelationManager.removeById(id);
		}
		Flash.current().success("删除人员成功！");
		// 跳转到action
		return "loadUserAction";
	}
	
	/**
	 * 加载组织机构下的岗位
	 * @return
	 */
	public String loadJob() {
		StationQuery query = newQuery(StationQuery.class,DEFAULT_SORT_COLUMNS);
		query.setDf("0");
		query.setOrganizationId(this.parentid);
		Page page = this.stationManager.findPageCommon(query);
		
		List<Station> result = page.getResult();
		for(Station station : result) {
			station.setOrgString(station.getOrganization().getName());
			station.setLevelString(this.dicUtil.getDicList("ORG_STAlEVEL").get(station.getLevel()));
		}
		
		savePage(page,query);
		return "loadjob";
	}
	
	/**
	 * 加载岗位列表,勾选过的数据要标记
	 * @return
	 */
	public String loadDefaultStation() {
		// 组织机构对应的岗位
		Organization organization = this.organizationManager.getById(this.parentid);
		Set<Station> stations = organization.getStations();
		StringBuffer sb = new StringBuffer();
		for(Station station : stations) {
			sb.append(station.getId() + ",");
		}
		getRequest().setAttribute("selectedItems", sb.toString());
		
		// 默认岗位列表
		StationQuery query = newQuery(StationQuery.class,DEFAULT_SORT_COLUMNS);
		query.setDf("0");
		Page page = stationManager.findPage(query);
		
		List<Station> result = page.getResult();
		for(Station station : result) {
			station.setOrgString(station.getOrganization().getName());
			station.setLevelString(dicUtil.getDicList("ORG_STAlEVEL").get(station.getLevel()));
		}
		savePage(page,query);
		
		return "loadDefaultStation";
	}
	
	/**
	 * 保存组织机构和岗位的关联信息
	 * @return
	 */
	public String saveOrganizationAndStation() {
		// 暂停实现
		return null;
	}
	// 删除关联信息
	
	/**
	 * 加载组织机构基本信息
	 */
	public String loadDept() {
		return "loaddept";
	}
	
	public void init(){
		Map<String,String> xbMap = dicUtil.getLinkedMap("DICT_SEX");//得到性别集合
		this.getRequest().setAttribute("DICT_SEX", xbMap);
		Map<String,String> xdMap = dicUtil.getLinkedMap("xd");//得到学段集合
		this.getRequest().setAttribute("xd", xdMap);
		Map<String,String> xkMap = dicUtil.getLinkedMap("xk");//得到任教学科集合
		this.getRequest().setAttribute("xk", xkMap);
		Map<String,String> mzMap = dicUtil.getLinkedMap("mz");//得到民族集合
		this.getRequest().setAttribute("mz",mzMap);
		Map<String,String> zzmmMap = dicUtil.getLinkedMap("zzmm");//得到政治面貌集合
		this.getRequest().setAttribute("zzmm",zzmmMap);
		Map<String,String> xlMap = dicUtil.getLinkedMap("xl");//得到学历集合
		this.getRequest().setAttribute("xl",xlMap);
		Map<String,String> zcMap = dicUtil.getLinkedMap("zc");//得到职称集合
		this.getRequest().setAttribute("zc",zcMap);
	}
	
	
	/**
	 * 加载组织结构XML
	 * @return
	 *//*
	public String deptXML() {
		try {
			String menuXmlString = "";
			//如果是查询分区下的组织机构
			if (partitionId != null && !"".equals(partitionId)) {
				Partition partition = partitionManager.getById(partitionId);
				menuXmlString = Common.getOrganizByPartitionTreeXML(partition
						.getOrganizations());
			} else {
//				List<Organization> resultOrgList = this.organizationManager.findAllByDf();
				List<Organization> resultOrgList = this.organizationManager.findTopByDf();
				menuXmlString = Common.getDeptTreeXML(resultOrgList);
			}
			
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(menuXmlString);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	private String selectedIds;

	/**
	 * 选择所属组织机构的方法
	 * @return
	 *//*
	public String choiceOrganization() {
		if(partitionId!=null&&!"".equals(partitionId)){
			Partition partition = partitionManager.getById(partitionId);
			String organizationIdsByPartition = organizationManager.findAllChildByPartition(partition);
			getRequest().setAttribute("organizationIdsByPartition",organizationIdsByPartition);
		}
		return "choiceOrganization";
	}
	*/
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
	public Map<String, String> getOrganizationTypeMap() {
		return organizationTypeMap;
	}
	public void setOrganizationTypeMap(Map<String, String> organizationTypeMap) {
		this.organizationTypeMap = organizationTypeMap;
	}
	public List<Organization> getOrganizationList() {
		return organizationList;
	}
	public void setOrganizationList(List<Organization> organizationList) {
		this.organizationList = organizationList;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setOrganizationManager(OrganizationManager manager) {
		this.organizationManager = manager;
	}	
	public Object getModel() {
		return organization;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentOrganizationId() {
		return parentOrganizationId;
	}
	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}
	public String getParentOrganizationType() {
		return parentOrganizationType;
	}
	public void setParentOrganizationType(String parentOrganizationType) {
		this.parentOrganizationType = parentOrganizationType;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setOrganizationUserRelationManager(
			OrganizationUserRelationManager organizationUserRelationManager) {
		this.organizationUserRelationManager = organizationUserRelationManager;
	}
	public void setStationManager(StationManager stationManager) {
		this.stationManager = stationManager;
	}
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	public String getArgArray() {
		return argArray;
	}
	public void setArgArray(String argArray) {
		this.argArray = argArray;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public OrganizationQuery getQuery() {
		return query;
	}

	public void setQuery(OrganizationQuery query) {
		this.query = query;
	}
	
	
	
	public OrganizationUserRelationQuery getUrQuery() {
		return urQuery;
	}

	public void setUrQuery(OrganizationUserRelationQuery urQuery) {
		this.urQuery = urQuery;
	}
}
