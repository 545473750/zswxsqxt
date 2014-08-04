/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.dao;

import java.util.*;

import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.User;
import com.opendata.sys.model.*;
import com.opendata.sys.vo.query.*;
import org.springframework.stereotype.Repository;


/**
 * 菜单管理的DAO层 负责和数据库的交互
 * @author 付威
 */
@Repository
public class ResourcesDao extends BaseHibernateDao<Resources,java.lang.String>{

	public Class getEntityClass() {
		return Resources.class;
	}
	/**
	 * 根据查询条件查询结果 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	public Page findPage(ResourcesQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Resources t where 1=1 "
				  		+ "/~ and t.name like '%[name]%' ~/"
				  		+ "/~ and t.code like '%[code]%' ~/"
				  		+ "/~ and t.sequence = {sequence} ~/";
				  		if("-1".equals(query.getParentId())){
				  			sql += " and t.parentId is null";
				  		}else{
				  			sql +=  "/~ and t.parentId = {parentId} ~/";
				  		}
				  		
				  		sql +=  "/~ and t.permissionId = {permissionId} ~/"
				  		+ "/~ and t.icon = {icon} ~/"
				  		+ "/~ and t.bigIcon = {bigIcon} ~/"
				  		+ "/~ and t.type = {type} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	
	/**
	 * 取得某父菜单下的最大排序号
	 * @param parentRerourcesId 父菜单ID
	 * @return
	 */
	 @SuppressWarnings("rawtypes")
	 public int findSequence(String parentRerourcesId) {
			String sql = "select " +
							"MAX(res.sequence) " +
						 "from Resources res where res.df = '0'";
			StringBuffer sb = new StringBuffer(sql);
			if(null == parentRerourcesId || "root".equals(parentRerourcesId)|| "-1".equals(parentRerourcesId)) {
				sb.append(" and res.parentId is NULL");
			} else {
				sb.append(" and  res.parentId = '" + parentRerourcesId + "'");
			}
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
