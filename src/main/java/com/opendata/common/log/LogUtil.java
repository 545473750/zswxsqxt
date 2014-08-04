package com.opendata.common.log;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.opendata.login.model.LoginInfo;
import com.opendata.sys.model.Log;
import com.opendata.sys.service.LogManager;

/**
 * 通过切面自动记录用户操作的日志
 * @author 付威
 */
@Aspect
// 声明切面
@Component
public class LogUtil {

	private LogManager logManager;

	/**
	 * 声明在调用BaseManager完成后调用此方法
	 */
	@Pointcut("execution(* com.opendata.common.base.BaseManager.*(..))")
	public void log() {
	}

	/**
	 * 自动保存操作日志 包括添加 修改 删除 在调用完log方法调用此方法
	 * @param joinpoint
	 */
	@After("log()")
	public void saveLog(JoinPoint joinpoint) {
		try {
			Object[] objs = joinpoint.getArgs();
			if (objs != null && objs.length > 0) {
				String method = joinpoint.getSignature().getName();
				if("save".equals(method)||"update".equals(method)||"delete".equals(method)){
					LoginInfo loginUser = null;
					try {
						//ThreadLocalUserHolder threadLocalSessionHolder = new ThreadLocalUserHolder();
						loginUser = UserContextHolder.getUser();
					} catch (Exception e) {
						loginUser = null;
					}
					
					String st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					Object obj = objs[0];
					if (obj != null) {
						Class classType = obj.getClass();
						
						String userid = null;
						if (loginUser != null) {
							userid = String.valueOf(loginUser.getUserID());
						}
						
						if (!classType.equals(java.lang.String.class) && !classType.equals(com.opendata.sys.model.Log.class) ) {
								String strMethod = "";// 记录拦截的操作方法
								if (method.equals("save")) {
									strMethod = "添加";
								} else if (method.equals("update")) {
									strMethod = "修改";
								} else if (method.equals("delete")) {
									strMethod = "删除";
								}
							    //取得ID值
								Field field = classType.getField("TABLE_ALIAS");
								PropertyDescriptor pd = new PropertyDescriptor("id", classType);
								Method rM = pd.getReadMethod();// 获得读方法
								String id = (String) rM.invoke(obj);
	
								//取得表名
								String tablename = (String) field.get(null);	
								
								// 新增一条日志记录
								Log log = new Log();
								log.setDf("0");
								if (loginUser != null) {
									log.setIp(loginUser.getIp());
									log.setUsername(loginUser.getUserName());
									log.setPermission(loginUser.getPermission());
								} else {
									log.setIp("127.0.0.1");
									log.setUsername("系统初始化");
								}
								log.setTs(st);
								log.setType("APP_LOG001");
								log.setContent(strMethod + "操作对象为" + tablename + id);
								
								logManager.save(log);
						}
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}
	
}
