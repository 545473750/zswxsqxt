package com.opendata.webservice.sys;

import javax.jws.WebService;

@WebService
public interface DictWebService {
	
	/**
	   * 其他应用调用数据字典接口传递字典类型，基础管理平台根据字典类型返回与之匹配的数据字典集合。
	   * @param type
	   * @return 返回结果为拼成的字符串 code:name|
	   */
	   public String getDicList(String type);
	   
	   /**
	    * 其他应用调用数据字典接口传递字典类型+字典key，基础管理平台根据字典类型+字典key返回与之匹配的数据字典值。
	    * @param type
	    * @param key
	    * @return
	    */
	   public String getDicValue(String type,String key);

}
