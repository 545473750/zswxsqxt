/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.dao;

import static cn.org.rapid_framework.util.SqlRemoveUtils.removeFetchKeyword;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javacommon.xsqlbuilder.XsqlBuilder;
import javacommon.xsqlbuilder.XsqlBuilder.XsqlFilterResult;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.rs.model.RsResources;
import com.opendata.rs.vo.query.RsResourcesQuery;

/**
 * dao层,用于和数据库进行交互
 * 
 * @author 王海龙
 */
@Repository
public class RsResourcesDao extends BaseHibernateDao<RsResources, java.lang.String> {

	public Class getEntityClass() {
		return RsResources.class;
	}

	public Page findPage(RsResourcesQuery query) {
		// XsqlBuilder syntax,please see
		// http://code.google.com/p/rapid-xsqlbuilder
		// [column]为字符串拼接, {column}为使用占位符.
		// [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接
		// [column] 为PageRequest的属性
		String sql = "select t from RsResources t where 1=1 "
				+ "/~ and t.logo = {logo} ~/" + "/~ and t.title like {title} ~/"
				+ "/~ and t.keyword = {keyword} ~/"
				+ "/~ and t.description = {description} ~/"
				+ "/~ and t.author = {author} ~/"
				+ "/~ and t.attaPath = {attaPath} ~/"
				+ "/~ and t.uploadType = {uploadType} ~/"
				+ "/~ and t.uploadTime = {uploadTime} ~/"
				+ "/~ and t.uploadUserId = {uploadUserId} ~/"
				+ "/~ and t.dataType = {dataType} ~/"
				+ "/~ and t.resourcesTypeId = {resourcesTypeId} ~/"
				+ "/~ and t.thumbnail = {thumbnail} ~/"
				+ "/~ and t.auditStatus = {auditStatus} ~/"
				+ "/~ and t.browseNumber = {browseNumber} ~/"
				+ "/~ and t.downloadNumber = {downloadNumber} ~/"
				+ "/~ and t.ts >= {tsBegin} ~/" + "/~ and t.ts <= {tsEnd} ~/"
				+ "/~ order by [sortColumns] ~/";

		return pageQuery(sql, query);
	}

	/**
	 * 按下载排行查询
	 * 
	 * @param query
	 * @return
	 */
	public Page findRanking(RsResourcesQuery query) {
		//query.setSortColumns("download_number desc");
		String sql = "select t from RsResources t where 1=1"
//			+ "/~ and t.title like {title} ~/"
//			+ "/~ and t.resourcesTypeId = {resourcesTypeId} ~/"
//			+ "/~ and t.auditStatus = {auditStatus} ~/"
//			+ "/~ and t.uploadUserId = {uploadUserId} ~/"
			+ "/~ order by [sortColumns] ~/";
		return pageQuery(sql, query);
	}

	/**
	 * 所有未审核的资源
	 */
	public Page<RsResources> findUnaudited(RsResourcesQuery query) {
		String sql = "select t from RsResources t where 1=1 and auditStatus = '0' "
				+ "/~ and t.title like {title} ~/"
				+ "/~ and t.resourcesTypeId = {resourcesTypeId} ~/"
				+ "/~ order by [sortColumns] ~/";
		return pageQuery(sql, query);
	}

	/**
	 * 根据用户模糊查询用户收藏的资源
	 * 
	 * @param query
	 * @param userid
	 * @return
	 */
	public Page findResourceFavoriteByuser(RsResourcesQuery query, String userid) {
		String sql = "select resou.* from rs_resources resou,rs_favorites favorites where resou.id=favorites.resources_id and favorites.user_id = '"
				+ userid + "'";
		sql += "/~ and resou.title like {title} ~/"
				+ "/~ and resou.audit_status = {auditStatus} ~/"
				+ "/~ and resou.resources_type_id = {resourcesTypeId} ~/";
		return pageSqlQuery(sql, query);
	}

