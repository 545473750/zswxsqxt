/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.dao;

import java.util.*;

import com.opendata.application.model.Permission;
import com.opendata.application.vo.query.PermissionQuery;
import com.opendata.common.base.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.*;

/**
 * 应用访问入口DAO层 负责和数据库的交互
 * @author 付威
 */




@Repository
public class PermissionDao extends BaseHibernateDao<Permission,Long>{

	public Class getEntityClass() {
		return Permission.class;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	public Page findPage(PermissionQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Permission t where 1=1 "
				  		+ "/~ and t.code like '%[code]%'  ~/"
				  		+ "/~ and t.name like '%[name]%'  ~/"
				  		+ "/~ and t.url like '%[url]%'  ~/"
				  		+ "/~ and t.df = {df} ~/";
				  		if(StringUtils.isBlank(query.getParentId())||"root".equals(query.getParentId())){
				  			sql += " and t.parentId is null";
				  		}else{
				  			sql +=  "/~ and t.parentId = {parentId} ~/";
				  		}
				  	sql+="/~ and t.applicationId = {applicationId} ~/"
				  		+ "/~ and t.ts >= {createtimeBegin} ~/"
						+ "/~ and t.ts <= {createtimeEnd} ~/"
				  		+ "/~ and t.description = {description} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	
	/**
	 * 取得所有的应用访问入口列表 按应用ID 父ID排序
	 */
    public List<Permission> findAll(){
    	List permissionAll =  this.getHibernateTemplate().find(" from Permission t where df='0' order by applicationId,parentId");
    	return permissionAll; 
    }
    
    /**
     * 取得某应用下某父访问入口下最大排序号
     * @param parentPermissionId 父访问入口ID
     * @param applicationId 应用ID
     * @return
     */
    @SuppressWarnings("rawtypes")
	public int findSequence(String parentPermissionId,String applicationId) {
		String sql = "select " +
						"MAX(per.sequence) " +
					 "from Permission per where per.df = '0'";
		StringBuffer sb = new StringBuffer(sql);
		if(null == parentPermissionId || "root".equals(parentPermissionId)|| "-1".equals(parentPermissionId)) {
			sb.append(" and per.parentId is NULL");
		} else {
			sb.append(" and per.parentId = '" + parentPermissionId + "'");
		}
		sb.append(" and per.applicationId = '" + applicationId + "'");
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
