package com.opendata.component.init;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@SuppressWarnings("serial")
public class InitGlobal extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=GBK";

    //Initialize global variables
    public void init() throws ServletException {
    	DBIniter.init();//数据库初始化
    	//预留
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>InitGlobal</title></head>");
        out.println("<body bgcolor=\"#ffffff\">");
        out.println("<p>项目启动初始化类</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    
    public void destroy(){}
}
