package com.opendata.common.base;

import cn.org.rapid_framework.page.PageRequest;

/**
 * 查询条件对象基类
 * @author 付威
 *
 */
public class BaseQuery extends PageRequest implements java.io.Serializable {
	private static final long serialVersionUID = -360860474471966681L;
	public static final int DEFAULT_PAGE_SIZE = 10;
	
    static {
        System.out.println("BaseQuery.DEFAULT_PAGE_SIZE="+DEFAULT_PAGE_SIZE);
    }
    /**
     * 构造方法 初始化页面显示条数
     */
	public BaseQuery() {
		setPageSize(DEFAULT_PAGE_SIZE);
		super.setPageNumber(1);
	}
	  
}
