<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div id="footer">
关于教师之家 | 使用帮助 | 服务说明 | 服务热线 | 版权所有 <br />
版权所有：朝阳区教育委员会  京ICP备05038995号<br />
地址：北京市朝阳区石佛营西里二号 邮编：100025${flash.success} 电话:010-85851085
</div>


<%-- Error Messages --%>
<c:if test="${flash.success != null}">
	
	<div id="flash_message" align="center" title="提示信息"  style="display:none;padding-top: 25px;background-color: #EEFFFF;">
		${flash.success}
	</div>
	<script type="text/javascript">
		$(function() {
			$( "#flash_message" ).dialog({width:200,height:100,draggable: false,show: "Bounce",hide: "puff" });
			setTimeout(function() {
				$( "#flash_message" ).dialog( "close" );
			}, 3000 );
		});
	</script>
</c:if>

<%-- Info Messages --%>
<c:if test="${flash.error != null}">
	<div id="flash_error" align="center" title="错误信息"  style="display:none;padding-top: 25px;background-color: #EEFFFF;">
		${flash.error}
	</div>
	<script type="text/javascript">
		$(function() {
			$( "#flash_error" ).dialog({width:200,height:100,draggable: false,show: "Bounce",hide: "puff" });
			//setTimeout(function() {
				//$( "#flash_error" ).dialog( "close" );
			//}, 4000 );
		});
	</script>
</c:if>


<script type="text/javascript">
	var iframe = document.getElementById("mainFrame");
	if (iframe) {
		var pheight = 1000;
		if (iframe.attachEvent){//IE
			iframe.attachEvent("onload", function(){
				setTimeout(function(){
					iframe.height=Math.max(iframe.contentWindow.document.body.scrollHeight, pheight);
				}, 150);
			});
		} else {
			iframe.onload = function(){
				setTimeout(function(){
					iframe.height=Math.max(iframe.contentWindow.document.documentElement.scrollHeight, pheight);
				}, 150);
			};
		}
	}
</script>
<%@ include file="/cfu/selectFile.jsp" %>