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

import cn.org.rapid_framework.page.*;


import com.opendata.application.model.*;
import com.opendata.application.dao.*;
import com.opendata.application.vo.query.*;

/**
 * 应用依赖关系的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class ApplicationRelyManager extends BaseManager<ApplicationRely,java.lang.String>{

	private ApplicationRelyDao applicationRelyDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setApplicationRelyDao(ApplicationRelyDao dao) {
		this.applicationRelyDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.applicationRelyDao;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPage(ApplicationRelyQuery query) {
		return applicationRelyDao.findPage(query);
	}
	
	/**
	 * 根据应用和类型查询符合条件的应用依赖关系集合
	 * @param applicationId 应用ID
	 * @param type 类型
	 * @return
	 */
	public List<ApplicationRely> findRelyApplicationByType(String applicationId,String type){
		return this.applicationRelyDao.getHibernateTemplate().find(" from ApplicationRely t where relyApplicationId=? and type=? and df=0 ", applicationId,type);
	}
	
	/**
	 * 删除某应用的所有依赖关系
	 * @param app
	 */
	public void deleteByApplication(Application app){
		//查找应用的依赖关系 删除
		List<ApplicationRely> appRelyList = this.applicationRelyDao.findAllByDf("applicationId", app.getId());
		for(ApplicationRely appRely : appRelyList){
			appRely.setDf("1");
			this.applicationRelyDao.update(appRely);
		}
		//查找应用的被依赖关系 删除
		appRelyList = this.applicationRelyDao.findAllByDf("relyApplicationId", app.getId());
		for(ApplicationRely appRely : appRelyList){
			appRely.setDf("1");
			super.delete(appRely);
		}
	}
	
}
