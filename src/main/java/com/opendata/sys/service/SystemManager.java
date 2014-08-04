/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.*;
import com.opendata.organiz.model.User;
import com.opendata.sys.dao.SystemDao;
import com.opendata.sys.model.System;
import com.opendata.sys.vo.query.SystemQuery;

/**
 * 系统同步业务层,用于对上层action提供支持
 * @author顾保臣
 */
@Service
@Transactional
@SuppressWarnings("rawtypes")
public class SystemManager extends BaseManager<com.opendata.sys.model.System,java.lang.String>{

	private SystemDao systemDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setSystemDao(SystemDao dao) {
		this.systemDao = dao;
	}
	
	public EntityDao getEntityDao() {
		return this.systemDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(SystemQuery query) {
		return systemDao.findPage(query);
	}

	/**
	 * 校验编号是否唯一
	 * @param code
	 */
	@Transactional(readOnly=true)
	public List<com.opendata.sys.model.System> checkCode(String code) {
		return this.systemDao.checkCode(code);
	}

	@Transactional(readOnly=true)
	public System getSystemById(String systemId) {
		return this.systemDao.getSystemById(systemId);
	}

	/**
	 * 为webservice提供支持
	 * 获取所有的系统同步数据
	 */
	public Set<User> findAllData(Set<User> users, int startIndex, int endIndex) {
		return this.systemDao.findAllData(users, startIndex, endIndex);
	}
	
}