	/**
	 * 根据用户模糊查询个人上传的资源
	 * 
	 * @param query
	 * @return
	 */
	public Page findResourceByuser(RsResourcesQuery query) {
		String sql = "select t from RsResources t where 1=1"
				+ "/~ and t.title like {title} ~/"
				+ "/~ and t.resourcesTypeId = {resourcesTypeId} ~/"
				+ "/~ and t.auditStatus = {auditStatus} ~/"
				+ "/~ and t.uploadUserId = {uploadUserId} ~/"
				+ "/~ order by [sortColumns] ~/";
		return pageQuery(sql, query);
	}

	/**
	 * 简单查询
	 * 
	 * @param query
	 * @return
	 */

	public Page<RsResources> easySearch(RsResourcesQuery query) {
		String sign = "<>"; // 如果所有的字段都未空，说明是全部查询
		if (StringUtils.isBlank(query.getTitle())
				&& StringUtils.isBlank(query.getAuthor())
				&& StringUtils.isBlank(query.getKeyword())
				&& StringUtils.isBlank(query.getDescription()))
			sign = "=";
		String sql = " select r.* from rs_resources r where  r.audit_status=1 and (1"
				+ sign
				+ "1"
				+ "/~ or r.title like {title} ~/"
				+ "/~ or r.keyword like {keyword} ~/"
				+ "/~ or r.description like {description} ~/"
				+ "/~ or r.author like {author} ~/"
				+ ")"
				+ "/~ order by [sortColumns] ~/";
		return this.pageSqlQuery(sql, query);
	}
	
	/**
	 * 资源搜索
	 * 
	 * @param query
	 * @return
	 */

	public Page<RsResources> resoucesSearch(RsResourcesQuery query) {
		String sign = "<>"; // 如果所有的字段都未空，说明是全部查询
		if (StringUtils.isBlank(query.getTitle()) && StringUtils.isBlank(query.getAuthor()) && StringUtils.isBlank(query.getKeyword()) && StringUtils.isBlank(query.getDescription()))
			sign = "=";
		String sql = " select r.* from rs_resources r where  r.audit_status=1 and (1"
				+ sign
				+ "1"
				+ "/~ or r.title like '%[title]%' ~/"
				+ "/~ or r.keyword like '%[keyword]%' ~/"
				+ "/~ or r.description like '%[description]%' ~/"
				+ "/~ or r.author like '%[author]%' ~/"
				+ ")"
				+ "/~ and r.resources_type_id = {resourcesTypeId} ~/"
				+ " order by r.ts desc";
		return this.pageSqlQuery(sql, query);
	}
	

	/**
	 * 查找某个节点下的所有资源
	 * 
	 * @param tid
	 * @param query
	 * @return
	 */
	public Page<RsResources> findRootChildren(String tid, RsResourcesQuery query) {
		String sql = "select * from (select rs.* from rs_resources rs, rs_resources_structure rrs where rs.id=rrs.resources_id and rrs.structure_id='"
				+ tid + "') r where r.audit_status=1 ";
		sql += "/~ order by [sortColumns] ~/";
		return this.pageSqlQuery(sql, query);
	}
	
	/**
	 * 有关联关系的资源
	 * @param query
	 * @return
	 */
	public Page<RsResources> beRelatedResouces(RsResourcesQuery query){
		String sql = "select t.* from rs_resources t where t.id in (select s.target_resources_id from rs_resources_resources s where s.start_resources_id ='"+query.getId()+"')" 
			+"/~ and t.title like {title} ~/"
			+ "/~ and t.audit_status = {auditStatus} ~/"
			+ "/~ and t.resources_type_id = {resourcesTypeId} ~/";
		return this.pageSqlQuery(sql, query);
	}
	
	/**
	 * 未关联关系的资源
	 * @param query
	 * @return
	 */
	public Page<RsResources> irrelevantResouces(RsResourcesQuery query){
		String sql = "select t.* from rs_resources t where t.id not in (select s.target_resources_id from rs_resources_resources s where s.start_resources_id ='"+query.getId()+"')" 
			+"/~ and t.title like {title} ~/"
			+ "/~ and t.audit_status = {auditStatus} ~/"
			+ "/~ and t.resources_type_id = {resourcesTypeId} ~/";
		return this.pageSqlQuery(sql, query);
	}

