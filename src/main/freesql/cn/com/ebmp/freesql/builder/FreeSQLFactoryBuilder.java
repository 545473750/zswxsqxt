package cn.com.ebmp.freesql.builder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.builder.xml.XMLConfigBuilder;
import cn.com.ebmp.freesql.io.Resources;

public class FreeSQLFactoryBuilder
{
	protected Log logger = LogFactory.getLog(getClass());
	private static FreeSQLFactoryBuilder factoryBuilder;
	private Configuration configuration;
	private static Boolean DevelopModel = null;

	private FreeSQLFactoryBuilder()
	{
		logger.info("system init freesql............");
	}

	/**
	 * 通过自己的freesql文件，动态增加配置文件
	 * @param otherFreesqlMapper
	 * @return
	 */
	public static FreeSQLFactoryBuilder builder(List <String> otherFreesqlMapper)
	{
		FreeSQLFactoryBuilder.configDevelopModel();
		if (DevelopModel)
		{
			try
			{
				factoryBuilder = new FreeSQLFactoryBuilder();
				Reader reader = Resources.getResourceAsReader("freesqlConfiguration.xml");
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				parser.setOtherFreesqlMapper(otherFreesqlMapper);
				factoryBuilder.configuration = parser.parse();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			if (factoryBuilder == null)
			{
				try
				{
					factoryBuilder = new FreeSQLFactoryBuilder();
					Reader reader = Resources.getResourceAsReader("freesqlConfiguration.xml");
					XMLConfigBuilder parser = new XMLConfigBuilder(reader);
					parser.setOtherFreesqlMapper(otherFreesqlMapper);
					factoryBuilder.configuration = parser.parse();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return factoryBuilder;
	}

	/**
	 * 根据系统中的主配置文件加载freesql
	 * @return
	 */
	public static FreeSQLFactoryBuilder builder()
	{
		FreeSQLFactoryBuilder.configDevelopModel();
		if (DevelopModel)
		{
			try
			{
				factoryBuilder = new FreeSQLFactoryBuilder();
				Reader reader = Resources.getResourceAsReader("freesqlConfiguration.xml");
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				factoryBuilder.configuration = parser.parse();
				return factoryBuilder;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			if (factoryBuilder == null)
			{
				try
				{
					factoryBuilder = new FreeSQLFactoryBuilder();
					Reader reader = Resources.getResourceAsReader("freesqlConfiguration.xml");
					XMLConfigBuilder parser = new XMLConfigBuilder(reader);
					factoryBuilder.configuration = parser.parse();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return factoryBuilder;
	}

	/**
	 *根据Readr加载freesql
	 * 
	 * @param reader
	 * @return
	 */
	public static FreeSQLFactoryBuilder builder(Reader reader)
	{
		FreeSQLFactoryBuilder.configDevelopModel();
		if (DevelopModel)
		{
			factoryBuilder = new FreeSQLFactoryBuilder();
			XMLConfigBuilder parser = new XMLConfigBuilder(reader);
			factoryBuilder.configuration = parser.parse();
		} else
		{

			if (factoryBuilder == null)
			{
				factoryBuilder = new FreeSQLFactoryBuilder();
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				factoryBuilder.configuration = parser.parse();
			}
		}
		return factoryBuilder;
	}

	/**
	 * 根据配置文件路径加载freesql
	 * @param configFilePath
	 * @return
	 * @throws Exception
	 */
	public static FreeSQLFactoryBuilder builder(String configFilePath) throws Exception
	{
		FreeSQLFactoryBuilder.configDevelopModel();
		if (DevelopModel)
		{
			factoryBuilder = new FreeSQLFactoryBuilder();
			Reader reader = Resources.getResourceAsReader(configFilePath);
			XMLConfigBuilder parser = new XMLConfigBuilder(reader);
			factoryBuilder.configuration = parser.parse();
		} else
		{
			if (factoryBuilder == null)
			{
				factoryBuilder = new FreeSQLFactoryBuilder();
				Reader reader = Resources.getResourceAsReader(configFilePath);
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				factoryBuilder.configuration = parser.parse();
			}
		}

		return factoryBuilder;
	}

	/**
	 * 根据业务名称，参数，返回绑定的SQL
	 * 
	 * @param name
	 * @param parameterObject
	 * @return
	 */
	public FreeSql findFreeSql(String name, Object parameterObject)
	{
		FreeSql.Builder builder = new FreeSql.Builder(configuration, name, parameterObject);
		return builder.build();
	}
	
	/**
	 * 获得开发模式
	 * @return
	 */
	private static void configDevelopModel()
	{
		if(DevelopModel==null)
		{
			try {
				Reader reader = Resources.getResourceAsReader("freesqlConfiguration.xml");
				DevelopModel= new XMLConfigBuilder(reader).getDevelopModel();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
