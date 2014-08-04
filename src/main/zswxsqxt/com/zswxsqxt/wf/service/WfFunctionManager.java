package com.zswxsqxt.wf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.zswxsqxt.wf.dao.WfFunctionDao;
import com.zswxsqxt.wf.model.WfFunction;
import com.zswxsqxt.wf.query.WfFunctionQuery;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;

/**
  	 describe:流程功能点表服务类
	 
*/
@Service
@Transactional
public class WfFunctionManager extends BaseManager<WfFunction, String>
{

	private WfFunctionDao wfFunctionDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO 自动生成的方法存根
		return this.wfFunctionDao;
	}

	public WfFunctionDao getWfFunctionDao() {
		return wfFunctionDao;
	}



	public void setWfFunctionDao(WfFunctionDao wfFunctionDao) {
		this.wfFunctionDao = wfFunctionDao;
	}



	@Transactional(readOnly = true)
	public Page findPage(WfFunctionQuery query)
	{
		return wfFunctionDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}



	
}
