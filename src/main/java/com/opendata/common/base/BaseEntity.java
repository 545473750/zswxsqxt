package com.opendata.common.base;


/**
 * 实体对象的父类
 * @author 付威
 */
public class BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7200095849148417467L;
	/**
	 * 日期格式
	 */
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 *时间格式 
	 */
	protected static final String TIME_FORMAT = "HH:mm:ss";
	/**
	 * 日期时间格式
	 */
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期时间格式带毫秒数
	 */
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	
}
