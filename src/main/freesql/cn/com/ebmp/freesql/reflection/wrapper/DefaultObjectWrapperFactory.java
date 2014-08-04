package cn.com.ebmp.freesql.reflection.wrapper;

import cn.com.ebmp.freesql.reflection.MetaObject;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

	public boolean hasWrapperFor(Object object) {
		return false;
	}

	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
		throw new RuntimeException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
	}

}
