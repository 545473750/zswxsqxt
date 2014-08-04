package com.zswxsqxt.wf.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfActivityDao;
import com.zswxsqxt.wf.dao.WfProjectDao;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfProject;
import com.zswxsqxt.wf.query.WfProjectQuery;

/**
  	 describe:流程表服务类
	 
*/
@Service
@Transactional
public class WfProjectManager extends BaseManager<WfProject, String>
{

	private WfProjectDao wfProjectDao;
	private WfActivityDao wfActivityDao;
	public void setWfProjectDao(WfProjectDao dao)
	{
		this.wfProjectDao = dao;
	}


	public void setWfActivityDao(WfActivityDao wfActivityDao) {
		this.wfActivityDao = wfActivityDao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfProjectDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfProjectQuery query)
	{
		return wfProjectDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	@Transactional(readOnly = true)
	public Page findProjects(WfProjectQuery query){
		return wfProjectDao.findProjects(query);
	}
	
	public WfProject getProject(String groupFlag){
		return wfProjectDao.getProject(groupFlag);
	}
	
	public void save(WfProject wfProject){
		wfProjectDao.save(wfProject);
	}


	
}
