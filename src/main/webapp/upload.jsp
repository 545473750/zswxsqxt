<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'upload.jsp' starting page</title>
    
	<link href="<%=basePath%>cfu/css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>scripts/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>cfu/js/swfupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>cfu/js/swfupload.queue.js"></script>
	<script type="text/javascript" src="<%=basePath%>cfu/js/fileprogress.js"></script>
	<script type="text/javascript" src="<%=basePath%>cfu/js/handlers.js"></script>
	
	<style type="text/css">
		#cmupload {
			padding: 0; margin: 0;
			width: 788px;
			height: 430px;
			left: 50%;
			top: 50%;
			margin-left: -375px;
			margin-top: -215px;
			position: absolute;
			border: 1px solid #dedede;
			-moz-border-radius: 15px 15px 0 0;      /* Gecko browsers */
		    -webkit-border-radius: 15px 15px 0 0;   /* Webkit browsers */
		    border-radius:15px 15px 0 0;
		}
		
		#cmupload .prograss-bar{
			height: 30px;
			width: 530px;
			border: 1px solid black;
			position: absolute;
			left: 50%;
			top: 50%;
			margin-left: -265px;
			margin-top: -14px;
			display: none;
			z-index: 999999999;
		}
		
		#cmupload .file-list-title{
			height: 25px;
			color: white;
			text-align: left;
			line-height: 25px;
			background: #BACCE6;/*#0000FF*/
			-moz-border-radius: 15px 15px 0 0;      /* Gecko browsers */
		    -webkit-border-radius: 15px 15px 0 0;   /* Webkit browsers */
		    border-radius:15px 15px 0 0;
		}
		
		
		#cmupload .file-list-title .choosefile{
			float: left;
		}
		
		#cmupload .file-list-title .close{
			width: 25px;
			height: 25px;
			float: right;
			text-align: center;
			cursor: pointer;
		}
		
		#cmupload .file-list-box{
			height: 330px;
			padding: 5px 0px 5px 5px;
			overflow-y: scroll;
		}
		
		#cmupload .file-list-box .filebox{
			width: 105px;
			height: 120px;
			float: left;
			margin: 5px 5px 5px 5px;
			padding-left: 10px;
			border: 1px solid white;
			overflow: hidden;
			position: relative;
		}
		
		#cmupload .file-list-box .filebox .fileCheck{
			position: absolute;
			z-index: 3;
		}
		
		#cmupload .file-list-box .filebox .fileImage{
			width: 90px;
			height: 85px;
			position: relative;
			border: 1px solid #dedede;
			-moz-border-radius: 8px;      /* Gecko browsers */
		    -webkit-border-radius: 8px;   /* Webkit browsers */
		    border-radius:8px;
		}
		
		#cmupload .file-list-box .filebox .fileImage img{
			position: absolute;
			left: 50%;
			top: 50%;
			margin-left: -8px;
			margin-top: -8px;
		}
		
		#cmupload .file-list-box .filebox .fileName{
			font-family:Tahoma,Verdana,STHeiTi,simsun,sans-serif;
			line-height:16px;
			width: 90px;
			font-size: 12px;
			text-align: center;
			margin: 2px 0px;
		}
		
		#cmupload .file-list-box .filebox .fileSize{
			display: none;
		}
		
		#cmupload .file-list-default{
			padding: 5px 0px 5px 5px;
		}
		
		#cmupload .file-list-default .filebox{
			border-bottom: 1px solid gray;
			height: 30px;
			line-height: 30px;
			overflow: hidden;
			padding: 0px 10px;
		}
		
		#cmupload .file-list-default .filebox .fileCheck{
			float: left;
			margin-top: 5px;
			margin-right: 5px;
		}
		
		#cmupload .file-list-default .filebox .fileImage{
			font-size: 12px;
			float: left;
			margin-top: 6px;
			margin-right: 5px;
		}
		
		#cmupload .file-list-default .filebox .fileName{
			font-size: 12px;
			text-align: center;
			float: left;
			margin-right: 5px;
			text-align: left;
			width: 500px;
			overflow: hidden;   
			white-space: nowrap;     
			-o-text-overflow: ellipsis;     
			text-overflow: ellipsis;
		}
		
		#cmupload .file-list-default .filebox .fileSize{
			text-align: center;
			float: right;
			width: 100px;
			text-align: left;
		}
		
		#cmupload .filebox:hover{
			border: 1px solid #66A7E8;
			background: #E5F3FB;
			-moz-border-radius: 8px;      /* Gecko browsers */
		    -webkit-border-radius: 8px;   /* Webkit browsers */
		    border-radius:8px;
		}
		
		.mask{
			width: 100%;
			height: 100%;
			position: absolute;
			top: 0;
			left: 0;
			background: #191919;
			filter: Alpha(Opacity=30);
			opacity: 0.3;
			-moz-opacity: 0.3;
			z-index: 999;
			display: none;
		}
		
	</style>
	
	<script type="text/javascript">
  	var upload1;
	window.onload = function() {
		upload1 = new SWFUpload({
			// Backend Settings
			upload_url: "<%=basePath%>ts/upload.do",
			post_params: {"picSESSID" : "songhao"},
			file_post_name: "upload",
			// File Upload Settings
			file_size_limit : "512000",	// 500MB
			file_types : "*.*",
			file_types_description : "All Files",
			file_upload_limit : "10",
			file_queue_limit : "0",

			// Event Handler Settings (all my handlers are in the Handler.js file)
			file_dialog_start_handler : fileDialogStart,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,

			// Button Settings
			button_image_url : "<%=basePath%>cfu/images/XPButtonUploadText_61x22.png",
			button_placeholder_id : "spanButtonPlaceholder1",
			button_width: 61,
			button_height: 22,
			
			// Flash Settings
			flash_url : "<%=basePath%>cfu/js/swfupload.swf",
			

			custom_settings : {
				progressTarget : "fsUploadProgress1",
				cancelButtonId : "btnCancel1"
			},
			
			// Debug Settings
			debug: false
		});
     };
	</script>
	<style type="text/css">
		.toolbar{
			height: 30px;
			line-height: 30px;
			border-top: 1px solid #dedede;
			border-bottom: 1px solid #dedede;
			background: #BACCE6;
			overflow: hidden;
		}
		
		.toolbar div{
			float: left;
			text-align: c
		}
		
		.toolbar div object{
			margin-top: 4px;
		}
		
		.toolbar .separator{
			width: 1px;
			height: 20px;
			border-left: 1px solid gray;
			display: block;
			float: left;
			margin: 5px 5px 5px 5px;
		}
		
		.toolbar .toolbar-right{
			float: right;
		}
		
		.navigat{
			height: 30px;
			line-height: 30px;
			border-bottom: 1px solid gray;
			overflow: hidden;
		}
		
		.navigat .parentDir{
			float: left;
			padding-left: 5px;
		}
		
		.navigat .dirPath{
			float: left;
			max-width: 600px;
			overflow: hidden;   
			white-space: nowrap;     
			-o-text-overflow: ellipsis;     
			text-overflow: ellipsis;
		}
		
		.navigat .loadnum{
			float: right;
		}
	</style>
  </head>
  
  <body>
  	  <div id="cmupload">
  	  	  <div class="mask" id="mask"></div>
  	  	  <div class="prograss-bar" id="fsUploadProgress1"></div>
  	  	  <div class="file-list-title">
  	  	  	<div class="choosefile">
  	  	  		选择文件
  	  	  	</div>
  	  	  	<div class="close">X</div>
  	  	  </div>
  	  	  <div class="toolbar">
  	  	  	  <div>
  	  	  	  	  上传到:
  	  	  	  	  <select name="">
  	  	  	  	  	<option value="1">研究性学习</option>
  	  	  	  	  	<option value="2">教师能力培训</option>
  	  	  	  	  </select>
  	  	  	  </div>
  	  	  	  <span class="separator"></span>
  	  	  	  <div>
  	  	  	  	  <form action="<%=basePath%>ts/upload.action" method="post" type="multipart/form-data" id="uploadForm">
					<span id="spanButtonPlaceholder1"></span>
					<input id="btnCancel1" type="button" value="Cancel Uploads" onclick="cancelQueue(upload1);" disabled="disabled" style="margin-left: 2px; height: 22px; font-size: 8pt; display: none;" />
            	  </form>
  	  	  	  </div>
  	  	  	  <span class="separator"></span>
  	  	  	  <div>
  	  	  	  	  <a href="#">新建文件夹</a>
  	  	  	  </div>
  	  	  	  <div class="toolbar-right">
  	  	  	  	  <a href="#">盒子</a>
  	  	  	  	  <a href="#">列表</a>
  	  	  	  </div>
  	  	  </div>
  	  	  <div class="navigat">
		   		<div class="parentDir" style="">
		   			<a href="#">返回上一级</a>|
		   		</div>
		   		<div id="dirPath" class="dirPath">
		   			<a href="<%=basePath%>ts/personalres!list.action">全部文件</a>
		   			<span class="separator">》</span>
		   			<span class="last">新建文件夹</span>
		   		</div>
		   		<div class="loadnum">已加载<span>20</span>个</div>
		   	</div>
  	  	  <div class="file-list-box">
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  		  <div class="filebox">
  	  		  	  <div class="fileCheck">
 	  		  	  	<input type="checkbox" />
  	  		  	  </div>
 	  		  	  <div class="fileImage">
 	  		  	  	<img alt="" src="cfu/images/folder-orange.png">
  	  		  	  </div>
  	  		  	  <div class="fileName" title="">
  	  		  	  	深入浅出EXTJS第2版光盘文件
  	  		  	  	深入浅出EXTJS第2版光盘文件深入浅出EXTJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件TJS第2版光盘文件件深入浅出EXTJS第2版光盘文件
  	  		  	  </div>
  	  		  	  <div class="fileSize">
  	  		  	  	120M
  	  		  	  </div>
  	  		  </div>
  	  	  </div>
  	  </div>
  </body>
</html>
