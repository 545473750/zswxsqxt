package cn.com.ebmp.freesql.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import cn.com.ebmp.freesql.builder.factory.BaseBuilder;
import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.mapping.ParameterMapping;
import cn.com.ebmp.freesql.mapping.SqlSource;
import cn.com.ebmp.freesql.parsing.GenericTokenParser;
import cn.com.ebmp.freesql.parsing.TokenHandler;
import cn.com.ebmp.freesql.reflection.MetaClass;

public class SqlSourceBuilder extends BaseBuilder {

	public SqlSourceBuilder(Configuration configuration) {
		super(configuration);
	}

	public SqlSource parse(String originalSql, Class<?> parameterType) {
		ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(configuration, parameterType);
		GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
		String sql = parser.parse(originalSql);
		return new StaticSqlSource(configuration, sql, handler.getParameterMappings());
	}

	private static class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {

		private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		private Class<?> parameterType;

		public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType) {
			super(configuration);
			this.parameterType = parameterType;
		}

		public List<ParameterMapping> getParameterMappings() {
			return parameterMappings;
		}

		public String handleToken(String content) {
			parameterMappings.add(buildParameterMapping(content));
			return "?";
		}

		private ParameterMapping buildParameterMapping(String content) {
			StringTokenizer parameterMappingParts = new StringTokenizer(content, ", \n\r\t");
			String propertyWithJdbcType = parameterMappingParts.nextToken();
			String property = extractPropertyName(propertyWithJdbcType);
			Class<?> propertyType;
			MetaClass metaClass = MetaClass.forClass(parameterType);
			if (metaClass.hasGetter(property)) {
				propertyType = metaClass.getGetterType(property);
			} else {
				propertyType = Object.class;
			}
			return new ParameterMapping(property, propertyType);
		}

		private String extractPropertyName(String property) {
			if (property.contains(":")) {
				StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
				if (simpleJdbcTypeParser.countTokens() == 2) {
					return simpleJdbcTypeParser.nextToken();
				}
			}
			return property;
		}

		private String extractJdbcType(String property) {
			if (property.contains(":")) {
				StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
				if (simpleJdbcTypeParser.countTokens() == 2) {
					simpleJdbcTypeParser.nextToken();
					return simpleJdbcTypeParser.nextToken();
				}
			}
			return null;
		}

	}
}
