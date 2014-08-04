package com.opendata.webservice.sys;

import javax.jws.WebService;


/**
 * 系统同步
 * 
 * @author顾保臣
 */
@WebService
public interface SystemSynchronousService {

	/**
	 * 获取所有的系统同步数据
	 * @param code 编号
	 * @param username 用户名
	 * @param password 密码
	 * @param startIndex 开始索引
	 * @param endIndex 结束索引
	 */
	public String[][] getAllData(String code, String username, String password, int startIndex, int endIndex);
	
}
