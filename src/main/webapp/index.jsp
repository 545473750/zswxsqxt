<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-x:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>朝阳分院师训信息化建设项目</title>
</head>
<frameset rows="110,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${ctx}/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" />
  <frameset id="mainFrameset" cols="196,10,*" frameborder="no" border="0" framespacing="0">
  <%--
    <c:if test="${leftFrameFlag==1 || leftFrameFlag==null }">
    <frame src="${ctx}/menu.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" />
    </c:if>
    <c:if test="${leftFrameFlag==2 }">
     <frame src="${ctx}/login/Login!findAllMenu.do" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame"/>
    </c:if> --%>
    <frame src="${ctx}/login/Login!findAllMenu.do" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame"/>
    <frame src="${ctx}/leftbar.jsp" name="leftbar" scrolling="No" noresize="noresize" id="leftbar" />
    <frame src="${ctx}/welcome.jsp" name="mainFrame" id="mainFrame"  />
  </frameset>
  <!-- 
  <frame src="${ctx}/footer.jsp" name="footerFrame" scrolling="No" noresize="noresize" id="footerFrame" />
	 -->
</frameset>
<noframes><body>
</body></noframes>
</html>