package cn.com.ebmp.freesql.mapping;

import java.util.List;

import cn.com.ebmp.freesql.builder.factory.Configuration;

/**
 * 操作映射段
 * 
 * @author Administrator
 * 
 */
public class MappedStatement {

	private String resource;
	private Configuration configuration;
	private String id;
	private SqlSource sqlSource;
	private ParameterMap parameterMap;
	private ResultMap resultMaps;
	private Class parameterType;
	private Class resultType;
	private boolean isSelect;// 是否是查询
	private boolean autoMapping = true;// 是否自动映射反回对象属性,默认为自动映射
	private boolean isCompile = false;// 是否编译
	private boolean isSimpleColumn=true;//判断此sql是否为简单的列输出（简单列，是指显示字段中不包括子查询sql,否则为复杂查询)

	public boolean isSimpleColumn() {
		return isSimpleColumn;
	}

	public void setSimpleColumn(boolean isSimpleColumn) {
		this.isSimpleColumn = isSimpleColumn;
	}

	public boolean isCompile() {
		return isCompile;
	}

	public void setCompile(boolean isCompile) {
		this.isCompile = isCompile;
	}

	public boolean isAutoMapping() {
		return autoMapping;
	}

	public void setAutoMapping(boolean autoMapping) {
		this.autoMapping = autoMapping;
	}

	public MappedStatement() {
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		// List<ParameterMapping> parameterMappings =
		// boundSql.getParameterMappings();
		// if (parameterMappings == null || parameterMappings.size() <= 0)
		// {
		// boundSql = new BoundSql(configuration, boundSql.getSql(),
		// parameterMap.getParameterMappings(), parameterObject);
		// }
		return boundSql;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SqlSource getSqlSource() {
		return sqlSource;
	}

	public void setSqlSource(SqlSource sqlSource) {
		this.sqlSource = sqlSource;
	}

	public ParameterMap getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(ParameterMap parameterMap) {
		this.parameterMap = parameterMap;
	}

	public ResultMap getResultMaps() {
		return resultMaps;
	}

	public void setResultMaps(ResultMap resultMaps) {
		this.resultMaps = resultMaps;
	}

	public Class getParameterType() {
		return parameterType;
	}

	public void setParameterType(Class parameterType) {
		this.parameterType = parameterType;
	}

	public Class getResultType() {
		return resultType;
	}

	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}

}
