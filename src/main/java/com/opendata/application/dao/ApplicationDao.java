/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.dao;

import java.util.*;

import com.opendata.common.base.*;

import cn.org.rapid_framework.page.*;

import org.springframework.stereotype.Repository;
import com.opendata.application.model.*;
import com.opendata.application.vo.query.*;

/**
 * 应用管理的DAO层 负责和数据库的交互
 * @author 付威
 */
@Repository
public class ApplicationDao extends BaseHibernateDao<Application,java.lang.String>{

	public Class getEntityClass() {
		return Application.class;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	public Page findPage(ApplicationQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Application t where 1=1 "
				  		+ "/~ and t.code like '%[code]%' ~/"
				  		+ "/~ and t.name like '%[name]%'  ~/"
				  		+ "/~ and t.description = {description} ~/"
				  		+ "/~ and t.state = {state} ~/"
					+ "/~ and t.ts >= {createtimeBegin} ~/"
					+ "/~ and t.ts <= {createtimeEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	
	/**
	 * 取得最大序列号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	 public int findSequence() {
			String sql = "select " +
							"MAX(app.sequence) " +
						 "from Application app where app.df='0' ";
			StringBuffer sb = new StringBuffer(sql);
			List result = this.getHibernateTemplate().find(sb.toString());
			if(result == null || result.size() == 0) {
				return 1;
			} else {
				if(result.get(0) != null) {
					int seq = Integer.parseInt(result.get(0).toString());
					return seq + 1 ;
				} else {
					return 1;
				}
			}
		}
}
