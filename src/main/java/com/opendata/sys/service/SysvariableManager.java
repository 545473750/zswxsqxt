/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.opendata.common.base.*;
import com.opendata.common.util.Util;

import cn.org.rapid_framework.page.*;


import com.opendata.sys.model.*;
import com.opendata.sys.dao.*;
import com.opendata.sys.vo.query.*;

/**
 * 系统变量业务层,用于对上层action提供支持
 * @author顾保臣
 */
@Service
@Transactional
public class SysvariableManager extends BaseManager<Sysvariable,java.lang.String>{

	private SysvariableDao sysvariableDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setSysvariableDao(SysvariableDao dao) {
		this.sysvariableDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.sysvariableDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(SysvariableQuery query) {
		return sysvariableDao.findPage(query);
	}
	
	public Sysvariable findByName(String name){
		 return sysvariableDao.findByDf("name",name);
	}
	
	/**
	 * 根据系统变量名称查找系统变量的编号
	 * @param name
	 * @return
	 */
	public String getCodeByName(String name){
		List<Sysvariable> l = sysvariableDao.findAllByDf("name",name);
		if(l.size()>0){
			Sysvariable sysvariable = l.get(0);
			return sysvariable.getCode();
		}
		return null;
	}
	
}
