<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<%-- Error Messages --%>
<c:if test="${flash.success != null}">
	
	<div id="flash_message" align="center" title="提示信息"  style="display:none;padding-top: 25px;background-color: #EEFFFF;">
		${flash.success}
	</div>
	<script type="text/javascript">
		var h = window.document.documentElement.clientHeight+$(document).scrollTop();
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