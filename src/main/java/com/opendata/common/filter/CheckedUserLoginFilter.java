package com.opendata.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.opendata.common.util.SessionUserUtil;
import com.opendata.common.util.StrUtil;

public class CheckedUserLoginFilter implements Filter
{

    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //MemCacheClientServiceImpl msi = (MemCacheClientServiceImpl) SpringTool.getBean("memCacheClientServiceImpl");
        SessionUserUtil userUtil = new SessionUserUtil(request, response);
        String requestUri = request.getRequestURI();
     
        if ( StrUtil.isNotNullOrBlank(requestUri) ) {
        	if ( requestUri.indexOf("index.jsp")!=-1||"/".equals(requestUri.trim()) ) {
				if ( userUtil.isNull() ) {
    				try {
                        userUtil.logout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    request.setAttribute("scriptString", "<script type=\"text/javascript\">parent.parent.parent.parent.parent.window.location.href='" + request.getContextPath() + "/login.jsp';</script>");
                    request.getRequestDispatcher(request.getContextPath() + "/commons/scriptString.jsp").forward(request, response);
                    return;
    			}
        	}
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
    }

}