	/**
	 * 高级查询,如果什么都不选，默认全部
	 * 
	 * @param query
	 * @param map
	 * @return
	 */
	public Page<RsResources> advancedSearch(RsResourcesQuery query,
			Map<String, String> map) {
		String codes = "";
		String values = "";
		String types = "";
		if (map != null) {
			for (String str : map.keySet())
				codes += "'" + str.trim() + "',";
			codes = codes.substring(0, codes.length() - 1);
			for (String str : map.values())
				values += "'" + str.trim() + "',";
			values = values.substring(0, values.length() - 1);
		}
		if(!query.getDataType().equals(""))
		{
			for (String str : query.getDataType().split(","))
				types += "'" + str.trim() + "',";
			types = types.substring(0, types.length() - 1);
		}
		String sql = "select * from (select distinct r.* from rs_resources r,rs_expand_property rexp where  r.audit_status=1"
				+ "/~ and r.title like '%[title]%' ~/"
				+ "/~ and r.keyword like '%[keyword]%' ~/"
				+ "/~ and r.description like '%[description]%' ~/"
				+ "/~ and r.author like '%[author]%' ~/"
				+ "/~ and r.logo like '%[logo]%' ~/";
			if(!types.equals("")) {
				sql += " and r.data_type in ("
				+ types
				+ ")";
			}
				sql += " and r.id=rexp.resources_id ";
			
			if(map!=null) {	
				sql += " and rexp.attr_code in ("
				+ codes
				+ ")"
				+ " and rexp.attr_value in ("
				+ values
				+ ")";
			}
				sql += ") as a";
				sql += "/~ order by [sortColumns] ~/";
		return pageSqlQuery(sql, query);
	}

	public Page pageSqlQuery(final String sql, final PageRequest pageRequest) {
		final String countQuery = "select count(*) "
				+ removeSelect(removeFetchKeyword((sql)));
		return pageSqlQuery(sql, countQuery, pageRequest);
	}

	public Page pageSqlQuery(final String sql, String countQuery,
			final PageRequest pageRequest) {
		Map otherFilters = new HashMap(1);
		otherFilters.put("sortColumns", pageRequest.getSortColumns());

		XsqlBuilder builder = getXsqlBuilder();

		// 混合使用otherFilters与pageRequest为一个filters使用
		XsqlFilterResult queryXsqlResult = builder.generateHql(sql,
				otherFilters, pageRequest);
		XsqlFilterResult countQueryXsqlResult = builder.generateHql(countQuery,
				otherFilters, pageRequest);

		return PageQueryUtils.pageSqlQuery(getHibernateTemplate(), pageRequest,
				queryXsqlResult, countQueryXsqlResult);
	}

	static class PageQueryUtils {
		private static Page pageSqlQuery(HibernateTemplate template,
				final PageRequest pageRequest,
				final XsqlFilterResult queryXsqlResult,
				final XsqlFilterResult countQueryXsqlResult) {
			return (Page) template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {

					Query query = setQueryParameters(
							session.createSQLQuery(queryXsqlResult.getXsql())
									.addEntity(RsResources.class), pageRequest);
					Query countQuery = setQueryParameters(session
							.createSQLQuery(removeOrders(countQueryXsqlResult
									.getXsql())), pageRequest);

					return executeQueryForPage(pageRequest, query, countQuery);
				}
			});
		}

		private static Object executeQueryForPage(
				final PageRequest pageRequest, Query query, Query countQuery) {
			Page page = new Page(pageRequest,
					((Number) countQuery.uniqueResult()).intValue());
			if (page.getTotalCount() <= 0) {
				page.setResult(new ArrayList(0));
			} else {
				page.setResult(query.setFirstResult(page.getFirstResult())
						.setMaxResults(page.getPageSize()).list());
			}
			return page;
		}

		public static Query setQueryParameters(Query q, Object params) {
			q.setProperties(params);
			return q;
		}

		public static Query setQueryParameters(Query q, Map params) {
			q.setProperties(params);
			return q;
		}
	}

}
