package com.opendata.springsecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;

/**
 * 登录验证器
 * @author 付威
 *
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String USERNAME = "loginname";
	public static final String PASSWORD = "password";
	
	private UserManager userManager;
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		 String j_captcha = request.getParameter("j_captcha");
	     String captcha = (String)request.getSession().getAttribute("captcha");
	     /*if(!j_captcha.equalsIgnoreCase(captcha)){//验证码 不区分大小写
	    	 throw new AuthenticationServiceException("验证码不正确");
	     }*/

		String username = obtainUsername(request);
		
		try {
			username = new String(username.getBytes("ISO-8859-1"), "utf8");
		} catch (Exception e) {
		}
		
		String password = obtainPassword(request);

		User user = this.userManager.findByName(username.trim());
		if (user == null || !password.equals(user.getPassword())) {
			throw new AuthenticationServiceException("用户名或者密码错误");
		}
		// 如果用户被禁用了，则不允许登录
		if("0".equals(user.getIsLeave())) {
			throw new AuthenticationServiceException("当前用户已被停用");
		}
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		setDetails(request, authRequest);//允许子类设置详细属性
		
		request.getSession().setAttribute("user", user);
		return this.getAuthenticationManager().authenticate(authRequest);
	}


	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}

}
