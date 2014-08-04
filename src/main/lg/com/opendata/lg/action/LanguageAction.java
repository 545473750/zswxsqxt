package com.opendata.lg.action;
import java.util.Date;
import java.util.Hashtable;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.util.HttpUtils;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.lg.model.Language;
import com.opendata.lg.query.LanguageQuery;
import com.opendata.lg.service.LanguageManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * describe:国际化管理
 * 
 *
 */
@Namespace("/lg")
@Results({
@Result(name="list",location="/WEB-INF/pages/lg/language/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/lg/language/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/lg/language/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/lg/language/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../lg/Language!list.do", type = "redirectAction")
})
public class LanguageAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	private LanguageManager languageManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private Language language;
	private LanguageQuery query=new LanguageQuery();
	
	public LanguageManager getLanguageManager() {
		return this.languageManager;
	}

	public void setLanguageManager(LanguageManager languageManager) {
		this.languageManager = languageManager;
	}
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.language= new Language();
		} else {
			this.language = (Language)this.languageManager.getById(id);
		}
	}
	
	public Object getModel() {
		return this.language;
	}
	
	public LanguageQuery getQuery() {
		return query;
	}

	public void setQuery(LanguageQuery query) {
		this.query = query;
	}
	
	/**
		查询列表
	*/
	public String list()
	{
		Page page = this.languageManager.findPage(query);
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
		新增
	*/
	public String add()
	{
		return ADD_JSP;
	}
	
	/**	
		保存新增
	*/
	public String saveAdd()
	{
		this.language.setTs(new Date());
		this.languageManager.save(this.language);
		return this.list();
	}
	
	/**	
		修改
	*/
	public String edit()
	{
		return EDIT_JSP;
	}
	
	
	/**	
		保存修改
	*/
	public String saveEdit()
	{
		this.languageManager.update(this.language);
		return this.list();
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
			this.languageManager.removeById(id);
		}
		return this.list();
	}
	
	/**	
		查看
	*/
	public String view()
	{
		return VIEW_JSP;
	}
	
}
