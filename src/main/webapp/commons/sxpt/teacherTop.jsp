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
      <li><a href="${ctx}/course/myCourse!teacherIndex.do?query.role=${role}&query.courseId=${courseId }&query.classId=${classId }&query.termId=${termId }"  id="index" class="domw">备课首页</a></li>
      <li><a href="${ctx}/course/myCourse!homeWorkIndex.do?query.role=${role}&query.courseId=${courseId }&query.classId=${classId }&query.termId=${termId }"  id="homeworkJudge">作业判定</a></li>
      <li><a href="${ctx}/course/myCourse!teachingScoreIndex.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}"  id="score">学员总评</a></li>
      <c:if test="${query.role<2}">
      <li><a href="${ctx}/course/myCourse!resPublishIndex.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}" id="resources">公共资源</a></li>
      </c:if>
      <c:if test="${query.role>1&&query.role<5}">
      <li><a href="${ctx}/course/myCourse!attendanceIndex.do?query.termId=${termId}&query.classId=${classId}&query.courseId=${courseId}&query.role=${role}"  id="kaoqin">考勤管理</a></li>
      </c:if>
      <li><a href="${ctx }/course/myCourse!list.do"  id="jiaoWu" >我的课程</a></li>
    </ul>
  </div>
</div>
<div id="mycourseHeader">当前课程：${term.classBigclass.name} &gt;&gt; ${term.name}期    </div>
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

function jump(action,id){
	/*
	$("#topUl").find("a").each(function(){
		if(this.id==id){
			this.className="domw";
		}else{
			$(this).removeClass("domw");
		}
	});
	*/
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

$("#topUl").find("a").each(function(){
	$(this).click(function(){
		$("#topUl").find("a").removeClass("domw");
		$(this).addClass("domw");
	});
});

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
});
function chooseFlag(){
	var flag = "${courseFlag}";
	$("#topUl").find("a").each(function(){
		if(this.id==flag){
			this.className="domw";
		}else{
			$(this).removeClass("domw");
		}
	});
}*/
//chooseFlag();
</script>
<div id="cmdialog" style="display: none;"></div>