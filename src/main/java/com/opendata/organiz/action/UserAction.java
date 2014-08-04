/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.action;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.com.opendata.attachment.client.ClientAttachmentService;
import cn.com.opendata.attachment.model.Attachment;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.Station;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.RoleManager;
import com.opendata.organiz.service.StationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.StationQuery;
import com.opendata.organiz.vo.query.UserQuery;
import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.model.Partition;
import com.opendata.sys.service.DictitemManager;
import com.opendata.sys.service.DictvalueManager;
import com.opendata.sys.service.PartitionManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 用户管理action, 用于转发用户管理模块的请求
 * 
 * @author 顾保臣
 */
@SuppressWarnings("rawtypes")
@Namespace("/organiz")
@Results( {
	@Result(name = "stationList", location = "/WEB-INF/pages/organiz/User/stationList.jsp", type = "dispatcher"),
	@Result(name = "toConfigStation", location = "/WEB-INF/pages/organiz/User/configStationMain.jsp", type = "dispatcher"),
	@Result(name = "closeDialog", location = "/commons/dialogcloseNoRefresh.jsp", type = "dispatcher"),
	@Result(name = "stationUserMain", location = "/WEB-INF/pages/organiz/Station/station-user-main.jsp", type = "dispatcher"),
	@Result(name = "loadUserList", location = "/WEB-INF/pages/component/userList.jsp", type = "dispatcher"),
	@Result(name = "list", location = "/WEB-INF/pages/organiz/User/list.jsp", type = "dispatcher"),
	@Result(name = "query", location = "/WEB-INF/pages/organiz/User/query.jsp", type = "dispatcher"),
	@Result(name = "create", location = "/WEB-INF/pages/organiz/User/create.jsp", type = "dispatcher"),
	@Result(name = "edit", location = "/WEB-INF/pages/organiz/User/edit.jsp", type = "dispatcher"),
	@Result(name = "edit_person", location = "/WEB-INF/pages/organiz/User/edit_person.jsp", type = "dispatcher"),
	@Result(name = "edit_password", location = "/WEB-INF/pages/organiz/User/edit_password.jsp", type = "dispatcher"),
	@Result(name = "update_password", location = "/WEB-INF/pages/organiz/User/passwordsoucess.jsp", type = "dispatcher"),

	@Result(name = "editPersonPage", location = "../organiz/User!editPersonPage.do", type = "redirectAction"),
	@Result(name = "show", location = "/WEB-INF/pages/organiz/User/show.jsp", type = "dispatcher"),
	@Result(name = "grant_role", location = "/WEB-INF/pages/organiz/User/grant_role.jsp", type = "dispatcher"),
	@Result(name = "userType", location = "/WEB-INF/pages/organiz/User/userType.jsp", type = "dispatcher"),
	@Result(name = "listAction", location = "../organiz/User!list.do?partitionId=${partititonId}", type = "redirectAction"),
	@Result(name = "listForSystem", location = "/WEB-INF/pages/sys/System/system_user-list.jsp", type = "dispatcher"),
	@Result(name = "setUserForStation", location = "/WEB-INF/pages/organiz/Station/station-user-list.jsp", type = "dispatcher"),
	@Result(name = "setUserForProject", location = "/WEB-INF/pages/nbpt/NbProject/project-user-list.jsp", type = "dispatcher"),
	
	@Result(name="userList",location="/WEB-INF/pages/group/group/userList.jsp", type = "dispatcher"),
	@Result(name="sponsorList",location="/WEB-INF/pages/group/groupActivity/sponsorList.jsp", type = "dispatcher"),
	@Result(name="localUserList",location="/WEB-INF/pages/course/courseEvaluate/teacherList.jsp", type = "dispatcher"),
	@Result(name="outUserList",location="/WEB-INF/pages/course/courseEvaluateOut/outUserList.jsp", type = "dispatcher"),
})
public class UserAction extends BaseStruts2Action implements Preparable, ModelDriven {
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "userImage";
	private static final String FILE_MODEL = "userImage";
	// 默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	// forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP = "list";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String EDIT_PERSON_INFO = "edit_person";

	protected static final String SHOW_JSP = "show";
	protected static final String RESET_PW = "reset_password"; // 重置密码

	// redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";

	protected static final String GRANT_ROLE_PAGE = "grant_role";
	
	private DictvalueManager dictvalueManager;

