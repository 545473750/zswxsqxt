/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;

import com.opendata.sys.model.*;
import com.opendata.sys.dao.*;
import com.opendata.sys.vo.query.*;

/**
 * 数据字典项业务层,用于对上层action提供支持
 * @author顾保臣
 */
@Service
@Transactional
public class DictitemManager extends BaseManager<Dictitem,java.lang.String>{

	private DictitemDao dictitemDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setDictitemDao(DictitemDao dao) {
		this.dictitemDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.dictitemDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(DictitemQuery query) {
		return dictitemDao.findPage(query);
	}
	
	public Dictitem findByCode(String code){
		return  dictitemDao.findByDf("code",code);
	}
	
	
	/**
	 * 修改前判断字典项code是否存在
	 * @param name
	 * @param esApplicationId
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean ifSameCode(Dictitem dictitem){
		return dictitemDao.isUnique(dictitem, "code");
	}
	
}
