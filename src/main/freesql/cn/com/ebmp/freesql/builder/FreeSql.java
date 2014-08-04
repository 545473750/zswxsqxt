package cn.com.ebmp.freesql.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.mapping.BoundSql;
import cn.com.ebmp.freesql.mapping.MappedStatement;
import cn.com.ebmp.freesql.mapping.ParameterMapping;
import cn.com.ebmp.freesql.mapping.ResultMap;
import cn.com.ebmp.freesql.mapping.ResultMapping;
import cn.com.ebmp.freesql.reflection.MetaObject;

public class FreeSql {
	private Object parameterObject;// 传入参数对象
	private List<ParameterMapping> parameterMappings;// 应用查询条件的参数属性列表
	private List<Object> parameterValue;// 查询使用的参数值
	private String sql;// 查询SQL语句
	private ResultMap resultMap;// 返回结果映射配置
	private boolean isSimpleColumn=true;//判断此sql是否为简单的列输出（简单列，是指显示字段中不包括子查询sql,否则为复杂查询)

	public static class Builder {
		private FreeSql freeSql = new FreeSql();
		private MetaObject metaParameter;
		private Configuration configuration;
		private MappedStatement mappendStatement;

		public Builder(Configuration configuration, String name, Object parameterObject) {
			mappendStatement = configuration.getMappedStatement(name);
			this.configuration = configuration;
			BoundSql boundSql = mappendStatement.getBoundSql(parameterObject);
			freeSql.sql = boundSql.getSql();
			freeSql.parameterObject = parameterObject;
			freeSql.parameterMappings = boundSql.getParameterMappings();
			if (mappendStatement.getResultMaps() != null) {
				freeSql.resultMap = mappendStatement.getResultMaps();
			} else {
				freeSql.resultMap = new ResultMap.Builder(null, mappendStatement.getResultType(), new ArrayList()).build();
			}
			metaParameter = boundSql.getMetaParameters();
		}

		public FreeSql build() {
			List<Object> parameterValue = new ArrayList<Object>();
			// 处理条件参数
			if (freeSql.parameterMappings != null && !freeSql.parameterMappings.isEmpty()) {
				for (ParameterMapping parameterMapping : freeSql.parameterMappings) {
					if (freeSql.parameterObject instanceof Map) {
						Map mapParam = (Map) freeSql.parameterObject;
						parameterValue.add(mapParam.get(parameterMapping.getProperty()));
					} else {
						parameterValue.add(metaParameter.getValue(parameterMapping.getProperty()));
					}
				}
			}
			freeSql.parameterValue = parameterValue;
			// 处理结果集映射列表
			if (mappendStatement.isSelect() && mappendStatement.isAutoMapping() && (!mappendStatement.isCompile())) 
			{
				ResultMappingAssistant resultMappingAssistant=new ResultMappingAssistant(freeSql.sql);
				List<ResultMapping> resultMappingList = resultMappingAssistant.builder();
				freeSql.resultMap.setResultMapping(resultMappingList);
				freeSql.isSimpleColumn=resultMappingAssistant.isSimpleColumn();
				ResultMap newResultMap = new ResultMap.Builder(freeSql.resultMap.getId(), freeSql.resultMap.getType(), resultMappingList).builderHibernateType();
				mappendStatement.setResultMaps(newResultMap);
				mappendStatement.setCompile(true);
				mappendStatement.setSimpleColumn(freeSql.isSimpleColumn);
			}
			freeSql.isSimpleColumn=mappendStatement.isSimpleColumn();
			return freeSql;
		}
	}

	private FreeSql() {

	}

	public ResultMap getResultMap() {
		return resultMap;
	}

	public void setResultMap(ResultMap resultMap) {
		this.resultMap = resultMap;
	}

	public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(List<Object> parameterValue) {
		this.parameterValue = parameterValue;
	}
}
