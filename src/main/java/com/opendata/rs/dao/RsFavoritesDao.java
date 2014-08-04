///*
// * Powered By [rapid-framework]
// * Web Site: http://www.rapid-framework.org.cn
// * Google Code: http://code.google.com/p/rapid-framework/
// * Since 2008 - 2011
// */
//
//package com.opendata.rs.dao;
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
//
//import static cn.org.rapid_framework.util.ObjectUtils.*;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class RsFavoritesDao extends BaseHibernateDao<RsFavorites,java.lang.String>{
//
//	public Class getEntityClass() {
//		return RsFavorites.class;
//	}
//	
//	public Page findPage(RsFavoritesQuery query) {
//        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
//        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
//        // [column] 为PageRequest的属性
//		String sql = "select t from RsFavorites t where 1=1 "
//				  		+ "/~ and t.resourcesId = {resourcesId} ~/"
//				  		+ "/~ and t.userId = {userId} ~/"
//				+ "/~ order by [sortColumns] ~/";
//
//        return pageQuery(sql,query);
//	}
//	
//
//}
