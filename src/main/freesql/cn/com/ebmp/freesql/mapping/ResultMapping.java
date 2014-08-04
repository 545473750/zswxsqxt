package cn.com.ebmp.freesql.mapping;

import org.hibernate.type.Type;

/**
 * 返回结果详细映射信息
 * 
 * @author Administrator
 * 
 */
public class ResultMapping {

	private String property;// 字段属性
	private String column;// 对应数据库字段名称
	private Class javaType;// 仅当返回类型为Map时，使用此属性
	private Type hibernateType;

	public Type getHibernateType() {
		return hibernateType;
	}

	public void setHibernateType(Type hibernateType) {
		this.hibernateType = hibernateType;
	}

	public Class getJavaType() {
		return javaType;
	}

	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public ResultMapping() {

	}

	public ResultMapping(String property, String column, Class javaType) {
		this.property = property;
		this.column = column;
		this.javaType = javaType;
	}
}
