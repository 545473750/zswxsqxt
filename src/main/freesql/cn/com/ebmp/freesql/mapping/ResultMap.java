package cn.com.ebmp.freesql.mapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;

/**
 * 结果配置
 * 
 * @author Administrator
 * 
 */
public class ResultMap
{

	private String id;// 名称
	private Class type;// 类别
	private List<ResultMapping> resultMapping;// 详细映射属性，映射属性顺序必需与数据库返回结果顺序一致

	public static class Builder
	{
		private ResultMap resultMap = new ResultMap();

		public Builder(String id, Class type, List<ResultMapping> resultMapping)
		{
			resultMap.id = id;
			resultMap.type = type;
			resultMap.resultMapping = resultMapping;
		}

		public ResultMap build()
		{
			return resultMap;
		}

		public ResultMap builderHibernateType()
		{
			// 进行java类型与hibernate类型映射对应
			buildHibernateType();
			return resultMap;
		}

		/**
		 * 根据返回类型，将对应的属性类型，映射对应的hibernate类型
		 */
		private void buildHibernateType()
		{
			if ((resultMap.type != null) && (!isBaseType(resultMap.type)))
			{
				if (!resultMap.resultMapping.isEmpty())
				{
					Class objType = resultMap.type;
					Method[] methods = objType.getMethods();
					for (ResultMapping mapping : resultMap.resultMapping)
					{
						for (Method method : methods)
						{
							if (method.getName().toLowerCase().equals(("set" + mapping.getProperty()).toLowerCase()))
							{
								Class javaType = method.getParameterTypes()[0];
								Type hibernateType = transHibernateType(javaType);
								mapping.setHibernateType(hibernateType);
								mapping.setJavaType(javaType);
								break;
							}
						}
					}

				}
			}

		}

		private Type transHibernateType(Class javaType)
		{
			if (javaType == Boolean.class)
			{
				return Hibernate.BOOLEAN;
			} else if (javaType == boolean.class)
			{
				return Hibernate.BOOLEAN;
			} else if (javaType == Short.class)
			{
				return Hibernate.SHORT;
			} else if (javaType == short.class)
			{
				return Hibernate.SHORT;
			} else if (javaType == Integer.class)
			{
				return Hibernate.INTEGER;
			} else if (javaType == int.class)
			{
				return Hibernate.INTEGER;
			} else if (javaType == Long.class)
			{
				return Hibernate.LONG;
			} else if (javaType == long.class)
			{
				return Hibernate.LONG;
			} else if (javaType == Double.class)
			{
				return Hibernate.DOUBLE;
			} else if (javaType == double.class)
			{
				return Hibernate.DOUBLE;
			} else if (javaType == Float.class)
			{
				return Hibernate.FLOAT;
			} else if (javaType == float.class)
			{
				return Hibernate.FLOAT;
			} else if (javaType == Date.class)
			{
				return Hibernate.DATE;
			} else if (javaType == String.class)
			{
				return Hibernate.STRING;
			}
			return Hibernate.STRING;
		}

		private boolean isBaseType(Object object)
		{
			if (object.getClass() == Boolean.class)
			{
				return true;
			}
			if (object.getClass() == boolean.class)
			{
				return true;
			} else if (object.getClass() == Short.class)
			{
				return true;
			} else if (object.getClass() == short.class)
			{
				return true;
			} else if (object.getClass() == Integer.class)
			{
				return true;
			} else if (object.getClass() == int.class)
			{
				return true;
			} else if (object.getClass() == Long.class)
			{
				return true;
			} else if (object.getClass() == long.class)
			{
				return true;
			} else if (object.getClass() == Double.class)
			{
				return true;
			} else if (object.getClass() == double.class)
			{
				return true;
			} else if (object.getClass() == Float.class)
			{
				return true;
			} else if (object.getClass() == float.class)
			{
				return true;
			} else if (object.getClass() == Date.class)
			{
				return true;
			} else if (object.getClass() == String.class)
			{
				return true;
			} else if (object.getClass() == Map.class)
			{
				return true;
			} else if (object.getClass() == List.class)
			{
				return true;
			} else if (object.getClass() == HashMap.class)
			{
				return true;
			} else if (object.getClass() == ArrayList.class)
			{
				return true;
			}
			return false;
		}

	}

	public String getId()
	{
		return id;
	}

	public Class getType()
	{
		return type;
	}

	public List<ResultMapping> getResultMapping()
	{
		return resultMapping;
	}

	public List<String> getResultProperty()
	{
		List<String> propertyList = new ArrayList<String>();
		if (resultMapping != null)
		{
			for (ResultMapping mapping : resultMapping)
			{
				propertyList.add(mapping.getProperty());
			}
		}
		return propertyList;
	}

	public void setResultMapping(List<ResultMapping> resultMapping)
	{
		this.resultMapping = resultMapping;
	}
}
