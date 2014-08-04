<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/styles/global.css" id="global" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
html,body {
	height: 100%;
}
-->
</style>
<script src="<c:url value="/scripts/jquery.js"/>" type="text/javascript"></script>
<script src="<c:url value="/scripts/hf.js"/>" type="text/javascript"></script>
<script type="text/javascript">
	var open = true;
	function OpenAndClose() {
		var obj = window.top.document.getElementById("mainFrameset");
		if (open) {
			obj.cols = "0,10,*";
			open = false;
			document.getElementById("opimg").style.display = "none";
			document.getElementById("climg").style.display = "block";
		} else {
			obj.cols = "196,10,*";
			open = true;
			document.getElementById("opimg").style.display = "block";
			document.getElementById("climg").style.display = "none";
		}
	}
</script>
</head>
<body class="barbody">
	<div class="barpic" onClick="OpenAndClose();">
		<div class="barpict"></div>
		<img id="opimg" src="${ctx}/images/leftbar-close.gif" width="10" height="37" alt="关闭左侧菜单" /><img id="climg" src="${ctx}/images/leftbar-open.gif" width="10" height="37" style="display: none;" alt="打开左侧菜单" />
	</div>
</body>
</html>