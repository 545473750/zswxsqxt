package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfInstanceResultDao;
import com.zswxsqxt.wf.model.WfInstanceResult;
import com.zswxsqxt.wf.query.WfInstanceResultQuery;

/**
  	 describe:实例结果服务类
	 
*/
@Service
@Transactional
public class WfInstanceResultManager extends BaseManager<WfInstanceResult, String>
{

	private WfInstanceResultDao wfInstanceResultDao;

	public void setWfInstanceResultDao(WfInstanceResultDao dao)
	{
		this.wfInstanceResultDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfInstanceResultDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfInstanceResultQuery query)
	{
		return wfInstanceResultDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
