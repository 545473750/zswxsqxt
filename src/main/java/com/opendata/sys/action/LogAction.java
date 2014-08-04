/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

import com.opendata.common.base.*;
import com.opendata.common.dict.DicUtil;
import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;

import com.opendata.sys.model.*;
import com.opendata.sys.service.*;
import com.opendata.sys.vo.query.*;

/**
 * 日志Action类 负责和表现层的交互以及调用相关业务逻辑层完成功能
 * @author付威
 */
@Namespace("/sys")
@Results({
@Result(name="list",location="/WEB-INF/pages/sys/Log/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Log/query.jsp", type = "dispatcher"),
@Result(name="hand_delete",location="/WEB-INF/pages/sys/Log/hand_delete.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Log/show.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//sys/Log!list.do", type = "redirectAction")
})
public class LogAction extends BaseStruts2Action implements Preparable,ModelDriven{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = " ts desc"; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String HAND_DELETE_JSP = "hand_delete";
	protected static final String SHOW_JSP = "show";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	private LogManager logManager;
	
	private Log log;
	private Map<String, String> logTypeMap;
	private DicUtil dicUtil;
	java.lang.String id = null;
	private String[] items;
	private LogQuery query=new LogQuery();
	
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			log = new Log();
		} else {
			log = (Log)logManager.getById(id);
		}
	}
	
	/** 执行搜索 */
	public String list() {
		query.setDf("0");
		query.setSortColumns(" ts desc ");
		Page page = this.logManager.findPage(query);
		
		logTypeMap = dicUtil.getDicList("APP_LOG");
		
		super.saveCurrentPage(page,query);
		
		return LIST_JSP;
	}
	
	/** 查看对象*/
	public String show() {
		logTypeMap = dicUtil.getDicList("APP_LOG");
		return SHOW_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		log.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		logManager.save(log);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	

	/**保存更新对象*/
	public String update() {
		logManager.update(this.log);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**删除对象*/
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			log = (Log)logManager.getById(id);
			log.setDf("1");
			logManager.delete(log);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 去手动清除日志页面
	 * @return
	 */
	public String toHandDeletePage(){
		return HAND_DELETE_JSP;
	}
	
	/**
	 * 手动清除日志
	 * @return
	 */
	public String handDelete(){
		LogQuery query = newQuery(LogQuery.class,DEFAULT_SORT_COLUMNS);
		logManager.remove(query.getTsBegin(), query.getTsEnd());
		return LIST_ACTION;
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setLogManager(LogManager manager) {
		this.logManager = manager;
	}	
	public Map<String, String> getLogTypeMap() {
		return logTypeMap;
	}
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}
	public Object getModel() {
		return log;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public LogQuery getQuery() {
		return query;
	}
	public void setQuery(LogQuery query) {
		this.query = query;
	}
	
}
