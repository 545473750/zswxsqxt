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
 * 日志的DAO层 负责和数据库的交互
 * @author 付威
 */
@Repository
public class LogDao extends BaseHibernateDao<Log, java.lang.String> {
	
	public Class getEntityClass() {
		return Log.class;
	}
	
	/**
	 * 根据查询条件查询结果 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	public Page findPage(LogQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Log t where 1=1 "
				  		+ "/~ and t.username like '%[username]%' ~/"
				  		+ "/~ and t.type = {type} ~/"
				  		+ "/~ and t.ip = {ip} ~/"
				  		+ "/~ and t.permission = {permission} ~/"
				  		+ "/~ and t.resources = {resources} ~/"
				  		+ "/~ and t.content = {content} ~/"
				  		+ "/~ and t.df = {df} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}

}
