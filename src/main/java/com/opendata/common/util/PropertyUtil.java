package com.opendata.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


/**
 * 获取全局配置的工具类
 * @author 付威
 */
public class PropertyUtil {
	
	
	/**
	 * 获取系统全局配置
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		try {
			Properties props = new Properties();
			InputStream fis = new FileInputStream(new File(getRootPath() + "\\WEB-INF\\classes\\" +"AppConfig.properties"));
			props.load(fis);
			String value=props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * 取得项目部署目录
	  * @return
	  */
	  private static String getRootPath()
		{
		  String classpath = PropertyUtil.class.getResource("").getPath();
			String path = classpath.substring(0,classpath.indexOf("WEB-INF/classes"));
			path = path.replace("%20", " ");
			
			return path;
		}
}
