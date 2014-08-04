package com.opendata.springsecurity;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.opendata.common.log.UserContextHolder;
import com.opendata.login.model.LoginInfo;

/**
 * 这个过滤器要插入到授权之前。 
 * 最核心的代码就是invoke方法中的InterceptorStatusToken token =super.beforeInvocation(fi);
 * 这一句，即在执行doFilter之前，进行权限的检查，而具体的实现已经交给accessDecisionManager了
 * @author 付威
 */
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {  
    //与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应，   
   //其他的两个组件，已经在AbstractSecurityInterceptor定义   
   private FilterInvocationSecurityMetadataSource securityMetadataSource;  
 
   @Override  
   public SecurityMetadataSource obtainSecurityMetadataSource() {  
        return this.securityMetadataSource;
   }
   
   public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain); 
        HttpServletRequest req = (HttpServletRequest)request;
        LoginInfo loginInfo = (LoginInfo)(req.getSession().getAttribute("Login_Info"));
        if(loginInfo!=null){
	        loginInfo.setPermission(req.getRequestURI());
	        UserContextHolder.setUser(loginInfo);
        }
       invoke(fi);
    }  
      
    private void invoke(FilterInvocation fi) throws IOException, ServletException {  
        // object为FilterInvocation对象   
        //super.beforeInvocation(fi);源码   
        //1.获取请求资源的权限   
        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);
        //2.是否拥有权限   
        //this.accessDecisionManager.decide(authenticated, object, attributes);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
           fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
        } finally {
            super.afterInvocation(token, null);  
        }  
   }  
 
    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {  
        return securityMetadataSource;  
    }  
  
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {  
        this.securityMetadataSource = securityMetadataSource;  
   }  
      
	public void init(FilterConfig arg0) throws ServletException {
	}
	public void destroy() {

	}
    @Override  
    public Class<? extends Object> getSecureObjectClass() {  
        //下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误   
       return FilterInvocation.class;  
    }  
}  

