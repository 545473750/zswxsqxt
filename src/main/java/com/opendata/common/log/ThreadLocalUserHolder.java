package com.opendata.common.log;

import org.springframework.util.Assert;

import com.opendata.login.model.LoginInfo;
/**
 * 通过ThreadLocal实现为每一个线程维护变量 每个线程都能取得自己登录的用户 
 * @author 付威
 */
public class ThreadLocalUserHolder {
	
	/**
	 * 为每一个线程维护变量 
	 */
	private static final ThreadLocal<LoginInfo> contextHolder = new ThreadLocal<LoginInfo>();
	
	public void clear() {
		contextHolder.remove();
	}

	/**
	 * 取得用户
	 * @return 返回登录用户
	 */
	public LoginInfo getUser() {
		LoginInfo user = contextHolder.get();
		return user;
	}

	/**
	 * 设置用户
	 * @param user 登录用户
	 */
	public void setUser(LoginInfo user) {
		Assert.notNull(user, "不能将空用户对象放入容器");
		contextHolder.set(user);
	}
	


}
