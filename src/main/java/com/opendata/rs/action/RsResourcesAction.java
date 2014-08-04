/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.FileUtil;
import com.opendata.common.util.HTMLUtil;
import com.opendata.common.util.StrUtil;
import com.opendata.common.util.Util;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.opendata.rs.action.Commom.ConvertTreeToXML;
import com.opendata.rs.model.RsExpandProperty;
import com.opendata.rs.model.RsProperty;
import com.opendata.rs.model.RsResources;
import com.opendata.rs.model.RsStructure;
import com.opendata.rs.model.RsType;
import com.opendata.rs.service.RsExpandPropertyManager;
import com.opendata.rs.service.RsPropertyManager;
import com.opendata.rs.service.RsResourcesManager;
import com.opendata.rs.service.RsStructureManager;
import com.opendata.rs.service.RsTypeManager;
import com.opendata.rs.vo.query.RsResourcesQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 资源控制器,用于处理资源模块的请求转发
 * 
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="QUERY_JSP", location="/WEB-INF/pages/rs/RsResources/query.jsp", type="dispatcher"),
@Result(name="LIST_JSP", location="/WEB-INF/pages/rs/RsResources/list.jsp", type="dispatcher"),
@Result(name="CREATE_JSP", location="/WEB-INF/pages/rs/RsResources/create.jsp", type="dispatcher"),
@Result(name="EDIT_JSP", location="/WEB-INF/pages/rs/RsResources/edit.jsp", type="dispatcher"),
@Result(name="SHOW_JSP", location="/WEB-INF/pages/rs/RsResources/show.jsp", type="dispatcher"),
@Result(name="LIST_ACTION", location="../rs/RsResources!list.do", type="redirectAction"),

@Result(name="FAVORITE_LIST_JSP", location="/WEB-INF/pages/rs/RsResources/favoritelist.jsp", type="dispatcher"),
@Result(name="FAVORITE_SHOW_JSP", location="/WEB-INF/pages/rs/RsResources/favoriteshow.jsp", type="dispatcher"),
@Result(name="USER_RESOURCE_SHOW_JSP", location="/WEB-INF/pages/rs/RsResources/userResouceshow.jsp", type="dispatcher"),
@Result(name="RESOURCELISTBYUSER_JSP", location="/WEB-INF/pages/rs/RsResources/userResourcelist.jsp", type="dispatcher"),
@Result(name="USER_RESOUCES_EDIT", location="/WEB-INF/pages/rs/RsResources/userResoucesEdit.jsp", type="dispatcher"),
@Result(name="EASY_SEARCH", location="/WEB-INF/pages/rs/RsResources/list_easySearch.jsp", type="dispatcher"),
@Result(name="UPLOAD_PAGE", location="/WEB-INF/pages/rs/RsResources/upload.jsp", type="dispatcher"),
@Result(name="UPLOAD_FILE_PROPERTIES", location="/WEB-INF/pages/rs/RsResources/uploadFileProperties.jsp", type="dispatcher"),
@Result(name="AUDIT_LIST_JSP", location="/WEB-INF/pages/rs/RsResources/auditList.jsp", type="dispatcher"),
@Result(name="AUDIT_SHOW_JSP", location="/WEB-INF/pages/rs/RsResources/auditshow.jsp", type="dispatcher"),
@Result(name="ADVANCED_SEARCH_JSP", location="/WEB-INF/pages/rs/RsResources/advancedSearch.jsp", type="dispatcher"),

@Result(name="ADVANCED_SEARCH_RESULT_JSP", location="/WEB-INF/pages/rs/RsResources/advancedResult.jsp", type="dispatcher"),
@Result(name="BERELATED_RESOU_LIST", location="/WEB-INF/pages/rs/RsResources/beRelatedResourcelist.jsp", type="dispatcher"),
@Result(name="WAP_LIST_JSP", location="/WEB-INF/pages/rs/RsResources/wapResourceList.jsp", type="dispatcher"),
@Result(name="WAP_SHOW_JSP", location="/WEB-INF/pages/rs/RsResources/wapResourceShow.jsp", type="dispatcher"),
@Result(name="BERELATED_RESOUCES", location="/WEB-INF/pages/rs/RsResources/beRelatedResource.jsp", type="dispatcher"),
@Result(name="IRRELEVANT_RESOUCES", location="/WEB-INF/pages/rs/RsResources/irrelevantResource.jsp", type="dispatcher"),
@Result(name="BERELATED_RESOUCES_SHOW", location="/WEB-INF/pages/rs/RsResources/beRelatedResourceShow.jsp", type="dispatcher"),
@Result(name="PORTALS_JSP", location="/WEB-INF/pages/portal/portalannouce.jsp", type="dispatcher"),

