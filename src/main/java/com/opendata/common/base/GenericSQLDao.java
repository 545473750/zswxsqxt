package com.opendata.common.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.bytecode.Descriptor.Iterator;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import cn.com.ebmp.freesql.builder.FreeSQLFactoryBuilder;
import cn.com.ebmp.freesql.builder.FreeSql;
import cn.com.ebmp.freesql.builder.ResultMappingAssistant;
import cn.com.ebmp.freesql.mapping.ResultMap;
import cn.com.ebmp.freesql.mapping.ResultMapping;
import cn.org.rapid_framework.page.Page;

/**
 * 纯SQL 基类。
 * 
 * @author liufeng
 * @see SQLDaoSupport
 * @since 3.0
 */

abstract public class GenericSQLDao extends HibernateDaoSupport {

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @param hql
	 *            原始hql语句
	 * @return 截取掉orderby子句后的hql语句
	 */
	protected static String removeOrders(String hql) {
		Assert.hasText(hql);

		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,，用于pagedQuery.
	 * 
	 * @param hql
	 *            原始hql
	 * @return 截取掉select的hql语句
	 */
	protected String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 获得实际的页数
	 * 
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	protected int getRealPageNum(long totalCount, int pageSize, int pageNum) {
		long sumPage = 0;
		int realPageNum=pageNum;
		if (totalCount == 0 || pageSize == 0) {
			sumPage = 0;
		}
		if (totalCount % pageSize == 0) {
			sumPage = totalCount / pageSize;
		} else {
			sumPage = totalCount / pageSize + 1;
		}
		if(pageNum>sumPage)
		{
			realPageNum=(int) sumPage;
		}
		if(realPageNum<0)
		{
			realPageNum=1;
		}
		return realPageNum;
	}

	/** ******************************直接通过SQL执行接口******************************************** */

	/**
	 * 根据普通SQL进行分页查询
	 * 
	 * @param sql
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public Page findBySql(String sql, int pageSize, int pageNum, Object... args) {
		int totalCount = 0;
		if (pageSize > 0 && pageNum > 0) {
			totalCount = countBySql(sql, args);
			if (totalCount < 1)
				return new Page(pageNum, pageSize, 0);
		}
		SQLQuery dataQuery = getSession().createSQLQuery(sql);
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
	 * 通过普通SQL语句进行查询 通过字段关联,构造entity对象
	 * 
	 * @param sql
	 * @param pageSize
	 * @param pageNum
	 * @param args
	 * @param fieldAlign
	 * @param entity
	 * @return
	 */
	public Page findBySql(String sql, int pageSize, int pageNum,
			Class metaObject, Object... args) {
		Assert.notNull(metaObject);
		List<ResultMapping> resultMapping = new ResultMappingAssistant(sql)
				.builder();
		ResultMap resultMap = new ResultMap.Builder(null, metaObject,
				resultMapping).builderHibernateType();
		Page page = findBySql(sql, pageSize, pageNum, metaObject, resultMap
				.getResultMapping(), args);
		return page;
	}

	public List findFastBySql(String sql, Object... args) {
		SQLQuery dataQuery = getSession().createSQLQuery(sql);
		for (int i = 0; i < args.length; i++) {
			dataQuery.setParameter(i, args[i]);
		}
		return dataQuery.list();
	}

	/**
	 * 通过普通SQL语句进行查询 通过字段关联,构造entity对象
	 * 
	 * @param sql
	 * @param pageSize
	 * @param pageNum
	 * @param args
	 * @param fieldAlign
	 * @param entity
	 * @return
	 */
	public List findFastBySql(String sql, Class metaObject, Object... args) {
		Assert.notNull(metaObject);
		List<ResultMapping> resultMapping = new ResultMappingAssistant(sql)
				.builder();
		ResultMap resultMap = new ResultMap.Builder(null, metaObject,
				resultMapping).builderHibernateType();
		List data = findFastBySql(sql, metaObject,
				resultMap.getResultMapping(), args);
		return data;
	}

	/**
	 * 查询单条记录
	 * 
	 * @param sql
	 * @param pageSize
	 * @param pageNum
	 * @param metaObject
	 * @param args
	 * @return
	 */
	public Object findOneBySql(String sql, Class metaObject, Object... args) {
		List list = this.findFastBySql(sql, metaObject, args);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 执行SQL,并反回记录数
	 */
	public int executeBySql(String sql, Object... args) {
		SQLQuery query = super.getSession().createSQLQuery(sql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.executeUpdate();
	}

	/**
	 * 获得count根据普通SQL
	 */
	public int countBySql(String sql, Object... args) {
		int totalCount = 0;
		String countQueryString = "";
		ResultMappingAssistant resultMappingAssistant = new ResultMappingAssistant(
				sql);
		if (resultMappingAssistant.isSimpleColumn()) {
			countQueryString = " select count(1) "
					+ this.removeSelect(this.removeOrders(sql));
		} else {
			countQueryString = " select count(1) from ( "
					+ this.removeOrders(sql) + " ) hibernateCountSQL";
		}
		SQLQuery query = getSession().createSQLQuery(countQueryString);
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
			}else if (obj instanceof BigInteger) {
				BigInteger num = (BigInteger) countlist.get(0);
				totalCount = num.intValue();
			}
			
		}
		return totalCount;
	}

	/**
	 * 根据普通SQL进行分页查询
	 * 
	 * @param sql
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	private Page findBySql(String sql, int pageSize, int pageNum,
			Class resultType, List<ResultMapping> resultMappingList,
			Object... args) {
		int totalCount = 0;
		if (pageSize > 0 && pageNum > 0) {
			totalCount = countBySql(sql, args);
			if (totalCount < 1)
				return new Page(pageNum, pageSize, 0);
			;
		}
		SQLQuery dataQuery = getSession().createSQLQuery(sql);
		if(!resultType.getName().equals(HashMap.class.getName()))
		{
			dataQuery.setResultTransformer(Transformers.aliasToBean(resultType));
			for (ResultMapping mapping : resultMappingList) {
				dataQuery.addScalar(mapping.getProperty(), mapping .getHibernateType());
			}
		}else
		{
			dataQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		pageNum=this.getRealPageNum(totalCount, pageSize, pageNum);
		for (int i = 0; i < args.length; i++) {
			dataQuery.setParameter(i, args[i]);
		}
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

	private List findFastBySql(String sql, Class resultType,
			List<ResultMapping> resultMappingList, Object... args) {
		SQLQuery dataQuery = getSession().createSQLQuery(sql);
		if(!resultType.getName().equals(HashMap.class.getName()))
		{
			dataQuery.setResultTransformer(Transformers.aliasToBean(resultType));
			for (ResultMapping mapping : resultMappingList) {
				dataQuery.addScalar(mapping.getProperty(), mapping .getHibernateType());
			}
		}else
		{
			dataQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		for (int i = 0; i < args.length; i++) {
			dataQuery.setParameter(i, args[i]);
		}
		return dataQuery.list();
	}

	/**
	 * **********************增加FreeSql****************************************
	 */

	/***************************************************************************
	 * 通过FreeSQL查询批定页数据,返回List
	 * 
	 * @param name
	 *            freeSQL名称
	 * @param pageSize
	 *            每页记录数
	 * @param pageNum
	 *            页码
	 * @param parameterObject
	 *            查询条件对象
	 * @return List
	 */
	public List findFastByFreeSql(String name, Object parameterObject) {
		FreeSQLFactoryBuilder freeSqlBuilder = FreeSQLFactoryBuilder.builder();
		FreeSql freeSql = freeSqlBuilder.findFreeSql(name, parameterObject);
		List listData = this.findFastBySql(freeSql.getSql(), freeSql
				.getResultMap().getType(), freeSql.getResultMap()
				.getResultMapping(), freeSql.getParameterValue().toArray());
		return listData;
	}

	/***************************************************************************
	 * 通过FreeSQL查询批定页数据
	 * 
	 * @param name
	 *            freeSQL名称
	 * @param pageSize
	 *            每页记录数
	 * @param pageNum
	 *            页码
	 * @param parameterObject
	 *            查询条件对象
	 * @return Page
	 */
	public Page findByFreeSql(String name, int pageSize, int pageNum,
			Object parameterObject) {
		FreeSQLFactoryBuilder freeSqlBuilder = FreeSQLFactoryBuilder.builder();
		FreeSql freeSql = freeSqlBuilder.findFreeSql(name, parameterObject);
		Page page = this.findBySql(freeSql.getSql(), pageSize, pageNum, freeSql
				.getResultMap().getType(), freeSql.getResultMap()
				.getResultMapping(), freeSql.getParameterValue().toArray());
		return page;
	}

	/**
	 * 查询单条数据
	 */
	public Object findOneByFreeSql(String name, Object parameterObject) {
		List list = this.findFastByFreeSql(name, parameterObject);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/***************************************************************************
	 * 通过FreeSQL查询总记录数
	 * 
	 * @param name
	 *            freeSQL名称
	 * @param parameterObject
	 *            查询条件对象
	 * @return long
	 */
	public long countByFreeSql(String name, Object parameterObject) {
		FreeSQLFactoryBuilder freeSqlBuilder = FreeSQLFactoryBuilder.builder();
		FreeSql freeSql = freeSqlBuilder.findFreeSql(name, parameterObject);
		return countBySql(freeSql.getSql(), freeSql.getParameterValue()
				.toArray());
	}

	/**
	 * 执行freesql
	 * 
	 * @param name
	 * @param parameterObject
	 * @return
	 */
	public long executeByFreeSql(String name, Object parameterObject) {
		FreeSQLFactoryBuilder freeSqlBuilder = FreeSQLFactoryBuilder.builder();
		FreeSql freeSql = freeSqlBuilder.findFreeSql(name, parameterObject);
		return this.executeBySql(freeSql.getSql(), freeSql.getParameterValue()
				.toArray());
	}

	public Connection getConnection() {
		return this.getSession().connection();
	}

	/**
	 * 存储过程执行
	 * 
	 * @param sql
	 *            存储过程名称 { ?=call qktq(?,?,?)}
	 * @param Map
	 *            <Integer,Integer> 输出参数 key为输出下标，value 为输出类型java.sql.Types
	 *            中的类型值
	 * @param resultParamIndex
	 *            返回结果下标值，从1开始
	 * @param args
	 *            存储过程输入参数
	 * @return Map<Integer,Object> key为下标 value为值
	 */
	public Map<Integer, Object> execProdure(String sql,
			Map<Integer, Integer> outParam, Object... args) {
		int paramSum = sql.toLowerCase().split("\\?").length - 1;
		Map<Integer, Object> result = new HashMap<Integer, Object>();
		try {
			CallableStatement callableStatement = getSession().connection()
					.prepareCall(sql);
			// 注册输出参数
			if (outParam != null) {
				Iterator ite = (Iterator) (outParam.keySet().iterator());
				while (ite.hasNext()) {
					Integer key = (Integer) ite.next();
					callableStatement.registerOutParameter(key, outParam
							.get(key));
				}
			}
			// 设置输入参数
			for (int i = 0; i < args.length; i++) {
				if (outParam != null) {

					Iterator ite = (Iterator) (outParam.keySet().iterator());
					while (ite.hasNext()) {
						Integer key = (Integer) ite.next();
						callableStatement.registerOutParameter(key, outParam
								.get(key));
						if (i + 1 != key) {
							callableStatement.setObject(i + 1, args[i]);
						}
					}
				} else {
					callableStatement.setObject(i + 1, args[i]);
				}
			}
			// 执行存储过程
			callableStatement.execute();
			// 设置输出参数值
			if (outParam != null) {

				Iterator ite = (Iterator) (outParam.keySet().iterator());
				while (ite.hasNext()) {
					Integer key = (Integer) ite.next();
					result.put(key, callableStatement.getObject(key));
				}
			}
		} catch (Exception e) {
		}
		return result;
	}

}
