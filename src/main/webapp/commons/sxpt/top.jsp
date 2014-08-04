<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
$(function(){
	var flag = "${flag}";
//	$("#"+flag).attr("className","domw");
	$("#topUl").find("a").each(function(){
		if(this.id==flag){
			this.className="domw";
		}else{
			$(this).removeClass("domw");
		}
	});
});

function goLogout()
{
	if(confirm("是否退出当前系统?"))
	{
		parent.window.location.href="/logout.jsp";
	}
}

</script>
<script type="text/javascript">
	function message(){	
		$.ajax({
			type:'POST',
			url:'${ctx}/message/ReceiveMessage!findAllMessageByUserId.do',
			success:function(msg){
				if(msg=="100"){
					clearInterval(timer1);//取消查询
				}else{
					document.getElementById("message").innerHTML=msg;
				}
			}
		});
	}
	var timer1 = setInterval("message()",5000) //5秒查一次

	
	
	
	window.onload=function(){ 
		message();
		$.ajax({
			type:'POST',
			url:'${ctx}/teacher/Teacher!img.do',
			success:function(msg){
				if(msg==0){
					document.getElementById("img").src="${ctx}/${sxpt }/images/tx.jpg";
				}else{
					document.getElementById("img").src="${ctx}/upload/teacher/"+msg;
				}
			
			}
		})
	}
</script>
<!-- Top -->
<div id="header">
  <div class="topbox">
      <div class="subnav" id="s">
      <!-- <a href="${ctx}/j_spring_security_logout" class="tc"><img src="${ctx}/${sxpt }/images/icon_next.png" width="12" height="12" align="absmiddle" /> 退出登录</a></p> -->
        <p class="left22"><strong><a href="${ctx}/message/Message!index.do"  target="mainFrame"><img src="${ctx}/${sxpt }/images/icon_xx.png" width="16" height="16" align="absmiddle" /> 消息中心(<span class="red" id="message"></span>)</a></strong> <a href="${ctx }/tools/Tools!viewList.do" target="mainFrame"><img src="${ctx}/${sxpt }/images/icon_b.png" width="12" height="12" align="absmiddle" /> 案头支持</a></p>
        <p class="right22"><a target="_blank" href="${ctx}/index/Index!sysIndex.do"><img src="${ctx}/${sxpt }/images/icon_sz.png" width="12" height="12" align="absmiddle" /><c:if test="${ }"></c:if> 系统管理</a> <a href="javascript:goLogout()" class="tc"><img src="${ctx}/${sxpt }/images/icon_next.png" width="12" height="12" align="absmiddle" /> 退出登录</a></p>
      </div>
      <div class="logo"><img src="${ctx}/${sxpt }/images/logo.png" width="490" height="80" /></div>
      <div class="grxx">
        <table width="280"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="72" height="66" rowspan="2"><img id="img"  width="58" height="58" class="tx" /></td>
            <td width="208"><img src="${ctx}/${sxpt }/images/icon_hy.png" width="13" height="13" align="absmiddle" /> <strong>${Login_Info.userName }</strong>&nbsp;<strong>继教号：${Login_Info.userNum }</strong>&nbsp;</td>
          </tr>
          <tr>
           <td>&nbsp;单位 ：${Login_Info.deptNames }</td>
          </tr>
        </table>
      </div>
  </div>
  <div class="nav">
    <ul id="topUl">
    	<!-- 
    	2、	导航栏目设计为：首页，资源中心，教师社区，教师培训，继教管理，教学教务，返回主页（点击此导航链接到朝阳教师研修网门户），详见设计图；
    	3、	左侧导航改为三大类，社区活动（我的任务）：我的课程、我的工作；我的活动：成长档案、案头支持、我的活动圈、我的资源（说明：我的资源就是个人图书馆）；内部办公：公文管理、场地管理、工资表管理、财产管理、周报月报、工作记录、问卷调查。
    	 -->
      <li><a href="${ctx }/index/Index!index.do"   id="index" class="domw">首  页</a></li>
      <li><a href="${ctx }/course/myCourse!list.do"  id="courseIndex">我的课程</a></li>
      <li><a href="${ctx }/index/Index!ziYuan.do"  id="ziYuan" >资源中心</a></li>
      <li><a href="${ctx }/group/Group!group.do" id="banGong" >教师社区</a></li> 
      <li><a href="${ctx }/index/Index!peiXun.do"  id="peiXun" >教师培训</a></li>
      <li><a href="${ctx }/index/Index!jiJiao.do"  id="jiJiao" >继教管理</a></li>
      <li><a href="${ctx }/index/Index!jiaoWu.do"  id="jiaoWu" >教学教务</a></li>
      
      <!-- 
      <li><a href="${ctx }/index/Index!xieZuo.do" id="xieZuo" >协作活动</a></li>
      <li><a href="${ctx }/index/Index!banGong.do" id="banGong" >内部办公</a></li>
      <li><a href="${ctx }/index/Index!tongJi.do" id="tongJi" >统计分析</a></li>
      <li><a href="${ctx }/index/Index!yanXiu.do" id="yanXiu" >研修门户</a></li>
      <li><a href="${ctx }/index/Index!xiTong.do" id="xiTong" >系统管理</a></li>
       -->
    </ul>
  </div>
</div>
<div id="cmdialog" ></div>
<!-- Top end -->
<script type="text/javascript">
function dialog(param){
	$("#cmdialog").html("");
	if ( param.useIframe ) {
		var iframe = document.createElement("iframe");
		iframe.src = param.url;
		iframe.scrolling = 'auto';
		iframe.frameborder = 'no';
		iframe.width=param.width-4;
		iframe.height=param.height-45;
		document.getElementById("cmdialog").appendChild(iframe);
	}
	$("#cmdialog").dialog({
        title: param.title,
        autoOpen: param.autoOpen,
        width: param.width,
        height: param.height,
        modal: param.modal,
        overlay: param.overlay
    });
}

function setDialogContent(ctt){
	$("#cmdialog").html(ctt);
}

function closeDialog(){
	$("#cmdialog").dialog("close");
}


/**$("#cmdialog").dialog({
	title: '<img src="${ctx}/images/main_ico14.gif"  width="16" height="16" align="absmiddle" /> 场地预定',
	autoOpen: true,
	width: 800,
	height: 300,
	modal: true,
	overlay: {
		opacity: 0.5,
		background: "black"
	},
	useIframe: true,
	url: 'http://www.baidu.com'
});*/
</script>