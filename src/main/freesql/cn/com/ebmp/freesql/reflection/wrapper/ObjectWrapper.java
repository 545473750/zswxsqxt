package cn.com.ebmp.freesql.reflection.wrapper;

import cn.com.ebmp.freesql.reflection.MetaObject;
import cn.com.ebmp.freesql.reflection.factory.ObjectFactory;
import cn.com.ebmp.freesql.reflection.property.PropertyTokenizer;

public interface ObjectWrapper {

	Object get(PropertyTokenizer prop);

	void set(PropertyTokenizer prop, Object value);

	String findProperty(String name);

	String[] getGetterNames();

	String[] getSetterNames();

	Class getSetterType(String name);

	Class getGetterType(String name);

	boolean hasSetter(String name);

	boolean hasGetter(String name);

	MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

}
