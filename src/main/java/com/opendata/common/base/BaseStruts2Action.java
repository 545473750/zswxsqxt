package com.opendata.common.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.util.Assert;

import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import cn.org.rapid_framework.util.ObjectUtils;

import com.opendata.common.util.ConvertRegisterHelper;
import com.opendata.common.util.FileUtil;
import com.opendata.common.util.PageBar;
import com.opendata.common.util.PageRequestFactory;
import com.opendata.login.model.LoginInfo;
import com.opendata.organiz.model.User;
import com.opendata.sys.model.Attachment;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action基类
 * 
 * @author 付威
 * 
 */
@Results( { @Result(name = "login", location = "/login.jsp", type = "dispatcher"), 
		@Result(name = "logout", location = "/logout.jsp", type = "dispatcher"),
		@Result(name = "close_dialog", location = "/commons/dialogclose.jsp", type = "dispatcher"),
		@Result(name = "close_dialogNoRefresh", location = "/commons/dialogcloseNoRefresh.jsp", type = "dispatcher") })
public abstract class BaseStruts2Action extends ActionSupport implements RequestAware {

	protected static final String QUERY_JSP = "QUERY_JSP";

	protected static final String LIST_JSP = "LIST_JSP";

	protected static final String CREATE_JSP = "CREATE_JSP";

	protected static final String EDIT_JSP = "EDIT_JSP";

	protected static final String SHOW_JSP = "SHOW_JSP";

	protected static final String LIST_ACTION = "LIST_ACTION";

	// 提示
	protected final static String CREATED_SUCCESS = "创建成功";

	protected final static String CREATED_FAILE = "创建失败";

	protected final static String UPDATE_SUCCESS = "更新成功";

	protected final static String MOVE_SUCCESS = "移动成功";

	protected final static String ADMIT_SUCCESS = "操作成功";

	protected final static String DELETE_SUCCESS = "删除成功";

	protected final static String DELETE_ERROR = "删除失败";

	protected final static String DELETE_STATUS_ERROR = "已启用，删除失败";

	protected final static String RELEASE_SUCCESS = "发布成功";

	protected final static String RELEASE_ERROR = "发布失败";

	protected final static String REVOKE_SUCCESS = "撤销成功";

	protected final static String REVOKE_ERROR = "撤销失败";

	protected final static String UPDATE_PASSWORD_SUCCESS = "更新密码成功";

	protected final static String UPDATE_PASSWORD_FAILURE = "更新密码失败";

	protected final static String RESET_PASSWORD_SUCCESS = "重置密码成功";

	protected final static String RESET_PASSWORD_FAILURE = "重置密码失败";

	public static final String LOGIN_INFO = "Login_Info";

	protected static final String LOGIN = "login";

	protected static final String LOGOUT = "logout";

	protected static final String CLOSE_DIALOG = "close_dialog";

	protected static final String CLOSE_DIALOG_NOREFRESH = "close_dialogNoRefresh";

	// 组织机构下拉填充通用
	public static final String USERS = "users";

	public static final String DEPTS = "depts";

	public static final String PERMISSIONS = "permissions";

	public static final String RESOURCES = "resources";

	public static final String APPLICATIONS = "applications";

	public static final String ROLES = "roles";

	public static final String STATIONS = "stations";

	public String params;// get参数集合
	
	//反回的URL
	protected String backUrl;
	//当前请求的URL
	protected String currentUrl;

	protected Map requestMap = null;

	private String jsonResult;

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	static {
		// 注册converters
		ConvertRegisterHelper.registerConverters();
	}

	public void copyProperties(Object target, Object source) {
		BeanUtils.copyProperties(target, source);
	}

	public <T> T copyProperties(Class<T> destClass, Object orig) {
		return BeanUtils.copyProperties(destClass, orig);
	}

	public void setRequest(Map request) {
		this.requestMap = request;
	}

	public void savePage(Page page, PageRequest pageRequest) {
		savePage("", page, pageRequest);
	}