@Result(name="USER_RESOUCES_LIST_ACTION", location="../rs/RsResources!resourceListByUser.do", type="redirectAction"),
@Result(name="UPLOAD_FILE_PROPERTIES_ACTION", location="../rs/RsResources!forwardUploadFileProperties.do", type="redirectAction"),
@Result(name="UPLOAD_PAGE_ACTION", location="../rs/RsResources!forwardUpload.do", type="redirectAction"),
@Result(name="AUDIT_LIST", location="../rs/RsResources!resoucesAuditList.do", type="redirectAction"),
@Result(name="FAVORITE_LIST", location="../rs/RsResources!favoriteList.do", type="redirectAction"),
@Result(name="RELATED_RESOURCES_SHOW", location="../rs/RsResources!beRelatedResoucesShow.do?resourceId=${resourceId}", type="redirectAction"),
@Result(name="RSRESOUCES_RANKING_LIST", location="/WEB-INF/pages/rs/statistics/rsresoucesRankingList.jsp", type="dispatcher"),
@Result(name="downLoadAtt", type="stream", params={"contentType", "application/octet-stream", "inputName", "inputStream", "contentDisposition", "attachment;filename=\"${downloadFileName}\"", "bufferSize", "4096"})
})
public class RsResourcesAction extends BaseStruts2Action implements Preparable, ModelDriven {
	protected static final String DEFAULT_SORT_COLUMNS = null;
	private static final String RESOURCE_DICT_NAME = "zysjlx";
	
	protected static final String FAVORITE_LIST_JSP = "FAVORITE_LIST_JSP";
	protected static final String FAVORITE_SHOW_JSP = "FAVORITE_SHOW_JSP";
	protected static final String USER_RESOURCE_SHOW_JSP = "USER_RESOURCE_SHOW_JSP";
	protected static final String RESOURCELISTBYUSER_JSP = "RESOURCELISTBYUSER_JSP";
	protected static final String USER_RESOUCES_EDIT = "USER_RESOUCES_EDIT";
	protected static final String EASY_SEARCH = "EASY_SEARCH";
	protected static final String UPLOAD_PAGE = "UPLOAD_PAGE";
	protected static final String UPLOAD_FILE_PROPERTIES = "UPLOAD_FILE_PROPERTIES";
	protected static final String AUDIT_LIST_JSP = "AUDIT_LIST_JSP";
	protected static final String AUDIT_SHOW_JSP = "AUDIT_SHOW_JSP";
	protected static final String ADVANCED_SEARCH_JSP = "ADVANCED_SEARCH_JSP";
	protected static final String ADVANCED_SEARCH_RESULT_JSP = "ADVANCED_SEARCH_RESULT_JSP";
	protected static final String BERELATED_RESOU_LIST = "BERELATED_RESOU_LIST";
	protected static final String WAP_LIST_JSP = "WAP_LIST_JSP";
	protected static final String WAP_SHOW_JSP = "WAP_SHOW_JSP";
	protected static final String BERELATED_RESOUCES = "BERELATED_RESOUCES";
	protected static final String IRRELEVANT_RESOUCES = "IRRELEVANT_RESOUCES";
	protected static final String BERELATED_RESOUCES_SHOW = "BERELATED_RESOUCES_SHOW";
	protected static final String PORTALS_JSP = "PORTALS_JSP";
	// redirect paths,startWith: !
	protected static final String USER_RESOUCES_LIST_ACTION = "USER_RESOUCES_LIST_ACTION";
	protected static final String UPLOAD_FILE_PROPERTIES_ACTION = "UPLOAD_FILE_PROPERTIES_ACTION";
	protected static final String UPLOAD_PAGE_ACTION = "UPLOAD_PAGE_ACTION";
	protected static final String AUDIT_LIST = "AUDIT_LIST";
	protected static final String FAVORITE_LIST = "FAVORITE_LIST";
	protected static final String RELATED_RESOURCES_SHOW = "RELATED_RESOURCES_SHOW";
	
	private File uploadFile;
	private String uploadFileFileName;
	private RsResourcesManager rsResourcesManager;
	private RsPropertyManager rsPropertyManager;
	private RsTypeManager rsTypeManager;
	private RsExpandPropertyManager rsExpandPropertyManager;
	private String tid;
	private RsResources rsResources;
	java.lang.String id = null;
	private String[] items;
	private String checkedNode;
	private String propertyValue;
	private UserManager userManager;
	private RsStructureManager rsStructureManager;
	private File uploadImage;
	private String uploadImageFileName;
	
