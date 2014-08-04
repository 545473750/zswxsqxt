package com.opendata.common.base;

import static cn.org.rapid_framework.util.SqlRemoveUtils.removeFetchKeyword;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javacommon.xsqlbuilder.SafeSqlProcesser;
import javacommon.xsqlbuilder.SafeSqlProcesserFactory;
import javacommon.xsqlbuilder.XsqlBuilder;
import javacommon.xsqlbuilder.XsqlBuilder.XsqlFilterResult;
import javacommon.xsqlbuilder.safesql.DirectReturnSafeSqlProcesser;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * 纯Hibernate Entity DAO基类。 通过泛型，子类无需扩展任何函数即拥有完整的CRUD操作。 。
 * @author 付威
 */
public abstract class BaseHibernateDao<E,PK extends Serializable> extends GenericSQLDao implements EntityDao<E,PK>{
	/**
	 * 日志记录器
	 */
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * 根据查询取得结果 返回类型为long
	 * @param queryString 查询语句
	 * @return 长整型
	 */
	public long queryForLong(final String queryString) {
		return queryForLong(queryString,new Object[]{});
	}
	
	
	/**
	 * 根据查询取得结果 返回类型为long
	 * @param queryString 查询语句
	 * @param value 参数集合
	 * @return 长整型
	 */
	public long queryForLong(final String queryString,Object value) {
		return queryForLong(queryString,new Object[]{value});
	}
	
	/**
	 * 根据查询取得结果 返回类型为long
	 * @param queryString 查询语句
	 * @param values 参数集合
	 * @return 长整型
	 */
	public long queryForLong(final String queryString,Object[] values) {
		return DataAccessUtils.longResult(getHibernateTemplate().find(queryString, values));
	}
	
