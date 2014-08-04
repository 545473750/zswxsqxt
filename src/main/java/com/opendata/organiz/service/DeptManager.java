package com.opendata.organiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.dao.DeptDao;
import com.opendata.organiz.model.Dept;
import com.opendata.organiz.vo.query.DeptQuery;

/**
  	 describe:部门
	 
*/
@Service
@Transactional
public class DeptManager extends BaseManager<Dept, String>
{

	private DeptDao deptDao;

	public void setDeptDao(DeptDao dao)
	{
		this.deptDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.deptDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(DeptQuery query)
	{
		return deptDao.findPage(query);
	}


	

	
}
