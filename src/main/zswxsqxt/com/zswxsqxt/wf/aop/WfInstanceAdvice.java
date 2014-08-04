package com.zswxsqxt.wf.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opendata.common.util.SecretKeyUtil;
import com.opendata.common.util.StrUtil;
import com.zswxsqxt.wf.service.WfInstanceManager;

@Aspect
@Component
public class WfInstanceAdvice {

    private static Logger log = LoggerFactory.getLogger(WfInstanceAdvice.class); 
    @Autowired
	private WfInstanceManager instanceManager;

    @Pointcut("execution(* com.zswxsqxt.core.service.impl.SubscribServiceImpl.saveOrUpdate(..))")
    private void saveOrUpdate() {}

    @Around("saveOrUpdate()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        String targetName = joinPoint.getTarget().getClass().getName();
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        String methodName = joinPointObject.getName();
        Object[] arguments = joinPoint.getArgs();
        
        Object result = null;
        if ( arguments!=null&&arguments.length>0 ) {
        	Method method = arguments[0].getClass().getMethod("getId", null);
        	Object id = method.invoke(arguments[0], null);
        	
    		if ( StrUtil.isNullOrBlank(id) ) {
    			result = joinPoint.proceed();
    		}else{
    			return joinPoint.proceed();
    		}
    		
    		id = method.invoke(arguments[0], null);
    		//在新增操作后添加流程实例
    		String groupFlag = SecretKeyUtil.md5(targetName+"."+methodName);
    		instanceManager.createInstance(groupFlag, id.toString(), 0);
    		
        }
        return result;
    }
    
    public static void main(String[] args) throws Exception {
    	String groupFlag = "com.zswxsqxt.core.service.impl.SubscribServiceImpl.saveOrUpdate";
    	groupFlag = SecretKeyUtil.md5(groupFlag);
		
		System.out.println(groupFlag);
	}
}