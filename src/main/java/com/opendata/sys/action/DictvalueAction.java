/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;
import javax.servlet.http.HttpServletResponse;
import com.opendata.common.base.*;
import com.opendata.common.dict.DicUtil;
import com.opendata.common.util.Common;
import com.opendata.sys.model.*;
import com.opendata.sys.service.*;
import com.opendata.sys.vo.query.DictvalueQuery;

/**
 * 数据字典值action,用于处理用户请求
 * @author 顾保臣
 */
@Namespace("/sys")
@Results({
	@Result(name="closeDialog", location="/commons/dialogclose.jsp", type="dispatcher"),
	@Result(name="relationItem", location="/WEB-INF/pages/sys/Dictvalue/relationItem.jsp", type="dispatcher"),
	@Result(name="leftTree", location="/WEB-INF/pages/sys/Dictvalue/dictValue-leftTree.jsp", type="dispatcher"),
	@Result(name="main", location="/WEB-INF/pages/sys/Dictvalue/dictValue-main.jsp", type="dispatcher"),
	@Result(name="list",location="/WEB-INF/pages/sys/Dictvalue/dictValue-right.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Dictvalue/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/Dictvalue/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/Dictvalue/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Dictvalue/show.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//sys/Dictvalue!list.do?dictitemId=${dictitemId}&nodetype=${nodetype}&editable=${editable}&dictvalueRefresh=${dictvalueRefresh}", type="redirectAction"),
@Result(name="listAction2",location="..//sys/Dictvalue!list.do?dictitemId=${dictvalueId}&nodetype=${nodetype}&editable=${editable}&dictvalueRefresh=${dictvalueRefresh}", type="redirectAction")
})
public class DictvalueAction extends BaseStruts2Action implements Preparable, ModelDriven {
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
	
	private DictvalueManager dictvalueManager;
	private DictitemManager dictitemManager;
	
	private Dictvalue dictvalue;
	java.lang.String id = null;
	private String[] items;
	private String dictitemId;
	private String dictvalueId;
	private DicUtil dicUtil;
	
