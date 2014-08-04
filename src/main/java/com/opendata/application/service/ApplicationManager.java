/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.opendata.common.base.*;
import com.opendata.sys.model.Partition;


import cn.org.rapid_framework.page.*;


import com.opendata.application.model.*;
import com.opendata.application.dao.*;
import com.opendata.application.vo.query.*;

/**
 * 应用的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class ApplicationManager extends BaseManager<Application,java.lang.String>{

	private ApplicationDao applicationDao;
	private PermissionManager permissionManager;
	private ApplicationRelyManager applicationRelyManager;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setApplicationDao(ApplicationDao dao) {
		this.applicationDao = dao;
	}
	
	
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
	
	
	public void setApplicationRelyManager(
			ApplicationRelyManager applicationRelyManager) {
		this.applicationRelyManager = applicationRelyManager;
	}


	public EntityDao getEntityDao() {
		return this.applicationDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(ApplicationQuery query) {
		return applicationDao.findPage(query);
	}
	
	public List<Application> findAllByDf(){
		return this.applicationDao.getHibernateTemplate().find("  from Application app where df=0 order by sequence");
	}
	
	/**
	 * 根据应用Code查询应用
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public Application findByCode(String code){
		return this.applicationDao.findByDf("code", code);
	}
	
	/**
	 * 根据应用Code、版本查询应用
	 * @param code
	 * @param version
	 * @return
	 */
	@Transactional(readOnly=true)
	public Application findByCodeVersion(String code,String version){
		List<Application> list =  this.applicationDao.getHibernateTemplate().find(" from Application app where df=0 and code=? and version=?", code,version);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	
	
	/**
	 * 删除应用
	 */
	public void delete(Application application){
		application.setDf("1");
		
		//删除应用时 把应用与分区的关系删除
		application.setPartitions(new HashSet<Partition>(0));
		//删除应用时 把应用的功能入口 删除
		permissionManager.deleteByApplication(application);
		//删除应用时 把应用的依赖关系删除 包括被依赖关系
		applicationRelyManager.deleteByApplication(application);
		super.delete(application);
	}
	
	/**
	 * 取得最大排序号
	 * @return
	 */
	@Transactional(readOnly=true)
	public int findSequence() {
		return applicationDao.findSequence();
	}
	
}
