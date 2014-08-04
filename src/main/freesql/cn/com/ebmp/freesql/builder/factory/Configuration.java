package cn.com.ebmp.freesql.builder.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.com.ebmp.freesql.builder.MapperBuilderAssistant;
import cn.com.ebmp.freesql.builder.xml.XMLStatementBuilder;
import cn.com.ebmp.freesql.mapping.MappedStatement;
import cn.com.ebmp.freesql.mapping.ParameterMap;
import cn.com.ebmp.freesql.mapping.ResultMap;
import cn.com.ebmp.freesql.parsing.XNode;
import cn.com.ebmp.freesql.reflection.MetaObject;
import cn.com.ebmp.freesql.reflection.factory.DefaultObjectFactory;
import cn.com.ebmp.freesql.reflection.factory.ObjectFactory;
import cn.com.ebmp.freesql.reflection.wrapper.DefaultObjectWrapperFactory;
import cn.com.ebmp.freesql.reflection.wrapper.ObjectWrapperFactory;
import cn.com.ebmp.freesql.type.TypeAliasRegistry;

public class Configuration {

	/**
	 * 存储业务操作SQL映射
	 */
	protected final Map<String, MappedStatement> mappedStatements = new StrictMap<String, MappedStatement>("Mapped Statements collection");
	/**
	 * 存储返回结果类型映射
	 */
	protected final Map<String, ResultMap> resultMaps = new StrictMap<String, ResultMap>("Result Maps collection");
	/**
	 * 存储传入参数类别映射
	 */
	protected final Map<String, ParameterMap> parameterMaps = new StrictMap<String, ParameterMap>("Parameter Maps collection");

	/**
	 * 数据类型注册
	 */
	protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
	/**
	 * 加载资源配置文件列表
	 */
	protected final Set<String> loadedResources = new HashSet<String>();
	/**
	 * 公共sql映射
	 */
	protected final Map<String, XNode> sqlFragments = new StrictMap<String, XNode>("XML fragments parsed from previous mappers");

	/**
	 * 未解决前所有SQL
	 */
	protected final ConcurrentMap<String, List<XNode>> statementNodesToParse = new ConcurrentHashMap<String, List<XNode>>();

	protected ObjectFactory objectFactory = new DefaultObjectFactory();
	protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
	/**
	 * 系统变量
	 */
	private Properties variables;

	public Properties getVariables() {
		return variables;
	}

	public void setVariables(Properties variables) {
		this.variables = variables;
	}

	/**
	 * 添加加载配置文件至配置文件中
	 * 
	 * @param resource
	 */
	public void addLoadedResource(String resource) {
		loadedResources.add(resource);
	}

	/**
	 * 判断配置文件是否被加载
	 * 
	 * @param resource
	 * @return
	 */
	public boolean isResourceLoaded(String resource) {
		return loadedResources.contains(resource);
	}

	/**
	 * 添加参数配置
	 * 
	 * @param parameterMap
	 */
	public void addParameterMap(ParameterMap parameterMap) {
		this.parameterMaps.put(parameterMap.getId(), parameterMap);
	}

	/**
	 * 参数结果配置
	 * 
	 * @param resultMap
	 */
	public void addResultMap(ResultMap resultMap) {
		this.resultMaps.put(resultMap.getId(), resultMap);
	}

	/**
	 * 添加当前空间中的所有节点
	 * 
	 * @param namespace
	 * @param nodes
	 */
	public void addStatementNodes(String namespace, List<XNode> nodes) {
		statementNodesToParse.put(namespace, nodes);
	}

	/**
	 * 添加解析后的业务操作节点对象
	 * 
	 * @param namespace
	 * @param mappedStatement
	 */
	public void addMappedStatements(String namespace, MappedStatement mappedStatement) {
		this.mappedStatements.put(namespace, mappedStatement);
	}

	/**
	 * Parses all the unprocessed statement nodes in the cache. It is
	 * recommended to call this method once all the mappers are added as it
	 * provides fail-fast statement validation.
	 */
	public void buildAllStatements() {
		if (!statementNodesToParse.isEmpty()) {
			Set<String> keySet = statementNodesToParse.keySet();
			for (String namespace : keySet) {
				buildStatementsForNamespace(namespace);
			}
		}
	}

