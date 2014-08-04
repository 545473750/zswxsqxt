<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="com.opendata.login.action.LoginAction"%>
<%@page import="com.opendata.login.model.LoginInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
无标题文档
</title>

<link href="${ctx}/pattern/default/css/header.css" rel="stylesheet"
	type="text/css" />
<link href="styles/message-style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/scripts/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
function showMsg(){
    $.ajax({
	    url: '${ctx}/message/Message!findAllMessageByUserId.do',
	    type: 'post',
	    error: function(){
	     	 	alert("出错啦");
	     	 },
	    success: function(str){
	    $("#messageCountId").html(str);
	       if(str!="0")
	       {
				var infoObj=$("#messageRemind");
				
				
				var tTip=$("#messageCountId");
				
				var left=tTip.offset().left;
				var top=tTip.offset().top;
				
				 infoObj.css({
					left:left,
					top:top-45
					}).fadeIn("show",function(){
					
					});
	        }else
	        {
	        	closeMsg();
	        }
	    }
	});
	 setTimeout("showMsg()",1000000);
}
function closeMsg(){
	$("#messageRemind").hide();
}
$(function(){
	//进入页面加载完成后调用js方法
//	showMsg();
});
		
</script>
</head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E");
	String currDate = sdf.format(new Date());
%>
<body class="topbody"> <div class="logo"></div> <div class="info">
<div class="time">日期：<%=currDate%></div> <div class="welcome">欢迎您，<span
	class="red1"><%=((LoginInfo) request.getSession().getAttribute(
							LoginAction.LOGIN_INFO)).getUserName()%></span></div>

</div> <div class="navbar">
<!-- 
<a href="${ctx}/organiz/User!editPasswordPage.do" target="mainFrame"><span>修改密码</span></a>
<a href="${ctx}/organiz/User!editPersonPage.do" target="mainFrame"><span>个人中心</span></a>
 -->
 <div class="function"> 
 		<!-- 
 		<div class="div_remind" id="messageRemind"  style="display: ">
			<span class="message_remind" id="messageDiv">您有新的未读消息<a class="message_img" onclick="closeMsg()"></a></span>
			<span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span>
		</div>
		<a href="${ctx }/message/Message!list.do" target="mainFrame">您有<span class="red1" id="messageCountId"><%=request.getSession().getAttribute("messageCount")%></span>条消息提醒</a>
 	 -->
 <a href="${ctx}/j_spring_security_logout" target="_parent">
<img src="${ctx}/pattern/default/images/icon_exit.gif" width="13"
	height="15" align="absmiddle" /> 退出登录</a></div> </div> </body>
</html>