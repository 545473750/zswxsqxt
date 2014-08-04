package cn.com.ebmp.freesql.builder.xml.dynamic;

import java.util.Map;

import cn.com.ebmp.freesql.builder.SqlSourceBuilder;
import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.mapping.BoundSql;
import cn.com.ebmp.freesql.mapping.SqlSource;

public class DynamicSqlSource implements SqlSource {

	private Configuration configuration;
	private SqlNode rootSqlNode;

	public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
		this.configuration = configuration;
		this.rootSqlNode = rootSqlNode;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(configuration, parameterObject);
		rootSqlNode.apply(context);
		SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
		Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
		SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType);
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
			boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
		}
		return boundSql;
	}

}
