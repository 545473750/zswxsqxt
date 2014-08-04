package com.zswxsqxt.wf.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfActivityDao;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfProject;
import com.zswxsqxt.wf.query.WfActivityQuery;

/**
  	 describe:流程节点表服务类
	 
*/
@Service
@Transactional
public class WfActivityManager extends BaseManager<WfActivity, String>
{

	private WfActivityDao wfActivityDao;

	public void setWfActivityDao(WfActivityDao dao)
	{
		this.wfActivityDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfActivityDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfActivityQuery query)
	{
		return wfActivityDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	public WfActivity getOrderNum(WfProject wfProject){
		List<WfActivity> list =  wfActivityDao.findFastByFreeSql("getOrederNum", wfProject);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	//得到指定流程下的所有节点
	public List<WfActivity> getWfActivitys(String proId){
		return wfActivityDao.getWfActivity(proId);
	}
}
