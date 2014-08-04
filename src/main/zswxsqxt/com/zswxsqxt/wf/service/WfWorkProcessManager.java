package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfWorkProcessDao;
import com.zswxsqxt.wf.model.WfWorkProcess;
import com.zswxsqxt.wf.query.WfWorkProcessQuery;

/**
  	 describe:流程工作任务表服务类
	 
*/
@Service
@Transactional
public class WfWorkProcessManager extends BaseManager<WfWorkProcess, String>
{

	private WfWorkProcessDao wfWorkProcessDao;

	public void setWfWorkProcessDao(WfWorkProcessDao dao)
	{
		this.wfWorkProcessDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfWorkProcessDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfWorkProcessQuery query)
	{
		return wfWorkProcessDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
