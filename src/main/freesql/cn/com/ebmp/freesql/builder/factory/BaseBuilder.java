package cn.com.ebmp.freesql.builder.factory;

import cn.com.ebmp.freesql.builder.Exception.BuilderException;
import cn.com.ebmp.freesql.type.TypeAliasRegistry;

public abstract class BaseBuilder
{
	protected final Configuration configuration;
	protected final TypeAliasRegistry typeAliasRegistry;

	public BaseBuilder(Configuration configuration)
	{
		this.configuration = configuration;
		this.typeAliasRegistry = configuration.getTypeAliasRegistry();
	}

	public Configuration getConfiguration()
	{
		return configuration;
	}

	protected String stringValueOf(String value, String defaultValue)
	{
		return value == null ? defaultValue : value;
	}

	protected Boolean booleanValueOf(String value, Boolean defaultValue)
	{
		return value == null ? defaultValue : Boolean.valueOf(value);
	}

	protected Integer integerValueOf(String value, Integer defaultValue)
	{
		return value == null ? defaultValue : Integer.valueOf(value);
	}

	protected Object resolveInstance(Class<?> type)
	{
		if (type == null)
			return null;
		try
		{
			return type.newInstance();
		} catch (Exception e)
		{
			throw new BuilderException("Error instantiating class. Cause: " + e, e);
		}
	}

	protected Class<?> resolveClass(String alias)
	{
		if (alias == null)
			return null;
		try
		{
			return resolveAlias(alias);
		} catch (Exception e)
		{
			throw new BuilderException("Error resolving class . Cause: " + e, e);
		}
	}

	protected Class<?> resolveAlias(String alias)
	{
		return typeAliasRegistry.resolveAlias(alias);
	}

}
