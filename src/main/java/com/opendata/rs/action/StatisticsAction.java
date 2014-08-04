package com.opendata.rs.action;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.org.rapid_framework.web.scope.Flash;

import com.opendata.common.base.BaseStruts2Action;

import com.opendata.rs.model.RsResources;
import com.opendata.rs.model.RsType;
import com.opendata.rs.service.RsResourcesManager;
import com.opendata.rs.service.RsTypeManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 资源统计action,对资源统计模块的请求进行转发
 * 
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="STATISTICS_TYPE_LIST_JSP", location="/WEB-INF/pages/rs/statistics/statisticsTypeList.jsp", type="dispatcher"),
@Result(name="STATISTICS_AUDITLIS_TJSP", location="/WEB-INF/pages/rs/statistics/statisticsauditlis.jsp", type="dispatcher"),
@Result(name="RSRESOUCES_RANKING_LIST", location="/WEB-INF/pages/rs/statistics/rsresoucesRankingList.jsp", type="dispatcher"),
@Result(name="RANKING_SHOW_JSP", location="/WEB-INF/pages/rs/statistics/rankingShow.jsp", type="dispatcher")
})
public class StatisticsAction extends BaseStruts2Action implements Preparable, ModelDriven {

	protected static final String STATISTICS_TYPE_LIST_JSP = "STATISTICS_TYPE_LIST_JSP";
	protected static final String STATISTICS_AUDITLIS_TJSP = "STATISTICS_AUDITLIS_TJSP";
	protected static final String RSRESOUCES_RANKING_LIST = "RSRESOUCES_RANKING_LIST";
	protected static final String RANKING_SHOW_JSP = "RANKING_SHOW_JSP";
	
	private RsTypeManager rsTypeManager;
	private RsResourcesManager rsResourcesManager;
	
	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public void prepare() throws Exception {
	}
	
	/**
	 * 按资源类型统计资源
	 * @return
	 */
	public String resourcesByTypeList(){
		Flash.current().clear();
		
		List<RsType> typeList=rsTypeManager.findAll();
		for (RsType type : typeList) {
			if (type.getRsResourcess() != null) {
				type.setResoucesNum(type.getRsResourcess().size() + "");
				int resouBrowseNum = 0;
				int resouDownloadNum = 0;
				for (RsResources resou : type.getRsResourcess()) {
					if (resou.getBrowseNumber() != null) {
						resouBrowseNum += resou.getBrowseNumber().intValue();
					}
					if (resou.getDownloadNumber() != null) {
						resouDownloadNum += resou.getDownloadNumber().intValue();
					}
				}
				type.setResouBrowseNum(resouBrowseNum + "");
				type.setResouDownloadNum(resouDownloadNum + "");
			}
		}
		getRequest().setAttribute("typeList", typeList);
		return STATISTICS_TYPE_LIST_JSP;
	}
	
	/**
	 * 按审核情况统计
	 * @return
	 */
	public String resourcesByAuditList(){
		int unauditedNum=rsResourcesManager.findAllByProVal("auditStatus", "0").size();//未审核的数量
		int approvedNum=rsResourcesManager.findAllByProVal("auditStatus", "1").size();//审核通过的数量
		int auditnotNum=rsResourcesManager.findAllByProVal("auditStatus", "2").size();//审核未通过的数量
		int allNum=unauditedNum+approvedNum+auditnotNum;//总数
		getRequest().setAttribute("unauditedNum", unauditedNum);
		getRequest().setAttribute("approvedNum", approvedNum);
		getRequest().setAttribute("auditnotNum", auditnotNum);
		getRequest().setAttribute("allNum", allNum);
		String unaudited="0";//默认未审核比率为0
		String approved="0";//默认审核通过比率为0
		String auditnot="0";//默认审核未通过比率为0
		if (allNum != 0) {
			DecimalFormat format = new DecimalFormat("#.0");
			if (unauditedNum != 0) {
				unaudited = format
						.format(Double.valueOf((Double.valueOf(unauditedNum) / Double
								.valueOf(allNum)) * 100))
						+ "%";
			}
			if (approvedNum != 0) {
				approved = format.format(Double.valueOf((Double
						.valueOf(approvedNum) / Double.valueOf(allNum)) * 100))
						+ "%";
			}
			if (auditnotNum != 0) {
				auditnot = format.format(Double.valueOf((Double
						.valueOf(auditnotNum) / Double.valueOf(allNum)) * 100))
						+ "%";
			}
		}
		getRequest().setAttribute("unaudited", unaudited);
		getRequest().setAttribute("approved", approved);
		getRequest().setAttribute("auditnot", auditnot);
		return STATISTICS_AUDITLIS_TJSP;
	}
	
	/**
	 * 资源下载排行榜
	 * @return
	 */
	public  String rsResoucesRankingList(){
		Flash.current().clear();
		List<RsResources> list = rsResourcesManager.findRanking();
		getRequest().setAttribute("list", list);
		return RSRESOUCES_RANKING_LIST;
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
