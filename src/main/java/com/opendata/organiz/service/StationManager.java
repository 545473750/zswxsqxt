/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.*;
import com.opendata.organiz.dao.*;
import com.opendata.organiz.vo.query.*;

/**
 * 岗位业务层
 * @author 顾保臣
 */
@Service
@Transactional
public class StationManager extends BaseManager<Station,java.lang.String>{

	private StationDao stationDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setStationDao(StationDao dao) {
		this.stationDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.stationDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(StationQuery query) {
		return stationDao.findPage(query);
	}
	
	@Transactional(readOnly=true)
	public Page findPageCommon(StationQuery query) {
		return stationDao.findPageCommon(query);
	}
	
}
