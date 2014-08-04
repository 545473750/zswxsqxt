package com.opendata.webservice.log.client;

import javax.jws.WebService;



@WebService
public interface AppLogWebService {
	
	
	/**
	 * 该方法提供服务器查看单个应用的日志记录
	 * @param type
	 * @param begintime
	 * @param endTime
	 * @param pageSize
	 * @param pageNum
	 * @return 
	 */
	public String getAppLog(String type,String begintime,String endTime,int pageSize,int pageNum);
}