	/**
	 * 用于一个页面有多个extremeTable是使用
	 * 
	 * @param tableId
	 *            等于extremeTable的tableId属性
	 */
	public void savePage(String tableId, Page page, PageRequest pageRequest) {
		Assert.notNull(tableId, "tableId must be not null");
		Assert.notNull(page, "page must be not null");

		getRequest().setAttribute(tableId + "page", page);
		getRequest().setAttribute(tableId + "totalRows", new Integer(page.getTotalCount()));
		getRequest().setAttribute(tableId + "pageRequest", pageRequest);
		getRequest().setAttribute(tableId + "query", pageRequest);
	}

	public void saveCurrentPage(Page page, PageRequest pageRequest) {
		Map parameter = new HashMap();
		Enumeration paramets = getRequest().getParameterNames();
		boolean haveSortColumns = false;
		while (paramets.hasMoreElements()) {
			String keys = (String) paramets.nextElement();
			String values = getRequest().getParameter(keys);
			if (keys.startsWith("query.")) {
				parameter.put(keys, values);
				if (keys.equals("query.sortColumns")) {
					haveSortColumns = true;
				}
			}
		}
		if (!haveSortColumns) {
			parameter.put("query.sortColumns", "");
		}
		parameter.put("query.pageSize", page.getPageSize());
		parameter.put("query.pageNumber", page.getThisPageNumber());
		PageBar pageBar = new PageBar(page, parameter);
		getRequest().setAttribute("_pageBar_", pageBar);
		getRequest().setAttribute("page", page);
		getRequest().setAttribute("query", pageRequest);
	}

	/**
	 * 页面传参
	 */
	public void saveParameters() {
		Map parameter = new HashMap();
		Enumeration paramets = getRequest().getParameterNames();
		while (paramets.hasMoreElements()) {
			String keys = (String) paramets.nextElement();
			String values = getRequest().getParameter(keys);
			if (keys.startsWith("query.")) {
				parameter.put(keys, values);
				// 拼接get串
				if (this.params == null) {
					this.params = "";
				}
				if (values != null && !"".equals(values)) {
					this.params += keys + "=" + values+"&";
				}
			}
		}
		getRequest().setAttribute("params", parameter);
	}
	
	protected String getParameterString() {
		StringBuilder parameter = new StringBuilder();
		Enumeration paramets = getRequest().getParameterNames();
		while (paramets.hasMoreElements()) {
			String keys = (String) paramets.nextElement();
			String values = getRequest().getParameter(keys);
			if (keys.startsWith("query.")) {
				parameter.append("&").append(keys).append("=").append(values);
			}
		}
		return parameter.toString();
	}
	
