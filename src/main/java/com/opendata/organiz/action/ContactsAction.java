package com.opendata.organiz.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.opendata.attachment.ClientAttachmentApplication;
import cn.com.opendata.attachment.client.ClientAttachmentService;
import cn.com.opendata.attachment.model.Attachment;
import cn.com.opendata.attachment.model.impl.AttachmentImpl;
import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Platform;
import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationManager;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.UserQuery;
import com.opendata.sys.model.Dictitem;
import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.model.Partition;
import com.opendata.sys.service.DictitemManager;
import com.opendata.sys.service.DictvalueManager;
import com.opendata.sys.service.PartitionManager;
import com.opendata.webservice.sys.PictureService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;


@SuppressWarnings("rawtypes")
@Namespace("/organiz")
@Results({
	@Result(name="list", location="/WEB-INF/pages/organiz/contacts/list.jsp", type="dispatcher"),
	@Result(name="show", location="/WEB-INF/pages/organiz/contacts/show.jsp", type="dispatcher")
})
public class ContactsAction extends BaseStruts2Action implements Preparable,ModelDriven {
	private static final String FILE_NAME = "userImage";
	private static final String FILE_MODEL = "userImage";
	private OrganizationManager organizationManager;
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	private OrganizationUserRelationManager organizationUserRelationManager;
	private DictitemManager dictitemManager;
	private DictvalueManager dictvalueManager;
	private PartitionManager partitionManager;
	private String partitionId;//分区ID
	private DicUtil dicUtil;
	private User user;
	private UserManager userManager;
	protected static final String LIST_JSP= "list";
	protected static final String SHOW_JSP = "show";
	private String id="";
	private String[] items;
	
	
	
	public void setDictitemManager(DictitemManager dictitemManager) {
		this.dictitemManager = dictitemManager;
	}
	public void setDictvalueManager(DictvalueManager dictvalueManager) {
		this.dictvalueManager = dictvalueManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public String[] getItems() {
		return items;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
	public OrganizationManager getOrganizationManager() {
		return organizationManager;
	}

	public void setOrganizationManager(OrganizationManager organizationManager) {
		this.organizationManager = organizationManager;
	}

	public OrganizationUserRelationManager getOrganizationUserRelationManager() {
		return organizationUserRelationManager;
	}

	public void setOrganizationUserRelationManager(
			OrganizationUserRelationManager organizationUserRelationManager) {
		this.organizationUserRelationManager = organizationUserRelationManager;
	}

	public PartitionManager getPartitionManager() {
		return partitionManager;
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

	public DicUtil getDicUtil() {
		return dicUtil;
	}

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	/**
	 *  执行搜索 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		
		UserQuery query = newQuery(UserQuery.class, DEFAULT_SORT_COLUMNS);
		String orgId = getRequest().getParameter("deptId");
		String orgIds = organizationManager.findChildrenIds(orgId);
		query.setOrgids(orgIds);
		// 删除标记的不显示
		query.setDf("0");
		//如果是取某分区下的用户，根据分区下的组织机构取得所有用户
		if(partitionId!=null && !"".equals(partitionId)){
			Partition partition = partitionManager.getById(partitionId);
			if(partition!=null){
				query.setOrganizIds(organizationManager.findAllChildByPartition(partition));
			}
		}
		Page page = organizationUserRelationManager.findByUserPage(query);
		List<User> users = page.getResult();
		// 处理部门列表
		convertUserDepartment(users);
		savePage(page,query);
		//机构		
		//getRequest().setAttribute("organizations", organizationManager.findAllByDf());
		return LIST_JSP;
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		
		convertUserDepartment(user);
		// 处理部门名称
		/*user.setDeptsString("");
		// 性别
		if(user.getSex() != null && !user.getSex().equals("")) {
			user.setSexString(convertCodeToValue("DICT_SEX",user.getSex()));
		}*/
		
		//调用webservice方法
		/*
		 * WebApplicationContext  wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getRequest().getSession().getServletContext()); 
		PictureService pictureService = (PictureService)wac.getBean("pictureService");
		AttachmentImpl [] attachments = pictureService.findAttachment(user.getId(), FILE_NAME,FILE_MODEL);
		*/
		ClientAttachmentService clientAttach=new ClientAttachmentService();
		List<Attachment> attachmentList=clientAttach.findAttachmentByXml(user.getId(), FILE_NAME,FILE_MODEL);
		
		if(attachmentList!=null && attachmentList.size()>0)	
			getRequest().setAttribute("attachment",attachmentList.get(0));
		getRequest().setAttribute("servletUrl",getServletUrl());
		return SHOW_JSP;
	}
	
	
	
	
	
	/**
	 * 将性别的代码值转化为实际值
	 * @param users
	 */
	private void convertUserSex(List<User> users)
	{
		/*for(User user : users) {
			if(user.getSex() != null && !user.getSex().equals("")) {
				user.setSexString(convertCodeToValue("DICT_SEX",user.getSex()));
			}
		}*/
	}
	/**
	 * 从数据字典中将key值转化为实际值
	 * @param code	字典编码值
	 * @param key	字典key值
	 * @return	字典value
	 */
	private String convertCodeToValue(String code,String key)
	{
		return dicUtil.getDicList(code).get(key);
	}
	
	/**
	 * <b>将部门的代码值转化为实际值</b>
	 * @param users   users集合
	 */
	private void convertUserDepartment(List<User> users)
	{
		for(User user : users) {
			user.setDeptsString("");
			convertUserDepartment(user);
		}
	}
	
	/**
	 * <b>将部门的代码值转化为实际值</b>
	 * @param user	user对象
	 */
	private void convertUserDepartment(User user)
	{
		if(user.getOuRelations() != null) {
			StringBuffer sb = new StringBuffer();
			for(OrganizationUserRelation our : user.getOuRelations()) {
				if(our.getOrganization() != null) {
					sb.append(our.getOrganization().getName() + ",");
				}
			}
			user.setDeptsString(sb.length()>0?sb.substring(0,sb.length()-1).toString():sb.toString());
		}
	}
	
	
	/**
	 * 从spring文件中获取下载图片的url
	 * @return
	 */
	private String getServletUrl()
	{
		ClientAttachmentApplication attachmentApp = (ClientAttachmentApplication) Platform.getBean(ClientAttachmentApplication.Bean);
		return attachmentApp.getDownloadPath();
	}
	
	@Override
	public Object getModel() {
		return user;
	}

	@Override
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			user = new User();
		} else {
			user = (User)userManager.getById(id);
		}
		
	}
	
	//查询岗位联动菜单
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
	
}
