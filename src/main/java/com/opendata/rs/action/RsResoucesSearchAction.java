package com.opendata.rs.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.dict.DicUtil;
import com.opendata.rs.model.RsResources;
import com.opendata.rs.model.RsType;
import com.opendata.rs.service.RsResourcesManager;
import com.opendata.rs.service.RsTypeManager;
import com.opendata.rs.vo.query.RsResourcesQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 资源搜索
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="RESOUCES_SEARCH", location="/WEB-INF/pages/rs/ResoucesSearch/resoucesSearchShow.jsp", type="dispatcher"),
@Result(name="GO_RESOUCES_SEARCH", location="/WEB-INF/pages/rs/ResoucesSearch/resoucesSearch.jsp", type="dispatcher"),
@Result(name="MINUTE_SHOW", location="/WEB-INF/pages/rs/ResoucesSearch/minuteShow.jsp", type="dispatcher"),
@Result(name="BERELATED_SHOW", location="/WEB-INF/pages/rs/ResoucesSearch/beRelatedShow.jsp", type="dispatcher"),
@Result(name="SHOW_JSP", location="/WEB-INF/pages/rs/RsExpandProperty/show.jsp", type="dispatcher"),
@Result(name="LIST_ACTION", location="../rs/RsExpandProperty!list.do", type="redirectAction")
})
public class RsResoucesSearchAction extends BaseStruts2Action implements Preparable, ModelDriven {
	//private static final String RESOURCE_DICT_NAME = "zysjlx";

	protected static final String RESOUCES_SEARCH = "RESOUCES_SEARCH";
	protected static final String GO_RESOUCES_SEARCH = "GO_RESOUCES_SEARCH";
	protected static final String MINUTE_SHOW = "MINUTE_SHOW";
	protected static final String BERELATED_SHOW = "BERELATED_SHOW";
	
	private RsTypeManager rsTypeManager;
	private RsResourcesManager rsResourcesManager;
	private DicUtil dicUtil;
	
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}
	
	public Object getModel() {
		return null;
	}

	public void prepare() throws Exception {
	}
	
	public String goResoucesSearch(){
		List<RsType> typeList=rsTypeManager.findAll();
		if(typeList!=null&&typeList.size()>0){
			getRequest().setAttribute("rstype", typeList.get(0));
		}
		getRequest().setAttribute("typeList", typeList);
		return GO_RESOUCES_SEARCH;
	}
	
	public String beRelatedResoucesShow(){
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,
				null);
		List<RsType> rsTypeList = rsTypeManager.findAll();
		getRequest().setAttribute("rsTypeList", rsTypeList);
		String id=getRequest().getParameter("resouid");
		query.setId(id);
		query.setAuditStatus("1");// 审核通过的
		if(query.getResourcesTypeId()==null){
			if(rsTypeList.get(0)!=null){
				query.setResourcesTypeId(rsTypeList.get(0).getId());
			}
		}
		Page page = rsResourcesManager.beRelatedResouces(query);
		savePage(page, query);
		getRequest().setAttribute("resouid", id);
		RsResources rsResources = (RsResources) rsResourcesManager.getById(id);
		getRequest().setAttribute("rsResources", rsResources);
		return BERELATED_SHOW;
	}
	
	/**
	 * 资源查询
	 * @return
	 */
	public String resoucesSearch(){
		RsResourcesQuery query = newQuery(RsResourcesQuery.class,null);
		
		if(query.getKeywords()!=null&&!query.getKeywords().equals("")){
			//query.setKeywords(StrUtil.UnicodeToUTF8(query.getKeywords()));
			try {
				query.setKeywords(java.net.URLDecoder.decode(query.getKeywords(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//默认在作者描述以及标题中全部查询
			String value=query.getKeywords();
			query.setKeyword("%"+value+"%");
			query.setTitle("%"+value+"%");
			query.setDescription("%"+value+"%");
			query.setAuthor("%"+value+"%");
		}
		
		Page<RsResources> page = rsResourcesManager.resoucesSearch(query);
		//query.setKeyword(StrUtil.GBToUnicode(query.getKeywords()));
		savePage(page, query);
		List<RsType> typeList=rsTypeManager.findAll();
		getRequest().setAttribute("typeList", typeList);
		getRequest().setAttribute("rstypeid", query.getResourcesTypeId());
		return RESOUCES_SEARCH;
	}
	
	/**
	 * 搜索资源中得详细
	 * @return
	 */
	public String minuteShow(){
		String resouId=getRequest().getParameter("resouId");
		RsResources rsResources = (RsResources) rsResourcesManager.getById(resouId);
		if(rsResources != null){
			String str = dicUtil.getDicValue("zysjlx",rsResources.getDataType());
			rsResources.setDataType(str);
			List<RsType> typeList=rsTypeManager.findAll();
			getRequest().setAttribute("typeList", typeList);
			getRequest().setAttribute("rstypeid", rsResources.getResourcesTypeId());
			getRequest().setAttribute("resou", rsResources);
		}
		return MINUTE_SHOW;
	}

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsTypeManager(RsTypeManager manager) {
		this.rsTypeManager = manager;
	}	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsResourcesManager(RsResourcesManager manager) {
		this.rsResourcesManager = manager;
	}
	
}
