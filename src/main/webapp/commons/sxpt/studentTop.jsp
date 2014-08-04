<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<!-- Top -->
<div id="header">
  <div class="topbox">
      <div class="subnav">
        <p class="left22"><strong><a href="${ctx}/message/Message!index.do"  target="mainFrame"><img src="${ctx}/${sxpt }/images/icon_xx.png" width="16" height="16" align="absmiddle" /> 消息中心(<span class="red" id="message"></span>)</a></strong> <a href="${ctx }/tools/Tools!viewList.do" target="mainFrame"><img src="${ctx}/${sxpt }/images/icon_b.png" width="12" height="12" align="absmiddle" /> 案头支持</a></p>
        <p class="right22"><a target="_blank" href="${ctx}/index.jsp"><img src="${ctx}/${sxpt }/images/icon_sz.png" width="12" height="12" align="absmiddle" /> 系统管理</a> <a href="${ctx}/j_spring_security_logout" class="tc"><img src="${ctx}/${sxpt }/images/icon_next.png" width="12" height="12" align="absmiddle" /> 退出登录</a></p>
      </div>
      <div class="logo"><img src="${ctx}/${sxpt }/images/logo.png" width="490" height="80" /></div>
      <div class="grxx">
        <table width="280"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="72" height="66" rowspan="2"><img id="img"  width="58" height="58" class="tx" /></td>
            <td width="208"><img src="${ctx}/${sxpt }/images/icon_hy.png" width="13" height="13" align="absmiddle" /> <strong>${Login_Info.userName }</strong>&nbsp;<strong>继教号：05009951</strong>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;单位：北京教育分院朝阳分院</td>
          </tr>
        </table>
      </div>
  </div>
  <div class="nav">
    <ul id="topUl">
      <li><a href="${ctx}/course/myCourse!stuMain.do?query.role=${role}&query.courseId=${courseId }&query.classId=${classId }&query.termId=${termId }"  id="index" class="domw"  target="mainFrame">学习首页</a></li>
      <li><a href="${ctx }/lesson/Inform!informList.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}"  id="inform"  target="mainFrame">通知公告</a></li>
      <li><a href="${ctx }/jjf/JjfItem!seachScore.do?query.term=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}&query.userId=${userId}"  id="score" target="mainFrame" >分数查询</a></li>
      <li><a href="${ctx }/survey/online!list.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}" id="survey" target="mainFrame" >问卷调查</a></li> 
      <li><a href="${ctx }/bj/ClassCheckAttend!list.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}&query.userId=${userId}"  id="kaoqin" target="mainFrame" >考勤记录</a></li>
      <li><a href="${ctx }/course/CourseEvaluate!stuEvaList.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}"  id="pingce" target="mainFrame" >课程评测</a></li>
      <li><a href="${ctx}/res/publish!stuList.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}"  id="publicRes" target="mainFrame" >公共资源</a></li>
      <li><a href="${ctx }/course/myCourse!list.do"  id="jiaoWu" >我的课程</a></li>
    </ul>
  </div>
</div>

<!-- Top end -->
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

function chooseFlag(flag){
	/**var flag = "${courseFlag}";
	$("#topUl").find("a").each(function(){
		if(this.id==flag){
			this.className="domw";
		}else{
			$(this).removeClass("domw");
		}
	});*/
}

$("#topUl").find("a").each(function(){
	$(this).click(function(){
		$("#topUl").find("a").removeClass("domw");
		$(this).addClass("domw");
	});
});

function jump(action,id){
	$("#topUl").find("a").each(function(){
		if(this.id==id){
			this.className="domw";
		}else{
			$(this).removeClass("domw");
		}
	});
//	$("#"+id).attr("class","domw");
	location.href = action + "?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}";
}
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

/**
dialog({
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
<div id="cmdialog" style="display: none;"></div>