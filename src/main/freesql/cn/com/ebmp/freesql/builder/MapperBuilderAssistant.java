package cn.com.ebmp.freesql.builder;

import java.util.ArrayList;
import java.util.List;

import cn.com.ebmp.freesql.builder.Exception.BuilderException;
import cn.com.ebmp.freesql.builder.factory.BaseBuilder;
import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.mapping.MappedStatement;
import cn.com.ebmp.freesql.mapping.ParameterMap;
import cn.com.ebmp.freesql.mapping.ParameterMapping;
import cn.com.ebmp.freesql.mapping.ResultMap;
import cn.com.ebmp.freesql.mapping.ResultMapping;
import cn.com.ebmp.freesql.mapping.SqlSource;

public class MapperBuilderAssistant extends BaseBuilder
{

	private String currentNamespace;
	private String resource;

	public MapperBuilderAssistant(Configuration configuration, String resource)
	{
		super(configuration);
		this.resource = resource;
	}

	public String getCurrentNamespace()
	{
		return currentNamespace;
	}

	public void setCurrentNamespace(String currentNamespace)
	{
		if (currentNamespace != null)
		{
			this.currentNamespace = currentNamespace;
		}
		if (this.currentNamespace == null)
		{
			throw new BuilderException("The mapper element requires a namespace attribute to be specified.");
		}
	}

	public String applyCurrentNamespace(String base)
	{
		if (base == null)
			return null;
		if (base.contains("."))
			return base;
		return currentNamespace + "." + base;
	}

	/**
	 * 添加参数Map
	 * 
	 * @param id
	 * @param parameterClass
	 * @return
	 */
	public ParameterMap addParameterMap(String id, Class<?> parameterClass)
	{
		id = applyCurrentNamespace(id);
		ParameterMap.Builder parameterMapBuilder = new ParameterMap.Builder(configuration, id, parameterClass, new ArrayList<ParameterMapping>());
		ParameterMap parameterMap = parameterMapBuilder.build();
		configuration.addParameterMap(parameterMap);
		return parameterMap;
	}

	/**
	 * 添加结果参数配置
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public ResultMap addResultMap(String id, Class<?> type, List<ResultMapping> resultMapping)
	{
		id = applyCurrentNamespace(id);
		ResultMap resultMap = new ResultMap.Builder(id, type, resultMapping).build();
		configuration.addResultMap(resultMap);
		return resultMap;
	}

	/**
	 * 添加执行操作映射
	 * 
	 * @param id
	 * @param sqlSource
	 * @param parameterMap
	 * @param parameterType
	 * @param resultMap
	 * @param resultType
	 * @param resultSetType
	 * @return
	 */
	public MappedStatement addMappedStatement(String id, SqlSource sqlSource, boolean isSelect, String parameterMap, Class<?> parameterType, String resultMap, Class<?> resultType, String autoMapping)
	{
		id = applyCurrentNamespace(id);
		MappedStatement statement = new MappedStatement();
		statement.setId(id);
		statement.setConfiguration(configuration);
		statement.setSqlSource(sqlSource);
		statement.setSelect(isSelect);
		statement.setParameterType(parameterType);
		statement.setResultType(resultType);
		statement.setResource(resource);
		if (autoMapping != null)
		{
			if (autoMapping.toLowerCase().equals("false"))
			{
				statement.setAutoMapping(false);
			}
		}
		parameterMap = applyCurrentNamespace(parameterMap);
		resultMap = applyCurrentNamespace(resultMap);
		if (configuration.getParameterMaps().containsKey(parameterMap))
		{
			statement.setParameterMap(configuration.getParameterMaps().get(parameterMap));
		}
		if (configuration.getResultMaps().containsKey(resultMap))
		{
			statement.setResultMaps(configuration.getResultMaps().get(resultMap));
		}
		configuration.addMappedStatements(id, statement);
		return statement;
	}

}
