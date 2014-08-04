package cn.com.ebmp.freesql.reflection.wrapper;

import cn.com.ebmp.freesql.reflection.MetaObject;

public interface ObjectWrapperFactory
{

	boolean hasWrapperFor(Object object);

	ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
