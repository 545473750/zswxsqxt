/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.User;
import com.opendata.sys.model.System;
import com.opendata.sys.vo.query.SystemQuery;
import org.springframework.stereotype.Repository;

/**
 * 系统同步模块dao层,用于和数据库进行交互
 * @author顾保臣
 */
@SuppressWarnings("rawtypes")
@Repository
public class SystemDao extends BaseHibernateDao<System,java.lang.String>{
	
	public Class getEntityClass() {
		return System.class;
	}
	
	public Page findPage(SystemQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from System t where 1=1 "
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				  		+ "/~ and t.code like '%[code]%' ~/" // 编号
				  		+ "/~ and t.name like '%[name]%' ~/" // 名称
				  		+ "/~ and t.username = {username} ~/"
				  		+ "/~ and t.password = {password} ~/"
				  		+ "/~ and t.manager = {manager} ~/"
				  		+ "/~ and t.phone = {phone} ~/"
				  		+ "/~ and t.remark = {remark} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}

	/**
	 * 校验编号是否唯一
	 * @param code
	 */
	@SuppressWarnings("unchecked")
	public List<com.opendata.sys.model.System> checkCode(String code) {
		return this.getHibernateTemplate().find("from System t where 1=1 and t.df = '0' and t.code = '" + code + "'");
	}

	public System getSystemById(String systemId) {
		return this.getHibernateTemplate().get(com.opendata.sys.model.System.class, systemId);
	}

	/**
	 * 获取所有的系统同步数据,支持分页
	 * @param code
	 */
	public Set<User> findAllData(Set<User> users, int startIndex, int endIndex) {
//		this.getSession().createQuery("").setFirstResult(startIndex).setMaxResults(endIndex);
		
//		String sql = "select * from t_user as t1 " +
//				"LEFT JOIN t_user_system as t2 on t1.id = t2.user_id " +
//				"LEFT JOIN t_system t3 on t2.system_id = t3.id";
//		List<User> result = this.getSession().createSQLQuery(sql).setFirstResult(startIndex).setMaxResults(endIndex).list();
//		Set<User> users = new HashSet<User>(0);
//		for (User user : result) {
//			users.add(user);
//		}

		if(users != null) {
			ArrayList list = new ArrayList(users);
			if(endIndex > users.size()) {
				endIndex = users.size();
			}
			Set<User> _users = new HashSet<User>(list.subList(startIndex, endIndex));
			return _users;
		}
		return null;
	}

}

	