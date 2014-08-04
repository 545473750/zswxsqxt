package com.opendata.webservice.sys.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;

import com.opendata.webservice.sys.PictureService;


/**
 * 用来测试webservice好用不
 * @author 王全
 *
 */
@Aspect
public class ServiceTest {
	
	@Test
	public void test()
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean(); 
		factory.setServiceClass(PictureService.class);
		factory.setAddress("http://127.0.0.1:8080/attachment/service/pictureService");
		PictureService obj = (PictureService)factory.create();   
		System.out.println("连接成功！！");
	}
}
