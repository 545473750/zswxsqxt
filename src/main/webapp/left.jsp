<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>menu</title>
<link href="${ctx}/pattern/default/css/left.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.6.2.min.js"></script>
		<style type="text/css">
<!--
html,body {
	height: 100%;
}
.closeUl {display:none;}
.openUl{display:block;}
-->
</style>

<script type="text/javascript" >
 
 $(function(){
	 var divh = $("body").height();
	 $(".menulist").height(divh);
 });
 
 function cancel(event){
	 //取消事件冒泡
	 var e=arguments.callee.caller.arguments[0]||event;
	 if (e && e.stopPropagation) {
	  // this code is for Mozilla and Opera
	  e.stopPropagation();
	 } else if (window.event) {
	  // this code is for IE
	  window.event.cancelBubble = true;
	 } 
 }
 function toggle(obj,className){
	  var ul0 = $(obj).find("ul").eq(0);
	  var a0 = $(obj).find("a").first();
	  if(a0.attr("class")==className){
		  a0.attr("class","");
	  }else{
		  a0.attr("class",className);
	  }
	  if(ul0.attr("class")=="closeUl"){
		 ul0.attr("class","openUl");
	 }else{
		 ul0.attr("class","closeUl");
	 }
 }
 function togglelevel3(obj,className){
	 $("a").attr("class","");
	  var a0 = $(obj).find("a").first();
	  a0.attr("class",className);
 }	
 function limitnnota1(parameters,n) {
	if (parameters) {
		if (parameters.length > n) {
			document.write(parameters.substr(0, n) + "...");
		} else {
			document.write(parameters);
		}
	}
}
</script>
</head>

<body>
<div class="operations">
  <div class="menulist" style="overflow: auto;">
  	<ul>
  		<c:forEach var="obj" items="${li}" varStatus="vs">
  		<!-- 一级菜单 -->
  		<li onclick="toggle(this,'open');cancel();" style="cursor: pointer;">
  			<c:if test="${obj.menuType eq '1' }">
		     <a href="${ctx}${obj.url }"  target="mainFrame">${obj.name}</a>
		   	</c:if>
		   	<!-- 目录 -->
		    <c:if test="${obj.menuType eq '0' }">
		    	<a class="noturl" >${obj.name}</a>
		    
		    	<c:if test="${not empty obj.child}">
		    		<ul class="closeUl"  >
				      	<c:forEach items="${obj.child}" var="child">
				      		<!-- 二级菜单 -->
				      		<li onclick="toggle(this,'op');cancel();"    style="cursor: pointer;">
					      		<c:if test="${child.menuType eq '1' }">
					      			<span>
					      			<a href="${ctx}${child.url }"   target="mainFrame"><img src="${ctx}/pattern/default/images/icon_menu.gif" align="absmiddle"  style="margin-left:-18px;" />
					      			<script type="text/javascript">
											limitnnota1('${child.name}',8);
									</script>
					      			 </a>
					      			</span>
					      		</c:if>
					      		<!-- 目录 -->
						      	<c:if test="${child.menuType eq '0' }">
						      	<!-- 菜单 -->
						      		<c:if test="${empty child.child}">
						      		<a  class="noturl" >
						      		<script type="text/javascript">
										limitnnota1('${child.name}',10);
									</script>
						      		</a>
						      		</c:if>
						      		<!-- 目录 -->
						      		<c:if test="${not empty child.child}">
						      		<a  target="mainFrame" >
						      		<script type="text/javascript">
										limitnnota1('${child.name}',10);
									</script>
						      		</a>
						      			<ul class="closeUl" >
									      	<c:forEach items="${child.child}" var="tt">
									      		<!-- 三级菜单 -->
									      		<li onclick="togglelevel3(this,'domw');cancel();" style="cursor: pointer;">
										      		<c:if test="${tt.menuType eq '1' }">
										      			<a href="${ctx}${tt.url }" target="mainFrame"  title="${tt.name}"><img src="${ctx}/pattern/default/images/icon_menu.gif" width="11" height="13" align="absmiddle" />
															<script type="text/javascript">
																limitnnota1('${tt.name}',10);
															</script>
														</a>
										      		</c:if>
									      		</li>
									      	</c:forEach>
								      	</ul>
						      		</c:if>
								</c:if>
				      		</li>
				      	</c:forEach>
				    </ul>
				</c:if>
		    </c:if>
		   
  		</li>	
  		</c:forEach>
  	</ul>
  	 
  </div>
</div>
</body>
</html>

