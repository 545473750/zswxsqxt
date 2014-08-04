package com.opendata.webservice.sys;

import javax.jws.WebService;

@WebService
public interface LoginWebService {
    
	/**
	 * 登录方法 返回登录用户信息 包括权限集合
	 * @param loginname
	 * @param password
	 * @return
	 */
	public String login(String loginname,String password);
}