	private DicUtil dicUtil;

	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			rsResources = new RsResources();
		} else {
			rsResources = (RsResources) rsResourcesManager.getById(id);
		}
	}

	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,
				DEFAULT_SORT_COLUMNS);
		Page page = rsResourcesManager.findPage(query);
		savePage(page, query);
		for (RsResources resou : (List<RsResources>) page.getResult()) {
			String str = dicUtil.getDicValue("zysjlx", resou.getDataType());
			resou.setDataType(str);
		}
		return LIST_JSP;
	}

	/**
	 * 查询个人收藏资源
	 * @return
	 */
	public String favoriteList() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,DEFAULT_SORT_COLUMNS);
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		Page page = rsResourcesManager.findResourceFavoriteByuser(query, getCurrUser().getUserID());
		savePage(page, query);
		for (RsResources resou : (List<RsResources>) page.getResult()) {
			String str = dicUtil.getDicValue("zysjlx", resou.getDataType());
			resou.setDataType(str);
		}
		query.setTitle(title);
		// 资源类型（查询用）
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);
		return FAVORITE_LIST_JSP;
	}

	/**
	 * 查询个人资源
	 * @return
	 */
	public String resourceListByUser() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,
				DEFAULT_SORT_COLUMNS);
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		query.setUploadUserId(getCurrUser().getUserID());
		Page page = rsResourcesManager.findResourceByuser(query);
		savePage(page, query);
		query.setTitle(title);
		for (RsResources resou : (List<RsResources>) page.getResult()) {
			String str = dicUtil.getDicValue("zysjlx", resou.getDataType());
			resou.setDataType(str);
		}
		// 资源类型（模糊查询用）
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);
		return RESOURCELISTBYUSER_JSP;
	}

	/**
	 * 审核列表
	 * @return
	 */
	public String resoucesAuditList() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,
				"auditStatus , ts asc");
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		Page page = rsResourcesManager.findUnaudited(query);
		savePage(page, query);
		query.setTitle(title);
		for (RsResources resou : (List<RsResources>) page.getResult()) {
			String str = dicUtil.getDicValue("zysjlx",resou.getDataType());
			resou.setDataType(str);
		}
		// 资源类型（模糊查询用）
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);
		return AUDIT_LIST_JSP;
	}

	/**
	 * 审核通过
	 * @return
	 */
	public String examineSuccess() {
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String(
					(String) params.get("id"));
			rsResources = (RsResources) rsResourcesManager.getById(id);
			rsResources.setTs(Util.getTimeStampString());
			rsResources.setAuditStatus("1");
			rsResourcesManager.delete(this.rsResources);
			Flash.current().success("审核通过");
		}
		return AUDIT_LIST;
	}

	/**
	 * 审核不通过
	 * @return
	 */
	public String examineError() {
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String(
					(String) params.get("id"));
			rsResources = (RsResources) rsResourcesManager.getById(id);
			rsResources.setTs(Util.getTimeStampString());
			rsResources.setAuditStatus("2");
			rsResourcesManager.delete(this.rsResources);
			Flash.current().success("审核不通过");
		}
		return AUDIT_LIST;
	}

	/**
	 * 查看个人资源
	 * @return
	 */
	public String userResourceShow() {
		if (rsResources.getBrowseNumber() != null) {
			rsResources.setBrowseNumber(rsResources.getBrowseNumber() + 1);
		} else {
			rsResources.setBrowseNumber(0l);
		}

		rsResourcesManager.update(rsResources);// 更新资源查看次数
		String str = dicUtil.getDicValue("zysjlx", rsResources.getDataType());
		rsResources.setDataType(str);
		return USER_RESOURCE_SHOW_JSP;
	}

	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		if (rsResources.getBrowseNumber() != null) {
			rsResources.setBrowseNumber(rsResources.getBrowseNumber() + 1);
		} else {
			rsResources.setBrowseNumber(0l);
		}
		rsResourcesManager.update(rsResources);// 更新资源查看次数
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
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		rsResources.setTs(Util.getTimeStampString());
		rsResources.setUploadTime(Util.getTimeStampString());
		rsResourcesManager.save(rsResources);
		Flash.current().success(CREATED_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}

	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
		return EDIT_JSP;
	}

	/**
	 * 进入个人资源更新页面
	 * @return
	 */
	public String userResoucesEdit() {
		// 保存进入该页面的是个人资源还是资源审核
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		HashMap<String,String> dict = dicUtil.getDicList(RESOURCE_DICT_NAME);
		
		getRequest().setAttribute("resourceDice", dict);
		return USER_RESOUCES_EDIT;
	}

	/**
	 * 打印拓展属性
	 */
	public void resourcesProList() {
		PrintWriter pw;
		try {
			pw = getResponse().getWriter();
			String resoucesId = getRequest().getParameter("resoucesId");
			rsResources = (RsResources) rsResourcesManager.getById(resoucesId);
			Set<RsExpandProperty> exproList = rsResources.getRsExpandPropertys();
			String html = HTMLUtil.generateHTMLbyExpand(exproList);
			if (html != null) {
				pw.write(html);
			}
			pw.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 展出可选关联资源
	 * @return
	 */
	public String beRelatedResouList() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,DEFAULT_SORT_COLUMNS);
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		
		query.setAuditStatus("1");// 审核通过的
		Page page = rsResourcesManager.findResourceByuser(query);
		query.setTitle(title);
		savePage(page, query);
		String resouid = getRequest().getParameter("resouid");
		RsResources rs = null;
		if (resouid != null) {
			// 修改关联关系时，拿到原本有得。
			rs = (RsResources) rsResourcesManager.getById(resouid);
		}
		// strids：从修改页面中获取的关联关系的ID集合，这个值在选完关联关系时才会有值。打开修改页面时，此值为空
		String strids = getRequest().getParameter("resouids");
		// 把strids付给关联关系中，以便于保存且利用此值进行增删，保证关联关系的ID集合不丢失
		getRequest().setAttribute("strids", strids);
		// checkids：打开关联关系进行选择和取消关联时，最新的值。当此值为空时，说明是第一次进入修改页面
		String checkids = getRequest().getParameter("checkid");
		if (checkids != null && !checkids.equals("")) {
			getRequest().setAttribute("strids", checkids);
		} else {
			if (rs != null && rs.getRsResourcess() != null) {
				String reids = "";
				for (RsResources rss : rs.getRsResourcess()) {
					reids += rss.getId() + ",";
				}
				if (reids.length() > 0) {
					reids = reids.substring(0, reids.length() - 1);
				}
				getRequest().setAttribute("strids", reids);
			}
		}
		// 资源类型（模糊查询用）
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);

		return BERELATED_RESOU_LIST;
	}

	/**
	 * 添加关联资源 修改时使用
	 */
	public void beRelated() {
		String resouid = getRequest().getParameter("resouid");
		rsResources = (RsResources) rsResourcesManager.getById(resouid);
		Set<RsResources> rsResourcess = new HashSet<RsResources>(0);

		String itemStr = getRequest().getParameter("resouids");
		if (!"".equals(itemStr) && itemStr != null) {
			String[] items = getRequest().getParameter("resouids").split(",");
			for (int i = 0; i < items.length; i++) {
				java.lang.String id = items[i];
				RsResources rs = (RsResources) rsResourcesManager.getById(id);
				rsResourcess.add(rs);
			}
		}
		rsResources.setRsResourcess(rsResourcess);
		rsResourcesManager.update(rsResources);
	}

	/**
	 * 有关联关系的资源
	 * @return
	 */
	public String beRelatedResouces() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,
				DEFAULT_SORT_COLUMNS);
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		String id = getRequest().getParameter("resouid");
		query.setId(id);
		query.setAuditStatus("1");// 审核通过的
		Page page = rsResourcesManager.beRelatedResouces(query);
		query.setTitle(title);
		savePage(page, query);
		String resouid = getRequest().getParameter("resouid");
		getRequest().setAttribute("resouid", resouid);
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);

		String checkids = getRequest().getParameter("checkid");
		getRequest().setAttribute("strids", checkids);
		return BERELATED_RESOUCES;
	}

	public String getTid() {
		return tid;
	}

	/**
	 * 未关联关系的资源
	 * @return
	 */
	public String irrelevantResouces() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class, DEFAULT_SORT_COLUMNS);
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		String id = getRequest().getParameter("resouid");
		query.setId(id);
		query.setAuditStatus("1");// 审核通过的
		Page page = rsResourcesManager.irrelevantResouces(query);
		query.setTitle(title);
		savePage(page, query);
		String resouid = getRequest().getParameter("resouid");
		getRequest().setAttribute("resouid", resouid);
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);

		String checkids = getRequest().getParameter("checkid");
		getRequest().setAttribute("strids", checkids);
		return IRRELEVANT_RESOUCES;
	}

	/**
	 * 添加已关联关系
	 */
	public void saveBeRelatedResouces() {
		String resouid = getRequest().getParameter("resouid");
		rsResources = (RsResources) rsResourcesManager.getById(resouid);
		Set<RsResources> rsResourcess = null;
		if (rsResources.getRsResourcess() != null) {
			rsResourcess = rsResources.getRsResourcess();
		} else {
			rsResourcess = new HashSet<RsResources>(0);
		}
		String itemStr = getRequest().getParameter("strids");
		if (!"".equals(itemStr) && itemStr != null) {
			String[] items = itemStr.split(",");
			for (int i = 0; i < items.length; i++) {
				java.lang.String id = items[i];
				RsResources rs = (RsResources) rsResourcesManager.getById(id);
				rsResourcess.add(rs);
			}
			rsResources.setRsResourcess(rsResourcess);
			rsResourcesManager.update(rsResources);
		}
	}

	/**
	 * 删除已关联关系
	 */
	public void editBeRelatedResouces() {
		String resouid = getRequest().getParameter("resouid");
		rsResources = (RsResources) rsResourcesManager.getById(resouid);
		String itemStr = getRequest().getParameter("strids");
		if (!"".equals(itemStr) && itemStr != null) {
			String[] items = itemStr.split(",");
			if (rsResources.getRsResourcess() != null) {
				for (int i = 0; i < items.length; i++) {
					java.lang.String id = items[i];
					for (RsResources rs : rsResources.getRsResourcess()) {
						if (rs.getId().equals(id)) {
							rsResources.getRsResourcess().remove(rs);
							break;
						}
					}
				}
				rsResourcesManager.update(rsResources);
			}
		}
	}

	/**
	 * 有关联关系的资源展示
	 * @return
	 */
	public String beRelatedResoucesShow() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,DEFAULT_SORT_COLUMNS);
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);
		String title = query.getTitle();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		query.setId(resourceId);
		query.setAuditStatus("1");// 审核通过的
		if (query.getResourcesTypeId() == null) {
			if (rsTypeList.get(0) != null) {
				//query.setResourcesTypeId(rsTypeList.get(0).getId());
			}
		}
		Page page = rsResourcesManager.beRelatedResouces(query);
		query.setTitle(title);
		savePage(page, query);

		return BERELATED_RESOUCES_SHOW;
	}

	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
		rsResources.setTs(Util.getTimeStampString());
		rsResourcesManager.update(this.rsResources);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}

	/**
	 * 更新个人资源
	 * @return
	 * @throws Exception
	 */
	public String userResoucesUpdate() throws Exception {
		rsResources.setTs(Util.getTimeStampString());
		if (!rsResources.getAuditStatus().equals("1")) {
			rsResources.setAuditStatus("0");// 个人资源修改，恢复默认资源状态为未审核
		}
		Set<RsStructure> list = new HashSet<RsStructure>();
		if (checkedNode != null && !checkedNode.equals("")) {
			String[] nodes = checkedNode.split(",");
			for (String str : nodes) {

				RsStructure rss = rsStructureManager.getById(str);
				RsStructure rsTmp = new RsStructure();
				BeanUtils.copyProperties(rsTmp, rss); // 防止两次更新操作，中间表操作只需要1次
				Set<RsResources> ss = new HashSet<RsResources>();
				ss.add(rsResources);
				rsTmp.setRsResourcess(ss);
				list.add(rsTmp);
			}
		}
		rsResources.setRsStructures(list);

		// 如果资源类型有变动，则保存更新
		List<RsExpandProperty> exproList = rsExpandPropertyManager.findByresoucrsId(rsResources.getId());
		if (exproList.size() > 0) {
			for (RsExpandProperty expro : exproList) {
				String str = "";
				if (expro.getInputType().equals("5")) {
					String[] strs = getRequest().getParameterValues(
							expro.getAttrCode());
					if(strs!=null){
						for (int i = 0; i < strs.length; i++) {
							str += strs[i];
							if (i < strs.length - 1) {
								str += ",";
							}
						}
					}
				} else {
					str = getRequest().getParameter(expro.getAttrCode());
				}
				if (!str.equals(expro.getAttrValue())) {
					expro.setAttrValue(str);
					rsExpandPropertyManager.update(expro);
				}
			}
		}
		rsResourcesManager.update(this.rsResources);
		Flash.current().success(UPDATE_SUCCESS);
		String type = getRequest().getParameter("type");
		// type=1进去个人资源列表页，type=2进入资源审核列表页
		if (type != null && type.equals("2")) {
			return AUDIT_LIST;
		} else {
			return USER_RESOUCES_LIST_ACTION;
		}
	}

	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String) params.get("id"));
			rsResources = (RsResources) rsResourcesManager.getById(id);
			if (rsResources != null) {
				rsResources.setTs(Util.getTimeStampString());
				rsResources.setMyFavoritess(null);
				rsResources.setRsResourcess(null);
				rsResources.setRsStructures(null);
				rsResources.setTargerRsResourcess(null);
				String url = rsResources.getAttaPath();
				rsResourcesManager.removeById(this.rsResources.getId());
				if (url != null && !url.equals("")) {
					String folderPath = getRequest().getSession()
							.getServletContext().getRealPath(url);
					File file = new File(folderPath);
					if (file.getParentFile().isDirectory()) {
						file.delete();
						file.getParentFile().delete();
					}
				}
			}
		}
		Flash.current().success(DELETE_SUCCESS);
		return USER_RESOUCES_LIST_ACTION;
	}

	public String favoritesDelete() {
		for (int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String(
					(String) params.get("id"));
			rsResources = (RsResources) rsResourcesManager.getById(id);
			rsResources.setMyFavoritess(null);
			rsResourcesManager.update(rsResources);
		}
		return FAVORITE_LIST;
	}

	/**
	 * 根据目录查找该目录下的所有资源
	 * @return
	 */
	public String findRootChildren() {
		if (tid == null || tid.equals(""))
			tid = (String) getRequest().getSession().getAttribute("structureId");
		
		getRequest().getSession().setAttribute("structureId", tid);
		RsResourcesQuery query = newQuery(RsResourcesQuery.class, DEFAULT_SORT_COLUMNS);
		Page<RsResources> page = rsResourcesManager.findRootChildren(tid, query);
		for (RsResources rs : page.getResult()) {
			String str = dicUtil.getDicValue(RESOURCE_DICT_NAME,rs.getDataType());
			rs.setDataType(str);
		}
		savePage(page, query);
		return LIST_JSP;
	}

	/**
	 * 简单查询
	 * @return
	 */
	public String easySearch() {
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,
				DEFAULT_SORT_COLUMNS);
		String title = query.getTitle();
		String author = query.getAuthor();
		String description = query.getDescription();
		String keyWord = query.getKeyword();
		if (title != null && !title.equals("")) {
			query.setTitle("%" + title.trim() + "%");
		}
		if (author != null && !author.equals("")) {
			query.setAuthor("%" + author.trim() + "%");
		}
		if (description != null && !description.equals("")) {
			query.setDescription("%" + description.trim() + "%");
		}
		if (keyWord != null && !keyWord.equals("")) {
			query.setKeyword("%" + keyWord.trim() + "%");
		}
		Page<RsResources> page = rsResourcesManager.easySearch(query);
		for (RsResources rs : page.getResult()) {
			String str = dicUtil.getDicValue(RESOURCE_DICT_NAME, rs.getDataType());
			rs.setDataType(str);
		}
		query.setAuthor(author);
		query.setTitle(title);
		query.setDescription(description);
		query.setKeyword(keyWord);
		savePage(page, query);
		return EASY_SEARCH;
	}

	/**
	 * 跳转到upload页面
	 * @return
	 */
	public String forwardUpload() {
		List<RsType> list = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypies", list);
		HashMap<String,String> dict = dicUtil.getDicList(RESOURCE_DICT_NAME);
		getRequest().setAttribute("resourceDice", dict);
		return UPLOAD_PAGE;
	}

	/**
	 * 跳转到上传文件属性配置界面
	 * @return
	 */
	public String forwardUploadFileProperties() {
		List<RsType> list = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypies", list);
		HashMap<String,String> dict = dicUtil.getDicList(RESOURCE_DICT_NAME);
		getRequest().setAttribute("resourceDice", dict);
		return UPLOAD_FILE_PROPERTIES;
	}

	/**
	 * 上传文件do方法
	 * @return
	 */
	public String upload() throws Exception {
		String type = (String) getRequest().getParameter("uploadType");
		if (rsResources.getAttaPath().indexOf(',') != -1) // 可能会遇到","的情况 直接删掉
			rsResources.setAttaPath(rsResources.getAttaPath().replace(",","").trim());
		// web上传
		if (type.equals("1")) {
			String path = uploadFile("1");
			rsResources.setAttaPath(path);
			// rsResources.setUploadType("1");
			rsResources.setFileName(uploadFileFileName);
		}
		Set<RsExpandProperty> set = new HashSet<RsExpandProperty>();
		RsType rsType = rsTypeManager.getById(rsResources.getResourcesTypeId());
		Map<String, String> map = HTMLUtil.parseParam(propertyValue);
		for (RsProperty p : rsType.getRsPropertys()) {
			RsExpandProperty rp = new RsExpandProperty();
			BeanUtils.copyProperty(rp, "attrName", p.getAttrName());
			BeanUtils.copyProperty(rp, "attrCode", p.getAttrCode());
			BeanUtils.copyProperty(rp, "attrSource", p.getAttrSource());
			BeanUtils.copyProperty(rp, "inputType", p.getInputType());
			BeanUtils.copyProperty(rp, "optionalValue", p.getOptionalValue());
			rp.setAttrValue(map.get(p.getAttrCode()));
			rp.setTs(Util.getTimeStampString());
			rp.setRsResources(rsResources);
			set.add(rp);
		}
		rsResources.setRsExpandPropertys(set);
		rsResources.setTs(Util.getTimeStampString());
		rsResources.setUploadTime(Util.getTimeStampString());
		rsResources.setUploadUserId(getCurrUser().getUserID());
		rsResources.setDownloadNumber(0L);
		rsResources.setAuditStatus("0");
		rsResources.setBrowseNumber(0L);
		String imageURL = uploadFile("2");
		rsResources.setThumbnail(imageURL);
		// 查找资源需要挂靠在哪些树上
		Set<RsStructure> list = new HashSet<RsStructure>();
		if (checkedNode != null && !checkedNode.equals("")) {
			String[] nodes = checkedNode.split(",");
			for (String str : nodes) {
				RsStructure rss = rsStructureManager.getById(str);
				RsStructure rsTmp = new RsStructure();
				BeanUtils.copyProperties(rsTmp, rss); // 防止两次更新操作，中间表操作只需要1次
				Set<RsResources> ss = new HashSet<RsResources>();
				ss.add(rsResources);
				rsTmp.setRsResourcess(ss);
				list.add(rsTmp);
			}
		}
		rsResources.setRsStructures(list);
		// ---------------------关联关系-----------------------
		String itemStr = getRequest().getParameter("resouids");
		if (!"".equals(itemStr) && itemStr != null) // 判断一下是否有关联，否则报错
		{
			Set<RsResources> rsResourcess = new HashSet<RsResources>(0);
			String[] items = getRequest().getParameter("resouids").split(",");
			for (int i = 0; i < items.length; i++) {
				java.lang.String id = items[i];
				RsResources rs = (RsResources) rsResourcesManager.getById(id);
				rsResourcess.add(rs);
			}
			rsResources.setRsResourcess(rsResourcess);
		}
		// ---------------------关联关系-----------------------
		rsResourcesManager.save(rsResources);
		Flash.current().success(CREATED_SUCCESS);
		return UPLOAD_PAGE_ACTION;
	}

	/**
	 * 添加更新上传文件的基本属性
	 * @return
	 */
	public String uploadFileProperty() throws Exception {
		Map<String, String> map = HTMLUtil.parseParam(propertyValue);
		String reId = (String) getRequest().getSession().getAttribute("resourceId");
		RsResources re = rsResourcesManager.getById(reId);
		rsExpandPropertyManager.physicsDeleteByResource(reId);
		re.setAuthor(rsResources.getAuthor());
		re.setKeyword(rsResources.getKeyword());
		re.setTitle(rsResources.getTitle());
		re.setLogo(rsResources.getLogo());
		re.setDescription(rsResources.getDescription());
		re.setDataType(rsResources.getDataType());
		re.setResourcesTypeId(rsResources.getResourcesTypeId());
		// re.setRsType(rsResources.getRsType());
		RsType rsType = rsTypeManager.getById(rsResources.getResourcesTypeId());
		re.setRsType(rsType);
		Set<RsExpandProperty> set = re.getRsExpandPropertys();
		if (set == null)
			set = new HashSet<RsExpandProperty>();
		if (re.getRsType() != null) {
			for (RsProperty p : re.getRsType().getRsPropertys()) {
				RsExpandProperty rp = new RsExpandProperty();
				BeanUtils.copyProperty(rp, "attrName", p.getAttrName());
				BeanUtils.copyProperty(rp, "attrCode", p.getAttrCode());
				BeanUtils.copyProperty(rp, "attrSource", p.getAttrSource());
				BeanUtils.copyProperty(rp, "inputType", p.getInputType());
				BeanUtils.copyProperty(rp, "optionalValue",p.getOptionalValue());
				rp.setAttrValue(map.get(p.getAttrCode()));
				rp.setResourcesId(reId);
				rp.setTs(Util.getTimeStampString());
				rp.setRsResources(re);
				set.add(rp);
			}
			re.setRsExpandPropertys(set);
		}
		// 上传缩略图
		String imageURL = uploadFile("2");
		re.setThumbnail(imageURL);
		// 查找资源需要挂靠在哪些树上
		Set<RsStructure> list = new HashSet<RsStructure>();
		if (checkedNode != null && !checkedNode.equals("")) {
			/*
			String[] nodes = checkedNode.split(",");
			for (String str : nodes) {
				RsStructure rss = rsStructureManager.getById(str);
				RsStructure rsTmp = new RsStructure();
				// BeanUtils.copyProperties(rsTmp, rss); // 防止两次更新操作，中间表操作只需要1次
				Set<RsResources> ss = new HashSet<RsResources>();
				ss.add(re);
				rsTmp.setRsResourcess(ss);
				list.add(rsTmp);
			}
			*/
			RsStructure rsTmp = new RsStructure();
			Set<RsResources> ss = new HashSet<RsResources>();
			ss.add(re);
			rsTmp.setRsResourcess(ss);
			list.add(rsTmp);
		}
		re.setRsStructures(list);
		rsResourcesManager.update(re);
		getRequest().getSession().removeAttribute("resourceId"); // 移除需要更新的resourceId
		Flash.current().success(ADMIT_SUCCESS);
		return UPLOAD_PAGE_ACTION;
	}

	/**
	 * 上传文件并返回文件的路径 1、文件 2、图片
	 * @return
	 */
	public synchronized String uploadFile(String flag) {
		File f = null;
		String fName = "";
		if (flag.equals("1") && uploadFile != null) {
			f = uploadFile;
			fName = uploadFileFileName;
		} else if (flag.equals("2") && uploadImage != null) {
			f = uploadImage;
			fName = uploadImageFileName;
		} else {
			return null;
		}
		String monthdirs = new SimpleDateFormat("yyyy-MM").format(new Date());
		String folderName = StrUtil.getMath(29);
		String folderPath = getRequest().getSession().getServletContext() .getRealPath("/upload") + "/" + monthdirs + "/" + folderName;
		File file = new File(folderPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		String fileLoadName = StrUtil.getMath(29) + "." + FileUtil.getFileExt(fName);
		FileUtil.CopyFile(f.getPath(), file.getPath() + "/" + fileLoadName);
		return "upload/" + monthdirs + "/" + folderName + "/" + fileLoadName + "";
	}

	/**
	 * 拼装并显示体系结构树
	 * @throws Exception
	 */
	public void showTree() throws Exception {
		String reId = (String) getRequest().getSession().getAttribute(
				"resourceId");
		if (reId == null)
			reId = id;
		if (reId != null)
			rsResources = rsResourcesManager.getById(reId);
		String ctx = getRequest().getContextPath();
		List<RsStructure> list = rsStructureManager.findAllRoot();
		Collections.sort(list);
		RsStructure[] rs = new RsStructure[rsResources.getRsStructures() == null ? 0
				: rsResources.getRsStructures().size()];
		if (rsResources.getRsStructures() != null
				&& rsResources.getRsStructures().size() != 0) {
			try {
				rs = (RsStructure[]) rsResources.getRsStructures().toArray(
						new RsStructure[0]);
			} catch (Exception ex) {
//				System.out.println(ex);
			}
		}
		String xml = ConvertTreeToXML.convertTreeToXML(list, ctx, rs);
		HttpServletResponse response = getResponse();
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-control", "no-cache");
		response.getWriter().print(xml);
	}

	public String rewardAdvancedSearch() {
		List<RsType> list = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypies", list);
		Map<String,String> dict = dicUtil.getDicList(RESOURCE_DICT_NAME);
		getRequest().setAttribute("resourceDice", dict);
		return ADVANCED_SEARCH_JSP;
	}

	/**
	 * 加入收藏
	 */
	public void addMyCollect() {
		rsResources = rsResourcesManager.getById(id);
		Set<User> myFavoritess = rsResources.getMyFavoritess();
		if( myFavoritess == null){
			myFavoritess = new HashSet<User>();
		}
		
		myFavoritess.add(userManager.getById(getCurrUser().getUserID()));
		rsResources.setMyFavoritess(myFavoritess);
		rsResourcesManager.update(rsResources);
		Flash.current().success(ADMIT_SUCCESS);
	}

	/**
	 * 高级查询
	 * @return
	 */
	public String advancedSearch() {
		Flash.current().clear();
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,DEFAULT_SORT_COLUMNS);
		query.setDataType(rsResources.getDataType());
//		if (StringUtils.isNotBlank(query.getAuthor())) {
//			query.setAuthor("%" + query.getAuthor() + "%");
//		}
//		if (StringUtils.isNotBlank(query.getDescription())) {
//			query.setDescription("%" + query.getDescription() + "%");
//		}
//		if (StringUtils.isNotBlank(query.getTitle())) {
//			query.setTitle("%" + query.getTitle() + "%");
//		}
//		if (StringUtils.isNotBlank(query.getKeyword())) {
//			query.setKeyword("%" + query.getKeyword() + "%");
//		}
//		if (StringUtils.isNotBlank(query.getLogo())) {
//			query.setLogo("%" + query.getLogo() + "%");
//		}
		Map<String, String> map = null;
		if (propertyValue != null && !propertyValue.equals("")) {
			this.propertyValue = this.propertyValue.replaceAll("\"", "'");
			map = HTMLUtil.parseParam(this.propertyValue);
		}
		Page<RsResources> page = rsResourcesManager.advancedSearch(query, map);
		for (RsResources rs : page.getResult()) {
			String str = dicUtil.getDicValue("zysjlx",rs.getDataType());
			rs.setDataType(str);
		}
		savePage(page, query);
		return ADVANCED_SEARCH_RESULT_JSP;
	}

	/**
	 * 查找资源
	 * @return
	 */
	public String findResources() {
		String name = getRequest().getParameter("name");
		if(StringUtils.isNotBlank(name)) {
			try {
				name = java.net.URLDecoder.decode(name,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,DEFAULT_SORT_COLUMNS);
		Page page = rsResourcesManager.resoucesSearch(query);
		savePage(page, query);
		getRequest().setAttribute("name", name);
		return WAP_LIST_JSP;
	}

	
	// ------------------------------下载附件------------------------------
	private String fileName;// 初始的通过param指定的文件名属性
	private String inputPath;// 指定要被下载的文件路径

	public String getDownloadFileName() throws UnsupportedEncodingException {
		String downFileName = fileName;
		try {
			downFileName = new String(downFileName.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
//		downFileName = java.net.URLEncoder.encode(fileName, "utf-8");
		return java.net.URLEncoder.encode(downFileName, "UTF-8");
	}

	public InputStream getInputStream() throws Exception {
		// 通过 ServletContext，也就是application 来读取数据
		return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
	}
	private String dlModule;
	private String resourceId;
	public String downLoadAtt() throws UnsupportedEncodingException {
		
		this.inputPath = new String(this.inputPath.getBytes("ISO-8859-1"), "UTF-8");
		String fileName = new String(this.fileName.getBytes("ISO-8859-1"), "utf-8");
		
		// 如果附件不存在 则提示
		String path = ServletActionContext.getServletContext().getRealPath("/");
		File file = new File(path + this.inputPath);
		if(!file.exists()) {
			// 快速查询
			if(this.dlModule != null && this.dlModule.equals("findRootChildren")) {
				if (tid == null || tid.equals(""))
					tid = (String) getRequest().getSession().getAttribute("structureId");
				getRequest().getSession().setAttribute("structureId", tid);
				
				RsResourcesQuery query = newQuery(RsResourcesQuery.class, DEFAULT_SORT_COLUMNS);
				Page<RsResources> page = rsResourcesManager.findRootChildren(tid, query);
				for (RsResources rs : page.getResult()) {
					String str = dicUtil.getDicValue(RESOURCE_DICT_NAME, rs.getDataType());
					rs.setDataType(str);
				}
				savePage(page, query);
				
				Flash.current().success("附件不存在.");
				return LIST_JSP;
			} else if(this.dlModule != null && this.dlModule.equals("rsResoucesRankingList")) {
				List<RsResources> list=rsResourcesManager.findRanking();
				getRequest().setAttribute("list", list);
				
				Flash.current().success("附件不存在.");
				return "RSRESOUCES_RANKING_LIST";
			} else if (this.dlModule != null && this.dlModule.equals("advancedSearch")) {
				RsResourcesQuery query = newQuery(RsResourcesQuery.class, DEFAULT_SORT_COLUMNS);
				query.setDataType(rsResources.getDataType());
				
				Map<String, String> map = null;
				if (propertyValue != null && !propertyValue.equals("")) {
					map = HTMLUtil.parseParam(propertyValue);
				}
				Page<RsResources> page = rsResourcesManager.advancedSearch(query, map);
				for (RsResources rs : page.getResult()) {
					String str = dicUtil.getDicValue("zysjlx",rs.getDataType());
					rs.setDataType(str);
				}
				savePage(page, query);
				
				Flash.current().success("附件不存在.");
				return ADVANCED_SEARCH_RESULT_JSP;
			} else if (this.dlModule != null && this.dlModule.equals("beRelatedResoucesShow")){
				Flash.current().success("附件不存在.");
				return RELATED_RESOURCES_SHOW;
			} else {
				// 资源审核
				if (rsResources.getBrowseNumber() != null) {
					rsResources.setBrowseNumber(rsResources.getBrowseNumber() + 1);
				} else {
					rsResources.setBrowseNumber(0l);
				}
				rsResources.setFileName(fileName);// 中文名乱码
				rsResourcesManager.update(rsResources);// 更新资源查看次数
				
				String str = dicUtil.getDicValue("zysjlx", rsResources.getDataType());
				rsResources.setDataType(str);
				
				Flash.current().success("附件不存在.");
				return USER_RESOURCE_SHOW_JSP;
			}
		}
		
		// 资源下载次数加一
		RsResources resource = this.rsResourcesManager.getById(this.resourceId);
		resource.setDownloadNumber(resource.getDownloadNumber() + 1);
		resource.setFileName(fileName);// 中文名乱码
		this.rsResourcesManager.update(resource);
		
		return "downLoadAtt";
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getInputPath() {
		return inputPath;
	}
	
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	
	public String getDlModule() {
		return dlModule;
	}
	
	public void setDlModule(String dlModule) {
		this.dlModule = dlModule;
	}
	
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public File getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(File uploadImage) {
		this.uploadImage = uploadImage;
	}

	public String getUploadImageFileName() {
		return uploadImageFileName;
	}

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	public void setUploadImageFileName(String uploadImageFileName) {
		this.uploadImageFileName = uploadImageFileName;
	}

	public void setRsStructureManager(RsStructureManager rsStructureManager) {
		this.rsStructureManager = rsStructureManager;
	}

	public String getCheckedNode() {
		return checkedNode;
	}

	public void setCheckedNode(String checkedNode) {
		this.checkedNode = checkedNode;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Object getModel() {
		return rsResources;
	}

	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsResourcesManager(RsResourcesManager manager) {
		this.rsResourcesManager = manager;
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsPropertyManager(RsPropertyManager rsPropertyManager) {
		this.rsPropertyManager = rsPropertyManager;
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsTypeManager(RsTypeManager rsTypeManager) {
		this.rsTypeManager = rsTypeManager;
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsExpandPropertyManager(RsExpandPropertyManager manager) {
		this.rsExpandPropertyManager = manager;
	}
	
}
