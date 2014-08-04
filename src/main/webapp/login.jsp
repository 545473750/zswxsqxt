<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<%@ include file="/commons/taglibs.jsp"%>
		<c:set var="ctx" value="${pageContext.request.contextPath}" />
		<script language="javascript" src="${ctx}/scripts/jquery.js"></script>
		<%@ include file="/commons/messages.jsp"%>
		
		<title>中视卫星电视节目有限责任公司授权管理系统</title>
		<style>
		<!--
		html,body{height:100%;}
		body{margin:0px;background:#067db5 url(${ctx}/${pattern}/images/loginbg.jpg) repeat-x top;font-family:"宋体";font-size:12px;}
		.login{background:url(${ctx}/${pattern}/images/loginboxbg.jpg) no-repeat center top;width:100%;height:100%;}
		.loginbox{width:225px;height:160px;padding-left:281px;left:50%;top:230px;margin-left:-253px;position:absolute;}
		form{margin:0px;padding:0px;}
		.text{font-size:12px; background:url(${ctx}/${pattern}/images/login_text.gif) no-repeat left top;width:140px;padding:0px 5px;line-height:22px;height:22px;border:0px solid #fff;}
		.yzm{font-size:12px;background:url(${ctx}/${pattern}/images/login_yzm.gif) no-repeat left top;width:38px;padding:0px 5px;line-height:22px;height:22px;border:0px solid #fff;}
		a{color:#0178d5;}
		a:hover{color:#f60;}
		-->
		</style>
		<script type="text/javascript">
			function init() {
				document.getElementById('loginname').focus();
			}
		</script>
	</head>
	<body onload="init();">
	<div class="login" style="">
		  <div class="loginbox">
		    <form id="loginFrm" name="loginFrm" method="post" action="${ctx}/j_spring_security_check">
		    <table width="225" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="60" height="35" align="center">用户名：</td>
		        <td colspan="2"><input type="text" name="loginname" id="loginname" class="text" /></td>
		      </tr>
		      <tr>
		        <td height="35" align="center">密　码：</td>
		        <td colspan="2"><input type="password" name="password" id="password" class="text" /></td>
		      </tr>
		      <!-- 
		      <tr>
		        <td height="35" align="center">验证码：</td>
		        <td colspan="2"><table width="165" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td width="56"><input type="text" name="j_captcha" id="textfield3" class="yzm" /></td>
		            <td width="60"><img id="captchaPic"  style="vertical-align:bottom;" src="${ctx}/SimpleCaptchaPic.jsp"  width="55" height="21" /></td>
		            <td width="49"><a href="#" onclick="document.getElementById('captchaPic').src = '${ctx}/SimpleCaptchaPic.jsp?temp=' + Math.random();" >换一张</a></td>
		          </tr>
		        </table></td>
		      </tr>
		       -->
		      <tr>
		        <td height="40">&nbsp;</td>
		        <td width="82" colspan="2"><input type="image" name="imageField"  src="${ctx}/${pattern}/images/but_login.gif" /></td>
		        <!-- 
		        <td width="83"><input type="checkbox" name="checkbox" id="checkbox" /> 记住密码
		        </td> -->
		      </tr>
		      <tr>
				<td height="24" style="color: #ff0000;" colspan="3">
					<div style="${SPRING_SECURITY_LAST_EXCEPTION != null ? 'display:block' : 'display:none'}">
						${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message} 
					</div>
				</td>
			  </tr>
		    </table>
		    </form>
		  </div>
	</div>
	</body>
</html>