	/**
	 * 得到全部数据,但执行分页
	 * @param pageRequest
	 * @return 分页对象
	 */
	public Page findAll(final PageRequest pageRequest) {
		return (Page)getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuffer queryString = new StringBuffer(" FROM ").append(getEntityClass().getSimpleName());
				String countQueryString = "SELECT count(*) " + queryString.toString();
				if(StringUtils.hasText(pageRequest.getSortColumns())) {
					queryString.append(" ORDER BY "+pageRequest.getSortColumns());
				}
				
				Query query = session.createQuery(queryString.toString());
				Query countQuery = session.createQuery(countQueryString);
				return PageQueryUtils.executeQueryForPage(pageRequest, query, countQuery);
			}
		});
	}
	
	/**
	 * 根据查询语句取得相应数据
	 * @param sql 查询语句
	 * @param pageRequest 查询条件
	 * @return 分页对象
	 */
	public Page pageQuery(final String sql,final PageRequest pageRequest) {
		final String countQuery = "select count(*) " + removeSelect(removeFetchKeyword((sql)));
		return pageQuery(sql,countQuery,pageRequest);
	}

	/**
	 * 根据查询语句取得相应数据
	 * @param sql 查询语句
	 * @param countQuery 总数
	 * @param pageRequest 查询条件
	 * @return page对象
	 */
	public Page pageQuery(final String sql,String countQuery,final PageRequest pageRequest) {
		Map otherFilters = new HashMap(1);
		otherFilters.put("sortColumns", pageRequest.getSortColumns());
		
		XsqlBuilder builder = getXsqlBuilder();
		
		//混合使用otherFilters与pageRequest为一个filters使用
		XsqlFilterResult queryXsqlResult = builder.generateHql(sql,otherFilters,pageRequest);
		XsqlFilterResult countQueryXsqlResult = builder.generateHql(countQuery,otherFilters,pageRequest);
		
		return PageQueryUtils.pageQuery(getHibernateTemplate(),pageRequest,queryXsqlResult,countQueryXsqlResult);
	}
	
	
	/**
	 * 取得XsqlBuilder对象
	 * @return 返回XsqlBuilder对象
	 */
	protected XsqlBuilder getXsqlBuilder() {
		SessionFactoryImpl sf = (SessionFactoryImpl)(getSessionFactory());
		Dialect dialect = sf.getDialect();
		
		//or SafeSqlProcesserFactory.getMysql();
		SafeSqlProcesser safeSqlProcesser = SafeSqlProcesserFactory.getFromCacheByHibernateDialect(dialect); 
		XsqlBuilder builder = new XsqlBuilder(safeSqlProcesser);
		
		if(builder.getSafeSqlProcesser().getClass() == DirectReturnSafeSqlProcesser.class) {
			System.err.println(BaseHibernateDao.class.getSimpleName()+".getXsqlBuilder(): 故意报错,你未开启Sql安全过滤,单引号等转义字符在拼接sql时需要转义,不然会导致Sql注入攻击的安全问题，请修改源码使用new XsqlBuilder(SafeSqlProcesserFactory.getDataBaseName())开启安全过滤");
		}
		return builder;
	}
	
	/**
	 * 内部静态类 page的工具类
	 * @author 
	 *
	 */
	static class PageQueryUtils {
		private static Page pageQuery(HibernateTemplate template,final PageRequest pageRequest, final XsqlFilterResult queryXsqlResult, final XsqlFilterResult countQueryXsqlResult) {
			Session session = template.getSessionFactory().getCurrentSession();
			Query query = setQueryParameters(session.createQuery(queryXsqlResult.getXsql()),pageRequest);
			Query countQuery = setQueryParameters(session.createQuery(removeOrders(countQueryXsqlResult.getXsql())),pageRequest);
			return (Page)executeQueryForPage(pageRequest, query, countQuery);
			/*return executeQueryForPage(pageRequest, query, countQuery);
			return (Page)template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					
					Query query = setQueryParameters(session.createQuery(queryXsqlResult.getXsql()),pageRequest);
					Query countQuery = setQueryParameters(session.createQuery(removeOrders(countQueryXsqlResult.getXsql())),pageRequest);
					
					return executeQueryForPage(pageRequest, query, countQuery);
				}
			});*/
		}
		
		/**
		 * 根据参数构造一个page对象
		 * @param pageRequest 查询结果
		 * @param query 查询条件
		 * @param countQuery 总数
		 * @return page对象 支持分页
		 */
		private static Object executeQueryForPage(final PageRequest pageRequest,Query query, Query countQuery) {
			Object totalResults = countQuery.uniqueResult();
			Page page = new Page(pageRequest,totalResults!=null?((Number)totalResults).intValue():0);
			if(page.getTotalCount() <= 0) {
				page.setResult(new ArrayList(0));
			}else {
				page.setResult(query.setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list());
			}
			return page;
		}
		
		/**
		 * 构造查询对象 设置查询参数到查询对象
		 * @param q 查询条件对象
		 * @param params 查询参数
		 * @return  查询对象
		 */
		public static Query setQueryParameters(Query q,Object params) {
			q.setProperties(params);
			return q;
		}
		
		/**
		 *构造查询对象 设置查询参数到查询对象 
		 * @param q 查询条件对象
		 * @param params 查询参数
		 * @return 查询对象
		 */
		public static Query setQueryParameters(Query q,Map params) {
			q.setProperties(params);
			return q;
		}
	}
	 
	/**
	 * 保存对象
	 * param entity 待操作实体
	 * @return 保存对象
	 */
	public void save(E entity) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * 查询所有数据
	 * @return 对象集合
	 */
	public List<E> findAll() {
		return getHibernateTemplate().loadAll(getEntityClass());
	}

	/**
	 * 根据ID查询对象
	 * @return 结果对象
	 */
	public E getById(PK id) {
		if(null==id){
			return null;
		}
		return (E)getHibernateTemplate().get(getEntityClass(),id);
	}
	
	
	/**
	 * 删除对象
	 * @param entity 待操作实体
	 */
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}
	
	/**
	 * 删除对象
	 * @param entity 待操作的序列化实体
	 */
	public void delete(Serializable entity) {
		getHibernateTemplate().delete(entity);
	}
	
	
	/**
	 * 根据ID删除对象
	 * @param id 待操作实体id值
	 */
	public void deleteById(PK id) {
		Object entity = getById(id);
		if(entity == null) {
			throw new ObjectRetrievalFailureException(getEntityClass(),id);
		}
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 更新对象
	 * @param entity 待操作实体
	 */
	public void update(E entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 新增或更新对象 如果对象的ID属性值不为空 则为更新 否则是新增
	 * @param entity 待操作实体
	 */
	public void saveOrUpdate(E entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 刷新对象
	 * @param entity 待操作实体
	 */
	public void refresh(Object entity) {
		getHibernateTemplate().refresh(entity);
	}

	/**
	 * 强制提交刷新session
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 将对象实例从session缓存清除
	 * @param entity 待操作实体
	 */
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	/**
	 * 保存多个对象
	 * @param entities 待保存的对象集合
	 */
	public void saveAll(Collection<E> entities) {
		for(Iterator<E> it = entities.iterator(); it.hasNext();) {
			save(it.next());
		}
	}

	/**
	 * 删除多个对象
	 * @param entities 待删除对象集合
	 */
	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 根据查询条件查询对象
	 * @param propertyName 对象属性名
	 * @param value 参数值
	 * @return 结果对象
	 */
    public E findByProperty(final String propertyName, final Object value){
    	StringBuffer queryString = new StringBuffer(" FROM ").append(getEntityClass().getSimpleName());
    	queryString.append(" t where 1=1 ");
    	queryString.append(" and t." +propertyName +" = ? ");
    	return (E)this.getSession().createQuery(queryString.toString()).setParameter(0,value).uniqueResult();
    }

    /**
     * 根据查询条件查询对象集合
     * @param propertyName 属性名
	 * @param value 参数值
	 * @return 查询结果对象集合
     */
    public List<E> findAllBy(final String propertyName, final Object value) {
    	if(propertyName!=null&&!propertyName.equals("")&&value!=null)
    	{
	    	String hql=" from "+this.getEntityClass().getName()+" as att where att."+propertyName+"=?";
	    	return this.findFastByHql(hql, value);
    	}
    	return new ArrayList(1);
    }
    
    /**
     * 根据查询条件查询未被逻辑删除的对象集合
     * @param propertyName 对象属性名
	 * @param value 参数值
	 * @return 查询结果对象集合
     */
    public List<E> findAllByDf(final String propertyName, final Object value) {
    	StringBuffer queryString = new StringBuffer(" FROM ").append(getEntityClass().getSimpleName());
    	queryString.append(" t where t.df='0' ");
    	queryString.append(" and t." +propertyName +" = ? ");
    	return getHibernateTemplate().find(queryString.toString(),value);
    }
    
    /**
     * 根据查询条件查询未被逻辑删除的对象集合 此查询是针对有关联关系（一对多、多对多）的对象查询 如用户和角色为多对多关系 先根据角色的某属性查询符合条件的所有用户
     * @param joinName 关联的对象集合名 如用户对象下的角色集合 roles
     * @param joinPropertyName  需查询的关联对象的属性名 如角色的ID属性
	 * @param value 参数值 需查询的关联对象的属性的值 如角色的ID值
	 * @return 查询结果对象集合
     */
    public List<E> findAllByDf(final String joinName,final String joinPropertyName, final Object value) {
    	StringBuffer queryString = new StringBuffer(" select t FROM ").append(getEntityClass().getSimpleName());
    	queryString.append(" t inner join t."+joinName+" as b where t.df='0' and b.df='0' ");
    	queryString.append(" and b." +joinPropertyName +" = ? ");
    	return getHibernateTemplate().find(queryString.toString(),value);
    }
    
    /**
	 * 根据查询条件查询未被逻辑删除的对象
	 * @param propertyName 对象属性名
	 * @param value 参数值
	 * @return 结果对象
	 */
    public E findByDf(final String propertyName, final Object value){
    	List list = this.findAllByDf(propertyName, value);
    	if(list.size()>0){
    		return (E)list.get(0);
    	}
        return null;
    }
    
    /**
     * 查询所有未被逻辑删除的对象集合
     * @return 对象集合
     */
    public List<E> findAllByDf() {
    	StringBuffer queryString = new StringBuffer(" FROM ").append(getEntityClass().getSimpleName());
    	queryString.append(" where df='0' ");
    	return getHibernateTemplate().find(queryString.toString());
    }
    /**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * @param entity 待判断对象
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return boolean值 如果是唯一则返回true 否则返回false
	 */
	public boolean isUnique(E entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (int i = 0; i < nameList.length; i++) {
				criteria.add(Restrictions.eq(nameList[i], PropertyUtils.getProperty(entity, nameList[i])));
			}

			// 以下代码为了如果是update的情况,排除entity自身.
//			String idName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
			String idName = getSessionFactory().getClassMetadata(getEntityClass()).getIdentifierPropertyName();
			if(idName != null) {
				// 取得entity的主键值
				Serializable id =  (Serializable)PropertyUtils.getProperty(entity, idName);
	
				// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
				if (id != null && !"".equals(id))
					criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (((Number) criteria.uniqueResult()).intValue() == 0);
	}
	
	
	/**
	 * 判断对象某些属性的值在未被逻辑删除的数据中是否唯一.
	 * @param entity 待判断对象
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return boolean值 如果是唯一则返回true 否则返回false
	 */
	public boolean isUniqueByDf(E entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (int i = 0; i < nameList.length; i++) {
				//有的字段是null的情况
				if(PropertyUtils.getProperty(entity, nameList[i])==null){
					criteria.add(Restrictions.isNull(nameList[i]));
				}else{
					criteria.add(Restrictions.eq(nameList[i], PropertyUtils.getProperty(entity, nameList[i])));
				}
			}
			criteria.add(Restrictions.eq("df", "0"));
			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
			if(idName != null) {
				// 取得entity的主键值
				Serializable id =  (Serializable)PropertyUtils.getProperty(entity, idName);
	
				// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
				if (id != null && !"".equals(id)){
					criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
				}	
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (((Number) criteria.uniqueResult()).intValue() == 0);
	}
	
	/**
	 * 分页查询
	 * @param hql
	 * @param pageSize
	 * @param pageNum
	 * @param args
	 * @return
	 */
	public Page findByHql(String hql, int pageSize, int pageNum, Object... args) {
		int totalCount = 0;
		if (pageSize > 0 && pageNum > 0) {
			totalCount = countByHql(hql, args);
			if (totalCount < 1)
				return new Page(pageNum,pageSize,0);
		}
		Query dataQuery = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			dataQuery.setParameter(i, args[i]);
		}
		pageNum=this.getRealPageNum(totalCount, pageSize, pageNum);
		if (pageNum > 0 && pageSize > 0) {
			int startIndex = (pageNum - 1) * pageSize;
			dataQuery.setFirstResult(startIndex).setMaxResults(pageSize);
		}
		List list = dataQuery.list();
		if (pageSize == 0 || pageNum == 0) {
			totalCount = list.size();
		}
		return new Page(pageNum, pageSize, totalCount, list);
	}
	
	/**
	 * 
	 * @param hql
	 * @param args
	 * @return
	 */
	public List findFastByHql(String hql,Object... args) {
		Query dataQuery = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			dataQuery.setParameter(i, args[i]);
		}
		return dataQuery.list();
	}
	
	public Object findOneByHql(String hql,Object... args){
		Query dataQuery = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			dataQuery.setParameter(i, args[i]);
		}
		return dataQuery.uniqueResult();
	}
	
	/**
	 * 获得count根据普通SQL
	 */
	public int countByHql(String hql, Object... args) {
		int totalCount = 0;
		String countQueryString = " select count(*) " + this.removeSelect(removeOrders(hql));
		Query query = getSession().createQuery(countQueryString);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		List countlist = query.list();
		if (countlist != null && countlist.size() >= 1) {
			Object obj = countlist.get(0);
			if (obj instanceof Integer) {
				Integer num = (Integer) countlist.get(0);
				totalCount = num;
			} else if (obj instanceof Long) {
				totalCount = ((Long) countlist.get(0)).intValue();
			} else if (obj instanceof BigDecimal) {
				BigDecimal num = (BigDecimal) countlist.get(0);
				totalCount = num.intValue();
			}
		}
		return totalCount;

	}
	
	public int executeByHql(String hql, Object... args) {
		Query query = super.getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.executeUpdate();
	}

    public abstract Class getEntityClass();


	

}
