package com.opendata.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.sys.dao.BianzhiDao;
import com.opendata.sys.model.Bianzhi;
import com.opendata.sys.vo.query.BianzhiQuery;

/**
  	 describe:系统编制服务类
	 
*/
@Service
@Transactional
public class BianzhiManager extends BaseManager<Bianzhi, String>
{

	private BianzhiDao bianzhiDao;

	public void setBianzhiDao(BianzhiDao dao)
	{
		this.bianzhiDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.bianzhiDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(BianzhiQuery query)
	{
		return bianzhiDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
