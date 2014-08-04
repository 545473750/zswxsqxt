<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.opendata.login.model.LoginInfo" %>
<%@page import="com.opendata.login.action.LoginAction" %>
<%@ include file="/commons/taglibs.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
html,body{height:100%;}
body{margin:0px;background:#FFF;}
img,div{display:block;position:absolute;z-index:50;border:none;}
.t{left:1px;top:1px;}
.b_l{left:10px;bottom:1px;}
.b_r{right:1px;bottom:1px;}
div{background:url(${ctx}/${pattern }/images/welcome_bg.jpg) no-repeat left top;z-index:30;top:16%;width:436px;height:160px;padding:88px 0px 0px 332px;left:50%;margin-left:-384px;}
p{font-size:12px;font-family:"宋体";padding:0px;margin:0px;line-height:40px;}
.msg{font-size:14px;font-weight:bold;color:#004c97;}
</style>
</head>

<body>
<img src="${ctx}/${pattern }/images/welcome_top.jpg" width="165" height="70" class="t" />
<img src="${ctx}/${pattern }/images/welcome_bot_l.gif" width="290" height="60" class="b_l" />
<img src="${ctx}/${pattern }/images/welcome_bot_r.jpg" width="333" height="259" class="b_r" />
<div>
  <p class="msg">欢迎您，管理员 <%=((LoginInfo)request.getSession().getAttribute(LoginAction.LOGIN_INFO)).getUserName() %></p>
</div>
</body>
</html>
