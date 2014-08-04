package com.zswxsqxt.wf.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.zswxsqxt.wf.dao.WfInstanceNodeDao;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.query.WfInstanceNodeQuery;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;

/**
  	 describe:流程实例节点表服务类
	 
*/
@Service
@Transactional
public class WfInstanceNodeManager extends BaseManager<WfInstanceNode, String>
{

	private WfInstanceNodeDao wfInstanceNodeDao;

	public void setWfInstanceNodeDao(WfInstanceNodeDao dao)
	{
		this.wfInstanceNodeDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfInstanceNodeDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfInstanceNodeQuery query)
	{
		return wfInstanceNodeDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	public List<WfInstanceNode> getNodeByState(String insId,Integer state){
		return wfInstanceNodeDao.getZbNodeByState(insId, state);
	}
	
	public WfInstanceNode getNode(String insId,Integer state){
		return wfInstanceNodeDao.getNode(insId, state);
	}
	public List<WfInstanceNode> getNodess(String insId){
		return wfInstanceNodeDao.getNodess(insId);
	}
	
	public Integer getMaxOrderNode(String insId){
		return wfInstanceNodeDao.getMaxOrderNode(insId);
	}
}
