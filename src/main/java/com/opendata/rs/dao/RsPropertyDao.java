/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.dao;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.rs.model.RsProperty;
import com.opendata.rs.vo.query.RsPropertyQuery;

/**
 * dao层,用于和数据库进行交互
 * 
 * @author 王海龙
 */
@Repository
public class RsPropertyDao extends BaseHibernateDao<RsProperty, java.lang.String> {

	public Class getEntityClass() {
		return RsProperty.class;
	}
	
	public Page findPage(RsPropertyQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from RsProperty t where 1=1 "
				  		+ "/~ and t.attrCode = {attrCode} ~/"
				  		+ "/~ and t.attrName like {attrName} ~/"
				  		+ "/~ and t.inputType = {inputType} ~/"
				  		+ "/~ and t.optionalValue = {optionalValue} ~/"
				  		+ "/~ and t.attrSource = {attrSource} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.editable = {editable} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	
}
