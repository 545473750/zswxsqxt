<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="java.util.*" %>

<html>
<head>
	<link href="${ctx}/styles/global.css" id="global" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/tree.css" rel="stylesheet" type="text/css" />

	<script src="${ctx}/scripts/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/dhtmlxcommon.js"></script>
	<script src="${ctx}/scripts/dhtmlxtree.js"></script>
	
	<script src="${ctx}/scripts/hf.js" type="text/javascript"></script>
	
	<style type="text/css">
	<!--
	html,body {
		height: 100%;
		font-size: 14px;
	}
	-->
	</style>
	<title>menu</title>
</head>
<body class="leftbody">
		<div class="left-tyj"></div>
		<div class="left-menu">
			<div class="left-tab">
				<ul>
					<li id="li_allURL" class="navover" style="font-size: 14px;">
						平台管理
					</li>
					<c:if test="${! empty sessionScope.partitions}">
						<li id="li_partition" style="font-size: 14px;">
							分区管理
						</li>
					</c:if>
				</ul>
			</div>
			<!-- 所有应用 -->
			<div id="div_allURL" class="subnav" style="font-size: 14px;"></div>
			<c:if test="${! empty sessionScope.partitions}">
				<!-- 分区管理列表 -->
				<div id="div_partition" class="subnav" style="display: none;font-size: 14px;">
				</div>
			</c:if>
		</div>
		<div class="left-dyj"></div>
</body>

	<script type="text/javascript" >
	$(function() {
		tree = new dhtmlXTreeObject("div_allURL", "100%", "100%", 0);
		tree.setSkin('dhx_skyblue');
		tree.setImagePath("${ctx}/images/tree/");
		tree.setOnClickHandler(function(id) {
			openPathDocs(id);
		});
	
		//设置树的编码  
		tree.setEscapingMode("utf8");
		tree.setXMLAutoLoading("${ctx}/login/Login!mainMenu.do");
		tree.loadXML("${ctx}/login/Login!mainMenu.do");
		if("${sessionScope.partitions}"){
			//分区的树型结构
			partitionTree = new dhtmlXTreeObject("div_partition", "100%", "100%", 0);
			partitionTree.setSkin('dhx_skyblue');
			partitionTree.setImagePath("${ctx}/images/tree/");
			partitionTree.setOnClickHandler(function(id) {
				openPartitionTreeDocs(id);
			});
			//设置树的编码  
			partitionTree.setEscapingMode("utf8");
			partitionTree.setXMLAutoLoading("${ctx}/login/Login!partitionMenu.do");
			partitionTree.loadXML("${ctx}/login/Login!partitionMenu.do");
		}
		
		$("li[id^='li_']").click(function() {
			var id = $(this).attr("id");
			id = id.replace("li", "div");
			$("li[id^='li_']").removeClass("navover");
			$("div[id^='div_']").css("display", "none");
			$("div[id^='div_']").find(".subnav").removeClass("subnav");
			$("#" + id).css("display", "block");
			$("#" + id).addClass("subnav");
			$(this).addClass("navover");
		});

		$("#ul_href li a").click(function() {
			var url = $(this).attr("id");
			window.open(url);
		});
	});
	function openPartitionTreeDocs(id) {
		partitionTree.setItemColor(partitionTree.getSelectedItemId(), "#696969", "#B8860B");
		var url = partitionTree.getUserData(id, "url");
		if (url != null) {
			url = '${ctx}'+url;
			window.parent.frames.mainFrame.location.href = url;
			return;
		}
	}

	function openPathDocs(id) {
		// 变色
		tree.setItemColor(tree.getSelectedItemId(), "#696969", "#B8860B");
		if (tree.getUserData(id, "url") != null) {
		    var kind = tree.getUserData(id, "kind");//是否弹出窗口标识
		    var url = tree.getUserData(id, "url");
		    if(url.substr(0,4)!='http'){
		    	url = '${ctx}'+url;
		    }
		    if(kind!=null&&kind=='1'){
		    	window.open(url);
		    }else{
				window.parent.frames.mainFrame.location.href = url;
			}
			
			return;
		}
	}

	function autoselectNode() {
		tree.selectItem(node, true);
		tree.openItem(node);
	}
</script>
</html>
