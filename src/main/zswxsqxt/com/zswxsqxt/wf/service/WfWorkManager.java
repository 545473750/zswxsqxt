package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfWorkDao;
import com.zswxsqxt.wf.model.WfWork;
import com.zswxsqxt.wf.query.WfWorkQuery;

/**
  	 describe:流程工作表服务类
	 
*/
@Service
@Transactional
public class WfWorkManager extends BaseManager<WfWork, String>
{

	private WfWorkDao wfWorkDao;

	public void setWfWorkDao(WfWorkDao dao)
	{
		this.wfWorkDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfWorkDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfWorkQuery query)
	{
		return wfWorkDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
