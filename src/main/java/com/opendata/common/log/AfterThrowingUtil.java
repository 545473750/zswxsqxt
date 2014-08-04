package com.opendata.common.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;

import com.opendata.login.model.LoginInfo;
import com.opendata.sys.model.Log;
import com.opendata.sys.service.LogManager;

/**
 * 通过捕获异常自动记录异常日志
 * @author 付威
 */
@Aspect
public class AfterThrowingUtil {
	
	/**
	 * 日志manager
	 */
	private LogManager logManager;

	
	
	

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}





	
	@Pointcut("execution(* com.opendata.*.action..*.*(..))")
	public void methods() {
	}

	/**
	 * 出现异常自动记录日志
	 * @param ex 异常
	 */
	@AfterThrowing(pointcut="methods()",
		    throwing="ex")
	public void ExceptionLog(Exception ex) {
		LoginInfo loginUser = UserContextHolder.getUser();
		//保证用户已经登陆后并通过菜单访问功能时才记录操作时间
		if (loginUser != null ) {
			Log log = new Log();
			log.setDf("0");
			log.setIp(loginUser.getIp());
			log.setPermission(loginUser.getPermission());
			log.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			log.setType("APP_LOG001");
			log.setUsername(loginUser.getUserName());
			logManager.save(log);
		}
	}

}
