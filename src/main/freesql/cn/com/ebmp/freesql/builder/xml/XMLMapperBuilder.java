package cn.com.ebmp.freesql.builder.xml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.ebmp.freesql.builder.MapperBuilderAssistant;
import cn.com.ebmp.freesql.builder.factory.BaseBuilder;
import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.mapping.ResultMap;
import cn.com.ebmp.freesql.mapping.ResultMapping;
import cn.com.ebmp.freesql.parsing.XNode;
import cn.com.ebmp.freesql.parsing.XPathParser;

public class XMLMapperBuilder extends BaseBuilder {

	private XPathParser parser;
	private MapperBuilderAssistant builderAssistant;
	private Map<String, XNode> sqlFragments;
	private String resource;

	public XMLMapperBuilder(Reader reader, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
		super(configuration);
		this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
		this.parser = new XPathParser(reader, true, configuration.getVariables(), new XMLMapperEntityResolver());
		this.sqlFragments = sqlFragments;
		this.resource = resource;
	}

	public void parse() {
		if (!configuration.isResourceLoaded(resource)) {
			configurationElement(parser.evalNode("/mapper"));
			configuration.addLoadedResource(resource);
		}
	}

	public XNode getSqlFragment(String refid) {
		return sqlFragments.get(refid);
	}

	private void configurationElement(XNode context) {
		try {
			String namespace = context.getStringAttribute("namespace");
			builderAssistant.setCurrentNamespace(namespace);
			parameterMapElement(context.evalNodes("/mapper/parameterMap"));
			resultMapElements(context.evalNodes("/mapper/resultMap"));
			sqlElement(context.evalNodes("/mapper/sql"));
			bufferStatementNodes(context.evalNodes("select|insert|update|delete"));
			configuration.buildAllStatements();
		} catch (Exception e) {
			throw new RuntimeException("Error parsing Mapper XML. Cause: " + e, e);
		}

	}

	private void parameterMapElement(List<XNode> list) throws Exception {
		for (XNode parameterMapNode : list) {
			String id = parameterMapNode.getStringAttribute("id");
			String type = parameterMapNode.getStringAttribute("type");
			Class<?> parameterClass = resolveClass(type);
			builderAssistant.addParameterMap(id, parameterClass);
		}
	}

	private void resultMapElements(List<XNode> list) throws Exception {
		for (XNode resultMapNode : list) {
			resultMapElement(resultMapNode);
		}
	}

	/**
	 * 结果参数配置加载
	 * 
	 * @param resultMapNode
	 * @return
	 * @throws Exception
	 */
	private ResultMap resultMapElement(XNode resultMapNode) throws Exception {
		String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
		String type = resultMapNode.getStringAttribute("type", null);
		Class<?> typeClass = resolveClass(type);
		List<ResultMapping> listResultMapping = new ArrayList<ResultMapping>();
		List<XNode> resultChildren = resultMapNode.getChildren();
		for (XNode resultChild : resultChildren) {
			if ("result".equals(resultChild.getName())) {
				String property = resultChild.getStringAttribute("property");
				String column = resultChild.getStringAttribute("column");
				String javaType = resultChild.getStringAttribute("javaType");
				Class<?> javaTypeClass = null;
				if (javaType != null) {
					javaTypeClass = resolveClass(javaType);
				}
				listResultMapping.add(new ResultMapping(property, column, javaTypeClass));
			}
		}
		return builderAssistant.addResultMap(id, typeClass, listResultMapping);
	}

	private void sqlElement(List<XNode> list) throws Exception {
		for (XNode context : list) {
			String id = context.getStringAttribute("id");
			id = builderAssistant.applyCurrentNamespace(id);
			sqlFragments.put(id, context);
		}
	}

	/**
	 * To achieve mapper-order-independent parsing, stores the statement nodes
	 * into the temporary map without actually parsing them.
	 * 
	 * @param list
	 * @see Configuration#getMappedStatement(String)
	 * @see Configuration#buildAllStatements()
	 */
	private void bufferStatementNodes(List<XNode> list) {
		String currentNamespace = builderAssistant.getCurrentNamespace();
		configuration.addStatementNodes(currentNamespace, list);
	}

}
