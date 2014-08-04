package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfWorkResultDao;
import com.zswxsqxt.wf.model.WfWorkResult;
import com.zswxsqxt.wf.query.WfWorkResultQuery;

/**
  	 describe:流程工作结果服务类
	 
*/
@Service
@Transactional
public class WfWorkResultManager extends BaseManager<WfWorkResult, String>
{

	private WfWorkResultDao wfWorkResultDao;

	public void setWfWorkResultDao(WfWorkResultDao dao)
	{
		this.wfWorkResultDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfWorkResultDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfWorkResultQuery query)
	{
		return wfWorkResultDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