	protected void buildStatementsFromId(String id) {
		final String namespace = extractNamespace(id);
		buildStatementsForNamespace(namespace);
	}

	/**
	 * Extracts namespace from fully qualified statement id.
	 * 
	 * @param statementId
	 * @return namespace or null when id does not contain period.
	 */
	protected String extractNamespace(String statementId) {
		int lastPeriod = statementId.lastIndexOf('.');
		return lastPeriod > 0 ? statementId.substring(0, lastPeriod) : null;
	}

	/**
	 * Parses cached statement nodes for the specified namespace and stores the
	 * generated mapped statements.
	 * 
	 * @param namespace
	 */
	protected void buildStatementsForNamespace(String namespace) {
		if (namespace != null) {
			final List<XNode> list = statementNodesToParse.get(namespace);
			if (list != null) {
				final String resource = namespace.replace('.', '/') + ".xml";
				final MapperBuilderAssistant builderAssistant = new MapperBuilderAssistant(this, resource);
				builderAssistant.setCurrentNamespace(namespace);
				parseStatementNodes(builderAssistant, list);
				statementNodesToParse.remove(namespace);
			}
		}
	}

	protected void parseStatementNodes(final MapperBuilderAssistant builderAssistant, final List<XNode> list) {
		for (XNode context : list) {
			final XMLStatementBuilder statementParser = new XMLStatementBuilder(this, builderAssistant);
			statementParser.parseStatementNode(context);
		}
	}

	public MetaObject newMetaObject(Object object) {
		return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
	}

	public Map<String, MappedStatement> getMappedStatements() {
		return mappedStatements;
	}

	public MappedStatement getMappedStatement(String name) {
		if (mappedStatements.containsKey(name)) {
			return mappedStatements.get(name);
		}
		return null;
	}

	public Map<String, ResultMap> getResultMaps() {
		return resultMaps;
	}

	public Map<String, ParameterMap> getParameterMaps() {
		return parameterMaps;
	}

	public Set<String> getLoadedResources() {
		return loadedResources;
	}

	public Map<String, XNode> getSqlFragments() {
		return sqlFragments;
	}

	public ConcurrentMap<String, List<XNode>> getStatementNodesToParse() {
		return statementNodesToParse;
	}

	public TypeAliasRegistry getTypeAliasRegistry() {
		return typeAliasRegistry;
	}

	/**
	 * 静态存储数据Map
	 * 
	 * @author Administrator
	 * 
	 * @param <J>
	 * @param <K>
	 */
	protected static class StrictMap<J extends String, K extends Object> extends HashMap<J, K> {

		private String name;

		public StrictMap(String name, int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor);
			this.name = name;
		}

		public StrictMap(String name, int initialCapacity) {
			super(initialCapacity);
			this.name = name;
		}

		public StrictMap(String name) {
			super();
			this.name = name;
		}

		public StrictMap(String name, Map<? extends J, ? extends K> m) {
			super(m);
			this.name = name;
		}

		public K put(J key, K value) {
			if (containsKey(key))
				throw new IllegalArgumentException(name + " already contains value for " + key);
			if (key.contains(".")) {
				final String shortKey = getShortName(key);
				if (super.get(shortKey) == null) {
					super.put((J) shortKey, value);
				} else {
					super.put((J) shortKey, (K) new Ambiguity(shortKey));
				}
			}
			return super.put(key, value);
		}

		public K get(Object key) {
			K value = super.get(key);
			if (value == null) {
				value = super.get(getShortName((J) key));
				if (value == null) {
					throw new IllegalArgumentException(name + " does not contain value for " + key);
				}
			}
			if (value instanceof Ambiguity) {
				throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name + " (try using the full name including the namespace, or rename one of the entries)");
			}
			return value;
		}

		private String getShortName(J key) {
			final String[] keyparts = key.split("\\.");
			final String shortKey = keyparts[keyparts.length - 1];
			return shortKey;
		}

		protected static class Ambiguity {
			private String subject;

			public Ambiguity(String subject) {
				this.subject = subject;
			}

			public String getSubject() {
				return subject;
			}
		}
	}
}
