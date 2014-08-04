///*
// * Powered By [rapid-framework]
// * Web Site: http://www.rapid-framework.org.cn
// * Google Code: http://code.google.com/p/rapid-framework/
// * Since 2008 - 2011
// */
//
//package com.opendata.rs.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
//import com.opendata.common.base.*;
//import com.opendata.common.util.*;
//
//import cn.org.rapid_framework.util.*;
//import cn.org.rapid_framework.web.util.*;
//import cn.org.rapid_framework.page.*;
//import cn.org.rapid_framework.page.impl.*;
//
//import com.opendata.common.util.Util;
//
//import com.opendata.rs.model.*;
//import com.opendata.rs.dao.*;
//import com.opendata.rs.service.*;
//import com.opendata.rs.vo.query.*;
//
///**
// * @author 王海龙
// * @version 1.0
// * @since 1.0
// */
//
//@Service
//@Transactional
//public class RsFavoritesManager extends BaseManager<RsFavorites,java.lang.String>{
//
//	private RsFavoritesDao rsFavoritesDao;
//	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
//	public void setRsFavoritesDao(RsFavoritesDao dao) {
//		this.rsFavoritesDao = dao;
//	}
//	public EntityDao getEntityDao() {
//		return this.rsFavoritesDao;
//	}
//	
//	@Transactional(readOnly=true)
//	public Page findPage(RsFavoritesQuery query) {
//		return rsFavoritesDao.findPage(query);
//	}
//	
//}
