package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfInstanceFunctionDao;
import com.zswxsqxt.wf.model.WfInstanceFunction;
import com.zswxsqxt.wf.query.WfInstanceFunctionQuery;

/**
  	 describe:流程节点功能点表服务类
	 
*/
@Service
@Transactional
public class WfInstanceFunctionManager extends BaseManager<WfInstanceFunction, String>
{

	private WfInstanceFunctionDao wfInstanceFunctionDao;

	public void setWfInstanceFunctionDao(WfInstanceFunctionDao dao)
	{
		this.wfInstanceFunctionDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfInstanceFunctionDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfInstanceFunctionQuery query)
	{
		return wfInstanceFunctionDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