	private String ableFlag;
	private String abledFlag;
	private UserManager userManager;
	private OrganizationManager organizationManager;
	private RoleManager roleManager;
	private PartitionManager partitionManager;
	private User user;
	String id = null;
	private String[] items;
	private String df;
	private String partitionId;// 分区ID
	private DictitemManager dictitemManager;
	
	private UserQuery query = new UserQuery();
	
	public void setDictitemManager(DictitemManager dictitemManager) {
		this.dictitemManager = dictitemManager;
	}
	public void setDictvalueManager(DictvalueManager dictvalueManager) {
		this.dictvalueManager = dictvalueManager;
	}
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			user = new User();
		} else {
			user = (User) userManager.getById(id);
		}
	}

	public Object getModel() {
		return user;
	}

	/**
	 * 执行搜索
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {

		// Flash.current().clear();

		/*// 删除标记的不显示
		query.setDf("0");
		// 启用状态
		query.setAbledFlag(this.abledFlag);
		// 如果是取某分区下的用户，根据分区下的组织机构取得所有用户
		if (partitionId != null && !"".equals(partitionId)) {
			Partition partition = partitionManager.getById(partitionId);
			if (partition != null) {
				query.setOrganizIds(organizationManager.findAllChildByPartition(partition));
			}
		}*/

		Page page = this.organizationUserRelationManager.findByUserPage(query);

		List<User> users = page.getResult();
		// 处理部门列表
		for (User user : users) {
			user.setDeptsString("");
			if (user.getOuRelations() != null) {
				StringBuffer sb = new StringBuffer();
				for (OrganizationUserRelation our : user.getOuRelations()) {
					if (our.getOrganization() != null) {
						sb.append(our.getOrganization().getName() + ",");
					}
				}
				user.setDeptsString(sb.toString());
			}
		}

		this.saveCurrentPage(page, query);
		return LIST_JSP;
	}

	/**
	 * 查看对象
	 * 
	 * @return
	 */
	public String show() {
		// 处理部门名称

		user.setDeptsString("");
		if (user.getOuRelations() != null) {
			StringBuffer sb = new StringBuffer();
			for (OrganizationUserRelation our : user.getOuRelations()) {
				if (our.getOrganization() != null) {
					sb.append(our.getOrganization().getName() + ",");
				}
			}
			user.setDeptsString(sb.length() > 0 ? sb.substring(0,
					sb.length() - 1).toString() : sb.toString());
		}

		/*// 性别
		if (user.getSex() != null && !user.getSex().equals("")) {
			this.sexMap = dicUtil.getDicList("DICT_SEX");
			user.setSexString(this.sexMap.get(user.getSex()));
		}*/

		try {
			/*
			 * WebApplicationContext wac =
			 * WebApplicationContextUtils.getRequiredWebApplicationContext
			 * (getRequest().getSession().getServletContext()); PictureService
			 * pictureService = (PictureService)wac.getBean("pictureService");
			 * //调用webservice方法 AttachmentImpl [] attachments =
			 * pictureService.findAttachment(user.getId(), FILE_NAME,FILE_MODEL);
			 * if(attachments!=null && attachments.length>0) {
			 * getRequest().setAttribute("attachment",attachments[0]); }
			 */

			ClientAttachmentService clientAttach = new ClientAttachmentService();
			List<Attachment> attachmentList = clientAttach.findAttachmentByXml(user
					.getId(), FILE_NAME, FILE_MODEL);
			if (attachmentList.size() > 0) {
				getRequest().setAttribute("attachment", attachmentList.get(0));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SHOW_JSP;
	}

	private Map<String, String> sexMap;
	private DicUtil dicUtil;

	/**
	 * 进入新增页面
	 * 
	 * @return
	 */
	public String create() {
		// getRequest().setAttribute(DEPTS, deptManager.findAll());
		getRequest().setAttribute(DEPTS, organizationManager.findAllByDf());
		// 性别
		this.sexMap = dicUtil.getDicList("DICT_SEX");
//		Dictitem item = dictitemManager.findByCode("post"); // 获取所有的岗位及岗位级别
		List stations = dictvalueManager.findByItemCode("post");// 获取所有的一级岗位

		getRequest().setAttribute("stations", stations);
		getRequest().setAttribute("clientId", "userImage"); // 调用外部上传头像程序所必须的目录名
		return CREATE_JSP;
	}

	private OrganizationUserRelationManager organizationUserRelationManager;

	/**
	 * 保存新增对象
	 * 
	 * @return
	 */
	public String save() {

		if (partitionId != null && !"".equals(partitionId)
				&& (user.getDeptIDs() == null || "".equals(user.getDeptIDs()))) {
			Flash.current().error("分区下用户必须选择组织机构!");
			return CREATE_JSP;
		}

		// 校验登录名是否重复
		User _user = this.userManager.findByName(user.getLoginname());
		if (_user != null) {

			// 性别
			this.sexMap = dicUtil.getDicList("DICT_SEX");
			// 部门列表
			// getRequest().setAttribute(DEPTS, deptManager.findAll());
			getRequest().setAttribute(DEPTS, organizationManager.findAllByDf());

			// 登录名重复
			Flash.current().error("登录名已经存在，请重新填写!");
			return CREATE_JSP;
		}

		user.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		// 设置删除标记
		user.setDf("0");
		// 默认启用
		if (user.getIsLeave().equals("0")) // 已离职 账户禁用 0 启用 1
		{
			user.setAbledFlag("1");
		} else {
			user.setAbledFlag("0");
		}
		// user.setPhoto(getRequest().getParameter("articleAttachPic"));
		userManager.save(user);
		// 创建一个webservice
		/*
		 * WebApplicationContext wac =
		 * WebApplicationContextUtils.getRequiredWebApplicationContext
		 * (getRequest().getSession().getServletContext()); PictureService
		 * pictureService = (PictureService)wac.getBean("pictureService");
		 * pictureService
		 * .saveAttach(user.getId(),getRequest().getParameter("attach_client_id"
		 * ), getRequest().getParameter("attach_form_key"), FILE_NAME,FILE_MODEL
		 * );
		 */
		ClientAttachmentService clientAttach = new ClientAttachmentService();
		clientAttach.saveAttach(user.getId(), getRequest().getParameter(
				"attach_client_id"), getRequest().getParameter(
				"attach_form_key"), FILE_NAME, FILE_MODEL);
		// 用户和组织机构的这种关系需要清除了
		if (user.getDeptIDs() != null && !user.getDeptIDs().equals("")) {
			String[] organizationIds = user.getDeptIDs().split(",");
			for (String organizationId : organizationIds) {
				OrganizationUserRelation our = new OrganizationUserRelation();
				our.setUserId(user.getId());
				our.setOrganizationId(organizationId);
				this.organizationUserRelationManager.save(our);
			}
		}
		Flash.current().success(CREATED_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}

	/**
	 * 进入更新页面
	 * 
	 * @return
	 */
	public String edit() {
		getRequest().setAttribute(DEPTS, organizationManager.findAllByDf());
		// 性别
		this.sexMap = dicUtil.getDicList("DICT_SEX");
		// 组织机构IDS
		StringBuffer sbId = new StringBuffer();
		StringBuffer sbText = new StringBuffer();
		for (OrganizationUserRelation our : user.getOuRelations()) {

			if (our.getOrganization() != null) {
				sbId.append(our.getOrganization().getId() + ",");
				sbText.append(our.getOrganization().getName() + ",");
			}
		}
		try {
			// 创建一个webservice
			/*
			 * WebApplicationContext wac =
			 * WebApplicationContextUtils.getRequiredWebApplicationContext
			 * (getRequest().getSession().getServletContext()); PictureService
			 * pictureService = (PictureService)wac.getBean("pictureService");
			 * AttachmentImpl [] attachments =
			 * pictureService.findAttachment(user.getId(),
			 * FILE_NAME,FILE_MODEL);
			 */
			ClientAttachmentService clientAttach = new ClientAttachmentService();
			List<Attachment> attachments = clientAttach.findAttachmentByXml(
					user.getId(), FILE_NAME, FILE_MODEL);
			if (attachments != null && attachments.size() > 0) {
				getRequest().setAttribute("attachment", attachments.get(0));
			}
		} catch (Exception e) {
			getRequest().setAttribute("attachment", null);
		}

		String ids = sbId.toString();
		String texts = sbText.toString();
		if (ids != null && !ids.equals("")) {
			user.setDeptIDs(ids.substring(0, ids.length() - 1));
			user.setDeptsString(texts.substring(0, texts.length() - 1));
		}
		return EDIT_JSP;
	}

	/**
	 * 保存更新对象
	 * 
	 * @return
	 */
	public String update() {
		this.organizationUserRelationManager.removeOURByUserId(user.getId());
		if (user.getDeptIDs() != null && !user.getDeptIDs().equals("")) {
			String[] organizationIds = user.getDeptIDs().split(",");
			for (String organizationId : organizationIds) {
				OrganizationUserRelation our = new OrganizationUserRelation();
				our.setOrganizationId(organizationId);
				our.setUserId(user.getId());
				this.organizationUserRelationManager.save(our);
			}
		}
		// user.setOuRelations(ours);
		/*if (user.getIsLeave().equals("0")) // 已离职 账户禁用 1 启用 0
		{
			user.setAbledFlag("1");
		} else if (user.getIsLeave().equals("1")) {
			user.setAbledFlag("0");
		} else {
			if (this.abledFlag != null && !this.abledFlag.equals("")) {
				// user.setAbledFlag("0");
				user.setAbledFlag(this.abledFlag);
			}
		}*/
		// 加密工资
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		this.userManager.update(user);

		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}

	/**
	 * 删除对象
	 * 
	 * @return
	 */
	public String delete() {
		
		String deleteMessage = "";
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String) params.get("id");
			User user = userManager.getById(id);
			
			try {
			} catch (Exception e) {
			}

			// 超级管理员禁止删除
			if (user.getLoginname() != null
					&& user.getLoginname().equals("admin")) {
				deleteMessage = "超级管理员禁止删除";
			} else {
				// 删除时，置换删除标记
				user.setDf("1");
				// 删除人员时 需要把人员与角色的关系 以及管理员与分区的关系删除
				user.setRoles(new HashSet<Role>(0));
				user.setOuRelations(null);
				user.setPartitions(new HashSet<Partition>(0));

				userManager.delete(user);
			}
		}

		if (deleteMessage != null && !deleteMessage.equals("")) {
			Flash.current().success(deleteMessage);
		} else {
			Flash.current().success(DELETE_SUCCESS);
		}

		return LIST_ACTION;
	}

	
	/**
	 * 用户类型
	 */
	public String updateUserType(){
		String uid="";
		HttpServletRequest request =ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("id");
		for (int i = 0; i < ids.length; i++) {
			
			uid += ids[i]+",";
		}
		getRequest().setAttribute("uid",uid);
		return "userType";
	}
	
	
	/**
	 * 更改用户类型
	 * 
	 * @return
	 */
	public String updatetype() {
		String ids = getRequest().getParameter("ids");
		String[] userIds = ids.split(",");
		String source = this.getRequest().getParameter("source");
		if (!isNullOrEmptyString(source)) {
			for (String uid : userIds) {
				User user = userManager.getById(uid);
				user.setSource(source);
				userManager.update(user);
			}
		}
		 return LIST_ACTION;
	}
	
	/**
	 * 修改个人设置页面
	 * 
	 * @return
	 */
	public String editPersonPage() {
		user = userManager.getById(getCurrUser().getUserID());
		// getRequest().setAttribute("depts", deptManager.findAll());
		// getRequest().setAttribute(DEPTS, organizationManager.findAllByDf());

		// 性别
		this.sexMap = dicUtil.getDicList("DICT_SEX");
		// 组织机构IDS
		StringBuffer sbId = new StringBuffer();
		StringBuffer sbText = new StringBuffer();
		for (OrganizationUserRelation our : user.getOuRelations()) {
			sbId.append(our.getOrganization().getId() + ",");
			sbText.append(our.getOrganization().getName() + ",");
		}
		String ids = sbId.toString();
		String texts = sbText.toString();
		if (ids != null && !ids.equals("")) {
			user.setDeptIDs(ids.substring(0, ids.length() - 1));
			user.setDeptsString(texts.substring(0, texts.length() - 1));
		}
		return EDIT_PERSON_INFO;
	}

	/**
	 * 修改个人设置
	 * 
	 * @return
	 */
	public String editPerson() {
		User dbResult = userManager.getById(getCurrUser().getUserID());
//		dbResult.setPhone(user.getPhone());
		//dbResult.setBirthday(user.getBirthday());
		//dbResult.setSex(user.getSex());
//		dbResult.setMail(user.getMail());
//		dbResult.setQq(user.getQq());
//		dbResult.setMsn(user.getMsn());
//		dbResult.setWblog(user.getWblog());
		/*// 维护组织机构信息
		this.organizationUserRelationManager.removeOURByUserId(getCurrUser().getUserID());
		if (user.getDeptIDs() != null && !user.getDeptIDs().equals("")) {
			String[] organizationIds = user.getDeptIDs().split(",");
			for (String organizationId : organizationIds) {
				OrganizationUserRelation our = new OrganizationUserRelation();
				our.setOrganizationId(organizationId);
				our.setUserId(getCurrUser().getUserID());

				this.organizationUserRelationManager.save(our);
			}
		}*/
		/*
		 * 判断图片是否被更改，以优化程序，不必每次都调用webservice
		 
		String fileName = getRequest().getParameter(FILE_NAME);
		if (StringUtils.isNotEmpty(fileName)) {
			// 创建一个webservice
			ClientAttachmentService clientAttach = new ClientAttachmentService();
			clientAttach.deleteAttach(dbResult.getId(), "true", null,
					getRequest().getParameter("attach_client_id"));
			clientAttach.saveAttach(dbResult.getId(), getRequest()
					.getParameter("attach_client_id"), getRequest()
					.getParameter("attach_form_key"), FILE_NAME, FILE_MODEL);
			System.out.println(getRequest().getParameter("attach_client_id"));
			System.out.println(getRequest().getParameter("attach_form_key"));
		}*/
		userManager.saveOrUpdate(dbResult);
		Flash.current().success("修改个人信息成功!");
		return "editPersonPage";
	}

	/**
	 * 进入权限授予页面
	 * 
	 * @return
	 */
	public String grantPage() {
		List<Role> roles = roleManager.findAllByDf();
		if (partitionId != null && !"".equals(partitionId)) {
			String roleIdsByPartition = "";
			List<Role> rolesByPartition = roleManager.findAllByDf(
					"partitionId", partitionId);
			for (Role r : rolesByPartition) {
				roleIdsByPartition += r.getId() + "|";
				getRequest().setAttribute("roleIdsByPartition",
						roleIdsByPartition);
			}
		}

		String roleEd = "";

		for (Role role : user.getRoles()) {
			if (role != null) {
				roleEd = roleEd + "," + role.getId();
			}
		}

		getRequest().setAttribute("roles", roles);
		getRequest().setAttribute("roleEd", roleEd);
		return GRANT_ROLE_PAGE;
	}

	/**
	 * 角色授予
	 * 
	 * @return
	 */
	public String grantRole() {
		Set<Role> roles = new HashSet<Role>(0);
		if (items != null) {
			for (String roleID : items) {
				Role role = roleManager.getById(roleID);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		userManager.saveOrUpdate(user);

		// Flash.current().success(UPDATE_SUCCESS);
		return "closeDialog";
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	public String resetPassword() {

		try {
			User user = this.userManager.getById(id);
			// 默认重置为000000
			user.setPassword("111111");
			this.userManager.save(user);

			// 提示信息
			Flash.current().success(RESET_PASSWORD_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();

			// 提示信息
			Flash.current().success(RESET_PASSWORD_FAILURE);
		}
		return LIST_ACTION;
	}

	/**
	 * 进入修改密码页面
	 * 
	 * @return
	 */
	public String editPasswordPage() {
		user = userManager.getById(getCurrUser().getUserID());
		return "edit_password";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String editPassword() {
		User dbResult = userManager.getById(getCurrUser().getUserID());
		User user = this.userManager.getById(getCurrUser().getUserID());
		String pwd = getRequest().getParameter("password");
		String pwdold = getRequest().getParameter("password_old");
		String msg = "修改密码成功";
		int type = 1;
		if (pwdold.equals(dbResult.getPassword())) {
			dbResult.setPassword(pwd);
			try {
				userManager.saveOrUpdate(dbResult);
				// 提示信息
				Flash.current().success(RESET_PASSWORD_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				msg = "修改密码失败!";
				type = 2;
				// 提示信息
				// Flash.current().success(RESET_PASSWORD_FAILURE);
			}
		} else {
			msg = "原始密码错误!";
			type = 3;
		}
		getRequest().setAttribute("type", type);
		getRequest().setAttribute("msg", msg);
		return "update_password";
	}

	/**
	 * 批量 启用/停用 0/1
	 * 
	 * @return
	 */
	public String batchAble() {
		String message = "";
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String) params.get("id");
			User user = userManager.getById(id);

			// 超级管理员不允许禁用
			if (user.getLoginname() != null
					&& user.getLoginname().equals("admin")) {
				message = "超级管理员禁止执行当前操作";
			} else {
				if (ableFlag != null && ableFlag.equals("enabled")) {
					if (user.getIsLeave().equals("0")) // 判断是否离职，如果已离职，则不允许启用该用户
					{
						message = "该员工已离职！不允许做操作！";
					} else {
						user.setAbledFlag("0");
					}
				}
				if (ableFlag != null && ableFlag.equals("disabled")) {
					user.setAbledFlag("1");
				}
				this.userManager.save(user);
			}
		}

		if (message != null && !message.equals("")) {
			Flash.current().success(message);
		} else {
			Flash.current().success(ADMIT_SUCCESS);
		}

		return LIST_ACTION;
	}

	// 岗位ID
	private String stationId;
	// 岗位业务操作
	private StationManager stationManager;

	/**
	 * 根据岗位加载对应的用户列表，跳转到设置用户界面
	 * 
	 * @return
	 */
	public String setUserForStation() {
		// 准备数据
		Station station = this.stationManager.getById(this.stationId);
		if (station != null) {
			StringBuffer sbIds = new StringBuffer();
			StringBuffer sbNames = new StringBuffer();
			StringBuffer html = new StringBuffer();
			// selectedIds
			// selectedNames
			// selectedUserHTML
			Set<User> users = station.getUsers(); // ,过滤掉是删除状态的用户
			Set<User> users_ = new HashSet<User>(0);
			for (User user : users) {
				if (user.getDf().equals("0")) {
					users_.add(user);
				}
			}

			for (User user : users_) {
				sbIds.append(user.getId() + ",");
				sbNames.append(user.getUsername() + ",");
				html.append("<div class='iframe_name_box' id='" + user.getId()
						+ "'>");
				html.append("<p class='iframe_name_text'>" + user.getUsername()
						+ "</p>");
				html.append("<p class='iframe_name_close'>");
				html.append("<a onclick=\"delItem('" + user.getId() + "', '"
						+ user.getUsername() + "')\">");
				html
						.append("<img src='"
								+ ServletActionContext.getRequest()
										.getContextPath()
								+ "/images/component/open_textboxclose.gif' width='11' height='11' />");
				html.append("</a>");
				html.append("</p>");
				html.append("</div>");
			}
			getRequest().setAttribute("selectedUserHTML", html.toString());

			if (sbIds.toString() != null && !sbIds.toString().equals("")) {
				String _sbIds = sbIds.substring(0,
						sbIds.toString().length() - 1);
				getRequest().setAttribute("selectedIds", _sbIds);
			}
			if (sbNames.toString() != null && !sbNames.toString().equals("")) {
				String _sbNames = sbNames.toString().substring(0,
						sbNames.toString().length() - 1);
				getRequest().setAttribute("selectedNames", _sbNames);
			}
		}

		return "stationUserMain";
	}

	private String selectedUserIds;

	public String listForStation() {
		Station station = this.stationManager.getById(this.stationId);
		if (this.selectedUserIds != null && !this.selectedUserIds.equals("")) {
			String[] userIds = this.selectedUserIds.split(",");
			Set<User> users = new HashSet<User>();
			for (String userId : userIds) {
				users.add(this.userManager.getById(userId));
			}
			station.setUsers(users);
			this.stationManager.saveOrUpdate(station);
		} else {
			station.setUsers(null);
			this.stationManager.saveOrUpdate(station);
		}
		return "closeDialog";
	}

	// 是否带排序功能
	private String withSortNumber;

	/**
	 * 加载用户列表,用于选择用户组件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadUserList() {
		query.setDf("0");
		// 如果是取某分区下的用户，根据分区下的组织机构取得所有用户
		if (partitionId != null && !"".equals(partitionId)) {
			Partition partition = partitionManager.getById(partitionId);
			if (partition != null) {
				query.setOrganizIds(organizationManager
						.findAllChildByPartition(partition));
			}
		}

		Page page = this.organizationUserRelationManager.findByUserPage(query);
		page = userManager.findPage(query);

		List<User> users = page.getResult();
		// 处理部门列表
		for (User user : users) {
			user.setDeptsString("");
			if (user.getOuRelations() != null) {
				StringBuffer sb = new StringBuffer();
				for (OrganizationUserRelation our : user.getOuRelations()) {
					if (our.getOrganization() != null) {
						sb.append(our.getOrganization().getName() + ",");
					}
				}
				user.setDeptsString(sb.toString());
			}
		}
		// 性别
		for (User user : users) {
			/*if (user.getSex() != null && !user.getSex().equals("")) {
				this.sexMap = this.dicUtil.getDicList("DICT_SEX");
				user.setSexString(this.sexMap.get(user.getSex()));
			}*/
		}
		this.saveCurrentPage(page, query);
		return "loadUserList";
	}

	/**
	 * 岗位设置
	 * 
	 * @return
	 */
	public String toConfigStation() {
		// 准备数据,过滤掉是删除状态的岗位信息
		Set<Station> stations = this.user.getStations();
		Set<Station> stations_ = new HashSet<Station>(0);
		for (Station station : stations) {
			if (station.getDf().equals("0")) {
				stations_.add(station);
			}
		}

		StringBuffer html = new StringBuffer();
		StringBuffer selectedIds = new StringBuffer();
		StringBuffer selectedNames = new StringBuffer();
		for (Station station : stations_) {
			selectedIds.append(station.getId() + ",");
			selectedNames.append(station.getName() + ",");

			html.append("<div class='iframe_name_box' id='" + station.getId()
					+ "'>");
			html.append("<p class='iframe_name_text'>" + station.getName()
					+ "</p>");
			html.append("<p class='iframe_name_close'>");
			html.append("<a onclick=\"delItem('" + station.getId() + "', '"
					+ station.getName() + "')\">");
			html
					.append("<img src='"
							+ ServletActionContext.getRequest()
									.getContextPath()
							+ "/images/component/open_textboxclose.gif' width='11' height='11' />");
			html.append("</a>");
			html.append("</p>");
			html.append("</div>");
		}
		getRequest().setAttribute("selectedHTML", html.toString());

		if (selectedIds.toString() != null
				&& !selectedIds.toString().equals("")) {
			String _selectedIds = selectedIds.toString().substring(0,
					selectedIds.toString().length() - 1);
			getRequest().setAttribute("selectedIds", _selectedIds);
		}
		if (selectedNames.toString() != null
				&& !selectedNames.toString().equals("")) {
			String _selectedNames = selectedNames.toString().substring(0,
					selectedNames.toString().length() - 1);
			getRequest().setAttribute("selectedNames", _selectedNames);
		}

		return "toConfigStation";
	}

	/**
	 * 待选岗位列表
	 * 
	 * @return
	 */
	public String stationList() {
		StationQuery query = newQuery(StationQuery.class, DEFAULT_SORT_COLUMNS);
		query.setDf("0");
		query.setPageSize(5);
		Page page = stationManager.findPage(query);
		savePage(page, query);

		return "stationList";
	}

	private String selectedIds;

	/**
	 * 岗位设置
	 * 
	 * @return
	 */
	public String configStation() {
		if (this.selectedIds != null && !this.selectedIds.equals("")) {
			Set<Station> stations = new HashSet<Station>();
			String[] ids = this.selectedIds.split(",");
			for (String id : ids) {
				Station station = this.stationManager.getById(id);
				stations.add(station);
			}
			this.user.setStations(stations);
		} else {
			this.user.setStations(null);
		}

		this.userManager.saveOrUpdate(user);

		// 关闭当前dialog
		getRequest().setAttribute("dialogId",
				getRequest().getParameter("dialogId"));
		return "closeDialog";
	}

	/**
	 * 协作组组长
	 * @return
	 */
	public String userList(){
		Page page = this.userManager.findHead(query);
		String userId = query.getId();
		List<User> usersList = page.getResult();
		
		for(User user:usersList){
			String uId = user.getId();
			if(!isNullOrEmptyString(userId)){
				if(userId.indexOf(uId)!=-1){
					user.setChecked("1");
				}
			}
		}
		
		this.saveCurrentPage(page, query);
		return "userList";
	}
	
	public String sponsorList(){
		Page page = this.userManager.findHead(query);
		String userId = query.getId();
		List<User> usersList = page.getResult();
		
		for(User user:usersList){
			String uId = user.getId();
			if(!isNullOrEmptyString(userId)){
				if(userId.indexOf(uId)!=-1){
					user.setChecked("1");
				}
			}
		}
		
		this.saveCurrentPage(page, query);
		return "sponsorList";
	}
	
	/**
	 * 发起测评 -- 本单位
	 * @return
	 */
	public String localUserList(){
			Page page = this.userManager.findLocalPage(query,this.getCurrUser().getDeptIDs());
			String userId = query.getId() ;
			List<User> userList = page.getResult();
			
			for(User user:userList){
				String uId = user.getId();
				if(!isNullOrEmptyString(userId)){
					if(userId.indexOf(uId)!=-1){
						user.setChecked("1");
					}
				}
			}
			
			super.saveCurrentPage(page,query);
			return "localUserList";
	}
	
	/**
	 * 发起测评 -- 跨单位
	 * @return
	 */
	public String outUserList(){
			Page page = this.userManager.findOutPage(query,this.getCurrUser().getDeptIDs());
			String userId = query.getId() ;
			List<User> userList = page.getResult();
			
			for(User user:userList){
				String uId = user.getId();
				if(!isNullOrEmptyString(userId)){
					if(userId.indexOf(uId)!=-1){
						user.setChecked("1");
					}
				}
			}
			
			super.saveCurrentPage(page,query);
			return "outUserList";
	}
	
	/**
	 * 修改用户继教号
	 * @return
	 */
	public String changeUserNum(){
		try{
			InputStream templatInputStream = this.getClass().getResourceAsStream("/com/opendata/organiz/action/teacher.xls");
			BufferedInputStream in = new BufferedInputStream(templatInputStream);
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook book=new HSSFWorkbook(fs);
			HSSFSheet sheet = book.getSheetAt(0);
			int num = sheet.getLastRowNum();
			
			List<Map<String ,String>> l = new ArrayList<Map<String,String>>();
			for(int i=1;i<num;i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell c1 = row.getCell(1);
				if(c1!=null){
					//继教号
					String userNum = c1.toString();
					HSSFCell c5 = row.getCell(5);
					if(c5!=null){
						//身份证号
						String IDNum = c5.toString();
						Map<String ,String> map = new HashMap<String,String>();
						map.put("userNum", userNum);
						map.put("IDNum", IDNum);
						l.add(map);
					}
				}
			}
			userManager.batchUpdateUserNum(l);
			Flash.current().success("继教号同步成功！");
		}catch(Exception e){
			e.printStackTrace();
			Flash.current().success("继教号同步失败！");
		}
		
		
		return LIST_ACTION;
	}
	
	/**
	 * 为每个用户赋予默认角色教师
	 * @return
	 */
	public String saveUserRole(){
		try{
			this.userManager.saveUserRole();
			Flash.current().success("为每个用户赋予默认角色教师成功！");
		}catch(Exception e){
			e.printStackTrace();
			Flash.current().success("为每个用户赋予默认角色教师失败！");
		}
		
		
		return LIST_ACTION;
	}
	
	public String getAbleFlag() {
		return ableFlag;
	}

	public void setAbleFlag(String ableFlag) {
		this.ableFlag = ableFlag;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void setStationManager(StationManager stationManager) {
		this.stationManager = stationManager;
	}

	public String getDf() {
		return df;
	}

	public void setDf(String df) {
		this.df = df;
	}

	public String getAbledFlag() {
		return abledFlag;
	}

	public void setAbledFlag(String abledFlag) {
		this.abledFlag = abledFlag;
	}

	public String getWithSortNumber() {
		return withSortNumber;
	}

	public void setWithSortNumber(String withSortNumber) {
		this.withSortNumber = withSortNumber;
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}

	public void setRoleManager(RoleManager manager) {
		this.roleManager = manager;
	}

	public void setId(String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	public Map<String, String> getSexMap() {
		return sexMap;
	}

	public void setSexMap(Map<String, String> sexMap) {
		this.sexMap = sexMap;
	}

	public void setOrganizationUserRelationManager(
			OrganizationUserRelationManager organizationUserRelationManager) {
		this.organizationUserRelationManager = organizationUserRelationManager;
	}

	public String getSelectedUserIds() {
		return selectedUserIds;
	}

	public void setSelectedUserIds(String selectedUserIds) {
		this.selectedUserIds = selectedUserIds;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public void setPartitionManager(PartitionManager partitionManager) {
		this.partitionManager = partitionManager;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	// 岗位级别联动菜单
	public String getStates() throws Exception {
		try {
			String code = getRequest().getParameter("code");
			Dictvalue dictvalue = dictvalueManager.findByCode(code);
			List list = new ArrayList(dictvalue.getDictvalues());
			JsonConfig cfg = new JsonConfig();
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					if (name.equals("code") || name.equals("value")) {
						return false;
					} else {
						return true;
					}
				}
			});
			String ss = JSONArray.fromObject(list, cfg).toString();
			StringBuffer returnstr = new StringBuffer("");
			returnstr.append(ss);
			super.renderHtmlUTF(returnstr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public UserQuery getQuery() {
		return query;
	}
	public void setQuery(UserQuery query) {
		this.query = query;
	}
	public String[] getItems() {
		return items;
	}

}
