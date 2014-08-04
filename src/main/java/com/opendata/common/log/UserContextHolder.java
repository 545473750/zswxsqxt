package com.opendata.common.log;

import com.opendata.login.model.LoginInfo;


/**
 * 设置和取得当前线程的登录用户
 * @author 付威
 */
public class UserContextHolder {
	
	private static ThreadLocalUserHolder userholder;
	
	static {
        initialize();
    }
	
	 /**
	  * 初始化方法
	  */
	 private static void initialize() {
		 userholder = new ThreadLocalUserHolder();
	 }
	 
	 /**
	  * 设置用户
	  * @param user 登录用户
	  */
	 public static void setUser(LoginInfo user) {
		 if (user != null) {
			 userholder.setUser(user);
		 }
	 }
	 
	 /**
	  * 清除用户
	  */
	 public static void clear() {
		 userholder.clear();
	 }
	 
	 /**
	  * 取得用户
	  * @return
	  */
	 public static LoginInfo getUser() {
		 return userholder.getUser();
	 }

}