	DictvalueQuery query = new DictvalueQuery();
	

	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			dictvalue = new Dictvalue();
		} else {
			dictvalue = (Dictvalue)dictvalueManager.getById(id);
		}
	}
	
	/**
	 * 跳转到主页面,主页面分左右两部分
	 * @return
	 */
	public String toMainPage() {
		return "main";
	}
	
	/**
	 * 跳转到左侧页面,用于加载树型结构
	 * @return
	 */
	public String toLeftPage() {
		return "leftTree";
	}
	
	/**
	 * 加载XML格式的字典值
	 * @return
	 */
	public String dictValueXML() {
		
		try {
			Dictvalue dictvalue = this.dictvalueManager.getById(this.dictitemId);
			if(dictvalue != null) {
				this.dictitemId = dictvalue.getDictitem().getId();
			}
			
			// 根据字典项ID加载对应的字典值
			List<Dictvalue> dictValues = this.dictvalueManager.findListByItemId(this.dictitemId);//dictitemId错误，查找这里
			String xmlString = Common.getDictValueXML(dictValues, this.dictitemManager.getById(this.dictitemId));
			
			HttpServletResponse response = getResponse();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().print(xmlString);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 节点类型
	private String nodetype;
	// 是否可编辑
	private String editable;

	/**
	 * 执行搜索
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list() {
		//java.lang.System.out.println(this.nodetype + "\t\t" + this.dictitemId);	
		//按照code升序排列
		query.setSortColumns(" code ");
		// 根据参数的不同分别作查询
		if(this.nodetype != null && this.nodetype.equals("dictitem")) {
			
			query.setDictitemId(this.dictitemId);
			query.setParentValue("item");
			query.setDf("0");
			
			Page page = this.dictvalueManager.findPage(query);
			super.saveCurrentPage(page, query);
		} else if(this.nodetype != null && this.nodetype.equals("dictvalue")) {
			query.setParentValue(this.dictitemId);
			query.setDictitemId(null);
			query.setDf("0");
			Page page = this.dictvalueManager.findPage(query);
			super.saveCurrentPage(page, query);
		}
		
		return LIST_JSP;
	}
	
	/**
	 * 查看对象
	 * @return
	 */
	public String show() {
		Flash.current().clear();
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
		
		List<Dictvalue> result = this.dictvalueManager.findByCode(dictvalue.getCode(), this.dictitemId, this.nodetype);
		if(result != null && result.size() > 0) {
			Flash.current().success("字典值编号已存在！");
			return CREATE_JSP;
		}
		
		// 两种情况
		if(this.nodetype != null && this.nodetype.equals("dictitem")) {
			dictvalue.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			dictvalue.setDf("0");
			
			dictvalue.setDictitemId(this.dictitemId);
			dictvalue.setDictitem(this.dictitemManager.getById(this.dictitemId));
			dictvalueManager.save(dictvalue);
			dictvalueId = this.dictitemId;
		} else if(this.nodetype != null && this.nodetype.equals("dictvalue")) {
			dictvalue.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			dictvalue.setDf("0");
			dictvalue.setParentId(this.dictitemId);
			
			dictvalue.setDictitemId(this.dictvalueManager.getById(this.dictitemId).getDictitem().getId());
			dictvalue.setDictitem(this.dictvalueManager.getById(this.dictitemId).getDictitem());
			dictvalueManager.save(dictvalue);
			dictvalueId = this.dictitemId;
		}
		
		Flash.current().success(CREATED_SUCCESS);
		dicUtil.install();//重新初始化
		this.dictvalueRefresh = "refresh";
		return "listAction2";
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
		
		dictvalueManager.update(this.dictvalue);
		if(this.dictvalue.getParentId() == null) {
			this.dictvalueId = this.dictvalue.getDictitemId();
		} else {
			this.dictvalueId = this.dictvalue.getParentId();
		}
		
		Flash.current().success(UPDATE_SUCCESS);
		dicUtil.install();//重新初始化
		
		return "listAction2";
	}
	
	private String dictvalueRefresh;
	
	/**
	 * 删除对象
	 * @return
	 */
	public String delete() {
		
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			dictvalue = (Dictvalue)dictvalueManager.getById(id);
			dictvalue.setDf("1");
			dictvalueManager.update(this.dictvalue);
		}
		Flash.current().success(DELETE_SUCCESS);
		
		dicUtil.install();//重新初始化
		this.dictvalueRefresh = "refresh";
		return LIST_ACTION;
	}

	/**
	 * 删除数据字典值后，及时刷新数据字典值树
	 * @return
	 */
	public String refreshTree() {
		return "leftTree";
	}
	
	private List<Dictitem> dictItemList;

	/**
	 * 关联字典
	 * @return
	 */
	public String relationItem() {
		// 字典项列表
		this.dictItemList = this.dictitemManager.findAllByDf();
		return "relationItem";
	}
	public String saveRelationItem() {
		if(this.dictvalue.getRelationItem() != null && this.dictvalue.getRelationItem().equals("")) {
			this.dictvalue.setRelationItem(null);
		}
		this.dictvalueManager.update(this.dictvalue);
		return "closeDialog";
	}
	
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}
	public void setId(java.lang.String val) {
		this.id = val;
	}
	public void setItems(String[] items) {
		this.items = items;
	}
	public void setDictitemId(String dictitemId) {
		this.dictitemId = dictitemId;
	}
	public Object getModel() {
		return dictvalue;
	}
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setDictvalueManager(DictvalueManager manager) {
		this.dictvalueManager = manager;
	}
	public String getDictitemId() {
		return dictitemId;
	}	
	public void setDictitemManager(DictitemManager dictitemManager) {
		this.dictitemManager = dictitemManager;
	}
	public String getDictvalueRefresh() {
		return dictvalueRefresh;
	}
	public void setDictvalueRefresh(String dictvalueRefresh) {
		this.dictvalueRefresh = dictvalueRefresh;
	}
	public String getNodetype() {
		return nodetype;
	}
	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}
	public String getDictvalueId() {
		return dictvalueId;
	}
	public void setDictvalueId(String dictvalueId) {
		this.dictvalueId = dictvalueId;
	}
	public List<Dictitem> getDictItemList() {
		return dictItemList;
	}
	public void setDictItemList(List<Dictitem> dictItemList) {
		this.dictItemList = dictItemList;
	}
	public String getEditable() {
		return editable;
	}
	public void setEditable(String editable) {
		this.editable = editable;
	}
	public DictvalueQuery getQuery() {
		return query;
	}

	public void setQuery(DictvalueQuery query) {
		this.query = query;
	}
}
