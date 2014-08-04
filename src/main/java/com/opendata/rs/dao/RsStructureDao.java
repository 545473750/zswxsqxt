/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.rs.model.RsStructure;
import com.opendata.rs.vo.query.RsStructureQuery;

/**
 * dao层,用于和数据库进行交互
 * @author 王海龙
 *
 */
@Repository
public class RsStructureDao extends BaseHibernateDao<RsStructure, java.lang.String> {

	public Class getEntityClass() {
		return RsStructure.class;
	}
	
	public Page findPage(RsStructureQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from RsStructure t where 1=1 "
				  		+ "/~ and t.name like {name} ~/"
				  		+ "/~ and t.description = {description} ~/"
				  		+ "/~ and t.parentId = {parentId} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				+ "/~ order by [sortColumns] ~/";

        return super.pageQuery(sql,query);
	}
	
	public List<RsStructure> findAllRoot() {
		try {
			return getSession().createSQLQuery("select * from rs_structure where parent_id=rtrim('0')").addEntity(getEntityClass()).list();
		}
		catch(Exception ex) {
			return null;
		}
	}

}