	protected void setFailMessage(String message){
		try {
			this.params = String.format("message=<font color=red>%1$s</font>", URLEncoder.encode(message, "UTF-8"))+getParameterString();
		}catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	protected void setSuccessMessage(String message){
		try {
			this.params = String.format("message=<font color=green>%1$s</font>", URLEncoder.encode(message, "UTF-8"))+getParameterString();
		}catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();

			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String renderText(String text) {
		return render(text, "text/plain;charset=UTF-8");
	}

	protected String renderHtmlGBK(String html) {
		return render(html, "text/html;charset=GBK");
	}

	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=UTF-8");
	}

	protected String renderHtmlUTF(String html) {
		return render(html, "text/html;charset=UTF-8");
	}

	/**
	 * 设置通过request传到页面的参数和值
	 * 
	 * @param name
	 *            参数
	 * @param items
	 *            值
	 */
	public void saveItems(String name, Object items) {
		getRequest().setAttribute(name, items);
	}

	/**
	 * 根据泛型取得查询对象
	 * 
	 * @param <T>
	 * @param queryClazz
	 *            操作对象
	 * @param defaultSortColumns
	 *            默认多列排序,例如: username desc,createTime asc
	 * @return 查询对象
	 */
	public <T extends PageRequest> T newQuery(Class<T> queryClazz, String defaultSortColumns) {
		PageRequest query = PageRequestFactory.bindPageRequest(org.springframework.beans.BeanUtils.instantiateClass(queryClazz), ServletActionContext.getRequest(), defaultSortColumns);
		return (T) query;
	}

	/**
	 * 判断此对象是不是null
	 * 
	 * @param o
	 *            待判断对象
	 * @return 返回boolean值 如果是空返回true 否则返回false
	 */
	public boolean isNullOrEmptyString(Object o) {
		return ObjectUtils.isNullOrEmptyString(o);
	}

	/**
	 * 取得request对象
	 * 
	 * @return 返回request对象
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得登录对象
	 * 
	 * @return 返回登录对象
	 */
	public LoginInfo getCurrUser() {
		LoginInfo loginInfo = (LoginInfo) getSession().getAttribute(LOGIN_INFO);
		return loginInfo;
	}

	/**
	 * 取得session对象
	 * 
	 * @return session对象
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得request对象
	 * 
	 * @return request对象
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 根据某字符串截取id值
	 * 
	 * @param value
	 *            带id=id值的字符串
	 * @return id值
	 */
	public String subId(String value) {
		String id = value;
		if (value.contains("id="))
			id = value.substring(3, value.length());
		return id;
	}

	/**
	 * 复制文件
	 * 
	 * @param src
	 *            源文件
	 * @param desc
	 *            被复制文件
	 * @return 返回结果 1表示异常 2表示正常执行成功
	 */
	public static int copy(File src, File desc) {
		InputStream in = null;
		OutputStream os = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		try {
			in = new BufferedInputStream(new FileInputStream(src));
			os = new BufferedOutputStream(new FileOutputStream(desc));
			while ((readLen = in.read(buf)) != -1) {
				os.write(buf, 0, readLen);
			}
		} catch (IOException e) {
			return 1;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
		return 2;
	}

	/**
	 * 换算文件大小 如果文件大于1024B则换成KB 如果大于1024KB则换成MB 如果大于1024MB则换成GB
	 * 
	 * @param index_file_size
	 *            文件大小
	 * @return 换算结果
	 */
	public static String getfile_size(long index_file_size) {
		String index_file_size_string = "";
		if (index_file_size >= 1024 && index_file_size < (1024 * 1024)) {
			index_file_size_string = String.valueOf(index_file_size / (1024)) + "KB";
		} else if (index_file_size >= (1024 * 1024) && index_file_size < (1024 * 1024 * 1024)) {
			index_file_size_string = String.valueOf(index_file_size / (1024 * 1024)) + "MB";
		} else if (index_file_size >= (1024 * 1024 * 1024)) {
			index_file_size_string = String.valueOf(index_file_size / (1024 * 1024 * 1024)) + "GB";
		} else {
			index_file_size_string = String.valueOf(index_file_size) + "B";
		}
		return index_file_size_string;
	}
	
	/**
	 * 获取请求的URL（包括参数）
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getReferUrl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String queryString = request.getQueryString();
		StringBuffer sb = request.getRequestURL();
		// 访问服务器所带有的参数信息
		boolean questionMarkExisted = false;
		Enumeration e = request.getParameterNames();
		try {
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String[] values = request.getParameterValues(name);
				if (questionMarkExisted) {
					sb.append("&").append(name);
				} else {
					sb.append("?").append(name);
					questionMarkExisted = true;
				}
				if (name.equals("backUrl")) {
					for (int i = 0; i < values.length; i++) {
						if (i == 0) {
							sb.append("=").append(
									URLEncoder.encode(values[i], "UTF-8"));
						} else {
							sb.append(",").append(
									URLEncoder.encode(values[i], "UTF-8"));
						}
					}
				} else {
					for (int i = 0; i < values.length; i++) {
						if (i == 0) {
							sb.append("=").append(
									URLEncoder.encode(values[i], "UTF-8"));
						} else {
							sb.append(",").append(
									URLEncoder.encode(values[i], "UTF-8"));
						}
					}
				}
			}
			return URLEncoder.encode(sb.toString(), "UTF-8");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return "";
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}
	
}
