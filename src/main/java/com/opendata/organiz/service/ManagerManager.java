package com.opendata.organiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.dao.ManagerDao;
import com.opendata.organiz.model.Manager;
import com.opendata.organiz.model.User;
import com.opendata.organiz.query.ManagerQuery;

/**
  	 describe:系统分级管理员服务类
	 
*/
@Service
@Transactional
public class ManagerManager extends BaseManager<Manager, String>
{

	private ManagerDao managerDao;

	public void setManagerDao(ManagerDao dao)
	{
		this.managerDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.managerDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(ManagerQuery query)
	{
		return managerDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}

	public List findAllName() {
		// TODO Auto-generated method stub
		return this.managerDao.findName();
	}

	public User findAllNameAndId(String user) {
		// TODO Auto-generated method stub
		return this.managerDao.findNameAndId(user);
	}

	public List findById(String id) {
		// TODO Auto-generated method stub
		return this.managerDao.findById(id);
	}

	public User findUserById(String id) {
		// TODO Auto-generated method stub
		return this.managerDao.findUserById(id);
	}

	

	
}
