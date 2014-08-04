/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.dao;

import com.opendata.common.base.*;

import cn.org.rapid_framework.page.*;

import com.opendata.sys.model.*;
import com.opendata.sys.vo.query.*;
import org.springframework.stereotype.Repository;

/**
 * 数据字典项dao层，用于和数据库进行交互
 * @author顾保臣
 */
@Repository
public class DictitemDao extends BaseHibernateDao<Dictitem,java.lang.String>{

	public Class getEntityClass() {
		return Dictitem.class;
	}
	
	public Page findPage(DictitemQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Dictitem t where 1=1 "
				  		+ "/~ and t.name like '%[name]%' ~/"
				  		+ "/~ and t.code like '%[code]%' ~/"
//				  		+ "/~ and t.description = {description} ~/"
				  		+ "/~ and t.editf = {editf} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				  		+ "/~ and t.applicationId = {applicationId} ~/" // 所属应用
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
}
