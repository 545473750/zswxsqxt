package cn.com.ebmp.freesql.builder.xml;

import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.ebmp.freesql.builder.Exception.BuilderException;
import cn.com.ebmp.freesql.builder.factory.BaseBuilder;
import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.io.Resources;
import cn.com.ebmp.freesql.parsing.XNode;
import cn.com.ebmp.freesql.parsing.XPathParser;

public class XMLConfigBuilder extends BaseBuilder {
	protected Log logger = LogFactory.getLog(getClass());
	private boolean parsed;// 是否解析
	private XPathParser parser;// 解析器
	private List<String> otherFreesqlMapper = new ArrayList<String>();// 额外的sqlmapper

	public XMLConfigBuilder(Reader reader) {
		this(reader, null);
	}

	public void setOtherFreesqlMapper(List<String> otherFreesqlMapper) {
		this.otherFreesqlMapper = otherFreesqlMapper;
	}

	/**
	 * 加载系统资源配置文件
	 * 
	 * @param reader
	 * @param environment
	 * @param props
	 */
	public XMLConfigBuilder(Reader reader, Properties props) {
		super(new Configuration());
		this.parsed = false;
		this.parser = new XPathParser(reader, true, props, new XMLMapperEntityResolver());
	}

	/**
	 * 解析配置文件
	 * 
	 * @return
	 */
	public Configuration parse() {
		if (parsed) {
			throw new BuilderException("Each MapperConfigParser can only be used once.");
		}
		parsed = true;
		parseConfiguration(parser.evalNode("/configuration"));
		return configuration;
	}

	private void parseConfiguration(XNode root) {
		try {
			mapperElement(root.evalNode("mappers"));
		} catch (Exception e) {
			throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
		}
	}

	/**
	 * 获得开发模式，默认为部署模式，当develop=true时，则为开发模式
	 * @return
	 */
	public boolean getDevelopModel()
	{
		return parser.evalNode("/configuration").getBooleanAttribute("develop",false);
	}
	
	/**
	 * 根据映射文件的主节点，逐次解析各个映射文件
	 * 
	 * @param parent
	 * @throws Exception
	 */
	private void mapperElement(XNode parent) throws Exception {
		if (parent != null) {
			for (XNode child : parent.getChildren()) {
				String resource = child.getStringAttribute("resource");
				String url = child.getStringAttribute("url");
				Reader reader;
				if (resource != null && url == null) {
					URL curUrl=this.getClass().getResource("/");
					String path=curUrl.getPath()+resource;
					reader= new FileReader(path.replaceAll("%20", " "));
					logger.info("load freesql configfile " + resource);
					XMLMapperBuilder mapperParser = new XMLMapperBuilder(reader, configuration, resource, configuration.getSqlFragments());
					mapperParser.parse();
				} else if (url != null && resource == null) {
					URL curUrl=this.getClass().getResource("/");
					String path=curUrl.getPath()+resource;
					reader= new FileReader(path.replaceAll("%20", " "));
					logger.info("load freesql configfile " + url);
					XMLMapperBuilder mapperParser = new XMLMapperBuilder(reader, configuration, url, configuration.getSqlFragments());
					mapperParser.parse();
				} else {
					throw new BuilderException("A mapper element may only specify a url or resource, but not both.");
				}
			}
			// 添加额外增加的freesql配置文件
			for (int i = 0; i < otherFreesqlMapper.size(); i++) {
				String configFile = otherFreesqlMapper.get(i);
				Reader reader = Resources.getResourceAsReader(configFile);
				logger.info("load freesql configfile " + configFile);
				XMLMapperBuilder mapperParser = new XMLMapperBuilder(reader, configuration, configFile, configuration.getSqlFragments());
				mapperParser.parse();
			}
		}
	}

}
