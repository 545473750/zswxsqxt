<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 所有页面父页面 -->
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<%@ include file="/commons/meta.jsp" %>
	<base href="<%=basePath%>">
	<!-- 定义head块，子页面覆盖head块 -->
	<rapid:block name="head"/>
</head>

<!-- 定义body块，子页面覆盖body块 -->
<rapid:block name="content"/>
<%@ include file="/commons/messages.jsp" %>
</html>	