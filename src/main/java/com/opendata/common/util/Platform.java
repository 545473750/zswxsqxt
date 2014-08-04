package com.opendata.common.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 平台类<br>
 * 记录平台的配置信息供各个应用使用，并加载并初始化各个应用。
 * 
 * @author 付威
 */
public class Platform implements ApplicationContextAware
{

	private static ApplicationContext applicationContext;

	/**
	 * ApplicationContextAware接口的context注入函数.
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext()
	{
		if (applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义Platform");
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException
	{
		return (T) applicationContext.getBean(name);
	}
}
