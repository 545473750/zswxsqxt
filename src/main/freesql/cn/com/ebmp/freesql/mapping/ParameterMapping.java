package cn.com.ebmp.freesql.mapping;

public class ParameterMapping
{
	private String property;
	private Class javaType = Object.class;

	public ParameterMapping()
	{

	}

	public ParameterMapping(String property, Class javaType)
	{
		this.property = property;
		this.javaType = javaType;
	}

	public String getProperty()
	{
		return property;
	}

	public Class getJavaType()
	{
		return javaType;
	}

}
