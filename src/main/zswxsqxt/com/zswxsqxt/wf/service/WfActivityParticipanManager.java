package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfActivityParticipanDao;
import com.zswxsqxt.wf.model.WfActivityParticipan;
import com.zswxsqxt.wf.query.WfActivityParticipanQuery;

/**
  	 describe:流程节点参与者服务类
	 
*/
@Service
@Transactional
public class WfActivityParticipanManager extends BaseManager<WfActivityParticipan, String>
{

	private WfActivityParticipanDao wfActivityParticipanDao;

	public void setWfActivityParticipanDao(WfActivityParticipanDao dao)
	{
		this.wfActivityParticipanDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfActivityParticipanDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfActivityParticipanQuery query)
	{
		return wfActivityParticipanDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	public WfActivityParticipan getParticipan(String refId,String activityId){
		return wfActivityParticipanDao.getParticipan(refId, activityId);
	}
}
