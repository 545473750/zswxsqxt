/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.application.model.Application;
import com.opendata.application.service.ApplicationManager;
import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.sys.model.Dictitem;
import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.service.DictitemManager;
import com.opendata.sys.service.DictvalueManager;
import com.opendata.sys.vo.query.DictitemQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 数据字典项action,用于处理用户请求
 * @author 顾保臣
 */
@Namespace("/sys")
@Results({
@Result(name="list",location="/WEB-INF/pages/sys/Dictitem/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Dictitem/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/Dictitem/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/Dictitem/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Dictitem/show.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//sys/Dictitem!list.do", type = "redirectAction")
})
public class DictitemAction extends BaseStruts2Action implements Preparable,ModelDriven{
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
	
	private DictitemManager dictitemManager;
	private ApplicationManager applicationManager;
	private  List<Application> applicationList;
	private DictitemQuery query=new DictitemQuery();
	
	private Dictitem dictitem;
	java.lang.String id = null;
	private String[] items;
	
	private DicUtil dicUtil;
	private String validUnitCode;
	private DictvalueManager dictvalueManager;

	/**
	 * ajax操作
	 * 根据审批部门，加载对应的审批项目
	 * @return
	 */
	public void jilian() {
		Map<String, String> validUnitMap = this.dicUtil.getDicList("validUnit");
		StringBuffer selectString = new StringBuffer("<option value=\"\">--请选择--</option>");
		for(Map.Entry<String, String> map : validUnitMap.entrySet()) {
			if(this.validUnitCode != null && this.validUnitCode.equals(map.getKey())) {
				//TODO 加载对应的审批项目
				List<Dictvalue> result = this.dictvalueManager.findAllByDf("relationItem", this.validUnitCode);
				for (Dictvalue dv : result) {
					selectString.append("<option value=\"" + dv.getCode() + "\">" + dv.getValue() + "</option>");
				}
			}
		}
		// 这句要在最后
		renderHtmlUTF(selectString.toString());
	}
	
	/**
	 * 执行搜索
	 * @return
	 */
	public String list() {
		// 所属应用
		this.applicationList = this.applicationManager.findAllByDf();
		query.setDf("0");
		query.setSortColumns(" ts desc ");
		Page page = this.dictitemManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
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
		// 所属应用
		this.applicationList = this.applicationManager.findAllByDf();
		return CREATE_JSP;
	}
	
	/**
	 * 保存新增对象
	 * @return
	 */
	public String save() {
		if (this.dictitemManager.findByCode(dictitem.getCode()) != null) {
			Flash.current().success("字典项编号已存在！");// 字典项编码已存在
			dictitem.setId(null);
			this.applicationList = this.applicationManager.findAllByDf();
			return CREATE_JSP;
		}
		
		Dictitem item = new Dictitem();
		item.setCode(dictitem.getCode());
		item.setName(dictitem.getName());
		item.setEditf(dictitem.getEditf());
		item.setDescription(dictitem.getDescription());
		
		item.setApplicationId(dictitem.getApplicationId());
		if(dictitem.getApplicationId() != null && dictitem.getApplicationId().equals("")) {
			item.setApplicationId(null);
		}
		
		item.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		item.setDf("0");
		
		this.dictitemManager.save(item);
		
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/**
	 * 进入更新页面
	 * @return
	 */
	public String edit() {
		// 所属应用
		this.applicationList = this.applicationManager.findAllByDf();
		return EDIT_JSP;
	}
	
	/**
	 * 保存更新对象
	 * @return
	 */
	public String update() {
//		if(dictitemManager.ifSameCode(dictitem)){
//			Flash.current().success("字典项编码已存在！");//字典项编码已存在
//			return EDIT_JSP;
//		}
		if(this.dictitem.getApplicationId() != null && this.dictitem.getApplicationId().equals("")) {
			this.dictitem.setApplicationId(null);
		}
		dictitemManager.update(this.dictitem);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			dictitem = (Dictitem)dictitemManager.getById(id);
			dictitem.setDf("1");
			dictitemManager.update(this.dictitem);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setDictitemManager(DictitemManager manager) {
		this.dictitemManager = manager;
	}	
	public Object getModel() {
		return dictitem;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public List<Application> getApplicationList() {
		return applicationList;
	}
	public void setApplicationList(List<Application> applicationList) {
		this.applicationList = applicationList;
	}
	public DictitemQuery getQuery() {
		return query;
	}
	public void setQuery(DictitemQuery query) {
		this.query = query;
	}
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			dictitem = new Dictitem();
		} else {
			dictitem = (Dictitem)dictitemManager.getById(id);
		}
	}
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}
	public String getValidUnitCode() {
		return validUnitCode;
	}
	public void setValidUnitCode(String validUnitCode) {
		this.validUnitCode = validUnitCode;
	}
	public void setDictvalueManager(DictvalueManager dictvalueManager) {
		this.dictvalueManager = dictvalueManager;
	}
	
}
