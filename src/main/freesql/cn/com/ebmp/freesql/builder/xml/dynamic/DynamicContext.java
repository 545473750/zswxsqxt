package cn.com.ebmp.freesql.builder.xml.dynamic;

import java.util.HashMap;
import java.util.Map;

import cn.com.ebmp.freesql.builder.factory.Configuration;
import cn.com.ebmp.freesql.ognl.OgnlException;
import cn.com.ebmp.freesql.ognl.OgnlRuntime;
import cn.com.ebmp.freesql.ognl.PropertyAccessor;
import cn.com.ebmp.freesql.reflection.MetaObject;

public class DynamicContext
{

	public static final String PARAMETER_OBJECT_KEY = "_parameter";

	static
	{
		OgnlRuntime.setPropertyAccessor(ContextMap.class, new ContextAccessor());
	}

	private final ContextMap bindings;
	private final StringBuilder sqlBuilder = new StringBuilder();
	private int uniqueNumber = 0;

	public DynamicContext(Configuration configuration, Object parameterObject)
	{
		if (parameterObject != null && !(parameterObject instanceof Map))
		{
			MetaObject metaObject = configuration.newMetaObject(parameterObject);
			bindings = new ContextMap(metaObject);
		} else
		{
			bindings = new ContextMap(null);
		}
		bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
	}

	public Map<String, Object> getBindings()
	{
		return bindings;
	}

	public void bind(String name, Object value)
	{
		bindings.put(name, value);
	}

	public void appendSql(String sql)
	{
		sqlBuilder.append(sql);
		sqlBuilder.append(" ");
	}

	public String getSql()
	{
		return sqlBuilder.toString().trim();
	}

	public int getUniqueNumber()
	{
		return uniqueNumber++;
	}

	static class ContextMap extends HashMap<String, Object>
	{
		private static final long serialVersionUID = 2977601501966151582L;

		private MetaObject parameterMetaObject;

		public ContextMap(MetaObject parameterMetaObject)
		{
			this.parameterMetaObject = parameterMetaObject;
		}

		@Override
		public Object get(Object key)
		{
			if (super.containsKey(key))
			{
				return super.get(key);
			}

			if (parameterMetaObject != null)
			{
				Object object = parameterMetaObject.getValue(key.toString());
				if (object != null)
				{
					super.put(key.toString(), object);
				}

				return object;
			}

			return null;
		}
	}

	static class ContextAccessor implements PropertyAccessor
	{

		public Object getProperty(Map context, Object target, Object name) throws OgnlException
		{
			Map map = (Map) target;

			Object result = map.get(name);
			if (result != null)
			{
				return result;
			}

			Object parameterObject = map.get(PARAMETER_OBJECT_KEY);
			if (parameterObject instanceof Map)
			{
				return ((Map) parameterObject).get(name);
			}

			return null;
		}

		public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException
		{
			Map map = (Map) target;
			map.put(name, value);
		}
	}

}
